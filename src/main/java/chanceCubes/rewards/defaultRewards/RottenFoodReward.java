package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RottenFoodReward extends BaseCustomReward
{
	public RottenFoodReward()
	{
		super(CCubesCore.MODID + ":Rotten_Food", -30);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		PlayerInventory inventory = player.getInventory();
		for(int i = 0; i < inventory.getSize(); i++)
		{
			ItemStack stack = inventory.getItem(i);
			if (!RewardsUtil.isAir(stack) && stack.getType().isEdible())
				inventory.setItem(i, new ItemStack(Material.ROTTEN_FLESH, stack.getAmount()));
		}

		player.sendMessage("Ewwww it's all rotten");

	}
}
