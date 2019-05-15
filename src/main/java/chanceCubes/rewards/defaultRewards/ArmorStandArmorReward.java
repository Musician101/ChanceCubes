package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ArmorStandArmorReward extends BaseCustomReward
{

	public ArmorStandArmorReward()
	{
		super(CCubesCore.MODID + ":Armor_Stand_Armor", 40);
	}

	// @formatter:off
	private String[] names = {"dmodoomsirius", "MJRLegends", "Twp156", "JSL7", "Ratblade", "DerRedstoneProfi", "Turkey2349"};

	private ItemStack[] headItems = {new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.DIAMOND_HELMET),
			new ItemStack(Material.GOLDEN_HELMET), new ItemStack(Material.IRON_HELMET), new ItemStack(Material.LEATHER_HELMET),
			new ItemStack(Material.SKELETON_SKULL, 1), new ItemStack(Material.WITHER_SKELETON_SKULL, 1), new ItemStack(Material.CREEPER_HEAD, 1),
			new ItemStack(Material.DRAGON_HEAD, 1), new ItemStack(Material.PLAYER_HEAD, 1), new ItemStack(Material.ZOMBIE_HEAD, 1),
			new ItemStack(Material.CHEST)};

	private ItemStack[] chestItems = {new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.DIAMOND_CHESTPLATE),
			new ItemStack(Material.GOLDEN_CHESTPLATE), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.LEATHER_CHESTPLATE),
			new ItemStack(Material.ELYTRA), new ItemStack(Material.WHITE_BANNER)};

	private ItemStack[] legsItems = {new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.DIAMOND_LEGGINGS),
			new ItemStack(Material.GOLDEN_LEGGINGS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.LEATHER_LEGGINGS)};

	private ItemStack[] bootsItems = {new ItemStack(Material.CHAINMAIL_BOOTS), new ItemStack(Material.DIAMOND_BOOTS),
			new ItemStack(Material.GOLDEN_BOOTS), new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.LEATHER_BOOTS)};

	private ItemStack[] handItems = {new ItemStack(Material.CAKE), new ItemStack(Material.TORCH),
			new ItemStack(Material.SHIELD), new ItemStack(Material.IRON_SWORD), new ItemStack(Material.DIAMOND_HOE),
			new ItemStack(Material.WHITE_BANNER), new ItemStack(Material.COOKIE), new ItemStack(Material.STICK),
			new ItemStack(Material.GOLDEN_CARROT)};
	// @formatter:on

	@Override
	public void trigger(Location location, Player player)
	{
		location.getWorld().spawn(location.clone().add(0.5, 0.5, 0.5), ArmorStand.class, armorStand -> {
			String name = names[RewardsUtil.rand.nextInt(names.length)];
			armorStand.setCustomName(name);
			armorStand.setCustomNameVisible(true);
			ItemStack headStack = headItems[RewardsUtil.rand.nextInt(headItems.length)].clone();
			if(headStack.getType() == Material.PLAYER_HEAD)
			{
				SkullMeta meta = (SkullMeta) headStack.getItemMeta();
				meta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
				headStack.setItemMeta(meta);
			}

			armorStand.setHelmet(headStack);
			armorStand.setChestplate(chestItems[RewardsUtil.rand.nextInt(chestItems.length)].clone());
			armorStand.setLeggings(legsItems[RewardsUtil.rand.nextInt(legsItems.length)].clone());
			armorStand.setBoots(bootsItems[RewardsUtil.rand.nextInt(bootsItems.length)].clone());
			armorStand.setItemInHand(handItems[RewardsUtil.rand.nextInt(handItems.length)].clone());
			armorStand.getEquipment().setItemInOffHand(handItems[RewardsUtil.rand.nextInt(handItems.length)].clone());
		});
	}
}
