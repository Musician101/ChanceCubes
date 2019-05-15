package chanceCubes.rewards.biodomeGen;

import java.util.List;
import java.util.Random;

import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import org.bukkit.Location;
import org.bukkit.Material;

public class DesertBiome implements IBioDomeBiome
{
	@Override
	public Material getFloorBlock()
	{
		return Material.SANDSTONE;
	}

	public void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay)
	{
		if(y != 0)
			return;

		if(dist < 0 && rand.nextInt(50) == 0)
		{
			delay++;
			OffsetBlock osb = new OffsetBlock(x, y, z, Material.SAND, false, (delay / BioDomeReward.delayShorten) + 1);
			blocks.add(osb);
			osb = new OffsetBlock(x, y - 1, z, Material.SANDSTONE, false, (delay / BioDomeReward.delayShorten));
			blocks.add(osb);
			delay++;
			osb = new OffsetBlock(x, y + 1, z, Material.DEAD_BUSH, false, (delay / BioDomeReward.delayShorten));
			blocks.add(osb);
			delay++;
		}

		if(dist < 0 && rand.nextInt(60) == 0)
		{
			delay++;
			OffsetBlock osb = new OffsetBlock(x, y, z, Material.SAND, false, (delay / BioDomeReward.delayShorten) + 1);
			blocks.add(osb);
			osb = new OffsetBlock(x, y - 1, z, Material.SANDSTONE, false, (delay / BioDomeReward.delayShorten));
			blocks.add(osb);
			delay++;
			osb = new OffsetBlock(x, y + 1, z, Material.CACTUS, false, (delay / BioDomeReward.delayShorten));
			blocks.add(osb);
			delay++;
			osb = new OffsetBlock(x, y + 2, z, Material.CACTUS, false, (delay / BioDomeReward.delayShorten));
			blocks.add(osb);
			delay++;
			osb = new OffsetBlock(x, y + 3, z, Material.CACTUS, false, (delay / BioDomeReward.delayShorten));
			blocks.add(osb);
			delay++;
		}
	}

	@Override
	public void spawnEntities(Location location)
	{

	}
}
