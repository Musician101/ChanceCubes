package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardBlockCache;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AnvilRain extends BaseCustomReward
{
	public AnvilRain()
	{
		super(CCubesCore.MODID + ":Anvil_Rain", -45);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		RewardBlockCache cache = new RewardBlockCache(location, player.getLocation());
		int x1 = RewardsUtil.rand.nextInt(9) - 4;
		int z1 = RewardsUtil.rand.nextInt(9) - 4;

		int x2 = RewardsUtil.rand.nextInt(9) - 4;
		int z2 = RewardsUtil.rand.nextInt(9) - 4;

		int x3 = RewardsUtil.rand.nextInt(9) - 4;
		int z3 = RewardsUtil.rand.nextInt(9) - 4;

		int x4 = RewardsUtil.rand.nextInt(9) - 4;
		int z4 = RewardsUtil.rand.nextInt(9) - 4;

		int x5 = RewardsUtil.rand.nextInt(9) - 4;
		int z5 = RewardsUtil.rand.nextInt(9) - 4;

		int yy;
		for(yy = 0; yy < 25; yy++)
			cache.cacheBlock(new Vector(0, yy, 0), Material.AIR.createBlockData());
		for(yy = 0; yy < 25; yy++)
			cache.cacheBlock(new Vector(x1, yy, z1), Material.AIR.createBlockData());
		for(yy = 0; yy < 25; yy++)
			cache.cacheBlock(new Vector(x2, yy, z2), Material.AIR.createBlockData());
		for(yy = 0; yy < 25; yy++)
			cache.cacheBlock(new Vector(x3, yy, z3), Material.AIR.createBlockData());
		for(yy = 0; yy < 25; yy++)
			cache.cacheBlock(new Vector(x4, yy, z4), Material.AIR.createBlockData());
		for(yy = 0; yy < 25; yy++)
			cache.cacheBlock(new Vector(x5, yy, z5), Material.AIR.createBlockData());
		for(yy = 0; yy < 25; yy++)
			cache.cacheBlock(new Vector(player.getLocation().getX() - location.getX(), yy, player.getLocation().getZ() - location.getZ()), Material.AIR.createBlockData());

		cache.cacheBlock(new Vector(0, 25, 0), Material.ANVIL.createBlockData());
		cache.cacheBlock(new Vector(x1, 25, z1), Material.ANVIL.createBlockData());
		cache.cacheBlock(new Vector(x2, 25, z2), Material.ANVIL.createBlockData());
		cache.cacheBlock(new Vector(x3, 25, z3), Material.ANVIL.createBlockData());
		cache.cacheBlock(new Vector(x4, 25, z4), Material.ANVIL.createBlockData());
		cache.cacheBlock(new Vector(x5, 25, z5), Material.ANVIL.createBlockData());
		cache.cacheBlock(new Vector(player.getLocation().getX() - location.getX(), 25, player.getLocation().getZ() - location.getZ()), Material.ANVIL.createBlockData());

		for(int xx = 0; xx < 2; xx++)
			for(int zz = -4; zz < 5; zz++)
				for(int yyy = 0; yyy < 3; yyy++)
					cache.cacheBlock(new Vector(xx == 1 ? 5 : -5, yyy, zz), Material.COBBLESTONE.createBlockData());

		for(int xx = -4; xx < 5; xx++)
			for(int zz = 0; zz < 2; zz++)
				for(int yyy = 0; yyy < 3; yyy++)
					cache.cacheBlock(new Vector(xx, yyy, zz == 1 ? 5 : -5), Material.COBBLESTONE.createBlockData());

		Scheduler.scheduleTask(new Task("Anvil_Rain_cache_Delay", 100)
		{
			@Override
			public void callback()
			{
				cache.restoreBlocks(player);
			}
		});
	}
}
