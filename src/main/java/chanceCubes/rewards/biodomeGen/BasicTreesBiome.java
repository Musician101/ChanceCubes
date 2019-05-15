package chanceCubes.rewards.biodomeGen;

import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Sheep;

public class BasicTreesBiome implements IBioDomeBiome
{
	private Random rand = new Random();

	@Override
	public Material getFloorBlock()
	{
		return Material.GRASS_BLOCK;
	}

	@Override
	public void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay)
	{
		if(y != 0)
			return;
		if(dist < 0 && rand.nextInt(5) == 0)
		{
			OffsetBlock osb = new OffsetBlock(x, y + 1, z, Material.TALL_GRASS, false, (delay / BioDomeReward.delayShorten));
			osb.setBlockData(Material.TALL_GRASS.createBlockData());
			blocks.add(osb);
		}
		else if(dist < -5 && rand.nextInt(100) == 0)
		{
			List<OffsetBlock> treeblocks = this.addTree(x, y, z, (delay / BioDomeReward.delayShorten));
			blocks.addAll(treeblocks);
		}
	}

	public List<OffsetBlock> addTree(int x, int y, int z, int delay)
	{
		List<OffsetBlock> blocks = new ArrayList<>();

		for(int yy = 1; yy < 6; yy++)
		{
			blocks.add(new OffsetBlock(x, y + yy, z, Material.OAK_LOG, false, delay));
			delay++;
		}

		for(int xx = -2; xx < 3; xx++)
		{
			for(int zz = -2; zz < 3; zz++)
			{
				for(int yy = 0; yy < 2; yy++)
				{
					if((xx != 0 || zz != 0))
					{
						blocks.add(new OffsetBlock(x + xx, y + 4 + yy, z + zz, Material.OAK_LEAVES, false, delay));
						delay++;
					}
				}
			}
		}

		blocks.add(new OffsetBlock(x + 1, y + 6, z, Material.OAK_LEAVES, false, delay));
		delay++;
		blocks.add(new OffsetBlock(x - 1, y + 6, z, Material.OAK_LEAVES, false, delay));
		delay++;
		blocks.add(new OffsetBlock(x, y + 6, z + 1, Material.OAK_LEAVES, false, delay));
		delay++;
		blocks.add(new OffsetBlock(x, y + 6, z - 1, Material.OAK_LEAVES, false, delay));
		delay++;
		blocks.add(new OffsetBlock(x, y + 6, z, Material.OAK_LEAVES, false, delay));
		delay++;

		return blocks;
	}

	@Override
	public void spawnEntities(Location center)
	{
		for(int i = 0; i < rand.nextInt(10) + 5; i++)
		{
			int ri = rand.nextInt(5);

			if(ri == 0)
				center.getWorld().spawn(center.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), Chicken.class);
			else if(ri == 1)
				center.getWorld().spawn(center.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), Cow.class);
			else if(ri == 2)
				center.getWorld().spawn(center.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), Horse.class);
			else if(ri == 3)
				center.getWorld().spawn(center.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), Pig.class);
			else if(ri == 4)
				center.getWorld().spawn(center.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), Sheep.class);
		}
	}
}
