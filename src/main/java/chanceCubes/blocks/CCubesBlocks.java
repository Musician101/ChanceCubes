package chanceCubes.blocks;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.ChanceCubeData.ChanceCubeType;
import chanceCubes.persistance.ChanceCubeDataType;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

public class CCubesBlocks
{
	public static final NamespacedKey DATA = new NamespacedKey(CCubesCore.getInstance(), "data");

	public static boolean checkItem(@Nullable ItemStack itemStack, @Nonnull ChanceCubeType type) {
		if (itemStack == null) {
			return false;
		}

		if (itemStack.getType() != Material.BEACON) {
			return false;
		}

		ItemMeta meta = itemStack.getItemMeta();
		if (meta == null) {
			return false;
		}

		return checkContainer(meta.getPersistentDataContainer(), type);
	}

	public static boolean checkBlock(@Nullable Block block, @Nonnull ChanceCubeType type) {
		if (block == null) {
			return false;
		}

		if (block.getType() != Material.BEACON) {
			return false;
		}

		BlockState state = block.getState();
		if (state instanceof TileState) {
			return false;
		}

		TileState tile = (TileState) state;
		return checkContainer(tile.getPersistentDataContainer(), type);
	}

	private static boolean checkContainer(@Nonnull PersistentDataContainer container, @Nonnull ChanceCubeType type) {
		ChanceCubeData data = container.get(DATA, new ChanceCubeDataType());
		if (data == null) {
			return false;
		}

		return data.getType() == type;
	}

	public static boolean isChanceCube(ItemStack itemStack) {
		return checkItem(itemStack, ChanceCubeType.CHANCE_CUBE);
	}

	public static boolean isGiantCube(ItemStack itemStack) {
		return checkItem(itemStack, ChanceCubeType.COMPACT_GIANT_CHANCE_CUBE);
	}

	public static boolean isD20(ItemStack itemStack) {
		return checkItem(itemStack, ChanceCubeType.D20);
	}

	public static boolean isChanceCube(Block block) {
		return checkBlock(block, ChanceCubeType.CHANCE_CUBE);
	}

	public static boolean isGiantCube(Block block) {
		return checkBlock(block, ChanceCubeType.GIANT_CHANCE_CUBE);
	}

	public static boolean isD20(Block block) {
		return checkBlock(block, ChanceCubeType.D20);
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
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(DATA, new ChanceCubeDataType(), new ChanceCubeData(ChanceCubeType.CHANCE_CUBE, chance));
		cube.setItemMeta(meta);
		return cube;
	}

	public static ItemStack getCompactGiantCube(int amount) {
		ItemStack cube = new ItemStack(Material.LAPIS_BLOCK, amount);
		ItemMeta meta = cube.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Compact Giant Cube");
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(DATA, new ChanceCubeDataType(), new ChanceCubeData(ChanceCubeType.COMPACT_GIANT_CHANCE_CUBE));
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
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(DATA, new ChanceCubeDataType(), new ChanceCubeData(ChanceCubeType.D20, chance));
		d20.setItemMeta(meta);
		return d20;
	}
}
