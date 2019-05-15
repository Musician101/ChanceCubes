package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

public class NukeReward extends BaseCustomReward
{
	public NukeReward()
	{
		super(CCubesCore.MODID + ":Nuke", -75);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		RewardsUtil.sendMessageToNearPlayers(location, 32, "May death rain upon them");
		location.getWorld().spawn(location.clone().add(-6, 65, -6), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(-2, 65, -6), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(+ 2, 65, -6), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(+ 6, 65, -6), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(-6, 65, -2), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(-2, 65, -2), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(+ 2, 65, -2), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(+ 6, 65, -2), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(-6, 65, + 2), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(-2, 65, + 2), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(+ 2, 65, + 2), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(+ 6, 65, + 2), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(-6, 65, + 6), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(-2, 65, + 6), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(+ 2, 65, + 6), TNTPrimed.class);
		location.getWorld().spawn(location.clone().add(+ 6, 65, + 6), TNTPrimed.class);
	}
}
