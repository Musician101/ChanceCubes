package chanceCubes.config;

import chanceCubes.rewards.IChanceCubeReward;
import chanceCubes.util.NonReplaceableBlockOverride;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.block.data.BlockData;

public class CCubesSettings
{
	public static int d20RenderID = -1;

	public static int pendantUses;

	public static boolean enableHardCodedRewards;

	public static int rangeMin;
	public static int rangeMax;
	public static boolean d20UseNormalChances;

	public static boolean oreGeneration;
	public static int oreGenAmount;
	public static boolean surfaceGeneration;
	public static int surfaceGenAmount;
	public static List<String> blockedWorlds;
	public static boolean chestLoot;
	public static boolean craftingRecipe;

	public static boolean userSpecificRewards;
	public static boolean disabledRewards;

	public static boolean holidayRewards;
	public static boolean holidayRewardTriggered;
	public static boolean doesHolidayRewardTrigger = false;
	public static IChanceCubeReward holidayReward = null;
	public static boolean hasHolidayTexture = false;
	public static String holidayTextureName = "";

	public static int dropHeight;

	public static List<BlockData> nonReplaceableBlocksIMC = new ArrayList<>();
	public static List<BlockData> nonReplaceableBlocks = new ArrayList<>();
	public static List<NonReplaceableBlockOverride> nonReplaceableBlocksOverrides = new ArrayList<>();
	public static List<BlockData> backupNRB = new ArrayList<>();

	public static boolean testRewards;
	public static boolean testCustomRewards;
	public static int testingRewardIndex = 0;

	public static boolean isBlockedWorld(String world)
	{
		return blockedWorlds.stream().anyMatch(worldName -> worldName.equals(world));
	}
}
