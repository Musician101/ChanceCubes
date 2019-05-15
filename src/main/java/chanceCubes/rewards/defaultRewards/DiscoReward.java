package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;

public class DiscoReward extends BaseCustomReward
{
	public DiscoReward()
	{
		super(CCubesCore.MODID + ":Disco", 40);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		for(int xx = -4; xx < 5; xx++)
			for(int zz = -4; zz < 5; zz++)
				RewardsUtil.placeBlock(RewardsUtil.getRandomWool(), location.clone().add(xx, -1, zz));

		for(int i = 0; i < 10; i++)
		{
			location.getWorld().spawn(location.clone().add(0, 1, 0), Sheep.class, sheep -> {
				sheep.setCustomNameVisible(true);
				sheep.setCustomName("jeb_");
			});
		}

		RewardsUtil.placeD20(location.clone().add(0, 3, 0));

		RewardsUtil.sendMessageToNearPlayers(location, 32, "Disco Party!!!!");
	}
}
