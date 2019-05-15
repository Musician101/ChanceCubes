package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkShowReward extends BaseCustomReward
{
	public FireworkShowReward()
	{
		super(CCubesCore.MODID + ":Firework_Show", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		RewardsUtil.executeCommand(location.getWorld(), player, "/time set 15000");
		stage1(location);
	}

	public void stage1(Location location)
	{
		Scheduler.scheduleTask(new Task("Firework_Show_Task_Stage_1", 200, 5)
		{
			double angle = 0;

			@Override
			public void callback()
			{
				stage2(location);
			}

			@Override
			public void update()
			{
				angle += 0.5;
				spawnFirework(location.clone().add(angle / 3f * Math.cos(angle), 0, angle / 3f * Math.sin(angle)));
				spawnFirework(location.clone().add(angle / 3f * Math.cos(angle + Math.PI), 0, angle / 3f * Math.cos(angle + Math.PI)));
			}

		});
	}

	public void stage2(Location location)
	{
		Scheduler.scheduleTask(new Task("Firework_Show_Task_Stage_2", 200, 5)
		{
			double tick = 0;

			@Override
			public void callback()
			{
				stage3(location);
			}

			@Override
			public void update()
			{
				tick += 0.5;
				spawnFirework(location.clone().add(tick - 20, 0, 1));
				spawnFirework(location.clone().add(20 - tick, 0, -1));
			}

		});
	}

	public void stage3(Location location)
	{
		Scheduler.scheduleTask(new Task("Firework_Show_Task_Stage_2", 200, 3)
		{

			@Override
			public void callback()
			{

			}

			@Override
			public void update()
			{
				spawnFirework(location.clone().add(RewardsUtil.rand.nextInt(10) - 5, 0, RewardsUtil.rand.nextInt(10) - 5));
			}

		});
	}

	public void spawnFirework(Location location)
	{
		location.getWorld().spawn(location, Firework.class, firework -> firework.setFireworkMeta((FireworkMeta) RewardsUtil.getRandomFirework().getItemMeta()));
	}
}
