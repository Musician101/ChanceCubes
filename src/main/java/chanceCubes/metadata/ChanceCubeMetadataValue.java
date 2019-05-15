package chanceCubes.metadata;

import java.util.Random;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class ChanceCubeMetadataValue implements MetadataValue
{
	private static Random RANDOM = new Random();

	private int chance;
	private boolean isScanned = false;

	public ChanceCubeMetadataValue() {
		this(Math.round((float) RANDOM.nextGaussian() * 40));
	}

	public ChanceCubeMetadataValue(int initialChance) {
		while (initialChance > 100 || initialChance < - 100)
			initialChance = Math.round((float) RANDOM.nextGaussian() * 40);

		this.chance = initialChance;
	}

	public int getChance()
	{
		return chance;
	}

	public boolean isScanned()
	{
		return isScanned;
	}

	public void setChance(int chance)
	{
		this.chance = chance;
	}

	public void setScanned(boolean scanned)
	{
		isScanned = scanned;
	}

	@Override
	public Object value()
	{
		return null;
	}

	@Override
	public int asInt()
	{
		return 0;
	}

	@Override
	public float asFloat()
	{
		return 0;
	}

	@Override
	public double asDouble()
	{
		return 0;
	}

	@Override
	public long asLong()
	{
		return 0;
	}

	@Override
	public short asShort()
	{
		return 0;
	}

	@Override
	public byte asByte()
	{
		return 0;
	}

	@Override
	public boolean asBoolean()
	{
		return false;
	}

	@Override
	public String asString()
	{
		return null;
	}

	@Override
	public Plugin getOwningPlugin()
	{
		return null;
	}

	@Override
	public void invalidate()
	{

	}
}
