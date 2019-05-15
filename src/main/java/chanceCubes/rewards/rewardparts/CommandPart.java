package chanceCubes.rewards.rewardparts;

import chanceCubes.rewards.variableTypes.IntVar;
import chanceCubes.rewards.variableTypes.StringVar;
import java.util.stream.Stream;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class CommandPart extends BasePart
{
	private StringVar command;

	public CommandPart(String command)
	{
		this(command, 0);
	}

	public CommandPart(String command, int delay)
	{
		this(new StringVar(command), new IntVar(delay));
	}

	public CommandPart(StringVar command)
	{
		this(command, new IntVar(0));
	}

	public CommandPart(StringVar command, IntVar delay)
	{
		this.command = command;
		this.setDelay(delay);
	}

	public String getRawCommand()
	{
		return command.getValue();
	}

	public String getParsedCommand(Location location, Player player)
	{
		String parsedCommand = command.getValue();
		parsedCommand = parsedCommand.replace("%player", player.getName());
		parsedCommand = parsedCommand.replace("%x", "" + location.getBlockX());
		parsedCommand = parsedCommand.replace("%y", "" + location.getBlockY());
		parsedCommand = parsedCommand.replace("%z", "" + location.getBlockZ());
		Location playerLocation = player.getLocation();
		parsedCommand = parsedCommand.replace("%px", "" + playerLocation.getBlockX());
		parsedCommand = parsedCommand.replace("%py", "" + playerLocation.getBlockY());
		parsedCommand = parsedCommand.replace("%pz", "" + playerLocation.getBlockZ());
		parsedCommand = parsedCommand.replace("%puuid", "" + player.getUniqueId());
		BlockFace direction = Stream.of(BlockFace.values()).filter(blockFace -> blockFace.getDirection().equals(playerLocation.getDirection())).findFirst().orElse(BlockFace.UP);
		parsedCommand = parsedCommand.replace("%pdir", "" + direction.toString());
		parsedCommand = parsedCommand.replace("%pyaw", "" + playerLocation.getYaw());
		parsedCommand = parsedCommand.replace("%ppitch", "" + playerLocation.getPitch());
		return parsedCommand;
	}
}
