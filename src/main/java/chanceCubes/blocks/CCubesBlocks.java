package chanceCubes.blocks;

import chanceCubes.CCubesCore;
import chanceCubes.metadata.ChanceCubeMetadataValue;
import chanceCubes.metadata.D20MetadataValue;
import chanceCubes.metadata.GiantCubeMetadataValue;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CCubesBlocks
{

	public static boolean isChanceCube(ItemStack itemStack) {
		ItemMeta meta;
		return itemStack != null && itemStack.getType() == Material.LAPIS_BLOCK && (meta = itemStack.getItemMeta()).hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && meta.getDisplayName().equals(ChatColor.AQUA + "Chance Cubes");
	}

	public static boolean isGiantCube(ItemStack itemStack) {
		ItemMeta meta;
		return itemStack != null && itemStack.getType() == Material.LAPIS_BLOCK && (meta = itemStack.getItemMeta()).hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && meta.getDisplayName().equals(ChatColor.AQUA + "Compact Giant Cubes");
	}

	public static boolean isD20(ItemStack itemStack) {
		ItemMeta meta;
		return itemStack != null && itemStack.getType() == Material.LAPIS_BLOCK && (meta = itemStack.getItemMeta()).hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL) && meta.getDisplayName().equals(ChatColor.AQUA + "Icosahedron");
	}

	public static boolean isChanceCube(Block block) {
		return block != null && block.getType() == Material.LAPIS_BLOCK && block.getMetadata("ChanceCubes").stream().anyMatch(metadata -> metadata.getOwningPlugin() instanceof CCubesCore && metadata instanceof ChanceCubeMetadataValue);
	}

	public static boolean isGiantCube(Block block) {
		return block != null && block.getType() == Material.LAPIS_BLOCK && block.getMetadata("ChanceCubes").stream().anyMatch(metadata -> metadata.getOwningPlugin() instanceof CCubesCore && metadata instanceof GiantCubeMetadataValue);
	}

	public static boolean isD20(Block block) {
		return block != null && block.getType() == Material.LAPIS_BLOCK && block.getMetadata("ChanceCubes").stream().anyMatch(metadata -> metadata.getOwningPlugin() instanceof CCubesCore && metadata instanceof D20MetadataValue);
	}

	public static ItemStack getChanceCube(int amount) {
		return getChanceCube(Math.round((float) new Random().nextGaussian() * 40), amount);
	}

	public static ItemStack getChanceCube(int chance, int amount) {
		while (chance > 100 || chance < - 100)
			chance = Math.round((float) new Random().nextGaussian() * 40);

		ItemStack cube = new ItemStack(Material.LAPIS_BLOCK, amount);
		ItemMeta meta = cube.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Chance Cube");
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, chance, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		cube.setItemMeta(meta);
		return cube;
	}

	public static ItemStack getCompactGiantCube(int amount) {
		ItemStack cube = new ItemStack(Material.LAPIS_BLOCK, amount);
		ItemMeta meta = cube.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Compact Giant Cube");
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		cube.setItemMeta(meta);
		return cube;
	}

	public static ItemStack getD20(int amount) {
		return getD20(Math.round((float) new Random().nextGaussian() * 40), amount);
	}

	public static ItemStack getD20(int chance, int amount) {
		ItemStack d20 = new ItemStack(Material.LAPIS_BLOCK, amount);
		ItemMeta meta = d20.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Icosahedron");
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, chance, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		d20.setItemMeta(meta);
		return d20;
	}
}
