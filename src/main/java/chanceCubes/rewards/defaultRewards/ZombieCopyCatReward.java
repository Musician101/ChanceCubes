package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import java.util.Objects;
import java.util.stream.IntStream;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ZombieCopyCatReward extends BaseCustomReward
{
	public ZombieCopyCatReward()
	{
		super(CCubesCore.MODID + ":Copy_Cat_Zombie", -25);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		location.getWorld().spawn(location, Zombie.class, zombie -> {
			ItemStack weapon = IntStream.range(0, 9).mapToObj(i -> player.getInventory().getItem(i)).filter(Objects::nonNull).filter(itemStack -> {
				switch(itemStack.getType()) {
					case DIAMOND_SWORD:
					case GOLDEN_SWORD:
					case IRON_SWORD:
					case STONE_SWORD:
					case WOODEN_SWORD:
						return true;
				}

				return false;
			}).findFirst().orElseGet(() -> {
				if(player.getInventory().getItemInMainHand().getType() != Material.AIR)
				{
					return player.getInventory().getItemInMainHand().clone();
				}

				return new ItemStack(Material.AIR);
			});

			PlayerInventory playerInventory = player.getInventory();
			ItemStack[] armor = playerInventory.getArmorContents();
			EntityEquipment equipment = zombie.getEquipment();
			applyItemAndDropChance(weapon, () -> {
				equipment.setItemInMainHand(weapon);
				equipment.setItemInMainHandDropChance(1F);
			});
			applyItemAndDropChance(armor[0], () -> {
				equipment.setHelmet(armor[0]);
				equipment.setHelmetDropChance(1F);
			});
			applyItemAndDropChance(armor[1], () -> {
				equipment.setChestplate(armor[1]);
				equipment.setChestplateDropChance(1F);
			});
			applyItemAndDropChance(armor[2], () -> {
				equipment.setLeggings(armor[2]);
				equipment.setLeggingsDropChance(1F);
			});
			applyItemAndDropChance(armor[3], () -> {
				equipment.setBoots(armor[3]);
				equipment.setBootsDropChance(1F);
			});
		});
	}

	private void applyItemAndDropChance(ItemStack itemStack, Runnable action) {
		if (itemStack != null && itemStack.getType() != Material.AIR) {
			action.run();
		}
	}
}
