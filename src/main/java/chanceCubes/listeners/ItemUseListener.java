package chanceCubes.listeners;

import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.config.CCubesSettings;
import chanceCubes.items.CCubesItems;
import chanceCubes.metadata.ChanceCubeMetadataValue;
import chanceCubes.metadata.D20MetadataValue;
import chanceCubes.metadata.GiantCubeMetadataValue;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUseListener implements Listener
{
	@EventHandler
	public void onItemUse(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		ItemStack itemStack = event.getItem();
		Player player = event.getPlayer();
		if (CCubesItems.isScanner(itemStack)) {
			if (CCubesBlocks.isChanceCube(block)) {
				block.getMetadata("ChanceCubes").stream().map(ChanceCubeMetadataValue.class::cast).findFirst().ifPresent(chanceCube -> {
					chanceCube.setScanned(true);
					player.sendMessage("Chance: " + chanceCube.getChance());
					if (damageScanner(itemStack)) {
						PlayerInventory inventory = player.getInventory();
						if (event.getHand() == EquipmentSlot.HAND) {
							inventory.setItemInMainHand(null);
						}
						else
							inventory.setItemInOffHand(null);
					}
				});
				event.setCancelled(true);
			}
			else if (CCubesBlocks.isGiantCube(block)) {
				block.getMetadata("ChanceCubes").stream().map(GiantCubeMetadataValue.class::cast).findFirst().ifPresent(giantCube -> {
					player.sendMessage("Chance: -201");
					if (damageScanner(itemStack)) {
						PlayerInventory inventory = player.getInventory();
						if (event.getHand() == EquipmentSlot.HAND) {
							inventory.setItemInMainHand(null);
						}
						else
							inventory.setItemInOffHand(null);
					}
				});
				event.setCancelled(true);
			}
			else if (CCubesBlocks.isD20(block)) {
				block.getMetadata("ChanceCubes").stream().map(D20MetadataValue.class::cast).findFirst().ifPresent(d20 -> {
					d20.setScanned(true);
					player.sendMessage("Chance: " + d20.getChance());
					if (damageScanner(itemStack)) {
						PlayerInventory inventory = player.getInventory();
						if (event.getHand() == EquipmentSlot.HAND) {
							inventory.setItemInMainHand(null);
						}
						else
							inventory.setItemInOffHand(null);
					}
				});
				event.setCancelled(true);
			}
		}
	}

	private boolean damageScanner(ItemStack itemStack) {
		ItemMeta meta = itemStack.getItemMeta();
		String durability = meta.getLore().get(0);
		Pattern pattern = Pattern.compile("Durability: ([0-9]+)/([0-9]+)");
		Matcher matcher = pattern.matcher(durability);
		if (matcher.find()) {
			int damage = Integer.parseInt(matcher.group(1)) - 1;
			if (damage <= 0) {
				return false;
			}
			meta.setLore(Collections.singletonList("Durability: " + damage + "/" + CCubesSettings.pendantUses));
			return true;
		}

		return false;
	}
}
