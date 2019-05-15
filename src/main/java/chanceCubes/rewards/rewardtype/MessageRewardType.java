package chanceCubes.rewards.rewardtype;

import chanceCubes.rewards.rewardparts.MessagePart;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MessageRewardType extends BaseRewardType<MessagePart>
{
	public MessageRewardType(MessagePart... messages)
	{
		super(messages);
	}

	@Override
	public void trigger(final MessagePart message, Location location, Player player)
	{
		Scheduler.scheduleTask(new Task("Message Reward Delay", message.getDelay())
		{
			@Override
			public void callback()
			{
				location.getWorld().getPlayers().forEach(p -> {
					if (p.getUniqueId().equals(player.getUniqueId())) {
						p.sendMessage(message.getMessage());
					}
					else {
						double dist = location.distance(p.getLocation());
						if (dist <= message.getRange() || message.isServerWide()) {
							p.sendMessage(message.getMessage());
						}
					}
				});
			}
		});
	}
}
