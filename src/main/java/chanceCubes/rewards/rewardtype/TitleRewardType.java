package chanceCubes.rewards.rewardtype;

import chanceCubes.rewards.rewardparts.TitlePart;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TitleRewardType extends BaseRewardType<TitlePart>
{
	public TitleRewardType(TitlePart... effects)
	{
		super(effects);
	}

	@Override
	public void trigger(TitlePart part, Location location, Player player)
	{
		Scheduler.scheduleTask(new Task("Title Delay", part.getDelay())
		{
			@Override
			public void callback()
			{
				location.getWorld().getPlayers().forEach(p -> {
					if (p.getUniqueId().equals(player.getUniqueId())) {
						p.sendTitle(part.getType().equalsIgnoreCase("title") ? part.getMessage() : null, part.getType().equalsIgnoreCase("subtitle") ? part.getMessage() : null, part.getFadeInTime(), part.getDisplayTime(), part.getFadeOutTime());
					}
					else {
						double dist = location.distance(p.getLocation());
						if (dist <= part.getRange() || part.isServerWide()) {
							p.sendTitle(part.getType().equalsIgnoreCase("title") ? part.getMessage() : null, part.getType().equalsIgnoreCase("subtitle") ? part.getMessage() : null, part.getFadeInTime(), part.getDisplayTime(), part.getFadeOutTime());
						}
					}
				});
			}
		});
	}
}
