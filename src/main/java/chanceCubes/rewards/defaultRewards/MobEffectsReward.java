package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import com.google.common.collect.Multimap;
import java.util.stream.IntStream;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SplashPotion;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MobEffectsReward extends BaseCustomReward
{
	public MobEffectsReward()
	{
		super(CCubesCore.MODID + ":Mob_Abilities_Effects", -15);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		int highestDamage = IntStream.range(0, 9).filter(i -> {
			ItemStack stack = player.getInventory().getItem(i);
			return stack != null && stack.getType() != Material.AIR;
		}).flatMap(i -> {
			ItemStack stack = player.getInventory().getItem(i);
			Multimap<Attribute, AttributeModifier> modifiers = stack.getItemMeta().getAttributeModifiers(EquipmentSlot.HAND);
			return modifiers.get(Attribute.GENERIC_ATTACK_DAMAGE).stream().mapToDouble(AttributeModifier::getAmount).mapToInt(int.class::cast);
		}).max().orElse(1);

		Class<? extends LivingEntity> entityClass;
		switch(RewardsUtil.rand.nextInt(3))
		{
			case 1:
				entityClass = Creeper.class;
				break;
			case 2:
				entityClass = Skeleton.class;
				break;
			default:
				entityClass = Zombie.class;
				break;
		}

		Entity entity = location.getWorld().spawn(location, entityClass, ent -> {
			ent.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(highestDamage * 30);
			ent.setHealth(highestDamage * 30);
			ent.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 6000, 2));
		});

		new BukkitRunnable() {

			int effect = RewardsUtil.rand.nextInt(5);
			int delay = 0;

			@Override
			public void run()
			{
				if (entity.isDead()) {
					cancel();
				}

				Location pos = entity.getLocation();
				switch(effect)
				{
					case 1:
						delay--;
						if(delay > 0)
							return;

						delay = 6;
						for(double rad = -Math.PI; rad <= Math.PI; rad += (Math.PI / 5))
						{
							SplashPotion splashPotion = location.getWorld().spawn(location, SplashPotion.class);
							splashPotion.setVelocity(new Vector(Math.cos(rad) * 0.2, 1, Math.sin(rad) * 0.2));
							ItemStack itemStack = new ItemStack(Material.SPLASH_POTION);
							PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
							meta.setBasePotionData(new PotionData(RewardsUtil.getRandomPotionType()));
							itemStack.setItemMeta(meta);
							splashPotion.setItem(itemStack);
						}
						break;
					case 2:
						delay--;
						if(delay > 0)
							return;

						delay = 8;
						Location posShift = pos.clone().add(RewardsUtil.rand.nextInt(9) - 4, 0, RewardsUtil.rand.nextInt(9) - 4);
						location.getWorld().strikeLightning(posShift);
						break;
					case 3:
						delay--;
						if(delay > 0)
							return;

						delay = 8;
						float yaw = player.getLocation().getYaw();
						float pitch = player.getLocation().getPitch();
						float floatX = (float) (-Math.sin(yaw * Math.PI / 180) * Math.cos(pitch * Math.PI / 180));
						float floatY = (float) -Math.sin(pitch * Math.PI / 180);
						float floatZ = (float) (Math.cos(yaw * Math.PI / 180) * Math.cos(pitch * Math.PI / 180));
						double f = Math.sqrt(floatX * floatX + floatY * floatY + floatZ * floatZ);
						double x = floatX / f;
						double y = floatY / f;
						double z = floatZ / f;
						x += RewardsUtil.rand.nextGaussian() * 0.0075 * (RewardsUtil.rand.nextFloat() * 0.4F + 0.8F);
						y += RewardsUtil.rand.nextGaussian() * 0.0075 * (RewardsUtil.rand.nextFloat() * 0.4F + 0.8F);
						z += RewardsUtil.rand.nextGaussian() * 0.0075 * (RewardsUtil.rand.nextFloat() * 0.4F + 0.8F);
						x *= 1.6;
						y *= 1.6;
						z *= 1.6;
						x += player.getVelocity().getX();
						z += player.getVelocity().getZ();
						if (!player.isOnGround())
							y += player.getVelocity().getY();

						TippedArrow arrow = location.getWorld().spawnArrow(player.getLocation(), new Vector(x, y, z), 1.6F, 12, TippedArrow.class);
						arrow.setShooter(player);
						player.playSound(location, Sound.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 1.0F / (RewardsUtil.rand.nextFloat() * 0.4F + 0.8F));
						break;
					case 4:
						delay--;
						if(delay > 0)
							return;

						delay = 10;
						location.getWorld().spawn(location, LingeringPotion.class, potion -> {
							ItemStack itemStack = new ItemStack(Material.LINGERING_POTION);
							PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
							meta.setBasePotionData(new PotionData(RewardsUtil.getRandomPotionType()));
							itemStack.setItemMeta(meta);
							potion.setItem(itemStack);
							potion.setVelocity(new Vector(-0.1, 1, -0.1));
						});
						break;
					default:
						location.getBlock().setType(Material.FIRE);
						break;
				}
			}
		}.runTaskTimer(CCubesCore.getInstance(), 5, 6000);
	}
}
