package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class BeaconArenaReward extends BaseCustomReward
{
	// @formatter:off
	private Material[] whitelist = { Material.OBSIDIAN, Material.DIRT, Material.STONE,
			Material.MELON, Material.BOOKSHELF, Material.CLAY,
			Material.RED_WOOL,
			Material.BRICKS, Material.COBWEB, Material.GLOWSTONE,
			Material.NETHERRACK};
	// @formatter:on

	public BeaconArenaReward()
	{
		super(CCubesCore.MODID + ":Beacon_Arena", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		List<OffsetBlock> blocks = new ArrayList<>();
		spawnWall(location.getWorld(), blocks);
		spawnSmallBeacon(blocks, new Location(location.getWorld(), 17, 0, 17), Material.GOLD_BLOCK);
		spawnSmallBeacon(blocks, new Location(location.getWorld(), -17, 0, 17), Material.DIAMOND_BLOCK);
		spawnSmallBeacon(blocks, new Location(location.getWorld(), -17, 0, -17), Material.EMERALD_BLOCK);
		spawnSmallBeacon(blocks, new Location(location.getWorld(), 17, 0, -17), Material.IRON_BLOCK);
		spawnBigBeacon(blocks);
		editFloor(location.getWorld(), blocks);

		for(OffsetBlock b : blocks)
			b.spawnInWorld(location.clone().subtract(0, 1, 0));
	}

	public void spawnSmallBeacon(List<OffsetBlock> blocks, Location location, Material b)
	{
		int delay = 0;
		for(int x = -1; x < 2; x++)
		{
			for(int z = -1; z < 2; z++)
			{
				blocks.add(new OffsetBlock(location.getBlockX() + x, location.getBlockY(), location.getBlockZ() + z, b, false, delay));
				delay++;
			}
		}
		blocks.add(new OffsetBlock(location.getBlockX(), location.getBlockY() + 1, location.getBlockZ(), Material.BEACON, false, delay).setCausesBlockUpdate(true));
	}

	public void spawnBigBeacon(List<OffsetBlock> blocks)
	{
		int delay = 0;
		for(int y = 0; y < 2; y++)
		{
			for(int x = -2; x < 3; x++)
			{
				for(int z = -2; z < 3; z++)
				{
					if(y != 1 || (x > -2 && x < 2 && z > -2 && z < 2))
					{
						blocks.add(new OffsetBlock(x, y, z, Material.IRON_BLOCK, false, delay));
						delay++;
					}
				}
			}
		}
		blocks.add(new OffsetBlock(0, 2, 0, Material.BEACON, false, delay).setCausesBlockUpdate(true));
	}

	public void spawnWall(World world, List<OffsetBlock> blocks)
	{
		List<Location> usedPositions = new ArrayList<>();
		Location temp;
		for(int degree = 0; degree < 360; degree++)
		{
			double arcVal = Math.toRadians(degree);
			int x = (int) (28d * Math.cos(arcVal));
			int z = (int) (28d * Math.sin(arcVal));
			temp = new Location(world, x, 0, z);
			if(!usedPositions.contains(temp))
			{
				usedPositions.add(temp);
			}
		}

		int delay = 0;
		for(Location pos : usedPositions)
		{
			blocks.add(new OffsetBlock(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ(), Material.GLASS, false, delay));
			blocks.add(new OffsetBlock(pos.getBlockX(), pos.getBlockY() + 1, pos.getBlockZ(), Material.GLASS, false, delay + 1));
			blocks.add(new OffsetBlock(pos.getBlockX(), pos.getBlockY() + 2, pos.getBlockZ(), Material.GLASS, false, delay + 2));
			delay++;
		}
	}

	public void editFloor(World world, List<OffsetBlock> blocks)
	{
		int delay = 0;
		List<Location> usedPositions = new ArrayList<>();
		Location temp;
		for(int radius = 0; radius < 28; radius++)
		{
			for(int degree = 0; degree < 360; degree++)
			{
				double arcVal = Math.toRadians(degree);
				int x = (int) (radius * Math.cos(arcVal));
				int z = (int) (radius * Math.sin(arcVal));
				temp = new Location(world, x, 0, z);
				if(!usedPositions.contains(temp))
				{
					usedPositions.add(temp);
				}
			}
		}
		for(Location pos : usedPositions)
		{
			Material material = whitelist[RewardsUtil.rand.nextInt(whitelist.length)];
			blocks.add(new OffsetBlock(pos.getBlockX(), -1, pos.getBlockZ(), material, false, delay / 8));
			delay++;
		}
	}
}
