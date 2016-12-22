package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TNTSlingReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.MODID + ":TNT_Throw";
    }

    public void throwTNT(final int count, final World world, final BlockPos pos, final EntityPlayer player) {
        EntityTNTPrimed tnt = new EntityTNTPrimed(world, pos.getX(), pos.getY() + 1D, pos.getZ(), player);
        world.spawnEntityInWorld(tnt);
        tnt.setFuse(60);
        tnt.motionX = -1 + (Math.random() * 2);
        tnt.motionY = Math.random();
        tnt.motionZ = -1 + (Math.random() * 2);

        if (count < 25) {
            Task task = new Task("Throw TNT", 10) {

                @Override
                public void callback() {
                    throwTNT(count + 1, world, pos, player);
                }

            };
            Scheduler.scheduleTask(task);
        }
        else {
            for (double xx = 1; xx > -1; xx -= 0.25) {
                for (double zz = 1; zz > -1; zz -= 0.25) {
                    tnt = new EntityTNTPrimed(world, pos.getX(), pos.getY() + 1D, pos.getZ(), null);
                    world.spawnEntityInWorld(tnt);
                    tnt.setFuse(60);
                    tnt.motionX = xx;
                    tnt.motionY = Math.random();
                    tnt.motionZ = zz;
                }
            }
        }
    }

    @Override
    public void trigger(World world, BlockPos pos, EntityPlayer player) {
        this.throwTNT(0, world, pos, player);
    }

}