package chanceCubes.rewards.biodomeGen;

import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.PolarBear;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.util.Vector;

public class SnowGlobeBiome implements IBioDomeBiome
{
	private Random rand = new Random();

	@Override
	public void spawnEntities(Location location)
	{
		for(int i = 0; i < rand.nextInt(10) + 5; i++)
		{
			int ri = rand.nextInt(2);

			if(ri == 0)
			{
				location.getWorld().spawn(location.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), Snowman.class);
			}
			else if(ri == 1)
			{
				location.getWorld().spawn(location.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), PolarBear.class);
			}
		}

		Scheduler.scheduleTask(new Task("SnowGlobe Snow", 20)
		{
			@Override
			public void callback()
			{
				for(int i = 0; i < 100; i++)
					location.getWorld().spawn(location.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), Snowball.class, snowball -> snowball.setVelocity(new Vector(-1 + Math.random() * 2, 0.8, -1 + Math.random() * 2)));
			}
		});
	}

	@Override
	public Material getFloorBlock()
	{
		return Material.SNOW_BLOCK;
	}

	@Override
	public void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay)
	{
		if(y != 0)
			return;
		if(dist < 0 && rand.nextInt(5) == 0)
		{
			OffsetBlock osb = new OffsetBlock(x, y + 1, z, Material.SNOW, false, (delay / BioDomeReward.delayShorten));
			blocks.add(osb);
		}
	}
}
