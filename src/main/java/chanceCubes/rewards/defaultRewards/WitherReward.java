package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;

public class WitherReward extends BaseCustomReward
{
	public WitherReward()
	{
		super(CCubesCore.MODID + ":Wither", -100);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		Wither wither = location.getWorld().spawn(location, Wither.class, w -> {
			if(RewardsUtil.rand.nextBoolean())
			{
				w.setCustomName("Kiwi");
			}
			else
			{
				w.setCustomName("Kehaan");
			}
		});

		RewardsUtil.sendMessageToNearPlayers(location, 32, "\"You've got to ask yourself one question: 'Do I feel lucky?' Well, do ya, punk?\"");
		Scheduler.scheduleTask(new Task("Wither Reward", 180)
		{
			@Override
			public void callback()
			{
				if(RewardsUtil.rand.nextInt(10) != 1)
					wither.remove();
			}
		});
	}
}
