package chanceCubes.items;

import chanceCubes.config.CCubesSettings;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CCubesItems
{
	public static boolean isScanner(ItemStack itemStack)
	{
		if (itemStack == null) {
			return false;
		}

		if (itemStack.getType() != Material.IRON_INGOT) {
			return false;
		}

		ItemMeta meta = itemStack.getItemMeta();
		return itemStack.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) > 0 && meta != null && meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.AQUA + "Cube Scanner") && meta.getLore().stream().anyMatch(s -> {
			Pattern pattern = Pattern.compile("Durability: ([0-9]+)/([0-9]+)");
			Matcher matcher = pattern.matcher(s);
			return matcher.find();
		});
	}

	public static ItemStack getScanner() {
		ItemStack scanner = new ItemStack(Material.IRON_INGOT);
		ItemMeta meta = scanner.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Cube Scanner");
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.setLore(Collections.singletonList("Durability: " + CCubesSettings.pendantUses + "/" + CCubesSettings.pendantUses));
		scanner.setItemMeta(meta);
		return scanner;
	}
}
