package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Arrow;
import org.bukkit.util.Vector;

public class ThrowablesReward extends BaseCustomReward
{
	public ThrowablesReward()
	{
		super(CCubesCore.MODID + ":Throwables", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		Scheduler.scheduleTask(new Task("Throw TNT", 250, 5)
		{
			@Override
			public void callback()
			{
				World world = location.getWorld();
				int entChoice = RewardsUtil.rand.nextInt(4);
				Entity throwEnt;
				if(entChoice == 0)
				{
					throwEnt = world.spawn(location, Arrow.class);
				}
				else if(entChoice == 1)
				{
					throwEnt = world.spawn(location, LargeFireball.class, largeFireball -> largeFireball.setDirection(new Vector(0.1F * (-1 + (Math.random() * 2)), 0.1F * (-1 + (Math.random() * 2)), 0.1F * (-1 + (Math.random() * 2)))));
				}
				else if(entChoice == 2)
				{
					throwEnt = world.spawn(location, Egg.class);
				}
				else
				{
					throwEnt = world.spawn(location, TNTPrimed.class, tntPrimed -> tntPrimed.setFuseTicks(20));
				}

				throwEnt.setVelocity(new Vector(0.1F * (-1 + (Math.random() * 2)), 0.1F * (-1 + (Math.random() * 2)), 0.1F * (-1 + (Math.random() * 2))));
			}
		});
	}
}
