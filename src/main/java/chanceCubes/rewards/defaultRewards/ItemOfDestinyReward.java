package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemOfDestinyReward extends BaseCustomReward
{
	public ItemOfDestinyReward()
	{
		super(CCubesCore.MODID + ":Item_Of_Destiny", 40);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		Item item = location.getWorld().dropItem(location, new ItemStack(RewardsUtil.getRandomItem()));
		item.setPickupDelay(100000);
		player.sendMessage("Selecting random item");
		Scheduler.scheduleTask(new Task("Item_Of_Destiny_Reward", -1, 5)
		{
			int iteration = 0;
			int enchants = 0;

			@Override
			public void callback()
			{

			}

			@Override
			public void update()
			{
				if(iteration < 17)
				{
					item.setItemStack(new ItemStack(RewardsUtil.getRandomItem(), 1));
				}
				else if(iteration == 17)
				{
					player.sendMessage("Random item selected");
					player.sendMessage("Selecting number of enchants to give item");
				}
				else if(iteration == 27)
				{
					int i = RewardsUtil.rand.nextInt(9);
					enchants = i < 5 ? 1 : i < 8 ? 2 : 3;
					player.sendMessage(enchants + " random enchants will be added!");
					player.sendMessage("Selecting random enchant to give to the item");
				}
				else if(iteration > 27 && (iteration - 7) % 10 == 0)
				{
					if((iteration / 10) - 3 < enchants)
					{
						Enchantment ench = RewardsUtil.randomEnchantment();
						int level = ench.getStartLevel() + RewardsUtil.rand.nextInt(ench.getMaxLevel());
						item.getItemStack().addUnsafeEnchantment(ench, level);
						player.sendMessage(ench.getKey() + " has been added to the item!");
					}
					else
					{
						player.sendMessage("Your item of destiny is complete! Enjoy!");
						item.setPickupDelay(0);
						Scheduler.removeTask(this);
					}
				}

				iteration++;
			}
		});
	}
}
