package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CreeperSurroundedReward extends BaseCustomReward
{
	public CreeperSurroundedReward()
	{
		super(CCubesCore.MODID + ":Surrounded_Creeper", -85);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		int px = player.getLocation().getBlockX();
		int pz = player.getLocation().getBlockX();
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, true, false));
		boolean skip = false;
		for(int xx = 0; xx < 2; xx++)
		{
			int xValue = px + (xx == 0 ? -4 : 4);
			for(int zz = -4; zz < 5; zz++)
			{
				if(!skip)
				{
					Material material = new Location(location.getWorld(), xValue, location.getY(), pz + zz).getBlock().getType();
					Material material2 = new Location(location.getWorld(), xValue, location.getY() + 1, pz + zz).getBlock().getType();
					Material material3 = new Location(location.getWorld(), xValue, location.getY() + 2, pz + zz).getBlock().getType();
					if(!material.isSolid() && !material2.isSolid() && !material3.isSolid())
					{
						location.getWorld().spawn(new Location(location.getWorld(), xValue, location.getY(), location.getZ() + zz, xx == 1 ? 90 : -90, 0), Creeper.class, creeper -> {
							creeper.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(60, 5));
							if(RewardsUtil.rand.nextInt(10) == 1)
								creeper.setPowered(true);
						});
					}
				}
				skip = !skip;
			}
		}

		for(int zz = 0; zz < 2; zz++)
		{
			int zValue = pz + (zz == 0 ? -4 : 4);
			for(int xx = -4; xx < 5; xx++)
			{
				if(!skip)
				{
					Material material = new Location(location.getWorld(), px + xx, location.getY(), zValue).getBlock().getType();
					Material material2 = new Location(location.getWorld(), px + xx, location.getY() + 1, zValue).getBlock().getType();
					Material material3 = new Location(location.getWorld(), px + xx, location.getY() + 2, zValue).getBlock().getType();
					if(!material.isSolid() && !material2.isSolid() && !material3.isSolid())
					{
						location.getWorld().spawn(new Location(location.getWorld(), location.getX() + xx, location.getY(), zValue, zz == 1 ? 180 : 0, 0), Creeper.class, creeper -> {
							creeper.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(60, 5));
							if(RewardsUtil.rand.nextInt(10) == 1)
								creeper.setPowered(true);
						});
					}
				}
				skip = !skip;
			}
		}
	}
}
