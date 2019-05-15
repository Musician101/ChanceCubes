package chanceCubes.rewards.rewardtype;

import chanceCubes.rewards.rewardparts.ChestChanceItem;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

public class ChestRewardType extends BaseRewardType<ChestChanceItem>
{
	private Chest chest;

	private int delay = 0;

	public ChestRewardType(ChestChanceItem... items)
	{
		super(items);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		Scheduler.scheduleTask(new Task("Chest Reward Delay", delay)
		{
			@Override
			public void callback()
			{
				RewardsUtil.placeBlock(Material.CHEST.createBlockData(), location);
				chest = (Chest) location.getBlock().getState();

				for(ChestChanceItem item : rewards)
					trigger(item, location, player);
			}
		});

	}

	@Override
	protected void trigger(ChestChanceItem item, Location location, Player player)
	{
		boolean addToChest = new Random().nextInt(100) < item.getChance();
		if(addToChest)
		{
			int slot = new Random().nextInt(chest.getInventory().getSize());
			chest.getInventory().setItem(slot, item.getRandomItemStack());
		}
	}

	public ChestRewardType setDelay(int delay)
	{
		this.delay = delay;
		return this;
	}
}
