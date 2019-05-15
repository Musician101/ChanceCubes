package chanceCubes.rewards.rewardparts;

import chanceCubes.metadata.ChanceCubeMetadataValue;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ChanceCubeOffsetBlock extends OffsetBlock
{
	private int chance;

	public ChanceCubeOffsetBlock(int x, int y, int z, ItemStack itemStack)
	{
		super(x, y, z, itemStack.getType(), false);
		this.chance = itemStack.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
	}

	public ChanceCubeOffsetBlock(int x, int y, int z, ItemStack itemStack, int delay)
	{
		super(x, y, z, itemStack.getType(), false, delay);
	}

	public int getChance()
	{
		return chance;
	}

	public void setChance(int chance)
	{
		this.chance = chance;
	}

	@Override
	public Location placeInWorld(Location location, boolean offset)
	{
		Location newLoc = super.placeInWorld(location, offset);
		newLoc.getBlock().setMetadata("ChanceCubes", new ChanceCubeMetadataValue(chance));
		return newLoc;
	}
}
