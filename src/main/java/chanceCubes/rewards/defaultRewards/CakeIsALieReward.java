package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.type.Cake;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class CakeIsALieReward extends BaseCustomReward
{

	public CakeIsALieReward()
	{
		super(CCubesCore.MODID + ":Cake", 20);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		RewardsUtil.sendMessageToNearPlayers(location, 32, "But is it a lie?");

		RewardsUtil.placeBlock(Material.CAKE.createBlockData(), location);

		if(RewardsUtil.rand.nextInt(3) == 1)
		{
			Scheduler.scheduleTask(new Task("Cake_Is_A_Lie", 6000, 20)
			{
				@Override
				public void callback()
				{
					location.getBlock().setType(Material.AIR);
				}

				@Override
				public void update()
				{
					if (location.getBlock().getType() != Material.CAKE)
					{
						Scheduler.removeTask(this);
					}
					else if(((Cake) location.getBlock().getBlockData()).getBites() > 0)
					{
						location.getBlock().setType(Material.AIR);
						RewardsUtil.sendMessageToNearPlayers(location, 32, "It's a lie!!!");
						location.getWorld().spawn(location, Creeper.class, creeper -> {
							creeper.setPowered(RewardsUtil.rand.nextInt(10) == 1);
							creeper.addPotionEffect(PotionEffectType.SPEED.createEffect(9999, 2));
							creeper.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(60, 999));
						});
						Scheduler.removeTask(this);
					}
				}
			});
		}
	}
}
