package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardBlockCache;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MathReward extends BaseCustomReward implements Listener
{
	private Map<UUID, RewardInfo> inQuestion = new HashMap<>();

	public MathReward()
	{
		super(CCubesCore.MODID + ":Math", -30);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		if(inQuestion.containsKey(player.getUniqueId()))
			return;

		int num1 = RewardsUtil.rand.nextInt(100);
		int num2 = RewardsUtil.rand.nextInt(100);
		player.sendMessage("Quick, what's " + num1 + "+" + num2 + "?");
		RewardBlockCache cache = new RewardBlockCache(location, player.getLocation());
		for(int xx = -2; xx < 3; xx++)
		{
			for(int zz = -2; zz < 3; zz++)
			{
				for(int yy = 1; yy < 5; yy++)
				{
					if(xx == -2 || xx == 2 || zz == -2 || zz == 2 || yy == 1 || yy == 4)
						cache.cacheBlock(new Vector(xx, yy, zz), Material.BEDROCK.createBlockData());
					else if(((xx == -1 || xx == 1) && (zz == -1 || zz == 1) && yy == 2))
						cache.cacheBlock(new Vector(xx, yy, zz), Material.GLOWSTONE.createBlockData());
				}
			}
		}

		player.teleport(player.getLocation().clone().add(0, 2, 0));
		List<Entity> tnt = new ArrayList<>();
		for(int i = 0; i < 5; i++)
			tnt.add(location.getWorld().spawn(player.getLocation().clone().add(0, 1, 0), TNTPrimed.class, tntPrimed -> tntPrimed.setFuseTicks(140)));

		inQuestion.put(player.getUniqueId(), new RewardInfo(num1 + num2, tnt, cache));

		Scheduler.scheduleTask(new Task("Math", 100, 20)
		{
			@Override
			public void callback()
			{
				timeUp(player, false);
			}

			@Override
			public void update()
			{
				if(this.delayLeft % 20 == 0)
					this.showTimeLeft(player);
			}

		});
	}

	private void timeUp(Player player, boolean correct)
	{
		if(!inQuestion.containsKey(player.getUniqueId()))
			return;

		if(!RewardsUtil.isPlayerOnline(player))
			return;

		RewardInfo info = inQuestion.get(player.getUniqueId());
		if(correct)
		{
			player.sendMessage("Correct!");
			player.sendMessage("Here, have a item!");
			player.getWorld().dropItem(player.getLocation(), new ItemStack(RewardsUtil.getRandomItem()));
		}
		else
		{
			player.getWorld().createExplosion(player.getLocation(), 1, false);
			player.damage(Float.MAX_VALUE);
		}

		for(Entity tnt : info.tnt)
			tnt.remove();

		info.cache.restoreBlocks(player);

		inQuestion.remove(player.getUniqueId());

	}

	@EventHandler
	public void onMessage(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		if(inQuestion.containsKey(player.getUniqueId()))
		{
			int answer = 0;
			try
			{
				answer = Integer.parseInt(event.getMessage());
			} catch(NumberFormatException e)
			{
				player.sendMessage("Incorrect!");
			}

			if(inQuestion.get(player).answer == answer)
				this.timeUp(player, true);
			else
				player.sendMessage("Incorrect!");
			event.setCancelled(true);
		}
	}

	private class RewardInfo
	{
		public int answer;
		public List<Entity> tnt;
		public RewardBlockCache cache;

		public RewardInfo(int answer, List<Entity> tnt, RewardBlockCache cache)
		{
			this.answer = answer;
			this.tnt = tnt;
			this.cache = cache;
		}
	}
}
