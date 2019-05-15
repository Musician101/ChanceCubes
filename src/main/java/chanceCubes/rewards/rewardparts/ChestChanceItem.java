package chanceCubes.rewards.rewardparts;

import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.rewards.variableTypes.IntVar;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ChestChanceItem extends BasePart
{
	private String mod;
	private String item;
	private IntVar amount;
	private IntVar chance;

	public ChestChanceItem(String item, IntVar chance, IntVar amount)
	{
		this.mod = item.substring(0, item.indexOf(":"));
		this.item = item.substring(item.indexOf(":") + 1);
		this.chance = chance;
		this.amount = amount;
	}

	private ItemStack getItemStack(int amount)
	{
		ItemStack stack = RewardsUtil.getItemStack(mod, item, amount);
		if(stack.getType() == Material.AIR)
		{
			stack = new ItemStack(RewardsUtil.getBlock(mod, item), amount);
			if(stack.getType() == Material.AIR)
				stack = CCubesBlocks.getChanceCube(1);
		}

		return stack;
	}

	public ItemStack getRandomItemStack()
	{
		return this.getItemStack(amount.getIntValue());
	}

	public int getChance()
	{
		return this.chance.getIntValue();
	}
}
