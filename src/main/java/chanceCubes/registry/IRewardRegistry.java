package chanceCubes.registry;

import chanceCubes.rewards.IChanceCubeReward;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IRewardRegistry
{
	/**
	 * Registers the given reward as a possible outcome
	 *
	 * @param reward
	 *            to register
	 */
	void registerReward(IChanceCubeReward reward);

	/**
	 * Unregisters a reward with the given name
	 *
	 * @param name of the reward to remove
	 * @return true is a reward was successfully removed, false if a reward was not removed
	 */
	boolean unregisterReward(String name);

	@Nullable
	IChanceCubeReward getRewardByName(String name);

	/**
	 * Triggers a random reward in the given world at the given location
	 *
	 * @param location The location of the block
	 * @param player The player receiving the reward
	 * @param chance The chance of the block
	 */
	void triggerRandomReward(Location location, Player player, int chance);
}
