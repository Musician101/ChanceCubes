package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.config.CCubesSettings;
import chanceCubes.config.CustomRewardsLoader;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.util.HTTPUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class CustomUserReward extends BaseCustomReward
{
	private String userName;
	private UUID uuid;
	private String type;

	private List<BasicReward> customRewards;

	public static void getCustomUserReward(UUID uuid)
	{
		if(!CCubesSettings.userSpecificRewards)
			return;

		Logger logger = CCubesCore.getInstance().getLogger();
		JsonElement users;
		try
		{
			users = HTTPUtil.getWebFile("GET", "https://api.theprogrammingturkey.com/chance_cubes/custom_rewards/UserList.json");
		} catch(Exception e)
		{
			e.printStackTrace();
			logger.severe("Chance Cubes failed to get the list of users with custom rewards!");
			return;
		}

		Optional<JsonObject> optional = StreamSupport.stream(users.getAsJsonArray().spliterator(), false).map(JsonElement::getAsJsonObject).filter(user -> user.has("UUID") && user.get("UUID").getAsString().equals(uuid.toString())).findFirst();
		if (!optional.isPresent()) {
			logger.info("No custom rewards detected for the current user!");
			return;
		}

		JsonObject user = optional.get();
		String userName = user.get("Name").getAsString();
		String type = user.get("Type").getAsString();

		JsonElement userRewards;
		try
		{
			userRewards = HTTPUtil.getWebFile("GET", "https://api.theprogrammingturkey.com/chance_cubes/custom_rewards/users/" + userName + ".json");
		} catch(Exception e)
		{
			logger.severe("Chance Cubes failed to get the custom list for " + userName + "!");
			logger.severe(e.getMessage());
			return;
		}

		List<BasicReward> customRewards = new ArrayList<>();
		for(Entry<String, JsonElement> reward : userRewards.getAsJsonObject().entrySet())
		{
			customRewards.add(CustomRewardsLoader.instance.parseReward(reward).getKey());
		}

		Bukkit.getScheduler().runTask(CCubesCore.getInstance(), () -> {
			ChanceCubeRegistry.INSTANCE.registerReward(new CustomUserReward(uuid, userName, type, customRewards));
			Player player = Bukkit.getPlayer(uuid);
			if(player != null)
			{
				player.sendMessage("Seems you have some custom Chance Cubes rewards " + userName + "....");
				player.sendMessage("Let the fun begin! >:)");
			}
		});
	}

	public CustomUserReward(UUID uuid, String userName, String type, List<BasicReward> customRewards)
	{
		super(CCubesCore.MODID + ":CR_" + userName, 0);
		this.uuid = uuid;
		this.userName = userName;
		this.type = type;
		this.customRewards = customRewards;
	}

	@Override
	public void trigger(Location location, Player player)
	{
		String name = Bukkit.getOfflinePlayer(uuid).getName();
		if(name == null || !name.equalsIgnoreCase(player.getName()))
		{
			player.sendMessage("Hey you aren't " + this.userName + "! You can't have their reward! Try again!");
			location.getWorld().spawn(location, Item.class, item -> item.setItemStack(CCubesBlocks.getChanceCube(1)));
			return;
		}

		player.sendMessage("Selecting best (possibly deadly) reward for " + this.type + " " + this.userName);
		Scheduler.scheduleTask(new Task("Custom Reward", 100)
		{
			@Override
			public void callback()
			{
				customRewards.get(new Random().nextInt(customRewards.size())).trigger(location, player);
			}
		});
	}
}
