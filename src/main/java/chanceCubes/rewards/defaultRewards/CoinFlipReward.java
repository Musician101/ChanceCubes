package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class CoinFlipReward extends BaseCustomReward implements Listener
{
	private List<UUID> inFlip = new ArrayList<>();

	public CoinFlipReward()
	{
		super(CCubesCore.MODID + ":Heads_or_Tails", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		if(inFlip.contains(player.getUniqueId()))
			return;

		player.sendMessage("Heads or Tails");
		inFlip.add(player.getUniqueId());
		Scheduler.scheduleTask(new Task("Heads_Or_Tails", 600)
		{
			@Override
			public void callback()
			{
				if(!inFlip.contains(player.getUniqueId()) || !RewardsUtil.isPlayerOnline(player))
					return;

				player.sendMessage("Seem's that you didn't pick heads or tails.");
				player.sendMessage("You must be real fun at parties....");
				inFlip.remove(player.getUniqueId());
			}

		});
	}

	@EventHandler
	public void onMessage(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();
		String message = event.getMessage();
		if(!message.equalsIgnoreCase("Heads") && !message.equalsIgnoreCase("Tails"))
			return;

		if(inFlip.contains(player.getUniqueId()))
		{
			int flip = RewardsUtil.rand.nextInt(6000);
			World world = player.getWorld();
			Location location = player.getLocation();
			// Yes I know heads has a 49.9833% chance and Tails has a 50% Chance. Deal with it.
			if(flip == 1738)
			{
				player.sendMessage("The coin landed on it's side.....");
				player.sendMessage("Well this is awkward");
			}
			else if(flip < 3000)
			{
				if(message.equalsIgnoreCase("Heads"))
				{
					player.sendMessage("It was heads! You're correct!");
					world.dropItem(player.getLocation(), new ItemStack(RewardsUtil.getRandomItem(), 1));
				}
				else
				{
					player.sendMessage("It was heads! You're incorrect!");
					for(int i = 0; i < 5; i++)
					{
						world.spawn(location.clone().add(0, 1, 0), TNTPrimed.class);
						world.playSound(location, Sound.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
					}
				}
			}
			else
			{
				if(message.equalsIgnoreCase("Tails"))
				{
					player.sendMessage("It was tails! You're correct!");
					world.dropItem(player.getLocation(), new ItemStack(RewardsUtil.getRandomItem(), 1));
				}
				else
				{
					player.sendMessage("It was tails! You're incorrect!");
					for(int i = 0; i < 5; i++)
					{
						world.spawn(location.clone().add(0, 1, 0), TNTPrimed.class);
						world.playSound(location, Sound.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
					}
				}
			}

			inFlip.remove(player.getUniqueId());
			event.setCancelled(true);
		}
	}
}
