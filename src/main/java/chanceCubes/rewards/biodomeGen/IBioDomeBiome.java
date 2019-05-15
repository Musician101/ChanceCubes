package chanceCubes.rewards.biodomeGen;

import chanceCubes.rewards.rewardparts.OffsetBlock;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;

public interface IBioDomeBiome
{
	Material getFloorBlock();

	void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay);

	void spawnEntities(Location center);
}
