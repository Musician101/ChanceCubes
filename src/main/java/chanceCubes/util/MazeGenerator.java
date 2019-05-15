package chanceCubes.util;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Sign;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MazeGenerator
{
	private int width;
	private int height;
	private int[][] map;
	private ArrayList<Location2I> walls = new ArrayList<>();
	private RewardBlockCache cache;
	private Random r = new Random();

	private int currentX = 1;
	private int currentY = 1;

	private final int nonWall = 0;
	private final int wall = 1;

	private Location startPos;
	private Location2I endBlock;
	public Location endBlockWorldCords;

	public MazeGenerator(Location location, Location playerLoc)
	{
		cache = new RewardBlockCache(location, playerLoc);
		startPos = location;
	}

	/**
	 * @param width
	 * @param height
	 */
	public void generate(int width, int height)
	{
		this.width = width;
		this.height = height;
		map = new int[width][height];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x++)
				map[x][y] = wall;

		map[1][1] = nonWall;

		walls.add(new Location2I(1, 1));
		Location2I current = new Location2I(0, 0);
		Location2I north;
		Location2I east;
		Location2I south;
		Location2I west;

		do
		{
			int randomLoc = r.nextInt(walls.size());
			currentX = walls.get(randomLoc).getX();
			currentY = walls.get(randomLoc).getY();
			current.setXY(currentX, currentY);
			north = current.add(0, -1);
			east = current.add(1, 0);
			south = current.add(0, 1);
			west = current.add(-1, 0);

			if(!checkWalls(current))
			{
				map[currentX][currentY] = nonWall;
				walls.remove(randomLoc);

				if((north.getY() > 0) && (map[north.getX()][north.getY()] == wall))
					if(map[north.getX()][north.getY() - 1] == wall && !walls.contains(north))
						walls.add(north);

				if((east.getX() + 1 < width) && (map[east.getX()][east.getY()] == wall))
					if(map[east.getX() + 1][east.getY()] == wall && !walls.contains(east))
						walls.add(east);

				if((south.getY() + 1 < height) && (map[south.getX()][south.getY()] == wall))
					if(map[south.getX()][south.getY() + 1] == wall && !walls.contains(south))
						walls.add(south);

				if((west.getX() > 0) && (map[west.getX()][west.getY()] == wall))
					if(map[west.getX() - 1][west.getY()] == wall && !walls.contains(west))
						walls.add(west);
			}
			else
			{
				walls.remove(randomLoc);
			}
		} while(walls.size() > 0);

		int endBlockX = width - 1;
		int endBlockZ = height - 1;
		boolean run = true;
		int i = 0;
		while(run)
		{
			for(int xx = 0; xx <= i; xx++)
			{
				for(int zz = i; zz >= 0; zz--)
				{
					if(this.map[endBlockX - xx][endBlockZ - zz] == this.nonWall && run)
					{
						endBlock = new Location2I(endBlockX - xx, endBlockZ - zz);
						run = false;
					}
				}
			}
			i++;
		}

		placeBlocks();
	}

	private boolean checkWalls(Location2I loc)
	{
		Location2I north = loc.add(0, -1);
		Location2I east = loc.add(1, 0);
		Location2I south = loc.add(0, 1);
		Location2I west = loc.add(-1, 0);

		int yes = 0;
		if(north.getY() >= 0 && map[north.getX()][north.getY()] == nonWall)
			yes++;
		if(east.getX() < width && map[east.getX()][east.getY()] == nonWall)
			yes++;
		if(south.getY() < height && map[south.getX()][south.getY()] == nonWall)
			yes++;
		if(west.getX() >= 0 && map[west.getX()][west.getY()] == nonWall)
			yes++;
		return yes > 1;
	}

	private void placeBlocks()
	{
		int xoff = -(this.width / 2);
		int zoff = -(this.height / 2);

		for(int xx = 0; xx < this.width; xx++)
		{
			for(int zz = 0; zz < this.height; zz++)
			{
				if(this.map[xx][zz] == 0)
				{
					cache.cacheBlock(new Vector(xoff + xx, -1, zoff + zz), Material.BEDROCK.createBlockData());
					cache.cacheBlock(new Vector(xoff + xx, 0, zoff + zz), Material.TORCH.createBlockData());
					cache.cacheBlock(new Vector(xoff + xx, 1, zoff + zz), Material.AIR.createBlockData());
					cache.cacheBlock(new Vector(xoff + xx, 2, zoff + zz), Material.BEDROCK.createBlockData());
				}
				else
				{
					cache.cacheBlock(new Vector(xoff + xx, -1, zoff + zz), Material.AIR.createBlockData());
					cache.cacheBlock(new Vector(xoff + xx, 0, zoff + zz), Material.BEDROCK.createBlockData());
					cache.cacheBlock(new Vector(xoff + xx, 1, zoff + zz), Material.BEDROCK.createBlockData());
					cache.cacheBlock(new Vector(xoff + xx, 2, zoff + zz), Material.AIR.createBlockData());
				}
			}
		}

		endBlockWorldCords = startPos.clone().add(xoff + this.endBlock.getX(), 0, zoff + this.endBlock.getY());
		Sign sign = (Sign) Material.SIGN.createBlockData();
		sign.setRotation(BlockFace.NORTH_NORTH_WEST);
		cache.cacheBlock(new Vector(xoff + this.endBlock.getX(), 0, zoff + this.endBlock.getY()), sign);
		org.bukkit.block.Sign signBlock = (org.bukkit.block.Sign) startPos.clone().add(xoff + endBlock.getX(), 0, zoff + endBlock.getY()).getBlock().getState();
		signBlock.setLine(0, "Break Me");
		signBlock.setLine(1, "To beat the");
		signBlock.setLine(2, "Maze");
	}

	public void endMaze(Player player)
	{
		cache.restoreBlocks(player);
	}
}
