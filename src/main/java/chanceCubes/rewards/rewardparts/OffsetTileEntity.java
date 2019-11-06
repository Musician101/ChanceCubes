package chanceCubes.rewards.rewardparts;

import chanceCubes.rewards.variableTypes.BoolVar;
import chanceCubes.rewards.variableTypes.IntVar;
import chanceCubes.rewards.variableTypes.NBTVar;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.TileEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;

public class OffsetTileEntity extends OffsetBlock
{
	private NBTVar teNBT;

	public OffsetTileEntity(int x, int y, int z, Material b, NBTTagCompound te, boolean falling, int delay)
	{
		this(x, y, z, b.createBlockData(), te, falling, delay);
	}

	public OffsetTileEntity(int x, int y, int z, BlockData state, NBTTagCompound te, boolean falling)
	{
		this(x, y, z, state, te, falling, 0);
	}

	public OffsetTileEntity(int x, int y, int z, BlockData state, NBTTagCompound te, BoolVar falling)
	{
		this(new IntVar(x), new IntVar(y), new IntVar(z), state, new NBTVar(te), falling, new IntVar(0));
	}

	public OffsetTileEntity(int x, int y, int z, BlockData state, NBTTagCompound te, boolean falling, int delay)
	{
		this(new IntVar(x), new IntVar(y), new IntVar(z), state, new NBTVar(te), new BoolVar(falling), new IntVar(delay));
	}

	public OffsetTileEntity(IntVar x, IntVar y, IntVar z, BlockData state, NBTVar te, BoolVar falling, IntVar delay)
	{
		super(x, y, z, state, falling, delay);
		this.teNBT = te;
	}

	@Override
	public void spawnInWorld(Location location)
	{
		if(!falling.getBoolValue())
		{
			Scheduler.scheduleTask(new Task("Delayed_Block", this.getDelay())
			{
				@Override
				public void callback()
				{
					placeInWorld(location, true);
				}
			});
		}
		else
		{
			Scheduler.scheduleTask(new Task("Falling_TileEntity", this.getDelay())
			{
				@Override
				public void callback()
				{
					spawnFallingBlock(location);
				}
			});
		}
	}

	@Override
	public Location placeInWorld(Location location, boolean offset)
	{
		Location pos = super.placeInWorld(location, offset);
		TileEntity te = TileEntity.create(teNBT.getNBTValue());
		if(!offset)
			pos = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());

		BlockPosition blockPosition = new BlockPosition(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
		te.setPosition(blockPosition);
		((CraftWorld) location.getWorld()).getHandle().setTileEntity(blockPosition, te);
		return pos;
	}
}
