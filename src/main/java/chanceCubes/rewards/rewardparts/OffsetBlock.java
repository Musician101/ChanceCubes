package chanceCubes.rewards.rewardparts;

import chanceCubes.blocks.BlockFallingCustom;
import chanceCubes.config.CCubesSettings;
import chanceCubes.rewards.variableTypes.BoolVar;
import chanceCubes.rewards.variableTypes.IntVar;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import net.minecraft.server.v1_13_R2.WorldServer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.block.data.CraftBlockData;

public class OffsetBlock extends BasePart
{
	protected BoolVar relativeToPlayer = new BoolVar(false);
	public IntVar xOff;
	public IntVar yOff;
	public IntVar zOff;

	protected BlockData state;

	protected BoolVar falling;

	protected BoolVar causeUpdate = new BoolVar(false);

	private BoolVar removeUnbreakableBlocks = new BoolVar(false);

	protected BoolVar playSound = new BoolVar(true);

	public OffsetBlock(int x, int y, int z, Material b, boolean falling)
	{
		this(x, y, z, b.createBlockData(), falling);
	}

	public OffsetBlock(int x, int y, int z, Material b, BoolVar falling)
	{
		this(x, y, z, b, falling, new IntVar(0));
	}

	public OffsetBlock(int x, int y, int z, Material b, BoolVar falling, IntVar delay)
	{
		this(new IntVar(x), new IntVar(y), new IntVar(z), b.createBlockData(), falling, delay);
	}

	public OffsetBlock(int x, int y, int z, Material b, boolean falling, int delay)
	{
		this(x, y, z, b.createBlockData(), falling, delay);
	}

	public OffsetBlock(int x, int y, int z, BlockData state, boolean falling)
	{
		this(x, y, z, state, falling, 0);
	}

	public OffsetBlock(int x, int y, int z, BlockData state, boolean falling, int delay)
	{
		this(new IntVar(x), new IntVar(y), new IntVar(z), state, new BoolVar(falling), new IntVar(delay));
	}

	public OffsetBlock(IntVar x, IntVar y, IntVar z, Material b, BoolVar falling)
	{
		this(x, y, z, b.createBlockData(), falling, new IntVar(0));
	}

	public OffsetBlock(IntVar x, IntVar y, IntVar z, BlockData state, BoolVar falling, IntVar delay)
	{
		this.xOff = x;
		this.yOff = y;
		this.zOff = z;
		this.falling = falling;
		this.setDelay(delay);
		this.state = state;
	}

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
			Scheduler.scheduleTask(new Task("Falling_Block", this.getDelay())
			{
				@Override
				public void callback()
				{
					spawnFallingBlock(location);
				}
			});
		}
	}

	protected void spawnFallingBlock(Location location)
	{
		int xOffVal = xOff.getIntValue();
		int yOffVal = yOff.getIntValue();
		int zOffVal = zOff.getIntValue();
		double yy = location.getY() + yOffVal + CCubesSettings.dropHeight + 0.5 >= 256 ? 255 : location.getY() + yOffVal + CCubesSettings.dropHeight + 0.5;
		for(int yyy = (int) yy; yyy >= location.getY() + yOffVal; yyy--)
			RewardsUtil.placeBlock(Material.AIR.createBlockData(), new Location(location.getWorld(), location.getX() + xOffVal, yyy, location.getZ() + zOffVal), removeUnbreakableBlocks.getBoolValue());

		WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
		BlockFallingCustom entityFallingBlock = new BlockFallingCustom(world, location.getX() + xOffVal + 0.5, yy, location.getZ() + zOffVal + 0.5, ((CraftBlockData) this.state).getState(), location.getBlockY() + yOffVal, this);
		world.g(entityFallingBlock);
	}

	public OffsetBlock setBlockData(BlockData state)
	{
		this.state = state;
		return this;
	}

	public BlockData getBlockData()
	{
		return this.state;
	}

	public OffsetBlock setRelativeToPlayer(boolean relative)
	{
		return this.setRelativeToPlayer(new BoolVar(relative));
	}

	public OffsetBlock setRelativeToPlayer(BoolVar relative)
	{
		this.relativeToPlayer = relative;
		return this;
	}

	public boolean isRelativeToPlayer()
	{
		return this.relativeToPlayer.getBoolValue();
	}

	public IntVar getDelayVar()
	{
		return this.delay;
	}

	public OffsetBlock setCausesBlockUpdate(boolean flag)
	{
		return this.setCausesBlockUpdate(new BoolVar(flag));
	}

	public OffsetBlock setCausesBlockUpdate(BoolVar flag)
	{
		this.causeUpdate = flag;
		return this;
	}

	public boolean isFalling()
	{
		return this.falling.getBoolValue();
	}

	public BoolVar isFallingVar()
	{
		return this.falling;
	}

	public void setFalling(boolean falling)
	{
		this.setFalling(new BoolVar(falling));
	}

	public void setFalling(BoolVar falling)
	{
		this.falling = falling;
	}

	public void setRemoveUnbreakableBlocks(boolean remove)
	{
		this.setRemoveUnbreakableBlocks(new BoolVar(remove));
	}

	public void setRemoveUnbreakableBlocks(BoolVar remove)
	{
		removeUnbreakableBlocks = remove;
	}

	public boolean doesRemoveUnbreakableBlocks()
	{
		return this.removeUnbreakableBlocks.getBoolValue();
	}

	public void setPlaysSound(BoolVar playSound)
	{
		this.playSound = playSound;
	}

	public boolean doesPlaySound()
	{
		return this.playSound.getBoolValue();
	}

	public Location placeInWorld(Location location, boolean offset)
	{
		int xx = location.getBlockX();
		int yy = location.getBlockY();
		int zz = location.getBlockZ();
		if(offset)
		{
			xx += xOff.getIntValue();
			yy += yOff.getIntValue();
			zz += zOff.getIntValue();
		}

		Location placePos = new Location(location.getWorld(), xx, yy, zz);
		RewardsUtil.placeBlock(state, placePos, causeUpdate.getBoolValue(), this.removeUnbreakableBlocks.getBoolValue());
		if(this.playSound.getBoolValue())
		{
			Location surfacePos = placePos.clone().add(0, -1, 0);
			// Spigot doesn't have a way to get valid sounds from blocks w/o NMS so we'll default to BLOCK_METAL_PLACE
			surfacePos.getWorld().playSound(new Location(surfacePos.getWorld(), xx + 0.5, yy + 0.5F, zz + 0.5F), Sound.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1, 1);
		}

		return placePos;
	}
}
