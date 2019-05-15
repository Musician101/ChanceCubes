package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class EnderCrystalTimerReward extends BaseCustomReward
{
	public EnderCrystalTimerReward()
	{
		super(CCubesCore.MODID + ":Ender_Crystal_Timer", -90);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		for(int i = 30; i > 0; i--)
			RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(0, i, 0));

		location.getWorld().spawn(location, EnderCrystal.class);
		location.getWorld().spawnArrow(location.clone().add(0, 29, 0), new Vector(0, -1, 0), 1, 0);
	}
}
