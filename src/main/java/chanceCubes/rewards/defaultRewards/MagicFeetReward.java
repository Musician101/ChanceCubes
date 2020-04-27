package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import net.minecraft.server.v1_15_R1.BlockPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.Player;

public class MagicFeetReward extends BaseCustomReward
{
	public MagicFeetReward()
	{
		super(CCubesCore.MODID + ":Magic_Feet", 85);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		player.sendMessage("<Dovah_Jun> You've got magic feet!!!");
		Scheduler.scheduleTask(new Task("Magic_Feet_Reward_Delay", 300, 2)
		{
			Location last = location;

			@Override
			public void callback()
			{
				player.sendMessage("<Dovah_Jun> You've used up all the magic in your feet!");
			}

			@Override
			public void update()
			{
				Location beneath = player.getLocation().clone().add(0, -1, 0);
				if(!RewardsUtil.isAir(beneath.getBlock()) && ((CraftWorld) beneath.getWorld()).getHandle().getTileEntity(new BlockPosition(beneath.getBlockX(), beneath.getBlockY(), beneath.getBlockZ())) == null && !last.equals(beneath))
				{
					Material block = RewardsUtil.getRandomOre();
					RewardsUtil.placeBlock(block.createBlockData(), beneath);
					last = beneath;
				}

				if(this.delayLeft % 20 == 0)
					this.showTimeLeft(player);
			}
		});
	}
}
