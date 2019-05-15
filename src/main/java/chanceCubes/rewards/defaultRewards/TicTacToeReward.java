package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardBlockCache;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class TicTacToeReward extends BaseCustomReward
{
	public TicTacToeReward()
	{
		super(CCubesCore.MODID + ":Tic_Tac_Toe", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		RewardBlockCache cache = new RewardBlockCache(location, player.getLocation());
		player.sendMessage("Lets play Tic-Tac-Toe!");
		player.sendMessage("Beat the Computer to get 500 Diamonds!");
		player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.RED_WOOL, 5));

		for(int x = -2; x < 3; x++)
			for(int z = -1; z < 2; z++)
				for(int y = 0; y < 5; y++)
					cache.cacheBlock(new Vector(x, y, z), Material.AIR.createBlockData());

		location.clone().add(-1, 0, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(-1, 1, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(-1, 2, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(-1, 3, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(-1, 4, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(1, 0, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(1, 1, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(1, 2, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(1, 3, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(1, 4, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(-2, 1, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(0, 1, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(2, 1, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(-2, 3, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(0, 3, 0).getBlock().setType(Material.BEDROCK);
		location.clone().add(2, 3, 0).getBlock().setType(Material.BEDROCK);

		Scheduler.scheduleTask(new Task("Tic_Tac_Toe_Game", 6000, 5)
		{
			Board board = new Board();

			@Override
			public void callback()
			{
				cache.restoreBlocks(player);
			}

			@Override
			public void update()
			{
				for(int x = -1; x < 2; x++)
					for(int y = -1; y < 2; y++)
						if(board.board[x + 1][y + 1] == 0)
							if(!RewardsUtil.isAir(location.clone().add(x * 2, y * 2 + 2, 0).getBlock()))
								makeMove(x + 1, y + 1);
			}

			private void makeMove(int x, int y)
			{
				board.placeMove(x, y, 2);

				if(!board.isGameOver())
				{
					//Make CPU Move
					board.minimax(0, 1);
					board.placeMove(board.computersMove.x, board.computersMove.y, 1);
					location.clone().add(board.computersMove.x * 2 - 2, board.computersMove.y * 2, 0).getBlock().setType(Material.BLUE_WOOL);
				}

				if(board.isGameOver())
				{
					if(board.hasCPUWon())
						player.sendMessage("The Computer won! Better luck next time!");
					else if(board.hasPlayerWon())
						player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.DIAMOND, 500));
					else
						player.sendMessage("You tied! Better luck next time!");

					Task superTask = this;
					Scheduler.scheduleTask(new Task("Tic_Tac_Toe_Game_End_Delay", 40)
					{
						@Override
						public void callback()
						{
							superTask.delayLeft = 0;
						}
					});

				}
			}
		});
	}

	private static class Point
	{
		int x, y;

		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}

	private static class Board
	{
		public int[][] board = new int[3][3];
		public Point computersMove;

		public boolean isGameOver()
		{
			return(hasPlayerWon() || hasCPUWon() || getAvailableStates().isEmpty());
		}

		public boolean hasCPUWon()
		{
			if((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1))
				return true;
			for(int i = 0; i < 3; ++i)
				if(((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1) || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1)))
					return true;
			return false;
		}

		public boolean hasPlayerWon()
		{
			if((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2))
				return true;
			for(int i = 0; i < 3; ++i)
				if((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2) || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2))
					return true;
			return false;
		}

		public List<Point> getAvailableStates()
		{
			List<Point> availablePoints = new ArrayList<>();
			for(int i = 0; i < 3; ++i)
				for(int j = 0; j < 3; ++j)
					if(board[i][j] == 0)
						availablePoints.add(new Point(i, j));
			return availablePoints;
		}

		public void placeMove(int x, int y, int player)
		{
			board[x][y] = player;
		}

		public int minimax(int depth, int turn)
		{
			//Game status...
			if(hasCPUWon())
				return +1;
			if(hasPlayerWon())
				return -1;

			List<Point> pointsAvailable = getAvailableStates();
			if(pointsAvailable.isEmpty())
				return 0;
			int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
			for(int i = 0; i < pointsAvailable.size(); ++i)
			{
				Point point = pointsAvailable.get(i);
				if(turn == 1)
				{
					placeMove(point.x, point.y, 1);
					int currentScore = minimax(depth + 1, 2);
					max = Math.max(currentScore, max);

					if(currentScore >= 0 && depth == 0)
						computersMove = point;

					if(currentScore == 1)
					{
						board[point.x][point.y] = 0;
						break;
					}

					if(i == pointsAvailable.size() - 1 && max < 0 && depth == 0)
						computersMove = point;
				}
				else if(turn == 2)
				{
					placeMove(point.x, point.y, 2);
					int currentScore = minimax(depth + 1, 1);
					min = Math.min(currentScore, min);
					if(min == -1)
					{
						board[point.x][point.y] = 0;
						break;
					}
				}
				board[point.x][point.y] = 0;
			}
			return turn == 1 ? max : min;
		}
	}
}
