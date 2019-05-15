package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RandomTeleportReward extends BaseCustomReward
{
	public RandomTeleportReward()
	{
		super(CCubesCore.MODID + ":Random_Teleport", -15);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		int xChange = ((new Random().nextInt(50) + 20) + location.getBlockX()) - 35;
		int zChange = ((new Random().nextInt(50) + 20) + location.getBlockZ()) - 35;
		int yChange = -1;
		for(int yy = 0; yy <= location.getWorld().getMaxHeight(); yy++)
		{
			Location temp = new Location(location.getWorld(), xChange, yy, zChange);
			if(RewardsUtil.isAir(temp.getBlock()) && RewardsUtil.isAir(temp.clone().add(0, 1, 0).getBlock()))
			{
				yChange = yy;
				break;
			}
		}
		if(yChange == -1)
			return;

		player.teleport(new Location(location.getWorld(), xChange, yChange, zChange));
	}
}
