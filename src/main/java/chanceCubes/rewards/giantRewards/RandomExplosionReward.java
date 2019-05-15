package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class RandomExplosionReward extends BaseCustomReward
{
	public RandomExplosionReward()
	{
		super(CCubesCore.MODID + ":Random_Explosion", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		location.getWorld().playSound(location, Sound.AMBIENT_CAVE, SoundCategory.BLOCKS, 1f, 1f);
		Scheduler.scheduleTask(new Task("Random Explosion", 300, 2)
		{
			int delay = 12;
			int count = 0;

			@Override
			public void callback()
			{
				RewardsUtil.placeBlock(Material.AIR.createBlockData(), location);
				World world = location.getWorld();
				int rand = RewardsUtil.rand.nextInt(6);
				for(double xx = 1; xx > -1; xx -= 0.25)
				{
					for(double zz = 1; zz > -1; zz -= 0.25)
					{
						Entity ent;
						switch(rand)
						{
							case 0:
								ent = world.spawn(location.clone().add(0, 1, 0), Creeper.class);
								break;
							case 1:
								ent = world.spawn(location.clone().add(0, 1, 0), TNTPrimed.class);
								break;
							case 3:
								ent = world.spawn(location.clone().add(0, 1, 0), Item.class, item -> item.setItemStack(new ItemStack(Material.MELON_SLICE)));
								break;
							case 4:
								ent = world.spawn(location.clone().add(0, 1, 0), Bat.class);
								break;
							case 5:
								ent = world.spawn(location.clone().add(0, 1, 0), Zombie.class);
								break;
							default:
								ent = world.spawn(location.clone().add(0, 1, 0), Item.class, item -> item.setItemStack(new ItemStack(Material.DIAMOND)));
								break;
						}

						ent.setVelocity(new Vector(xx, Math.random(), zz));
					}
				}
			}

			@Override
			public void update()
			{
				count++;
				if(count >= delay)
				{
					if(delay > 2)
					{
						delay--;
					}

					count = 0;
					int xInc = RewardsUtil.rand.nextInt(2) * (RewardsUtil.rand.nextBoolean() ? -1 : 1);
					int yInc = RewardsUtil.rand.nextInt(2) * (RewardsUtil.rand.nextBoolean() ? -1 : 1);
					int zInc = RewardsUtil.rand.nextInt(2) * (RewardsUtil.rand.nextBoolean() ? -1 : 1);
					World world = location.getWorld();
					if(delay < 3)
					{
						world.spawnParticle(Particle.EXPLOSION_HUGE, location.clone().add(0.5, 0.5, 0.5), 1);
						world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1f, 1f);
					}
					else
					{
						if(RewardsUtil.rand.nextBoolean())
						{
							world.playSound(location, Sound.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1f, 1f);
						}
						else
						{
							world.playSound(location, Sound.ENTITY_BLAZE_HURT, SoundCategory.BLOCKS, 1f, 1f);
						}

						world.spawnParticle(Particle.LAVA, location.clone().add(0.5 + xInc, 0.5 + yInc, 0.5 + zInc), 1);
					}

				}
			}
		});
	}
}
