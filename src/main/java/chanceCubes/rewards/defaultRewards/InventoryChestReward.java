package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryChestReward extends BaseCustomReward
{
	public InventoryChestReward()
	{
		super(CCubesCore.MODID + ":Inventory_Chest", -70);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		ItemStack[] main = player.getInventory().getStorageContents();
		ItemStack[] armor = player.getInventory().getArmorContents();
		player.getInventory().clear();
		player.getInventory().setArmorContents(armor);
		player.sendMessage("At least i didn't delete your items...");
		RewardsUtil.placeBlock(Material.CHEST.createBlockData(), location);
		RewardsUtil.placeBlock(Material.CHEST.createBlockData(), location.clone().add(1, 0, 0));
		RewardsUtil.placeBlock(Material.OBSIDIAN.createBlockData(), location.clone().add(0, -1, 0));
		RewardsUtil.placeBlock(Material.OBSIDIAN.createBlockData(), location.clone().add(1, -1, 0));
		RewardsUtil.placeBlock(Material.OBSIDIAN.createBlockData(), location.clone().add(-1, 0, 0));
		RewardsUtil.placeBlock(Material.OBSIDIAN.createBlockData(), location.clone().add(2, 0, 0));
		RewardsUtil.placeBlock(Material.OBSIDIAN.createBlockData(), location.clone().add(0, 0, 1));
		RewardsUtil.placeBlock(Material.OBSIDIAN.createBlockData(), location.clone().add(1, 0, 1));
		RewardsUtil.placeBlock(Material.OBSIDIAN.createBlockData(), location.clone().add(0, 0, -1));
		RewardsUtil.placeBlock(Material.OBSIDIAN.createBlockData(), location.clone().add(1, 0, -1));
		RewardsUtil.placeBlock(Material.OBSIDIAN.createBlockData(), location.clone().add(0, -1, 0));
		RewardsUtil.placeBlock(Material.OBSIDIAN.createBlockData(), location.clone().add(1, -1, 0));
		RewardsUtil.placeBlock(Material.OBSIDIAN.createBlockData(), location.clone().add(0, 1, 0));
		RewardsUtil.placeBlock(Material.OBSIDIAN.createBlockData(), location.clone().add(1, 1, 0));
		Chest chest = (Chest) location.getBlock().getState();
		chest.getInventory().setStorageContents(main);
		chest.update(true);
	}
}
