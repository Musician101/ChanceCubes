package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.util.Vector;

public class CountDownReward extends BaseCustomReward
{

	public CountDownReward()
	{
		super(CCubesCore.MODID + ":Countdown", 15);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		Scheduler.scheduleTask(new Task("Countdown_Reward_Delay", 80, 20)
		{
			@Override
			public void callback()
			{
				int thing = RewardsUtil.rand.nextInt(10);

				if(thing == 0)
				{
					RewardsUtil.placeBlock(Material.DIAMOND_BLOCK.createBlockData(), location);
				}
				else if(thing == 1)
				{
					RewardsUtil.placeBlock(Material.GLASS.createBlockData(), location);
				}
				else if(thing == 2)
				{
					RewardsUtil.placeBlock(Material.COBBLESTONE.createBlockData(), location);
				}
				else if(thing == 3)
				{
					location.getWorld().spawn(location, Creeper.class);
				}
				else if(thing == 4)
				{
					location.getWorld().spawn(location, Cow.class);
				}
				else if(thing == 5)
				{
					location.getWorld().spawn(location, Villager.class);
				}
				else if(thing == 6)
				{
					location.getWorld().spawn(location, TNTPrimed.class, tntPrimed -> tntPrimed.setFuseTicks(20));
				}
				else if(thing == 7)
				{
					location.getWorld().dropItem(location, new ItemStack(RewardsUtil.getRandomItem()));
				}
				else if(thing == 8)
				{
					location.getWorld().spawn(location, ThrownPotion.class, thrownPotion -> {
						ItemStack potion = new ItemStack(Material.SPLASH_POTION);
						PotionMeta meta = (PotionMeta) potion.getItemMeta();
						meta.setBasePotionData(new PotionData(RewardsUtil.getRandomPotionType()));
						thrownPotion.setItem(potion);
						thrownPotion.setVelocity(new Vector(0, -1, 0));
					});
				}
				else if(thing == 9)
				{
					RewardsUtil.placeBlock(RewardsUtil.getRandomFluid().createBlockData(), location);
				}
			}

			@Override
			public void update()
			{
				this.showTimeLeft(player);
			}
		});
	}
}
