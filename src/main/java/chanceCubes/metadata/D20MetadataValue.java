package chanceCubes.metadata;

import chanceCubes.CCubesCore;
import chanceCubes.config.CCubesSettings;
import java.util.Random;
import javax.annotation.Nonnull;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class D20MetadataValue implements MetadataValue
{
	private static final Random RANDOM = new Random();

	private int chance;
	private boolean isScanned = false;

	public D20MetadataValue() {
		if (!CCubesSettings.d20UseNormalChances) {
			this.chance = RANDOM.nextBoolean() ? -100 : 100;
		}
		else {
			this.chance = Math.round((float) (RANDOM.nextGaussian() * 40));
			while(this.chance > 100 || this.chance < -100)
				this.chance = Math.round((float) RANDOM.nextGaussian() * 40);
		}
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
