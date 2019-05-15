package chanceCubes.rewards.rewardtype;

import chanceCubes.rewards.rewardparts.SoundPart;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class SoundRewardType extends BaseRewardType<SoundPart>
{
	public SoundRewardType(SoundPart... sounds)
	{
		super(sounds);
	}

	@Override
	public void trigger(final SoundPart sound, Location location, Player player)
	{
		Scheduler.scheduleTask(new Task("Sound Reward Delay", sound.getDelay())
		{
			@Override
			public void callback()
			{
				if(sound.playAtPlayersLocation())
					player.getWorld().playSound(player.getLocation(), sound.getSound(), SoundCategory.BLOCKS, sound.getVolume(), sound.getPitch());
				else
					location.getWorld().playSound(location, sound.getSound(), SoundCategory.BLOCKS, sound.getVolume(), sound.getPitch());
			}
		});
	}
}
