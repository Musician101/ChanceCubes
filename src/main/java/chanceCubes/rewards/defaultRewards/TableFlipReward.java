package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Slab.Type;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.Stairs.Shape;
import org.bukkit.entity.Player;

public class TableFlipReward extends BaseCustomReward
{
	public TableFlipReward()
	{
		super(CCubesCore.MODID + ":Table_Flip", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		RewardsUtil.sendMessageToAllPlayers(location.getWorld(), "(╯°□°）╯︵ ┻━┻)");

		Scheduler.scheduleTask(new Task("Table_Flip", 1000, 10)
		{
			int stage = 0;

			private Slab slab(Type type) {
				Slab slab = (Slab) Material.OAK_SLAB.createBlockData();
				slab.setType(type);
				return slab;
			}

			private Stairs stairs(BlockFace face, Half half, Shape shape) {
				Stairs stairs = (Stairs) Material.OAK_STAIRS.createBlockData();
				stairs.setFacing(face);
				stairs.setHalf(half);
				stairs.setShape(shape);
				return stairs;
			}

			@Override
			public void update()
			{
				switch(stage)
				{
					case 0:
					{
						Slab data = (Slab) Material.OAK_SLAB.createBlockData();
						data.setType(Type.TOP);
						RewardsUtil.placeBlock(slab(Type.TOP), location);
						RewardsUtil.placeBlock(stairs(BlockFace.WEST, Half.TOP, Shape.STRAIGHT), location.clone().add(1, 0, 0));
						RewardsUtil.placeBlock(stairs(BlockFace.EAST, Half.TOP, Shape.STRAIGHT), location.clone().add(-1, 0, 0));
						break;
					}
					case 1:
					{
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location);
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(1, 0, 0));
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(-1, 0, 0));

						RewardsUtil.placeBlock(slab(Type.TOP), location.clone().add(0, 1, 0));
						RewardsUtil.placeBlock(stairs(BlockFace.WEST, Half.TOP, Shape.STRAIGHT), location.clone().add(1, 1, 0));
						RewardsUtil.placeBlock(stairs(BlockFace.EAST, Half.TOP, Shape.STRAIGHT), location.clone().add(-1, 1, 0));
						break;
					}
					case 2:
					{
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(0, 1, 0));
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(1, 1, 0));
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(-1, 1, 0));

						RewardsUtil.placeBlock(slab(Type.TOP), location.clone().add(0, 2, 1));
						RewardsUtil.placeBlock(stairs(BlockFace.WEST, Half.TOP, Shape.STRAIGHT), location.clone().add(1, 2, 1));
						RewardsUtil.placeBlock(stairs(BlockFace.EAST, Half.TOP, Shape.STRAIGHT), location.clone().add(-1, 2, 1));
						break;
					}
					case 3:
					{
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(0, 2, 1));
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(1, 2, 1));
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(-1, 2, 1));

						RewardsUtil.placeBlock(slab(Type.BOTTOM), location.clone().add(0, 1, 2));
						RewardsUtil.placeBlock(stairs(BlockFace.WEST, Half.BOTTOM, Shape.STRAIGHT), location.clone().add(1, 1, 2));
						RewardsUtil.placeBlock(stairs(BlockFace.EAST, Half.BOTTOM, Shape.STRAIGHT), location.clone().add(-1, 1, 2));
						break;
					}
					case 4:
					{
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(0, 1, 2));
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(1, 1, 2));
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(-1, 1, 2));

						RewardsUtil.placeBlock(slab(Type.BOTTOM), location.clone().add(0, 0, 2));
						RewardsUtil.placeBlock(stairs(BlockFace.WEST, Half.BOTTOM, Shape.STRAIGHT), location.clone().add(1, 0, 2));
						RewardsUtil.placeBlock(stairs(BlockFace.EAST, Half.BOTTOM, Shape.STRAIGHT), location.clone().add(-1, 0, 2));
						break;
					}
				}

				if(stage < 4)
					stage++;
				else
					Scheduler.removeTask(this);
			}

			@Override
			public void callback()
			{

			}

		});
	}
}
