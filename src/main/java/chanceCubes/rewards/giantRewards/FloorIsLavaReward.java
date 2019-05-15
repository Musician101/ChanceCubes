package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class FloorIsLavaReward extends BaseCustomReward
{
	public FloorIsLavaReward()
	{
		super(CCubesCore.MODID + ":Floor_Is_Lava", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		player.sendMessage("Quick! The Floor is lava!");
		List<OffsetBlock> blocks = new ArrayList<>();
		int delay = 0;
		for(int yy = location.getBlockY() + 5; yy > location.getBlockY() - 5; yy--)
		{
			int xx = 0, zz = 0, dx = 0, dy = -1;
			int t = 32;
			int maxI = t * t;

			for(int i = 0; i < maxI; i++)
			{
				if((-16 / 2 <= xx) && (xx <= 16 / 2) && (-16 / 2 <= zz) && (zz <= 16 / 2))
				{
					Block blockAt = new Location(location.getWorld(), location.getX() + xx, yy, location.getZ() + zz).getBlock();
					if(blockAt.getType() != (Material.AIR) && !CCubesBlocks.isChanceCube(blockAt))
					{
						blocks.add(new OffsetBlock(xx, yy - location.getBlockY(), zz, Material.LAVA, false, delay));
						delay++;
					}
				}

				if((xx == zz) || ((xx < 0) && (xx == -zz)) || ((xx > 0) && (xx == 1 - zz)))
				{
					t = dx;
					dx = -dy;
					dy = t;
				}
				xx += dx;
				zz += dy;
			}
		}

		blocks.forEach(b -> b.spawnInWorld(location));
	}
}
