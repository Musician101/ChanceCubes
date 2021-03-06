package chanceCubes.rewards.defaultRewards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.logging.log4j.Level;

import com.google.gson.JsonElement;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.config.CCubesSettings;
import chanceCubes.config.CustomRewardsLoader;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.util.HTTPUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;

public class CustomUserReward implements IChanceCubeReward
{
	private String userName = "";
	private UUID uuid = null;
	private String type;

	private List<BasicReward> customRewards = new ArrayList<BasicReward>();

	public CustomUserReward(EntityPlayer player)
	{
		if(!CCubesSettings.userSpecificRewards)
			return;

		JsonElement users;
		try
		{
			users = HTTPUtil.getWebFile(CCubesSettings.rewardURL + "/UserList.json");
		} catch(Exception e)
		{
			CCubesCore.logger.log(Level.ERROR, "Chance Cubes failed to get the list of users with custom rewards!");
			return;
		}

		UUID uuidTemp = this.getPlayerUUID(player.getName());
		if(uuidTemp == null)
		{
			CCubesCore.logger.log(Level.ERROR, "Chance Cubes failed to get the uuid of the user!");
			return;
		}
		for(JsonElement user : users.getAsJsonArray())
		{
			if(user.getAsJsonObject().get("UUID").getAsString().equalsIgnoreCase(uuidTemp.toString()))
			{
				userName = user.getAsJsonObject().get("Name").getAsString();
				uuid = uuidTemp;
				type = user.getAsJsonObject().get("Type").getAsString();
			}
		}

		if(userName.equals(""))
		{
			CCubesCore.logger.log(Level.INFO, "No custom rewards detected for the current user!");
			return;
		}

		JsonElement userRewards;

		try
		{
			userRewards = HTTPUtil.getWebFile(CCubesSettings.rewardURL + "/Users/" + userName + ".json");
		} catch(Exception e)
		{
			CCubesCore.logger.log(Level.ERROR, "Chance Cubes failed to get the custom list for " + userName + "!");
			CCubesCore.logger.log(Level.ERROR, e.getMessage());
			return;
		}

		for(Entry<String, JsonElement> reward : userRewards.getAsJsonObject().entrySet())
		{
			customRewards.add(CustomRewardsLoader.instance.parseReward(reward).getKey());
		}

		ChanceCubeRegistry.INSTANCE.registerReward(this);
		player.addChatMessage(new TextComponentString("Seems you have some custom Chance Cubes rewards " + this.userName + "...."));
		player.addChatMessage(new TextComponentString("Let the fun begin! >:)"));
	}

	@Override
	public void trigger(final World world, final BlockPos pos, final EntityPlayer player)
	{

		if(!UsernameCache.getLastKnownUsername(uuid).equalsIgnoreCase(player.getName()))
		{
			player.addChatMessage(new TextComponentString("Hey you aren't " + this.userName + "! You can't have their reward! Try again!"));
			Entity itemEnt = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(CCubesBlocks.CHANCE_CUBE, 1));
			world.spawnEntityInWorld(itemEnt);
			return;
		}

		player.addChatMessage(new TextComponentString("Selecting best (possibly deadly) reward for " + this.type + " " + this.userName));

		Task task = new Task("Custom Reward", 100)
		{
			@Override
			public void callback()
			{
				triggerAcutalReward(world, pos, player);
			}
		};

		Scheduler.scheduleTask(task);
	}

	public void triggerAcutalReward(World world, BlockPos pos, EntityPlayer player)
	{
		this.customRewards.get(world.rand.nextInt(this.customRewards.size())).trigger(world, pos, player);
	}

	@Override
	public int getChanceValue()
	{
		return 0;
	}

	@Override
	public String getName()
	{
		return CCubesCore.MODID + ":CR_" + this.userName;
	}

	public UUID getPlayerUUID(String username)
	{
		for(Map.Entry<UUID, String> entry : UsernameCache.getMap().entrySet())
		{
			if(entry.getValue().equalsIgnoreCase(username))
			{
				return entry.getKey();
			}
		}
		return null;
	}

}
