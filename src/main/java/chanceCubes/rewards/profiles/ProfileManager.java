package chanceCubes.rewards.profiles;

import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.rewards.IChanceCubeReward;
import chanceCubes.rewards.profiles.triggers.DifficultyTrigger;
import chanceCubes.rewards.profiles.triggers.DimensionChangeTrigger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Difficulty;

public class ProfileManager
{
	private static List<IProfile> enabledProfiles = new ArrayList<>();
	private static List<IProfile> disabledProfiles = new ArrayList<>();

	private static Map<String, List<Integer>> chanceChangesCache = new HashMap<>();

	//private static Configuration config;
	public static final String genCat = "Profile Status";

	public static void registerProfile(IProfile profile)
	{
		registerProfile(profile, false);
	}

	public static void registerProfile(IProfile profile, boolean enabled)
	{
		enabled = false;//config.getBoolean(profile.getName(), genCat, enabled, profile.getDesc());
		if(enabled)
		{
			enabledProfiles.add(profile);
			profile.onEnable();
		}
		else
		{
			disabledProfiles.add(profile);
			profile.onDisable();
		}
	}

	public static void enableProfile(IProfile profile)
	{
		if(disabledProfiles.remove(profile))
		{
			enabledProfiles.add(profile);
			profile.onEnable();
			//config.load();
			//config.get(genCat, profile.getName(), "").setValue(true);
			//config.save();
		}
	}

	public static void disableProfile(IProfile profile)
	{
		if(enabledProfiles.remove(profile))
		{
			disabledProfiles.add(profile);
			profile.onDisable();
			//config.load();
			//config.get(genCat, profile.getName(), "").setValue(false);
			//config.save();
		}
	}

	public static void clearProfiles()
	{
		enabledProfiles.clear();
		disabledProfiles.clear();
	}

	public static List<IProfile> getAllProfiles()
	{
		List<IProfile> toReturn = new ArrayList<>();
		toReturn.addAll(enabledProfiles);
		toReturn.addAll(disabledProfiles);
		return toReturn;
	}

	public static IProfile getProfileFromID(String id)
	{
		for(IProfile prof : getAllProfiles())
			if(prof.getID().equals(id))
				return prof;
		return null;
	}

	public static List<String> getEnabledProfileNames()
	{
		List<String> toReturn = new ArrayList<>();
		for(IProfile prof : enabledProfiles)
			toReturn.add(prof.getName());
		return toReturn;
	}

	public static List<String> getDisabledProfileNames()
	{
		List<String> toReturn = new ArrayList<>();
		for(IProfile prof : disabledProfiles)
			toReturn.add(prof.getName());
		return toReturn;
	}

	public static List<String> getAllProfileNames(boolean sorted)
	{
		List<String> toReturn = new ArrayList<>();
		toReturn.addAll(getEnabledProfileNames());
		toReturn.addAll(getDisabledProfileNames());
		if(sorted)
			Collections.sort(toReturn);
		return toReturn;
	}

	public static IProfile getProfileFromName(String name)
	{
		for(IProfile prof : enabledProfiles)
			if(prof.getName().equals(name))
				return prof;
		for(IProfile prof : disabledProfiles)
			if(prof.getName().equals(name))
				return prof;
		return null;
	}

	public static boolean isProfileEnabled(IProfile prof)
	{
		return enabledProfiles.contains(prof);
	}

	public static void setRewardChanceValue(String rewardName, int chance)
	{
		IChanceCubeReward reward = ChanceCubeRegistry.INSTANCE.getRewardByName(rewardName);
		if(reward != null)
		{
			List<Integer> cache = chanceChangesCache.get(rewardName);
			if(cache == null)
			{
				cache = new ArrayList<>();
				cache.add(reward.getChanceValue());
				chanceChangesCache.put(rewardName, cache);
			}
			cache.add(chance);
			reward.setChanceValue(chance);
		}
	}

	public static void resetRewardChanceValue(String rewardName, int chanceFrom)
	{
		IChanceCubeReward reward = ChanceCubeRegistry.INSTANCE.getRewardByName(rewardName);
		if(reward != null)
		{
			List<Integer> cache = chanceChangesCache.get(rewardName);
			if(cache == null)
			{
				//Something dun messed up
				return;
			}
			cache.remove((Integer) chanceFrom);
			if(cache.size() == 0)
			{
				//Again, something dun messed up
				return;
			}
			reward.setChanceValue(cache.get(cache.size() - 1));
		}
	}

