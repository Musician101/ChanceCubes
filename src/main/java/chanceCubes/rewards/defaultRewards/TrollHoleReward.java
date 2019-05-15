package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardBlockCache;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TrollHoleReward extends BaseCustomReward
{
	public TrollHoleReward()
	{
		super(CCubesCore.MODID + ":Troll_Hole", -20);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		final Location worldPos = player.getLocation().clone().add(0, -1, 0);
		final RewardBlockCache cache = new RewardBlockCache(worldPos, player.getLocation());

		for(int y = 0; y > -75; y--)
			for(int x = -2; x < 3; x++)
				for(int z = -2; z < 3; z++)
					cache.cacheBlock(new Vector(x, y, z), Material.AIR.createBlockData());

		Scheduler.scheduleTask(new Task("TrollHole", 35)
		{
			@Override
			public void callback()
			{
				cache.restoreBlocks(player);
			}

		});

	}
}
