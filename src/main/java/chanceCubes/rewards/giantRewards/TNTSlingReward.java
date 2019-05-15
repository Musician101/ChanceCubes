package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

public class TNTSlingReward extends BaseCustomReward
{
	public TNTSlingReward()
	{
		super(CCubesCore.MODID + ":TNT_Throw", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		Scheduler.scheduleTask(new Task("Throw TNT", 250, 10)
		{
			private TNTPrimed tnt;

			@Override
			public void callback()
			{
				for(double xx = 1; xx > -1; xx -= 0.25)
				{
					for(double zz = 1; zz > -1; zz -= 0.25)
					{
						tnt = location.getWorld().spawn(location.clone().add(0, 1, 0), TNTPrimed.class, tntPrimed -> tntPrimed.setFuseTicks(60));
						tnt.setVelocity(new Vector(xx, Math.random(), zz));
					}
				}
			}

			@Override
			public void update()
			{
				tnt = location.getWorld().spawn(location.clone().add(0, 1, 0), TNTPrimed.class, tntPrimed -> tntPrimed.setFuseTicks(60));
				tnt.setVelocity(new Vector(-1 + Math.random() * 2, Math.random(), -1 + Math.random() * 2));
			}
		});
	}
}