	public static void initProfiles()
	{
		BasicProfile profile;

		//@formatter:off
		//config.load();
		profile = new BasicProfile("default", "Default", "Rewards that are disabled by default");
		profile.addDisabledRewards("chancecubes:Clear_Inventory");
		registerProfile(profile, true);

		profile = new BasicProfile("no_explosions", "No Explosions", "Disable all rewards that use explode");
		profile.addDisabledRewards("chancecubes:Tnt_Structure", "chancecubes:Explosion", "chancecubes:TNT_Cat",
				"chancecubes:TNT_Diamond", "chancecubes:TNT_Bats", "chancecubes:Coal_To_Diamonds", "chancecubes:Help_Me",
				"chancecubes:Nuke", "chancecubes:Pssst", "chancecubes:Surrounded_Creeper", "chancecubes:Troll_TNT",
				"chancecubes:Ender_Crystal_Timer", "chancecubes:Wait_For_It", "chancecubes:Charged_Creeper",
				"chancecubes:Torches_To_Creepers", "chancecubes:Cake", "chancecubes:Wolves_To_Creepers",
				"chancecubes:Monty_Hall", "chancecubes:Countdown", "chancecubes:Heads_or_Tails");
		registerProfile(profile);

		profile = new BasicProfile("no_death_mg", "No Death Mini-games", "Disable all minigame rewards that kills the player if they lose");
		profile.addDisabledRewards("chancecubes:Maze", "chancecubes:Dig_Build_Reward", "chancecubes:Matching",
				"chancecubes:Math", "chancecubes:Question");
		registerProfile(profile);

		profile = new BasicProfile("no_status_effects", "No Potions/Effects", "Disable all rewards that throw a potion or give a status effect");
		profile.addDisabledRewards("chancecubes:Poison", "chancecubes:Wither_Status_Effect", "chancecubes:Arrow_Trap",
				"chancecubes:Random_Status_Effect", "chancecubes:Lingering_Potions_Ring", "chancecubes:Surrounded_Creeper",
				"chancecubes:Mob_Abilities_Effects");
		registerProfile(profile);

		//TODO: Finish this one up
		profile = new BasicProfile("hardcore", "Hardcore", "For users who play on hardcore difficulty");
		profile.addDisabledRewards("chancecubes:Heads_or_Tails", "chancecubes:Monty_Hall", "chancecubes:Ender_Crystal_Timer",
				"chancecubes:Wither");
		profile.addSubProfile(getProfileFromID("no_death_mg"));
		profile.addRewardChanceChange("chancecubes:Half_Heart", -100);
		profile.addTriggers(new DifficultyTrigger(profile, Difficulty.HARD));
		registerProfile(profile);

		profile = new BasicProfile("nether", "Nether", "Updates the reward pool for when players are in the nether");
		profile.addDisabledRewards("chancecubes:Rain", "chancecubes:Sail_Away", "chancecubes:Squid_Horde",
				"chancecubes:Ice_Cold", "chancecubes:Hot_Tub", "chancecubes:Guardians", "chancecubes:Nuke",
				"chancecubes:Cats_And_Dogs");
		profile.addTriggers(new DimensionChangeTrigger(profile, "world"));
		registerProfile(profile);

		profile = new BasicProfile("peaceful", "Peaceful", "For users who play on peaceful diffuculty. Removes rewards that have hostile Mobs (Doesn't remove TNT)");
		profile.addDisabledRewards("chancecubes:Pssst", "chancecubes:Horde", "chancecubes:Silverfish_Surround",
				"chancecubes:Slime_Man", "chancecubes:Witch", "chancecubes:Spawn_Jerry", "chancecubes:Spawn_Glenn",
				"chancecubes:Invisible_Creeper", "chancecubes:Knockback_Zombie", "chancecubes:Actual_Invisible_Ghast",
				"chancecubes:Nether_Jelly_Fish", "chancecubes:Quidditch", "chancecubes:One_Man_Army", "chancecubes:Silvermite_Stacks",
				"chancecubes:Invizible_Silverfish", "chancecubes:Skeleton_Bats", "chancecubes:Cave_Spider_Web", "chancecubes:Guardians",
				"chancecubes:Cookie_Monster", "chancecubes:Charged_Creeper", "chancecubes:Torches_To_Creepers", "chancecubes:Herobrine",
				"chancecubes:Surrounded", "chancecubes:Surrounded_Creeper", "chancecubes:Wither", "chancecubes:Wait_For_It",
				"chancecubes:Cake", "chancecubes:Wolves_To_Creepers", "chancecubes:Countdown", "chancecubes:Mob_Tower");
		profile.addTriggers(new DifficultyTrigger(profile, Difficulty.PEACEFUL));
		registerProfile(profile);

		profile = new BasicProfile("no_area_of_effects", "No Area of Effect Rewards", "Disables rewards that place blocks that have a 3x3x3 area of effect or greater (Does not include rewards that reset blocks to their original state after)");
		profile.addDisabledRewards("chancecubes:Tnt_Structure", "chancecubes:TNT_Diamond", "chancecubes:STRING!",
				"chancecubes:CARPET!", "chancecubes:Squid_Horde", "chancecubes:D-rude_SandStorm", "chancecubes:Ice_Cold",
				"chancecubes:Watch_World_Burn", "chancecubes:Coal_To_Diamonds", "chancecubes:Hot_Tub",
				"chancecubes:Arrow_Trap", "chancecubes:Trampoline", "chancecubes:Cave_Spider_Web", "chancecubes:Guardians",
				"chancecubes:Path_To_Succeed", "chancecubes:Help_Me", "chancecubes:Beacon_Build", "chancecubes:Disco",
				"chancecubes:5_Prongs", "chancecubes:Table_Flip", "chancecubes:Sky_Block", "chancecubes:Double_Rainbow");
		registerProfile(profile);
		//config.save();
		//@formatter:on
	}

	//	public static void setupConfig(Configuration configuration)
	//	{
	//		config = configuration;
	//	}
}
