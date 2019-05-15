package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.config.CCubesSettings;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class BlockThrowerReward extends BaseCustomReward
{
	public BlockThrowerReward()
	{
		super(CCubesCore.MODID + ":Block_Thrower", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		for(int x = -20; x < 21; x++)
		{
			for(int z = -20; z < 21; z++)
			{
				if(RewardsUtil.isAir(location.clone().add(x, 11, z).getBlock()))
					for(int y = -1; y < 12; y++)
						location.clone().add(x, y, z).getBlock().setType(Material.AIR);
			}
		}

		Scheduler.scheduleTask(new Task("Throw_Block", 450, 2)
		{
			private List<FallingBlock> blocks = new ArrayList<>();

			@Override
			public void update()
			{
				if(this.delayLeft > 100)
				{
					int x = RewardsUtil.rand.nextInt(41) - 21;
					int z = RewardsUtil.rand.nextInt(41) - 21;
					int y;
					for(y = 12; y > -2; y--)
						if(RewardsUtil.isAir(location.clone().add(x, y, z).getBlock()))
							break;

					Location newLoc = location.clone().add(x, y, z);
					BlockData state = newLoc.getBlock().getBlockData();

					if(!CCubesSettings.nonReplaceableBlocks.contains(state) || state.getMaterial() != Material.AIR || state.getMaterial() != Material.WATER || state.getMaterial() != Material.LAVA)
					{
						newLoc.getBlock().setType(Material.AIR);
					}

					blocks.add(newLoc.getWorld().spawn(newLoc, FallingBlock.class, fallingBlock -> {
						fallingBlock.setGravity(true);
						fallingBlock.setVelocity(new Vector(0, 0.25, 0));
					}));
				}

				blocks.stream().filter(b -> b.getLocation().getY() > location.getY() + 8).forEach(b -> {
					b.teleport(location.clone().add(0, 8, 0));
					Vector vector = b.getVelocity();
					b.setVelocity(new Vector(vector.getX(), 0, vector.getZ()));
				});
			}

			@Override
			public void callback()
			{
				int rand = RewardsUtil.rand.nextInt(6);
				blocks.forEach(b -> {
					Location bLoc = b.getLocation();
					World world = bLoc.getWorld();
					world.spawnParticle(Particle.EXPLOSION_LARGE, bLoc, 1);
					switch(rand)
					{
						case 0:
							world.spawn(bLoc, Creeper.class, creeper -> creeper.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 3)));
							break;
						case 1:
							world.spawn(bLoc, TNTPrimed.class);
							break;
						case 3:
							world.spawn(bLoc, Item.class, item -> item.setItemStack(new ItemStack(Material.MELON_SLICE)));
							break;
						case 4:
							world.spawn(bLoc, Bat.class);
							break;
						case 5:
							world.spawn(bLoc, Zombie.class, zombie -> zombie.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 3)));
							break;
						default:
							world.spawn(bLoc, Item.class, item -> item.setItemStack(new ItemStack(Material.DIAMOND)));
							break;
					}
					b.remove();
				});
				location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1f, 1f);
			}
		});

	}
}
