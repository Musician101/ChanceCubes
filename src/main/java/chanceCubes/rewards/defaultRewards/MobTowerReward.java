package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Silverfish;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public class MobTowerReward extends BaseCustomReward
{
	//@formatter:off
	private List<Class<? extends Entity>> entities = Arrays.asList(Creeper.class, Skeleton.class, Blaze.class,
			Enderman.class, Endermite.class, PigZombie.class, Silverfish.class, Slime.class,
			Snowman.class, Spider.class, Witch.class, Zombie.class, Bat.class, Chicken.class,
			Cow.class, Ocelot.class, Parrot.class, Pig.class, Rabbit.class, Sheep.class,
			Villager.class, Wolf.class);
	//@formatter:on

	public MobTowerReward()
	{
		super(CCubesCore.MODID + ":Mob_Tower", 0);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		player.sendMessage("How did they end up like that? O.o");
		int height = RewardsUtil.rand.nextInt(6) + 7;
		Entity last = location.getWorld().spawn(location, entities.get(RewardsUtil.rand.nextInt(entities.size())));
		for(int i = 0; i < height; i++)
		{
			Entity ent = location.getWorld().spawn(location, entities.get(RewardsUtil.rand.nextInt(entities.size())));
			last.addPassenger(ent);
			last = ent;
		}
	}
}
