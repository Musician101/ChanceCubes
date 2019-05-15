package chanceCubes.rewards.defaultRewards;

import chanceCubes.rewards.rewardtype.IRewardType;
import java.util.Arrays;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BasicReward extends BaseCustomReward
{
	private IRewardType[] rewards;

	public BasicReward(String name, int chance, IRewardType... rewards)
	{
		super(name, chance);
		this.rewards = rewards;
	}

	@Override
	public void trigger(Location location, Player player)
	{
		if(rewards != null)
			Arrays.stream(rewards).forEach(reward -> reward.trigger(location, player));
	}
}
