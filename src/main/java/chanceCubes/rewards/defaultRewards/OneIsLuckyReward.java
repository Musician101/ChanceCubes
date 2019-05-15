package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.metadata.ChanceCubeMetadataValue;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class OneIsLuckyReward extends BaseCustomReward
{
	public OneIsLuckyReward()
	{
		super(CCubesCore.MODID + ":One_Is_Lucky", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		RewardsUtil.sendMessageToNearPlayers(location, 32, "A Lucky Block Salute");
		boolean leftLucky = RewardsUtil.rand.nextBoolean();
		ChanceCubeMetadataValue leftCube = new ChanceCubeMetadataValue(leftLucky ? 100 : -100);
		ChanceCubeMetadataValue rightCube = new ChanceCubeMetadataValue(!leftLucky ? 100 : -100);

		if(RewardsUtil.placeBlock(Material.LAPIS_BLOCK.createBlockData(), location.clone().add(-1, 0, 0)))
			location.clone().add(-1, 0, 0).getBlock().setMetadata("ChanceCubes", leftCube);
		if(RewardsUtil.placeBlock(Material.SIGN.createBlockData(), location))
		{
			Sign sign = (Sign) location.getBlock().getState();
			sign.setLine(0, "One is lucky");
			sign.setLine(1, "One is not");
			sign.setLine(3, "#OGLuckyBlocks");
		}
		if(RewardsUtil.placeBlock(Material.LAPIS_BLOCK.createBlockData(), location.clone().add(1, 0, 0)))
			location.clone().add(1, 0, 0).getBlock().setMetadata("ChanceCubes", rightCube);

		Scheduler.scheduleTask(new Task("One_Is_Lucky_Reward", 6000, 10)
		{
			@Override
			public void callback()
			{
				location.clone().add(-1, 0, 0).getBlock().setType(Material.AIR);
				location.getBlock().setType(Material.AIR);
				location.clone().add(1, 0, 0).getBlock().setType(Material.AIR);
			}

			@Override
			public void update()
			{
				if(CCubesBlocks.isChanceCube(location.clone().add(-1, 0, 0).getBlock()) || CCubesBlocks.isChanceCube(location.clone().add(1, 0, 0).getBlock()))
				{
					this.callback();
					Scheduler.removeTask(this);
				}
			}
		});
	}
}
