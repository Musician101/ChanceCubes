package chanceCubes.rewards.rewardtype;

import chanceCubes.rewards.rewardparts.ExperiencePart;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;

public class ExperienceRewardType extends BaseRewardType<ExperiencePart>
{

	public ExperienceRewardType(ExperiencePart... levels)
	{
		super(levels);
	}

	@Override
	public void trigger(final ExperiencePart levels, Location location, Player player)
	{
		Scheduler.scheduleTask(new Task("Expirence Reward Delay", levels.getDelay())
		{
			@Override
			public void callback()
			{
				for(int i = 0; i < levels.getNumberofOrbs(); i++)
					location.getWorld().spawn(location.clone().add(0, 1, 0), ExperienceOrb.class, xp -> xp.setExperience(levels.getAmount() / levels.getNumberofOrbs()));
			}
		});
	}
}
