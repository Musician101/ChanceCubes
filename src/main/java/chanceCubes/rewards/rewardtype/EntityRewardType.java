package chanceCubes.rewards.rewardtype;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.rewardparts.EntityPart;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import net.minecraft.server.v1_14_R1.Entity;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.MojangsonParser;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.entity.Player;

public class EntityRewardType extends BaseRewardType<EntityPart>
{
	public EntityRewardType(EntityPart... entities)
	{
		super(entities);
	}

	@Override
	public void trigger(final EntityPart part, Location location, Player player)
	{
		Scheduler.scheduleTask(new Task("Entity Reward Delay", part.getDelay())
		{
			@Override
			public void callback()
			{
				if(part.shouldRemovedBlocks())
					for(int yy = 0; yy < 4; yy++)
						for(int xx = -1; xx < 2; xx++)
							for(int zz = -1; zz < 2; zz++)
								RewardsUtil.placeBlock(Material.AIR.createBlockData(), location.clone().add(xx, yy, zz));

				Optional<Entity> newEntOptional = EntityTypes.a(part.getNBT(), ((CraftWorld) location.getWorld()).getHandle());
				if(!newEntOptional.isPresent())
				{
					CCubesCore.getInstance().getLogger().severe("Invalid entity NBT! " + part.getNBT().toString());
					return;
				}

				Entity newEnt = newEntOptional.get();
				newEnt.setPosition(location.getBlockX() + 0.5, location.getBlockY(), location.getBlockZ() + 0.5);
				((CraftWorld) location.getWorld()).getHandle().addEntity(newEnt);
			}
		});
	}

	public static NBTTagCompound getBasicNBTForEntity(String entity)
	{
		String json = "{id:" + entity + "}";
		NBTTagCompound nbt;
		try
		{
			nbt = MojangsonParser.parse(json);
		} catch(CommandSyntaxException e)
		{
			CCubesCore.getInstance().getLogger().severe("Failed to create a simple NBTTagCompound from " + entity);
			return null;
		}

		return nbt;
	}
}
