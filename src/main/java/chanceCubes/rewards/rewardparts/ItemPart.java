package chanceCubes.rewards.rewardparts;

import chanceCubes.rewards.variableTypes.IntVar;
import chanceCubes.rewards.variableTypes.NBTVar;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemPart extends BasePart
{
	private NBTVar itemNBT;

	public ItemPart(ItemStack stack)
	{
		this(stack, 0);
	}

	public ItemPart(ItemStack stack, int delay)
	{
		this(stack, new IntVar(delay));
	}

	public ItemPart(ItemStack stack, IntVar delay)
	{
		this.itemNBT = new NBTVar(CraftItemStack.asNMSCopy(stack).save(new NBTTagCompound()));
		this.setDelay(delay);
	}

	public ItemPart(String nbt)
	{
		this(new NBTVar(nbt), new IntVar(0));
	}

	public ItemPart(NBTVar nbt)
	{
		this(nbt, new IntVar(0));
	}

	public ItemPart(NBTVar nbt, IntVar delay)
	{
		this.itemNBT = nbt;
		this.setDelay(delay);
	}

	public ItemStack getItemStack()
	{
		return CraftItemStack.asBukkitCopy(net.minecraft.server.v1_14_R1.ItemStack.a(this.itemNBT.getNBTValue()));
	}
}
