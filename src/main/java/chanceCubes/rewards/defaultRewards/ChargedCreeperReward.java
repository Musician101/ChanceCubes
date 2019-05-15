package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ChargedCreeperReward extends BaseCustomReward
{

	public ChargedCreeperReward()
	{
		super(CCubesCore.MODID + ":Charged_Creeper", -40);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(0, 1, 0));
		Creeper creeper = location.getWorld().spawn(location, Creeper.class, c -> {
			c.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 300, 99, true, false));
			c.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 300, 99, true, false));
		});

		Scheduler.scheduleTask(new Task("Charged Creeper Reward", 2)
		{
			@Override
			public void callback()
			{
				location.getWorld().strikeLightning(location);
				creeper.setFireTicks(0);
			}
		});
	}
}
