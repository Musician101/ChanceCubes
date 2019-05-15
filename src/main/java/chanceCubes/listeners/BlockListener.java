package chanceCubes.listeners;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.metadata.ChanceCubeMetadataValue;
import chanceCubes.metadata.D20MetadataValue;
import chanceCubes.metadata.GiantCubeMetadataValue;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.registry.GiantCubeRegistry;
import chanceCubes.util.CubeStorage.Type;
import chanceCubes.util.GiantCubeUtil;
import java.util.stream.Stream;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

public class BlockListener implements Listener
{
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{
		Block block = event.getBlock();
		Location location = block.getLocation();
		Player player = event.getPlayer();
		ItemStack itemStack = player.getInventory().getItemInMainHand();
		Stream<MetadataValue> stream = block.getMetadata("ChanceCubes").stream().filter(metadata -> metadata.getOwningPlugin() instanceof CCubesCore);
		if(CCubesBlocks.isChanceCube(block))
		{
			stream.map(ChanceCubeMetadataValue.class::cast).findFirst().ifPresent(metadata -> {
				if(itemStack.getEnchantmentLevel(Enchantment.SILK_TOUCH) > 0)
				{
					player.getWorld().dropItem(player.getLocation(), CCubesBlocks.getChanceCube(metadata.getChance(), 1));
				}
				else
				{
					ChanceCubeRegistry.INSTANCE.triggerRandomReward(location, player, metadata.getChance());
				}

				block.removeMetadata("ChanceCubes", CCubesCore.getInstance());
				block.setType(Material.AIR);
				CCubesCore.getInstance().getCubeStorage().remove(block);
				event.setCancelled(true);
			});
		}
		else if(CCubesBlocks.isGiantCube(block))
		{
			stream.map(GiantCubeMetadataValue.class::cast).findFirst().ifPresent(metadata -> {
				if(itemStack.getEnchantmentLevel(Enchantment.SILK_TOUCH) > 0)
				{
					player.getWorld().dropItem(player.getLocation(), CCubesBlocks.getCompactGiantCube(1));
					GiantCubeUtil.removeStructure(location);
					return;
				}
				else
				{
					if(!metadata.hasMaster() || !metadata.checkForMaster())
					{
						block.setType(Material.AIR);
						block.removeMetadata("ChanceCubes", CCubesCore.getInstance());
					}

					GiantCubeRegistry.INSTANCE.triggerRandomReward(metadata.getMasterLocation(), player, 0);
					GiantCubeUtil.removeStructure(metadata.getMasterLocation());
				}

				CCubesCore.getInstance().getCubeStorage().remove(block);
				event.setCancelled(true);
			});
		}
		else if(CCubesBlocks.isD20(block))
		{
			stream.map(D20MetadataValue.class::cast).findFirst().ifPresent(metadata -> {
				if(itemStack.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 0)
				{
					player.getWorld().dropItem(player.getLocation(), CCubesBlocks.getD20(metadata.getChance(), 1));
				}
				else
				{
					ChanceCubeRegistry.INSTANCE.triggerRandomReward(location, player, metadata.getChance());
				}

				CCubesCore.getInstance().getCubeStorage().remove(block);
				event.setCancelled(true);
			});
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{
		Block block = event.getBlockPlaced();
		if(CCubesBlocks.isChanceCube(event.getItemInHand()))
		{
			block.setMetadata("ChanceCubes", new ChanceCubeMetadataValue(event.getItemInHand().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL)));
			CCubesCore.getInstance().getCubeStorage().add(block, Type.NORMAL);
		}
		else if(CCubesBlocks.isGiantCube(event.getItemInHand()))
		{
			block.setMetadata("ChanceCubes", new GiantCubeMetadataValue());
			CCubesCore.getInstance().getCubeStorage().add(block, Type.GIANT);
		}
		else if (CCubesBlocks.isD20(event.getItemInHand())) {
			block.setMetadata("ChanceCubes", new D20MetadataValue());
			CCubesCore.getInstance().getCubeStorage().add(block, Type.D20);
		}
	}
}
