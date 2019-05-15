package chanceCubes.metadata;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import javax.annotation.Nonnull;
import org.bukkit.Location;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class GiantCubeMetadataValue implements MetadataValue
{
	private boolean hasMaster, isMaster;
	private Location masterLocation;

	public Location getMasterLocation()
	{
		return masterLocation;
	}

	public boolean hasMaster()
	{
		return hasMaster;
	}

	public boolean isMaster()
	{
		return isMaster;
	}

	public void setIsMaster(boolean hasMaster)
	{
		this.hasMaster = hasMaster;
	}

	public void setHasMaster(boolean master)
	{
		isMaster = master;
	}

	public void setMasterLocation(Location location)
	{
		this.masterLocation = location;
	}

	/** Reset method to be run when the master is gone or tells them to */
	public void reset() {
		masterLocation = new Location(masterLocation.getWorld(), 0, 0, 0);
		hasMaster = false;
		isMaster = false;
	}

	/** Check that the master exists */
	public boolean checkForMaster()
	{
		return CCubesBlocks.isGiantCube(masterLocation.getBlock());
	}

	@Override
	public Object value()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public int asInt()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public float asFloat()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public double asDouble()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public long asLong()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public short asShort()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public byte asByte()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean asBoolean()
	{
		return false;
	}

	@Nonnull
	@Override
	public String asString()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Plugin getOwningPlugin()
	{
		return CCubesCore.getInstance();
	}

	@Override
	public void invalidate()
	{

	}
}
