package chanceCubes.rewards.rewardtype;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class BaseRewardType<T> implements IRewardType
{
	protected T[] rewards;

	@SafeVarargs
	protected BaseRewardType(T... rewards)
	{
		this.rewards = rewards;
	}

	@Override
	public void trigger(Location location, Player player)
	{
		for (T t : rewards)
			trigger(t, location, player);
	}

	protected abstract void trigger(T obj, Location location, Player player);

	public T[] getRewardParts()
	{
		return this.rewards.clone();
	}
}
