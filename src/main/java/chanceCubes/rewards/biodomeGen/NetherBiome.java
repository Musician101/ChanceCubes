package chanceCubes.rewards.biodomeGen;

import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.PigZombie;

public class NetherBiome implements IBioDomeBiome
{
	private Random rand = new Random();

	@Override
	public void spawnEntities(Location location)
	{
		for(int i = 0; i < rand.nextInt(10) + 5; i++)
		{
			int ri = rand.nextInt(5);

			if(ri == 0)
			{
				location.getWorld().spawn(location.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), Ghast.class);
			}
			else
			{
				location.getWorld().spawn(location.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), PigZombie.class);
			}
		}
	}

	@Override
	public Material getFloorBlock()
	{
		return Material.NETHERRACK;
	}

	@Override
	public void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay)
	{
		if(y != 0)
			return;

		if(dist < 0 && rand.nextInt(50) == 0)
		{
			OffsetBlock osb = new OffsetBlock(x, y - 1, z, Material.NETHERRACK, false, (delay / BioDomeReward.delayShorten));
			blocks.add(osb);
			delay++;
			osb = new OffsetBlock(x, y, z, Material.LAVA, false, (delay / BioDomeReward.delayShorten) + 1);
			blocks.add(osb);
			delay++;
		}
		else if(dist < 0 && rand.nextInt(20) == 0)
		{
			OffsetBlock osb = new OffsetBlock(x, y, z, Material.SOUL_SAND, false, (delay / BioDomeReward.delayShorten) + 1);
			blocks.add(osb);
			delay++;
		}
	}
}
