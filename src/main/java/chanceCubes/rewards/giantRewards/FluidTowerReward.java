package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.util.RewardsUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidTowerReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return 15;
    }

    @Override
    public String getName() {
        return CCubesCore.MODID + ":Fluid_Tower";
    }

    @Override
    public void trigger(World world, BlockPos pos, EntityPlayer player) {
        for (int i = 0; i < 25; i++)
            RewardsUtil.placeBlock(RewardsUtil.getRandomFluid().getBlock().getDefaultState(), world, pos.add(0, i, 0));
    }
}