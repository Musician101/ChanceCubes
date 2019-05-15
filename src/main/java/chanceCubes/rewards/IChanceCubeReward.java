package chanceCubes.rewards;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IChanceCubeReward {

	/**
	 * What occurs when the block is "opened"
	 * @param location
	 * @param player Player who triggered the block
	 */
	void trigger(Location location, Player player);

	/**
	 * @return How "lucky" this block is (can be negative). 0 would indicate an "average" reward. Range -100 to 100.
	 */
	int getChanceValue();

	/**
	 * Set how "lucky" this block is (can be negative). 0 would indicate an "average" reward. Range -100 to 100.
	 */
	void setChanceValue(int chance);

	/**
	 * @return Unique name for reward (suggested to pre-pend MODID).
	 */
	String getName();
}
