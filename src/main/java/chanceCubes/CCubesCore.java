package chanceCubes;

import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.commands.CCubesServerCommands;
import chanceCubes.config.ConfigLoader;
import chanceCubes.config.CustomProfileLoader;
import chanceCubes.config.CustomRewardsLoader;
import chanceCubes.items.CCubesItems;
import chanceCubes.listeners.BlockListener;
import chanceCubes.listeners.PlayerConnectListener;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.registry.GiantCubeRegistry;
import chanceCubes.rewards.profiles.ProfileManager;
import chanceCubes.rewards.profiles.TriggerHooks;
import chanceCubes.rewards.profiles.triggerHooks.VanillaTriggerHooks;
import chanceCubes.util.CubeStorage;
import chanceCubes.util.NonReplaceableBlockOverride;
import chanceCubes.util.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CCubesCore extends JavaPlugin
{
	public static final String MODID = "chancecubes";
	public static final String VERSION = "@VERSION@";

	public static final String gameVersion = "1.13.2";

	TriggerHooks triggerHooks;
	private CubeStorage cubeStorage;

	public CubeStorage getCubeStorage()
	{
		return cubeStorage;
	}

	@Override
	public void onEnable()
	{
		ConfigLoader.loadConfig();
		cubeStorage = new CubeStorage();
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerConnectListener(), CCubesCore.getInstance());
		pm.registerEvents(new VanillaTriggerHooks(), CCubesCore.getInstance());
		pm.registerEvents(new BlockListener(), CCubesCore.getInstance());
		ChanceCubeRegistry.loadDefaultRewards();
		GiantCubeRegistry.loadDefaultRewards();
		CustomRewardsLoader.instance.loadCustomRewards();
		CustomRewardsLoader.instance.fetchRemoteInfo();
		ProfileManager.initProfiles();
		CustomProfileLoader.instance.loadProfiles();
		NonReplaceableBlockOverride.loadOverrides();

		Bukkit.getPluginCommand("chancecubes").setExecutor(new CCubesServerCommands());
		triggerHooks = new TriggerHooks();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			triggerHooks.run();
			Scheduler.tickTasks();
		}, 1L, 1L);
		ShapedRecipe cubeScanner = new ShapedRecipe(new NamespacedKey(this, "cube_scanner"), CCubesItems.getScanner());
		cubeScanner.setIngredient('G', Material.GLASS);
		cubeScanner.setIngredient('I', Material.IRON_INGOT);
		cubeScanner.setIngredient('D', Material.DIAMOND_BLOCK);
		cubeScanner.shape("IGI", "GDG", "IGI");
		Bukkit.addRecipe(cubeScanner);
		ShapedRecipe chanceCube = new ShapedRecipe(new NamespacedKey(this, "chance_cube"), CCubesBlocks.getChanceCube(1));
		chanceCube.setIngredient('L', Material.LAPIS_BLOCK);
		chanceCube.setIngredient('B', Material.LAPIS_LAZULI);
		chanceCube.shape("LLL", "LBL", "LLL");
		Bukkit.addRecipe(chanceCube);
		getLogger().info("Death and destruction prepared! (And Cookies. Cookies were also prepared.)");
	}

	@Override
	public void onDisable()
	{
		cubeStorage.save();
	}

	public static CCubesCore getInstance()
	{
		return JavaPlugin.getPlugin(CCubesCore.class);
	}
}
