package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DoubleRainbow extends BaseCustomReward
{
	private static Material[] colors = new Material[] { Material.RED_WOOL, Material.ORANGE_WOOL, Material.YELLOW_WOOL, Material.GREEN_WOOL, Material.BLUE_WOOL, Material.PURPLE_WOOL };

	public DoubleRainbow()
	{
		super(CCubesCore.MODID + ":Double_Rainbow", 15);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		RewardsUtil.sendMessageToNearPlayers(location, 32, "Double Rainbow!");
		OffsetBlock b;
		for(int x = -7; x < 8; x++)
		{
			for(int y = 0; y < 8; y++)
			{
				float dist = (float) Math.abs(location.distance(location.clone().add(x, y, 0)));
				if(dist > 1 && dist <= 8)
				{
					int distIndex = (int) (dist - 2);
					Material wool = colors[distIndex];
					b = new OffsetBlock(x, y, 0, wool, false);
					b.setBlockData(wool.createBlockData());
					b.setDelay((x + 7) * 10);
					b.spawnInWorld(location);
				}
			}
		}

		for(int x = -17; x < 18; x++)
		{
			for(int y = 0; y < 18; y++)
			{
				float dist = (float) Math.abs(location.distance(location.clone().add(x, y, 0)));
				if(dist >= 12 && dist <= 18)
				{
					int distIndex = (int) (dist - 12);
					Material wool = colors[distIndex];
					b = new OffsetBlock(x, y, 0, wool, false);
					b.setBlockData(wool.createBlockData());
					b.setDelay((x + 12) * 5);
					b.spawnInWorld(location);
				}
			}
		}
	}
}
