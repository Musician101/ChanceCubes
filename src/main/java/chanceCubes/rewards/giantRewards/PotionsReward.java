package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

public class PotionsReward extends BaseCustomReward
{
	private ThrownPotion pot;

	public PotionsReward()
	{
		super(CCubesCore.MODID + ":Raining_Potions", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		player.sendMessage("chancecubes.reward.raining_potions");
		throwPotionCircle(location);
	}

	private void throwPotionCircle(Location location)
	{
		Scheduler.scheduleTask(new Task("Potion Circle", 100, 20)
		{
			@Override
			public void callback()
			{
				throwPotion(location);
			}

			@Override
			public void update()
			{
				for(double rad = -Math.PI; rad <= Math.PI; rad += (Math.PI / 20))
				{
					PotionType potionType = RewardsUtil.getRandomPotionType();
					pot = location.getWorld().spawn(location, ThrownPotion.class, thrownPotion -> {
						ItemStack itemStack = new ItemStack(Material.SPLASH_POTION);
						PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
						meta.setBasePotionData(new PotionData(potionType));
						itemStack.setItemMeta(meta);
						thrownPotion.setItem(itemStack);
					});
					pot.setVelocity(new Vector(Math.cos(rad), 1, Math.sin(rad)));
				}
			}
		});
	}

	private void throwPotion(Location location)
	{
		Scheduler.scheduleTask(new Task("Throw potion", 400, 2)
		{
			@Override
			public void callback()
			{

			}

			@Override
			public void update()
			{
				for(double yy = -0.2; yy <= 1; yy += 0.1)
				{
					PotionType potionType = RewardsUtil.getRandomPotionType();
					pot = location.getWorld().spawn(location, ThrownPotion.class, thrownPotion -> {
						ItemStack itemStack = new ItemStack(Material.SPLASH_POTION);
						PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
						meta.setBasePotionData(new PotionData(potionType));
						itemStack.setItemMeta(meta);
						thrownPotion.setItem(itemStack);
					});
					pot.setVelocity(new Vector(Math.cos(delayLeft / 2) * 0.2 * yy, 1, Math.sin(delayLeft / 2) * 0.2 * yy));
				}
			}
		});
	}
}
