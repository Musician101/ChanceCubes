package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChanceCubeRenameReward extends BaseCustomReward
{

	// @formatter:off
	private String[] chanceSyn = {"Lucky", "Fortune", "Unforseen", "Probabalistic", "Favored",
			"Charmed", "Auspicious", "Advantageous"};

	private String[] cubeSyn = {"Blocks", "Squares", "Boxes", "Bricks", "Hunks", "Solids"};

	// @formatter:on

	public ChanceCubeRenameReward()
	{
		super(CCubesCore.MODID + ":Cube_Rename", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		ItemStack stack = CCubesBlocks.getChanceCube(2);
		String name = chanceSyn[RewardsUtil.rand.nextInt(chanceSyn.length)];
		String adj = cubeSyn[RewardsUtil.rand.nextInt(cubeSyn.length)];

		String newName = name + " " + adj;
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(newName);
		stack.setItemMeta(meta);

		player.sendMessage("Chance Cubes are sooooo 2017. Here have some " + newName + " instead!");
		location.getWorld().spawn(location, Item.class, item -> item.setItemStack(stack));
	}
}
