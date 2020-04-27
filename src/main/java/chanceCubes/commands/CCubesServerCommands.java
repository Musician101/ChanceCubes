package chanceCubes.commands;

import chanceCubes.CCubesCore;
import chanceCubes.config.CCubesSettings;
import chanceCubes.config.CustomProfileLoader;
import chanceCubes.config.CustomRewardsLoader;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.registry.GiantCubeRegistry;
import chanceCubes.rewards.profiles.ProfileManager;
import chanceCubes.util.GiantCubeUtil;
import chanceCubes.util.NonReplaceableBlockOverride;
import chanceCubes.util.RewardsUtil;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.sqlite.util.StringUtils;

public class CCubesServerCommands implements TabExecutor
{
	private boolean executeReload(CommandSender sender)
	{
		new Thread(() -> {
			ChanceCubeRegistry.INSTANCE.ClearRewards();
			GiantCubeRegistry.INSTANCE.ClearRewards();
			ProfileManager.clearProfiles();
			ChanceCubeRegistry.loadDefaultRewards();
			GiantCubeRegistry.loadDefaultRewards();
			CustomRewardsLoader.instance.loadCustomRewards();
			CustomRewardsLoader.instance.fetchRemoteInfo();
			ChanceCubeRegistry.loadCustomUserRewards();
			NonReplaceableBlockOverride.loadOverrides();
			ProfileManager.initProfiles();
			CustomProfileLoader.instance.loadProfiles();
			sender.sendMessage(ChatColor.GREEN + "Rewards Reloaded");
		}).start();
		return true;
	}

	private boolean executeVersion(CommandSender sender)
	{
		sender.sendMessage("Chance Cubes Version " + CCubesCore.VERSION);
		return true;
	}

	private boolean executeHandNBT(CommandSender sender)
	{
		Player player = (Player) sender;
		NBTTagCompound nbt = CraftItemStack.asNMSCopy(player.getInventory().getItemInMainHand()).getOrCreateTag();
		player.sendMessage(nbt.toString());
		return true;
	}

	private boolean executeHandID(CommandSender sender)
	{
		Player player = (Player) sender;
		ItemStack stack = player.getInventory().getItemInMainHand();
		if(stack.getType() != Material.AIR)
		{
			player.sendMessage(stack.getType().getKey().toString());
		}

		return true;
	}

	private boolean executeDisableReward(CommandSender sender, String reward)
	{
		Player player = (Player) sender;
		if(ChanceCubeRegistry.INSTANCE.disableReward(reward))
			player.sendMessage(ChatColor.GREEN + reward + " Has been temporarily disabled.");
		else
			player.sendMessage(ChatColor.RED + reward + " is either not currently enabled or is not a valid reward name.");

		return true;
	}

	private boolean executeEnableReward(CommandSender sender, String reward)
	{
		Player player = (Player) sender;
		if(ChanceCubeRegistry.INSTANCE.enableReward(reward))
			player.sendMessage(ChatColor.GREEN + reward + " Has been enabled.");
		else
			player.sendMessage(ChatColor.RED + reward + " is either not currently disabled or is not a valid reward name.");

		return true;
	}

	private boolean executeRewardInfo(CommandSender sender)
	{
		sender.sendMessage("There are currently " + ChanceCubeRegistry.INSTANCE.getNumberOfLoadedRewards() + " rewards loaded and " + ChanceCubeRegistry.INSTANCE.getNumberOfDisabledRewards() + " rewards disabled");
		return true;
	}

	private boolean executeTestRewards(CommandSender sender)
	{
		CCubesSettings.testRewards = !CCubesSettings.testRewards;
		CCubesSettings.testingRewardIndex = 0;
		if(CCubesSettings.testRewards)
			sender.sendMessage("Reward testing is now enabled for all rewards!");
		else
			sender.sendMessage("Reward testing is now disabled and normal randomness is back.");
		return true;
	}

	private boolean executeTestCustomRewards(CommandSender sender)
	{
		CCubesSettings.testCustomRewards = !CCubesSettings.testCustomRewards;
		CCubesSettings.testingRewardIndex = 0;
		if(CCubesSettings.testCustomRewards)
			sender.sendMessage("Reward testing is now enabled for custom rewards!");
		else
			sender.sendMessage("Reward testing is now disabled and normal randomness is back.");
		return true;
	}

	private boolean executeTest(CommandSender sender)
	{
		return true;
	}

	private boolean executeSchematicCreate(CommandSender sender)
	{
		sender.sendMessage("This function does not work on the Forge version. Sorry :(");
		return true;
	}

	private boolean executeSchematicCancel(CommandSender sender)
	{
		sender.sendMessage("This function does not work on the Forge version. Sorry :(");
		return true;
	}

