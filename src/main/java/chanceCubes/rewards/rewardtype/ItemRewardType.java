package chanceCubes.rewards.rewardtype;

import chanceCubes.rewards.rewardparts.ItemPart;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class ItemRewardType extends BaseRewardType<ItemPart>
{
	public ItemRewardType(ItemPart... items)
	{
		super(items);
	}

	@Override
	public void trigger(final ItemPart part, Location location, Player player)
	{
		Scheduler.scheduleTask(new Task("ItemStack Reward Delay", part.getDelay())
		{
			@Override
			public void callback()
			{
				location.getWorld().spawn(location.clone().add(0.5, 0.5, 0.5), Item.class, item -> {
					item.setItemStack(part.getItemStack().clone());
					item.setPickupDelay(10);
				});
			}
		});
	}
}
