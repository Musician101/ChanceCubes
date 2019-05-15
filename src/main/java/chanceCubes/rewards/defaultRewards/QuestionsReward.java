package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class QuestionsReward extends BaseCustomReward implements Listener
{

	private Map<UUID, String> inQuestion = new HashMap<>();

	private List<Entry<String, String>> questionsAndAnswers = new ArrayList<>();

	public QuestionsReward()
	{
		super(CCubesCore.MODID + ":Question", -30);
		this.addQuestionAnswer("What is the username of the creator of Chance Cubes?", "Turkey -or- Turkey2349");
		this.addQuestionAnswer("How many sides does the sparkly, shiny, colorful, spinny Chance Cube have?", "20");
		this.addQuestionAnswer("What is 9 + 10", "19 -or- 21");
		this.addQuestionAnswer("What year was minecraft officially released", "2011");
		this.addQuestionAnswer("What company developes Java?", "Sun -or- Sun Microsystems -or- Oracle");
		this.addQuestionAnswer("Who created Minecraft?", "Notch");
		//this.addQuestionAnswer("What is the air-speed velocity of an unladen European swallow?", "24 -or- 11 -or- 11m/s -or- 24mph -or- 11 m/s -or- 24 mph");
	}

	public void addQuestionAnswer(String q, String a)
	{
		questionsAndAnswers.add(new SimpleEntry<>(q, a));
	}

	@Override
	public void trigger(Location location, Player player)
	{
		if(inQuestion.containsKey(player.getUniqueId()))
			return;

		if(!RewardsUtil.isPlayerOnline(player))
			return;

		int question = RewardsUtil.rand.nextInt(questionsAndAnswers.size());

		player.sendMessage(questionsAndAnswers.get(question).getKey());
		player.sendMessage("You have 20 seconds to answer! (Answer is not case sensitive)");
		inQuestion.put(player.getUniqueId(), questionsAndAnswers.get(question).getValue());
		Scheduler.scheduleTask(new Task("Question", 400, 20)
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

		if(correct)
		{
			player.sendMessage("Correct!");
			player.sendMessage("Here, have a item!");
			player.getWorld().dropItem(player.getLocation(), new ItemStack(RewardsUtil.getRandomItem()));
		}
		else
		{
			player.sendMessage("Incorrect! The answer was " + this.inQuestion.get(player.getUniqueId()));
			player.getWorld().createExplosion(player.getLocation(), 1, false);
			player.damage(Float.MAX_VALUE);
		}

		inQuestion.remove(player.getUniqueId());

	}

	@EventHandler
	public void onMessage(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();

		if(inQuestion.containsKey(player.getUniqueId()))
		{
			String answer = event.getMessage();
			boolean correct = false;
			for(String s : inQuestion.get(player.getUniqueId()).split("-or-"))
				if(s.trim().equalsIgnoreCase(answer.trim()))
					correct = true;
			this.timeUp(player, correct);
			event.setCancelled(true);
		}
	}
}
