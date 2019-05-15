package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class BlockInfectionReward extends BaseCustomReward
{
	// @formatter:off
	private Material[] whitelist = { Material.OBSIDIAN, Material.DIRT, Material.STONE,
			Material.MELON, Material.BOOKSHELF, Material.CLAY,
			Material.RED_WOOL,
			Material.BRICKS, Material.COBWEB, Material.GLOWSTONE,
			Material.NETHERRACK};
	// @formatter:on

	private Vector[] touchingPos = { new Vector(1, 0, 0), new Vector(0, 0, 1), new Vector(0, 1, 0), new Vector(-1, 0, 0), new Vector(0, 0, -1), new Vector(0, -1, 0) };

	public BlockInfectionReward()
	{
		super(CCubesCore.MODID + ":World_Infection", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		int delay = 0;
		int delayShorten = 20;

		Location lastLoc = location;
		List<Location> possibleBlocks = new ArrayList<>();
		List<Location> changedBlocks = new ArrayList<>();
		changedBlocks.add(new Location(location.getWorld(), 0, 0, 0));
		List<OffsetBlock> blocks = new ArrayList<>();
		addSurroundingBlocks(lastLoc, new Location(location.getWorld(), 0, 0, 0), changedBlocks, possibleBlocks);

		for(int i = 0; i < 5000; i++)
		{
			Location nextPos;
			if(possibleBlocks.size() > 0)
			{
				int index = RewardsUtil.rand.nextInt(possibleBlocks.size());
				nextPos = possibleBlocks.get(index);
				possibleBlocks.remove(index);
			}
			else
			{
				nextPos = lastLoc.add(touchingPos[RewardsUtil.rand.nextInt(touchingPos.length)]);
			}

			changedBlocks.add(nextPos);
			addSurroundingBlocks(location, nextPos, changedBlocks, possibleBlocks);
			Material state = whitelist[RewardsUtil.rand.nextInt(whitelist.length)];
			blocks.add(new OffsetBlock(nextPos.getBlockX(), nextPos.getBlockY(), nextPos.getBlockZ(), state, false, delay / delayShorten));
			delay++;
			lastLoc = nextPos;
		}

		for(OffsetBlock b : blocks)
			b.spawnInWorld(location);

	}

	private void addSurroundingBlocks(Location location, Location offsetCord, List<Location> changedBlocks, List<Location> possibleBlocks)
	{
		for(Vector pos : touchingPos)
		{
			Location checkPos = offsetCord.clone().add(pos);
			if(!changedBlocks.contains(checkPos) && !possibleBlocks.contains(checkPos))
			{
				if(RewardsUtil.isAir(location.clone().add(checkPos).getBlock()))
				{
					possibleBlocks.add(checkPos);
				}
			}
		}
	}
}
