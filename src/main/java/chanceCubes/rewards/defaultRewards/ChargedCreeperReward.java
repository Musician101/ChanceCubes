package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChargedCreeperReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -40;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Charged_Creeper";
    }

    @Override
    public void trigger(final Location location, final Player player) {
        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(0, 1, 0));
        EntityCreeper ent = new EntityCreeper(world);
        ent.setLocationAndAngles(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0, 0);
        ent.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1, 99, true, false));
        ent.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 10, 99, true, false));
        world.spawnEntityInWorld(ent);

        Task task = new Task("Charged Creeper Reward", 2) {
            @Override
            public void callback() {
                world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));
            }
        };

        Scheduler.scheduleTask(task);
    }

}
