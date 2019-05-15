package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SphereSnakeReward extends BaseCustomReward
{

	// @formatter:off
	private Material[] whitelist = { Material.OBSIDIAN, Material.DIRT, Material.STONE,
			Material.MELON, Material.BOOKSHELF, Material.CLAY,
			RewardsUtil.getRandomWool().getMaterial(), Material.BRICKS, Material.COBWEB, Material.GLOWSTONE,
			Material.NETHERRACK};
	// @formatter:on

	public SphereSnakeReward()
	{
		super(CCubesCore.MODID + ":Sphere_Snake", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		Material state = whitelist[RewardsUtil.rand.nextInt(whitelist.length)];
		int[] posChange = { 0, 0, 0 };
		int[] currentDir = { 0, 1, 0 };

		Scheduler.scheduleTask(new Task("Sphere_Snake_Spawn_Delay", 1000, 10)
		{
			@Override
			public void callback()
			{

			}

			public void update()
			{
				boolean valid;
				int xChange;
				int yChange;
				int zChange;
				do
				{
					xChange = RewardsUtil.rand.nextInt(3) - 1;
					yChange = RewardsUtil.rand.nextInt(3) - 1;
					zChange = RewardsUtil.rand.nextInt(3) - 1;

					int dotProduct = xChange * currentDir[0] + yChange * currentDir[1] + zChange * currentDir[2];
					double changeLength = Math.sqrt((xChange * xChange) + (yChange * yChange) + (zChange * zChange));
					double currentLength = Math.sqrt((currentDir[0] * currentDir[0]) + (currentDir[1] * currentDir[1]) + (currentDir[2] * currentDir[2]));

					double angle = Math.acos(dotProduct / (changeLength * currentLength));
					valid = angle < 1.5708;
				} while(!valid);

				currentDir[0] = xChange;
				currentDir[1] = yChange;
				currentDir[2] = zChange;
				posChange[0] += xChange;
				posChange[1] += yChange;
				posChange[2] += zChange;
				Location currentpos = location.clone().add(posChange[0], posChange[1], posChange[2]);

				for(int yy = -3; yy < 4; yy++)
				{
					for(int zz = -3; zz < 4; zz++)
					{
						for(int xx = -3; xx < 4; xx++)
						{
							Vector loc = new Vector(xx, yy, zz);
							double dist = Math.abs(loc.distance(new Vector ()));
							if(dist <= 3 && dist > 3 - 1)
							{
								currentpos.clone().add(loc).getBlock().setType(state);
							}
						}
					}
				}
			}
		});
	}
}
