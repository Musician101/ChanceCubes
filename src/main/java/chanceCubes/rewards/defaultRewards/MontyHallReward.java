package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardBlockCache;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MontyHallReward extends BaseCustomReward
{
	public MontyHallReward()
	{
		super(CCubesCore.MODID + ":Monty_Hall", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		player.sendMessage("Which button do you press?");

		RewardBlockCache cache = new RewardBlockCache(location, player.getLocation());
		cache.cacheBlock(new Vector(-1, 0, 0), Material.OBSIDIAN.createBlockData());
		cache.cacheBlock(new Vector(0, 0, 0), Material.OBSIDIAN.createBlockData());
		cache.cacheBlock(new Vector(1, 0, 0), Material.OBSIDIAN.createBlockData());
		cache.cacheBlock(new Vector(-1, 0, 1), Material.STONE_BUTTON.createBlockData());
		cache.cacheBlock(new Vector(0, 0, 1), Material.STONE_BUTTON.createBlockData());
		cache.cacheBlock(new Vector(1, 0, 1), Material.STONE_BUTTON.createBlockData());

		Scheduler.scheduleTask(new Task("Monty_Hall_Reward", 6000, 10)
		{
			int[] chance = { RewardsUtil.rand.nextInt(3) - 1, RewardsUtil.rand.nextInt(3) - 1, RewardsUtil.rand.nextInt(3) - 1 };

			@Override
			public void callback()
			{
				cache.restoreBlocks(player);
			}

			@Override
			public void update()
			{
				Block block = location.clone().add(-1, 0, 1).getBlock();
				if(block.getBlockPower() > 0)
					giveReward(chance[0]);

				block = location.clone().add(0, 0, 1).getBlock();
				if(block.getBlockPower() > 0)
					giveReward(chance[1]);

				block = location.clone().add(1, 0, 1).getBlock();
				if(block.getBlockPower() > 0)
					giveReward(chance[2]);
			}

			private void giveReward(int value)
			{
				if(value == -1)
				{
					location.getWorld().spawn(player.getLocation().clone().add(0, 1, 0), TNTPrimed.class, tntPrimed -> tntPrimed.setFuseTicks(40));
					location.getWorld().playSound(player.getLocation(), Sound.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				else if(value == 0)
				{
					player.sendMessage("You walk away to live another day...");
				}
				else if(value == 1)
				{
					location.getWorld().spawn(player.getLocation(), Item.class, item -> item.setItemStack(new ItemStack(RewardsUtil.getRandomItem())));
				}

				this.callback();
				Scheduler.removeTask(this);
			}
		});
	}
}
