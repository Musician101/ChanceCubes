package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffectType;

public class WaitForItReward extends BaseCustomReward
{
	public WaitForItReward()
	{
		super(CCubesCore.MODID + ":Wait_For_It", -30);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		player.sendMessage("Wait for it.......");

		Scheduler.scheduleTask(new Task("Wait For It", RewardsUtil.rand.nextInt(4000) + 1000)
		{
			@Override
			public void callback()
			{
				int reward = RewardsUtil.rand.nextInt(3);
				player.sendMessage("NOW!");

				if(reward == 0)
				{
					location.getWorld().spawn(player.getLocation().clone().add(0, 1, 0), TNTPrimed.class);
				}
				else if(reward == 1)
				{
					location.getWorld().spawn(player.getLocation().clone().add(0, 1, 0), Creeper.class, creeper -> creeper.setPowered(true));
				}
				else if(reward == 2)
				{
					RewardsUtil.placeBlock(Material.BEDROCK.createBlockData(), player.getLocation());
				}
				else if(reward == 3)
				{
					RewardsUtil.placeBlock(Material.EMERALD_ORE.createBlockData(), player.getLocation());
				}
				else if(reward == 4)
				{
					location.getWorld().spawn(player.getLocation().clone().add(0, 1, 0), Zombie.class, zombie -> {
						zombie.setBaby(true);
						zombie.addPotionEffect(PotionEffectType.SPEED.createEffect(1000000, 0));
						zombie.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(1000000, 0));
					});
				}
			}
		});
	}
}