	private boolean executeSpawnGiantCube(CommandSender sender, Location location)
	{
		Player player = (Player) sender;
		World world = player.getWorld();
		if(RewardsUtil.isBlockUnbreakable(location) && CCubesSettings.nonReplaceableBlocks.contains(location.getBlock().getBlockData()))
			return true;

		GiantCubeUtil.setupStructure(location.clone().subtract(1, 1, 1), true);
		world.playSound(location, Sound.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
		return true;
	}

	private Map<String, CommandExecutor> subCommands = ImmutableMap.<String, CommandExecutor>builder()
			.put("reload", (sender, command, label, args) -> executeReload(sender))
			.put("version", (sender, command, label, args) -> executeVersion(sender))
			.put("handnbt", (sender, command, label, args) -> executeHandNBT(sender))
			.put("handid", (sender, command, label, args) -> executeHandID(sender))
			.put("disablereward", new TabExecutor()
			{
				@Override
				public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args)
				{
					if (args.length == 0) {
						sender.sendMessage(ChatColor.RED + "/chancecubes disableReward <rewardName>");
						return false;
					}

					return executeDisableReward(sender, args[0]);
				}

				@Override
				public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,@Nonnull  String[] args)
				{
					Set<String> rewards = ChanceCubeRegistry.INSTANCE.getRewardNames();
					if (args.length == 0) {
						return new ArrayList<>(rewards);
					}

					return rewards.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
				}
			})
			.put("enableReward", new TabExecutor()
			{
				@Override
				public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args)
				{
					if (args.length == 0) {
						sender.sendMessage(ChatColor.RED + "/chancecubes enableReward <rewardName>");
						return false;
					}

					return executeEnableReward(sender, args[0]);
				}

				@Override
				public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,@Nonnull  String[] args)
				{
					Set<String> rewards = ChanceCubeRegistry.INSTANCE.getRewardNames();
					if (args.length == 0) {
						return new ArrayList<>(rewards);
					}

					return rewards.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
				}
			})
			.put("schematic", new TabExecutor()
			{
				private Map<String, CommandExecutor> schematicSubCommands = ImmutableMap.<String, CommandExecutor>builder()
						.put("create", (sender, command, label, args) -> executeSchematicCreate(sender))
						.put("cancel", (sender, command, label, args) -> executeSchematicCancel(sender)).build();

				@Override
				public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args)
				{
					if (args.length == 0) {
						sender.sendMessage(ChatColor.RED + "/chancecubes schematic <create | cancel>");
						return false;
					}

					return schematicSubCommands.getOrDefault(args[0], (commandSender, command1, s, strings) -> {
						sender.sendMessage(ChatColor.RED + "/chancecubes schematic <create | cancel>");
						return false;
					}).onCommand(sender, command, label, args);
				}

				@Override
				public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,@Nonnull  String[] args)
				{
					Set<String> sub = schematicSubCommands.keySet();
					if (args.length <= 1) {
						return new ArrayList<>(sub);
					}

					return sub.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
				}
			})
			.put("rewardsInfo", (sender, command, label, args) -> executeRewardInfo(sender))
			.put("test", (sender, command, label, args) -> executeTest(sender))
			.put("testRewards", (sender, command, label, args) -> executeTestRewards(sender))
			.put("testCustomRewards", (sender, command, label, args) -> executeTestCustomRewards(sender))
			.put("spawnGiantCube", (sender, command, label, args) -> {
				if (args.length < 3) {
					sender.sendMessage(ChatColor.RED + "/chancecubes spawnGiantCube <x> <y> <z>");
					return false;
				}

				int x;
				try {
					x = Integer.parseInt(args[0].replace("~", ""));
				}
				catch(NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + args[0] + " is not a valid coordinate." );
					return false;
				}

				int y;
				try {
					y = Integer.parseInt(args[1].replace("~", ""));
				}
				catch(NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + args[1] + " is not a valid coordinate." );
					return false;
				}

				int z;
				try {
					z = Integer.parseInt(args[2].replace("~", ""));
				}
				catch(NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + args[2] + " is not a valid coordinate." );
					return false;
				}

				return executeSpawnGiantCube(sender, new Location(((Player) sender).getWorld(), x, y, z));
			}).build();

	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args)
	{
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This is a player only command.");
			return false;
		}

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "/chancecubes <" + StringUtils.join(new ArrayList<>(subCommands.keySet()), " | ") + ">");
			return false;
		}

		CommandExecutor ce = subCommands.getOrDefault(args[0].toLowerCase(), ((commandSender, command1, s, strings) -> {
			sender.sendMessage(ChatColor.RED + "/chancecubes <" + StringUtils.join(new ArrayList<>(subCommands.keySet()), " | ") + ">");
			return false;
		}));

		String[] newArgs = new String[args.length - 1];
		System.arraycopy(args, 1, newArgs, 0, args.length - 1);
		return ce.onCommand(sender, command, label, newArgs);
	}

	@Override
	public List<String> onTabComplete(@Nonnull CommandSender commandSender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args)
	{
		Set<String> sub = subCommands.keySet();
		if (args.length == 0) {
			return new ArrayList<>(sub);
		}

		return sub.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
	}
}
