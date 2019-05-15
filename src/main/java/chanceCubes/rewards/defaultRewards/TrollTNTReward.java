package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

public class TrollTNTReward extends BaseCustomReward
{
	public TrollTNTReward()
	{
		super(CCubesCore.MODID + ":Troll_TNT", -5);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		for(int x = -1; x < 2; x++)
		{
			for(int z = -1; z < 2; z++)
			{
				RewardsUtil.placeBlock(Material.COBWEB.createBlockData(), location.clone().add(x, 0, z));
			}
		}

		TNTPrimed tntPrimed = location.getWorld().spawn(location.clone().add(1, 1, 0), TNTPrimed.class);
		location.getWorld().playSound(player.getLocation(), Sound.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);

		if(RewardsUtil.rand.nextInt(5) != 1)
		{
			Scheduler.scheduleTask(new Task("TrollTNT", 77)
			{
				@Override
				public void callback()
				{
					player.sendMessage("BOOM");
					tntPrimed.remove();
				}

			});
		}
	}
}
