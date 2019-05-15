package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemChestReward extends BaseCustomReward
{
	//@formatter:off
	ItemStack[] stacks = new ItemStack[] { new ItemStack(Material.GLASS), new ItemStack(Material.APPLE), new ItemStack(Material.BREAD),
			new ItemStack(Material.CAKE) , new ItemStack(Material.COOKIE), new ItemStack(Material.COOKED_BEEF), new ItemStack(Material.DIAMOND),
			new ItemStack(Material.EGG), new ItemStack(Material.FEATHER), new ItemStack(Material.GOLD_INGOT), new ItemStack(Material.IRON_SWORD),
			new ItemStack(Material.LEATHER), new ItemStack(Material.EMERALD), new ItemStack(Material.MELON_SLICE), new ItemStack(Material.OAK_DOOR),
			new ItemStack(Material.PAPER), new ItemStack(Material.POTATO), new ItemStack(Material.PUMPKIN_PIE), new ItemStack(Material.QUARTZ),
			new ItemStack(Material.MUSIC_DISC_13), new ItemStack(Material.REDSTONE), new ItemStack(Material.SUGAR_CANE), new ItemStack(Material.SIGN),
			new ItemStack(Material.SLIME_BALL), new ItemStack(Material.SNOWBALL), new ItemStack(Material.SPIDER_EYE), new ItemStack(Material.WHEAT),
			new ItemStack(Material.EXPERIENCE_BOTTLE), new ItemStack(Material.CLAY_BALL), new ItemStack(Material.BLAZE_ROD),
			new ItemStack(Material.ENDER_PEARL)};
	//@formatter:on

	public ItemChestReward()
	{
		super(CCubesCore.MODID + ":Item_Chest", 25);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		Block block = location.getBlock();
		block.setType(Material.CHEST);
		Chest chest = (Chest) block.getState();
		Scheduler.scheduleTask(new Task("Item_Chest_Init_Delay", 60)
		{

			@Override
			public void callback()
			{
				spawnItems(location);
				player.openInventory(chest.getInventory());
				//				world.addBlockEvent(pos, chest.getType(), 1, TileEntityChest.getPlayersUsing(world, pos));
				//				world.notifyNeighborsOfStateChange(pos, chest.getType(), true);
				//				world.notifyNeighborsOfStateChange(pos.down(), chest.getType(), true);
			}
		});
	}

	public void spawnItems(Location location)
	{
		Scheduler.scheduleTask(new Task("Item_Chest_Squids", 250, 5)
		{
			@Override
			public void callback()
			{
				location.getBlock().setType(Material.AIR);
			}

			@Override
			public void update()
			{
				Item item = location.getWorld().dropItem(location, stacks[RewardsUtil.rand.nextInt(stacks.length)].clone());
				item.setVelocity(new Vector(0, 1.5, -1));
				item.setPickupDelay(60);
			}
		});
	}
}
