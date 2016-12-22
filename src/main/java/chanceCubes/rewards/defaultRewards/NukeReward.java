package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NukeReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -75;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Nuke";
    }

    @Override
    public void trigger(Location location, Player player) {
        RewardsUtil.sendMessageToNearPlayers(world, pos, 32, "May death rain upon them");
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() - 6, pos.getY() + 65, pos.getZ() - 6, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() - 2, pos.getY() + 65, pos.getZ() - 6, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() + 2, pos.getY() + 65, pos.getZ() - 6, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() + 6, pos.getY() + 65, pos.getZ() - 6, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() - 6, pos.getY() + 65, pos.getZ() - 2, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() - 2, pos.getY() + 65, pos.getZ() - 2, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() + 2, pos.getY() + 65, pos.getZ() - 2, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() + 6, pos.getY() + 65, pos.getZ() - 2, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() - 6, pos.getY() + 65, pos.getZ() + 2, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() - 2, pos.getY() + 65, pos.getZ() + 2, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() + 2, pos.getY() + 65, pos.getZ() + 2, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() + 6, pos.getY() + 65, pos.getZ() + 2, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() - 6, pos.getY() + 65, pos.getZ() + 6, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() - 2, pos.getY() + 65, pos.getZ() + 6, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() + 2, pos.getY() + 65, pos.getZ() + 6, null));
        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() + 6, pos.getY() + 65, pos.getZ() + 6, null));
    }

}
