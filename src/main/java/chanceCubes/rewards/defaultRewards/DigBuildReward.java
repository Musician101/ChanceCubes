package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DigBuildReward extends BaseCustomReward
{

	public DigBuildReward()
	{
		super(CCubesCore.MODID + ":Did_You_Know", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		int initialY = player.getLocation().getBlockY();
		int distance = RewardsUtil.rand.nextInt(20) + 5;
		boolean up = (initialY + distance <= 150) && ((initialY - distance < 2) || RewardsUtil.rand.nextBoolean());

		player.sendMessage("Quick! Go " + (up ? "up " : "down ") + distance + " blocks!");
		player.sendMessage("You have " + (distance + 3) + " seconds!");

		Scheduler.scheduleTask(new Task("Dig_Build_Reward_Delay", (distance + 3) * 20, 5)
		{
			@Override
			public void callback()
			{
				location.getWorld().createExplosion(player.getLocation(), 1, false);
				player.damage(Float.MAX_VALUE);
			}

			@Override
			public void update()
			{
				if(up && player.getLocation().getY() >= initialY + distance)
				{
					player.sendMessage("Good Job!");
					player.sendMessage("Here, have a item!");
					location.getWorld().dropItem(player.getLocation(), new ItemStack(RewardsUtil.getRandomItem()));
					Scheduler.removeTask(this);
				}
				else if(!up && player.getLocation().getY() <= initialY - distance)
				{
					player.sendMessage("Good Job!");
					player.sendMessage("Here, have a item!");
					location.getWorld().dropItem(player.getLocation(), new ItemStack(RewardsUtil.getRandomItem()));
					Scheduler.removeTask(this);
				}

				if(this.delayLeft % 20 == 0)
					this.showTimeLeft(player);
			}
		});
	}
}
