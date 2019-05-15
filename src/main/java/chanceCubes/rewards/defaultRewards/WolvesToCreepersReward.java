package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class WolvesToCreepersReward extends BaseCustomReward
{
	public WolvesToCreepersReward()
	{
		super(CCubesCore.MODID + ":Wolves_To_Creepers", -20);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		final List<Wolf> wolves = new ArrayList<>();
		for(int i = 0; i < 10; i++)
		{
			for(int yy = 0; yy < 4; yy++)
				for(int xx = -1; xx < 2; xx++)
					for(int zz = -1; zz < 2; zz++)
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(xx, yy, zz));

			wolves.add(location.getWorld().spawn(location, Wolf.class, wolf -> {
				wolf.setTamed(true);
				wolf.setOwner(player);
				wolf.setCustomName("Kehaan");
				wolf.setCustomNameVisible(true);
			}));
		}

		RewardsUtil.sendMessageToNearPlayers(location, 32, "Do they look weird to you?");

		Scheduler.scheduleTask(new Task("Mob_Switch", 200)
		{
			@Override
			public void callback()
			{
				for(Wolf wolf : wolves)
				{
					wolf.remove();
					location.getWorld().spawn(wolf.getLocation(), Creeper.class, creeper -> {
						creeper.setCustomName("Jacky");
						creeper.setCustomNameVisible(true);
					});
				}
			}
		});
	}
}
