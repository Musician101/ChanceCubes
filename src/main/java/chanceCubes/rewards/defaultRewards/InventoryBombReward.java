package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import java.util.Objects;
import java.util.stream.IntStream;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryBombReward extends BaseCustomReward
{
	public InventoryBombReward()
	{
		super(CCubesCore.MODID + ":Inventory_Bomb", -55);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		PlayerInventory inventory = player.getInventory();
		IntStream.range(0, inventory.getSize()).mapToObj(inventory::getItem).filter(Objects::nonNull).forEach(itemStack -> location.getWorld().dropItem(location, itemStack));
		for(int i = 0; i < player.getInventory().getContents().length; i++)
			inventory.getStorageContents()[i] = new ItemStack(Material.DEAD_BUSH, 64);

		ItemStack[] armorContents = new ItemStack[4];
		for(int i = 0; i < 4; i++)
		{
			ItemStack stack = new ItemStack(Material.DEAD_BUSH, 64);
			if(i == 0)
			{
				ItemMeta meta = stack.getItemMeta();
				meta.setDisplayName("ButtonBoy");
				stack.setItemMeta(meta);
				stack.setAmount(13);
			}
			else if(i == 1)
			{
				ItemMeta meta = stack.getItemMeta();
				meta.setDisplayName("TheBlackswordsman");
				stack.setItemMeta(meta);
				stack.setAmount(13);
			}

		}

		inventory.setArmorContents(armorContents);
		player.sendMessage("Inventory Bomb!!!!");
	}
}
