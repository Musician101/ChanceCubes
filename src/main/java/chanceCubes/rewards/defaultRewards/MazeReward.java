package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.MazeGenerator;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MazeReward extends BaseCustomReward
{
	public MazeReward()
	{
		super(CCubesCore.MODID + ":Maze", -25);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		player.sendMessage("Generating maze..... May be some lag...");
		final MazeGenerator gen = new MazeGenerator(location, player.getLocation());
		gen.generate(20, 20);
		Location initialPos = location.clone().add(-8, 0, -8);
		player.teleport(initialPos);

		Scheduler.scheduleTask(new Task("Maze_Reward_Update", 900, 20)
		{
			@Override
			public void callback()
			{
				gen.endMaze(player);
				if(RewardsUtil.isPlayerOnline(player))
					player.damage(Float.MAX_VALUE);
			}

			@Override
			public void update()
			{
				if(initialPos.distance(player.getLocation()) < 2)
				{
					this.delayLeft++;
					return;
				}

				if(this.delayLeft % 20 == 0)
					this.showTimeLeft(player);

				if(gen.endBlockWorldCords.getBlock().getType() != Material.SIGN)
				{
					gen.endMaze(player);
					player.sendMessage("Hey! You won!");
					player.sendMessage("Here, have a item!");
					location.getWorld().dropItem(player.getLocation(), new ItemStack(RewardsUtil.getRandomItem()));
					Scheduler.removeTask(this);
				}
			}
		});

		player.sendMessage("Beat the maze and find the sign!");
		player.sendMessage("You have 45 seconds!");
	}
}
