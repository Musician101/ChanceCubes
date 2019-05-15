package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffectType;

public class RainingCatsAndCogsReward extends BaseCustomReward
{
	private String[] names = { "Radiant_Sora", "Turkey", "MrComputerGhost", "Valsis", "Silver", "Amatt", "Musician", "ReNinjaKitteh", "QuirkyGeek17" };

	public RainingCatsAndCogsReward()
	{
		super(CCubesCore.MODID + ":Cats_And_Dogs", 15);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		RewardsUtil.sendMessageToNearPlayers(location, 36, "It's raining Cats and dogs!");

		Scheduler.scheduleTask(new Task("Raining Cats and Dogs", 500, 5)
		{
			@Override
			public void callback()
			{
			}

			@Override
			public void update()
			{
				int xInc = RewardsUtil.rand.nextInt(10) * (RewardsUtil.rand.nextBoolean() ? -1 : 1);
				int zInc = RewardsUtil.rand.nextInt(10) * (RewardsUtil.rand.nextBoolean() ? -1 : 1);
				Tameable ent;
				if(RewardsUtil.rand.nextBoolean())
				{
					ent = location.getWorld().spawn(new Location(player.getWorld(), player.getLocation().getX() + xInc, 256, player.getLocation().getZ() + zInc), Wolf.class);
				}
				else
				{
					ent = location.getWorld().spawn(new Location(player.getWorld(), player.getLocation().getX() + xInc, 256, player.getLocation().getZ() + zInc), Wolf.class);
					((Ocelot) ent).setCatType(Type.BLACK_CAT);
				}

				ent.setTamed(true);
				((LivingEntity) ent).addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(500, 1000));
				ent.setCustomName(names[RewardsUtil.rand.nextInt(names.length)]);
				ent.setCustomNameVisible(true);
				Scheduler.scheduleTask(new Task("Despawn Delay", 200)
				{
					@Override
					public void callback()
					{
						ent.remove();
					}
				});
			}
		});
	}
}
