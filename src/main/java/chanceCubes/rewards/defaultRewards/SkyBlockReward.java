package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SkyBlockReward extends BaseCustomReward
{

	// @formatter:off

	ItemStack[] chestStuff = {
		new ItemStack(Material.STRING, 12), new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.BONE), new ItemStack(Material.SUGAR_CANE),
		new ItemStack(Material.RED_MUSHROOM), new ItemStack(Material.ICE, 2), new ItemStack(Material.PUMPKIN_SEEDS), new ItemStack(Material.OAK_SAPLING),
		new ItemStack(Material.BROWN_MUSHROOM), new ItemStack(Material.MELON_SLICE), new ItemStack(Material.CACTUS), new ItemStack(Material.OAK_LOG, 6)
		};

	// @formatter:on

	public SkyBlockReward()
	{
		super(CCubesCore.MODID + ":Sky_Block", 10);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		int skyBlockHeight = location.getWorld().getMaxHeight() - 16;
		Material b = Material.DIRT;
		Location skyBlockPos = new Location(location.getWorld(), location.getX(), skyBlockHeight, location.getZ());
		for(int i = 0; i < 3; i++)
		{
			if(i == 2)
				b = Material.GRASS;
			for(int c = 0; c < 3; c++)
			{
				int xOffset = c == 0 ? -1 : 2;
				int zOffset = c == 2 ? 2 : -1;
				for(int xx = 0; xx < 3; xx++)
				{
					for(int zz = 0; zz < 3; zz++)
					{
						location.clone().add(xOffset + xx, i, zOffset + zz).getBlock().setType(b);
						// RewardsUtil.placeBlock(b.getDefaultState(), world, skyblockPos.add(xOffset + xx, i, zOffset + zz));
					}
				}
			}
		}

		RewardsUtil.placeBlock(Material.BEDROCK.createBlockData(), location.clone().add(0, 1, 0));
		location.getWorld().generateTree(location, TreeType.TREE);
		Chest chest = (Chest) Material.CHEST.createBlockData();
		chest.setFacing(BlockFace.WEST);
		RewardsUtil.placeBlock(chest, skyBlockPos.clone().add(-1, 3, 0));
		org.bukkit.block.Chest chestTE = (org.bukkit.block.Chest) skyBlockPos.clone().add(-1, 3, 0).getBlock().getState();
		for(int i = 0; i < chestStuff.length; i++)
		{
			int slot = ((i < 4 ? 0 : i < 8 ? 1 : 2) * 9) + i % 4;
			chestTE.getInventory().setItem(slot, chestStuff[i].clone());
		}

		player.teleport(new Location(location.getWorld(), location.getX(), skyBlockHeight + 3, location.getZ()));
	}
}
