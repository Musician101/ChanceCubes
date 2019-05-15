package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardBlockCache;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MatchingReward extends BaseCustomReward
{
	public MatchingReward()
	{
		super(CCubesCore.MODID + ":Matching", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		Material[] metas = { Material.WHITE_WOOL, Material.ORANGE_WOOL, Material.ORANGE_WOOL, Material.MAGENTA_WOOL, Material.MAGENTA_WOOL, Material.LIGHT_BLUE_WOOL, Material.LIGHT_BLUE_WOOL, Material.LIME_WOOL, Material.LIME_WOOL };
		RewardBlockCache cache = new RewardBlockCache(location, player.getLocation());
		for(int i = 0; i < 500; i++)
		{
			int index1 = RewardsUtil.rand.nextInt(9);
			int index2 = RewardsUtil.rand.nextInt(9);
			Material metaTemp = metas[index1];
			metas[index1] = metas[index2];
			metas[index2] = metaTemp;
		}

		for(int i = 0; i < metas.length; i++)
		{
			int x = (i % 3) - 1;
			int z = (i / 3) - 1;
			cache.cacheBlock(new Vector(x, -1, z), metas[i].createBlockData());
		}
		player.sendMessage("Memerize these blocks!");

		Scheduler.scheduleTask(new Task("Matching_Reward_Memerize_Delay", 200, 20)
		{
			@Override
			public void callback()
			{
				for(int i = 0; i < metas.length; i++)
				{
					int x = (i % 3) - 1;
					int z = (i / 3) - 1;
					location.clone().add(x, -1, x).getBlock().setType(Material.GLASS);
				}

				match(location, player, metas, cache);
			}

			@Override
			public void update()
			{
				this.showTimeLeft(player);
			}
		});
	}

	public void match(Location location, Player player, Material[] metas, RewardBlockCache cache)
	{
		player.sendMessage("Now break the matching blocks (in pairs with white last)! You have 45 seconds!");
		Scheduler.scheduleTask(new Task("Matching_Reward_Memerize_Delay", 900, 2)
		{
			boolean[] checked = new boolean[9];
			int lastBroken = -1;
			int matches = 0;

			@Override
			public void callback()
			{
				lose();
			}

			@Override
			public void update()
			{
				if(this.delayLeft % 20 == 0)
					this.showTimeLeft(player);

				for(int i = 0; i < metas.length; i++)
				{
					int x = (i % 3) - 1;
					int z = (i / 3) - 1;
					if(RewardsUtil.isAir(location.clone().add(x, -1, z).getBlock()) && !checked[i])
					{
						checked[i] = true;
						location.clone().add(x, -1, z).getBlock().setType(metas[i]);
						if(lastBroken != -1)
						{
							if(metas[i] == metas[lastBroken])
							{
								matches++;
								lastBroken = -1;
								break;
							}
							else
							{
								lose();
								Scheduler.removeTask(this);
								break;
							}
						}
						else
						{
							lastBroken = i;
						}

						if(matches == 4)
						{
							win();
							Scheduler.removeTask(this);
							break;
						}
					}
				}
			}

			private void lose()
			{
				location.getWorld().createExplosion(player.getLocation(), 1.0F, false);
				player.damage(Float.MAX_VALUE);
				reset();
			}

			private void win()
			{
				player.sendMessage("Good job! Have a cool little item!");
				location.getWorld().dropItem(player.getLocation(), new ItemStack(RewardsUtil.getRandomItem()));
				reset();
			}

			public void reset()
			{
				cache.restoreBlocks(player);
			}
		});
	}
}
