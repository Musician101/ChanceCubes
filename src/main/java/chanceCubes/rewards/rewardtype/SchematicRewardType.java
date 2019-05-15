package chanceCubes.rewards.rewardtype;

import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.CustomSchematic;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SchematicRewardType implements IRewardType
{
	private CustomSchematic schematic;

	public SchematicRewardType(CustomSchematic schematic)
	{
		this.schematic = schematic;
	}

	@Override
	public void trigger(Location location, Player player)
	{
		List<OffsetBlock> stack = new ArrayList<>();
		for(OffsetBlock osb : schematic.getBlocks())
			if(schematic.includeAirBlocks() || osb.getBlockData().getMaterial() != Material.AIR)
				stack.add(osb);

		Scheduler.scheduleTask(new Task("Schematic_Spawn_Delay", schematic.getDelay())
		{
			@Override
			public void callback()
			{

				Scheduler.scheduleTask(new Task("Schematic_Reward_Block_Spawn", -1, schematic.getSpacingDelay() < 1 ? 1 : (int) schematic.getSpacingDelay())
				{
					@Override
					public void callback()
					{
					}

					@Override
					public void update()
					{
						float lessThan1 = 0;
						while(lessThan1 < 1 && !stack.isEmpty())
						{
							OffsetBlock osb = stack.remove(0);
							if(schematic.isRelativeToPlayer())
							{
								Location pos = player.getLocation().clone().add(osb.xOff.getIntValue(), osb.yOff.getIntValue(), osb.zOff.getIntValue());
								if(RewardsUtil.isAir(pos.getBlock()))
									continue;

								osb.spawnInWorld(player.getLocation());
							}
							else
							{
								Location pos = location.clone().add(osb.xOff.getIntValue(), osb.yOff.getIntValue(), osb.zOff.getIntValue());
								if(RewardsUtil.isAir(pos.getBlock()))
									continue;

								osb.spawnInWorld(location);
							}

							lessThan1 += schematic.getSpacingDelay();
							if(stack.size() == 0)
								lessThan1 = 1;
						}

						if(stack.size() == 0)
							Scheduler.removeTask(this);
					}
				});
			}
		});
	}
}
