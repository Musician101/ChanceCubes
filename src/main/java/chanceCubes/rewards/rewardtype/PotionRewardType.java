package chanceCubes.rewards.rewardtype;

import chanceCubes.rewards.rewardparts.PotionPart;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.SplashPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.util.Vector;

public class PotionRewardType extends BaseRewardType<PotionPart>
{
	public PotionRewardType(PotionPart... effects)
	{
		super(effects);
	}

	@Override
	public void trigger(final PotionPart part, Location location, Player player)
	{
		Scheduler.scheduleTask(new Task("Potion Reward Delay", part.getDelay())
		{
			@Override
			public void callback()
			{
				ItemStack potion = new ItemStack(Material.SPLASH_POTION);
				PotionMeta meta = (PotionMeta) potion.getItemMeta();
				meta.addCustomEffect(part.getEffect(), true);
				potion.setItemMeta(meta);

				location.getWorld().spawn(player.getLocation().add(0, 2, 0), SplashPotion.class, splashPotion -> splashPotion.setVelocity(new Vector(0, 0.1, 0)));
			}
		});
	}
}
