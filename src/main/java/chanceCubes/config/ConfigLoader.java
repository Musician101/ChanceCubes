package chanceCubes.config;

import chanceCubes.CCubesCore;
import java.io.File;
import java.util.ArrayList;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Handles Configuration file management
 */
public class ConfigLoader
{
	public static final String genCat = "General Settings";
	public static final String rewardCat = "Rewards";
	public static final String giantRewardCat = "Giant Chance Cube Rewards";

	public static File folder;

	private ConfigLoader()
	{

	}

	public static void loadConfig()
	{
		CCubesCore plugin = CCubesCore.getInstance();
		File folder = plugin.getDataFolder();
		plugin.saveDefaultConfig();
		FileConfiguration config = plugin.getConfig();
		ConfigurationSection rewards = config.getConfigurationSection(rewardCat);
		ConfigurationSection generalCategory = rewards.getConfigurationSection(genCat);

		//CCubesSettings.nonReplaceableBlocksOverrides = NonreplaceableBlockOverride.parseStrings(config.getStringList("nonreplaceableBlockOverrides", genCat, new String[] { "minecraft:bedrock" }, "Blocks that ChanceCube rewards will be unable to replace or remove, can override IMC-added blocks by prefacing the block ID with \'-\'."));
		CCubesSettings.rangeMin = generalCategory.getInt("changeRangeMin", 10);
		CCubesSettings.rangeMax = generalCategory.getInt("changeRangeMax", 10);
		CCubesSettings.d20UseNormalChances = generalCategory.getBoolean("D20UseNormalChanceValues", false);
		CCubesSettings.enableHardCodedRewards = generalCategory.getBoolean("EnableDefaultRewards", true);
		CCubesSettings.pendantUses = generalCategory.getInt("pendantUses", 32);
		CCubesSettings.oreGeneration = generalCategory.getBoolean("GenerateAsOre", true);
		CCubesSettings.oreGenAmount = generalCategory.getInt("oreGenAmount", 4);
		CCubesSettings.surfaceGeneration = generalCategory.getBoolean("GenerateOnSurface", true);
		CCubesSettings.surfaceGenAmount = generalCategory.getInt("surfaceGenerationAmount", 100);
		CCubesSettings.blockedWorlds = generalCategory.contains("BlockedWorlds") ? generalCategory.getStringList("BlockedWorlds") : new ArrayList<>();
		CCubesSettings.chestLoot = generalCategory.getBoolean("ChestLoot", true);
		CCubesSettings.craftingRecipe = generalCategory.getBoolean("CraftingRecipe", true);
		CCubesSettings.dropHeight = generalCategory.getInt("FallingBlockDropHeight", 20);
		CCubesSettings.userSpecificRewards = generalCategory.getBoolean("UserSpecificRewards", true);
		CCubesSettings.disabledRewards = generalCategory.getBoolean("GloballyDisabledRewards", true);
		CCubesSettings.holidayRewards = generalCategory.getBoolean("HolidayRewards", true);
		CCubesSettings.holidayRewardTriggered = generalCategory.getBoolean("HolidayRewardTriggered", false);

		File customConfigFolder = new File(folder.getAbsolutePath() + "/CustomRewards");
		customConfigFolder.mkdirs();
		new CustomRewardsLoader(customConfigFolder);

		new File(folder.getAbsolutePath() + "/CustomRewards/Schematics").mkdirs();
		//new File(folder.getAbsolutePath() + "/CustomRewards/Sounds").mkdirs();

		File customProfileFolder = new File(folder.getAbsolutePath() + "/Profiles");
		customProfileFolder.mkdirs();
		new CustomProfileLoader(customConfigFolder);

		//ProfileManager.setupConfig(new Configuration(new File(folder + "/" + "Profiles.cfg")));
	}
}
