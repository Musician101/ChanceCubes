package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.config.CCubesSettings;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class ChunkFlipReward extends BaseCustomReward
{
	public ChunkFlipReward()
	{
		super(CCubesCore.MODID + ":Chunk_Flip", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		int z = location.getBlockZ() - (location.getBlockZ() % 16);
		int x = location.getBlockX() - (location.getBlockX() % 16);
		location.getWorld().playSound(location, Sound.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
		player.sendMessage("Inception!!!!");
		Scheduler.scheduleTask(new Task("Chunk_Flip_Delay", -1, 10)
		{
			private int y = 0;

			@Override
			public void callback()
			{
			}

			@Override
			public void update()
			{
				World world = location.getWorld();
				if(y >= world.getMaxHeight() / 2)
				{
					Scheduler.removeTask(this);
					return;
				}

				for(int zz = 0; zz < 16; zz++)
				{
					for(int xx = 0; xx < 16; xx++)
					{
						Location pos1 = new Location(location.getWorld(), x + xx, y, z + zz);
						Location pos2 = new Location(location.getWorld(), x + xx, location.getWorld().getMaxHeight() - y, z + zz);
						Block b = world.getBlockAt(pos1);
						Block b2 = world.getBlockAt(pos2);
						BlockState s = b.getState();
						BlockState s2 = b2.getState();
						BlockData d = b.getBlockData();
						BlockData d2 = b2.getBlockData();
						MaterialData te1 = b.getState().getData();
						MaterialData te2 = b2.getState().getData();

						if(b.getType() != Material.GRAVEL && !CCubesBlocks.isGiantCube(b) && !RewardsUtil.isBlockUnbreakable(location) && !CCubesSettings.nonReplaceableBlocks.contains(location.getBlock().getBlockData()))
						{
							s.setBlockData(d2);
							s.setData(te2);
							s.update(true);
							s.setBlockData(d);
							s2.setData(te1);
							s2.update(true);
						}
					}
				}
				y++;
			}
		});
	}
}
