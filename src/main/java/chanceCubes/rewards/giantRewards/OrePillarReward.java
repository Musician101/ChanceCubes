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

public class OrePillarReward extends BaseCustomReward
{
	public OrePillarReward()
	{
		super(CCubesCore.MODID + ":Ore_Pillars", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		List<OffsetBlock> blocks = new ArrayList<>();
		int delay = 0;
		for(int i = 0; i < RewardsUtil.rand.nextInt(4) + 2; i++)
		{
			int xx = RewardsUtil.rand.nextInt(30) - 15;
			int zz = RewardsUtil.rand.nextInt(30) - 15;
			for(int yy = 1; yy < 255; yy++)
			{
				Material ore = RewardsUtil.getRandomOre();
				OffsetBlock osb = new OffsetBlock(xx, yy - location.getBlockY(), zz, ore, false, delay / 3);
				osb.setBlockData(ore.createBlockData());
				blocks.add(osb);
				delay++;
			}
		}

		blocks.forEach(b -> b.spawnInWorld(location));
	}
}
