package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ThrownInAirReward extends BaseCustomReward
{
	public ThrownInAirReward()
	{
		super(CCubesCore.MODID + ":Thrown_In_Air", -35);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		int px = (int) Math.floor(player.getLocation().getBlockX());
		int py = (int) Math.floor(player.getLocation().getBlockY()) + 1;
		int pz = (int) Math.floor(player.getLocation().getBlockZ());

		for(int y = 0; y < 40; y++)
			for(int x = -1; x < 2; x++)
				for(int z = -1; z < 2; z++)
					RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.add(px + x, py + y, pz + z));

		Scheduler.scheduleTask(new Task("Thrown_In_Air_Reward", 5)
		{
			@Override
			public void callback()
			{
				player.setVelocity(new Vector(0, 20, 0));
			}
		});
	}
}
