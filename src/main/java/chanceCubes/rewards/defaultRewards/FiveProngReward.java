package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FiveProngReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -10;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":5_Prongs";
    }

    @Override
    public void trigger(Location location, Player player) {
        for (int xx = pos.getX() - 3; xx <= pos.getX() + 3; xx++)
            for (int zz = pos.getZ() - 3; zz <= pos.getZ() + 3; zz++)
                for (int yy = pos.getY(); yy <= pos.getY() + 4; yy++)
                    RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, new BlockPos(xx, yy, zz));

        RewardsUtil.placeBlock(Blocks.QUARTZ_BLOCK.getDefaultState(), world, pos);
        RewardsUtil.placeBlock(Blocks.QUARTZ_BLOCK.getDefaultState(), world, pos.add(0, 1, 0));
        RewardsUtil.placeBlock(CCubesBlocks.CHANCE_ICOSAHEDRON.getDefaultState(), world, pos.add(0, 2, 0));

        RewardsUtil.placeBlock(Blocks.QUARTZ_BLOCK.getDefaultState(), world, pos.add(-3, 0, -3));
        RewardsUtil.placeBlock(CCubesBlocks.CHANCE_CUBE.getDefaultState(), world, pos.add(-3, 1, -3));

        RewardsUtil.placeBlock(Blocks.QUARTZ_BLOCK.getDefaultState(), world, pos.add(-3, 0, 3));
        RewardsUtil.placeBlock(CCubesBlocks.CHANCE_CUBE.getDefaultState(), world, pos.add(-3, 1, 3));

        RewardsUtil.placeBlock(Blocks.QUARTZ_BLOCK.getDefaultState(), world, pos.add(3, 0, -3));
        RewardsUtil.placeBlock(CCubesBlocks.CHANCE_CUBE.getDefaultState(), world, pos.add(3, 1, -3));

        RewardsUtil.placeBlock(Blocks.QUARTZ_BLOCK.getDefaultState(), world, pos.add(3, 0, 3));
        RewardsUtil.placeBlock(CCubesBlocks.CHANCE_CUBE.getDefaultState(), world, pos.add(3, 1, 3));
    }
}