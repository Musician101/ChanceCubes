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

public class MixedFluidSphereReward extends BaseCustomReward
{
	public MixedFluidSphereReward()
	{
		super(CCubesCore.MODID + ":Mixed_Fluid_Sphere", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		List<OffsetBlock> blocks = new ArrayList<>();

		int delay = 0;
		for(int i = 0; i <= 5; i++)
		{
			for(int yy = -5; yy < 6; yy++)
			{
				for(int zz = -5; zz < 6; zz++)
				{
					for(int xx = -5; xx < 6; xx++)
					{
						Vector loc = new Vector(xx, yy, zz);
						double dist = Math.abs(loc.distance(new Vector()));
						if(dist <= 5 - i && dist > 5 - (i + 1))
						{
							if(i == 0)
							{
								OffsetBlock osb = new OffsetBlock(xx, yy, zz, Material.GLASS, false, delay);
								osb.setBlockData(Material.GLASS.createBlockData());
								blocks.add(osb);
								delay++;
							}
							else
							{
								Material block = RewardsUtil.getRandomFluid();
								OffsetBlock osb = new OffsetBlock(xx, yy, zz, block, false, delay);
								osb.setBlockData(block.createBlockData());
								blocks.add(osb);
								delay++;
							}

						}
					}
				}
			}
			delay += 10;
		}

		blocks.forEach(b -> b.spawnInWorld(location));
	}
}