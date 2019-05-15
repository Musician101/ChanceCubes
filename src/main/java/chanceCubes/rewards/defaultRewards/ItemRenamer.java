package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemRenamer extends BaseCustomReward
{
	// @formatter:off
	private String[] names = {"Turkey", "qnxb", "Darkosto", "Wyld", "Funwayguy", "ButtonBoy", "SlothMonster",
			"Vash", "Cazador", "KiwiFails", "Matrixis", "FlameGoat", "iChun", "tibbzeh", "Reninsane",
			"PulpJohnFiction", "Zeek", "Sevadus", "Bob Ross", "T-loves", "Headwound", "JonBams", "Sketch",
			"Lewdicolo", "Sinful", "Drakma", "1chick", "Deadpine", "Amatt_", "Jacky"};

	private String[] adjectives = {"Destroyer", "Terror", "Wrath", "Smasher", "P90", "Wisdom", "Savior",
			"Lightning Bringer", "Rage", "Happiness", "Shocker", " Slayer", "Sunshine", "Giant Crayon", "Blade",
			"Tamer", "Order", "Sharp Edge", "Noodle", "Diamond", "Rod", "Big Giant Sharp Pokey Thing"};
	// @formatter:on

	public ItemRenamer()
	{
		super(CCubesCore.MODID + ":Item_Rename", 10);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		List<ItemStack> stacks = new ArrayList<>();
		PlayerInventory inventory = player.getInventory();
		stacks.addAll(Arrays.asList(inventory.getContents()));
		stacks.addAll(Arrays.asList(inventory.getArmorContents()));
		stacks.removeIf(RewardsUtil::isAir);

		if(stacks.size() == 0)
		{
			ItemStack dirt = new ItemStack(Material.DIRT);
			ItemMeta meta = dirt.getItemMeta();
			meta.setDisplayName("A lonely piece of dirt");
			dirt.setItemMeta(meta);
			inventory.addItem(dirt);
			return;
		}

		for(int i = 0; i < 3; i++)
		{
			String name = names[RewardsUtil.rand.nextInt(names.length)];
			String adj = adjectives[RewardsUtil.rand.nextInt(adjectives.length)];

			if(name.substring(name.length() - 1).equalsIgnoreCase("s"))
				name += "'";
			else
				name += "'s";
			String newName = name + " " + adj;
			ItemStack stack = stacks.get(RewardsUtil.rand.nextInt(stacks.size()));
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(newName);
			stack.setItemMeta(meta);
		}

		player.sendMessage("Those items of yours need a little personality!");
	}
}
