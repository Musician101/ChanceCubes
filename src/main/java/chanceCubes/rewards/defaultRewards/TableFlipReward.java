package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TableFlipReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Table_Flip";
    }

    public void nextStep(final int stage, final Location location, final BlockPos pos) {
        Task task = new Task("Table_Flip", 10) {
            @Override
            public void callback() {
                switch (stage) {
                    case 0: {
                        RewardsUtil.placeBlock(Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), world, pos);
                        RewardsUtil.placeBlock(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.STRAIGHT), world, pos.add(1, 0, 0));
                        RewardsUtil.placeBlock(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.STRAIGHT), world, pos.add(-1, 0, 0));
                        break;
                    }
                    case 1: {
                        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos);
                        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(1, 0, 0));
                        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(-1, 0, 0));

                        RewardsUtil.placeBlock(Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), world, pos.add(0, 1, 0));
                        RewardsUtil.placeBlock(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.STRAIGHT), world, pos.add(1, 1, 0));
                        RewardsUtil.placeBlock(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.STRAIGHT), world, pos.add(-1, 1, 0));
                        break;
                    }
                    case 2: {
                        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(0, 1, 0));
                        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(1, 1, 0));
                        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(-1, 1, 0));

                        RewardsUtil.placeBlock(Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), world, pos.add(0, 2, 1));
                        RewardsUtil.placeBlock(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.STRAIGHT), world, pos.add(1, 2, 1));
                        RewardsUtil.placeBlock(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.STRAIGHT), world, pos.add(-1, 2, 1));
                        break;
                    }
                    case 3: {
                        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(0, 2, 1));
                        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(1, 2, 1));
                        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(-1, 2, 1));

                        RewardsUtil.placeBlock(Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), world, pos.add(0, 1, 2));
                        RewardsUtil.placeBlock(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.STRAIGHT), world, pos.add(1, 1, 2));
                        RewardsUtil.placeBlock(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.STRAIGHT), world, pos.add(-1, 1, 2));
                        break;
                    }
                    case 4: {
                        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(0, 1, 2));
                        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(1, 1, 2));
                        RewardsUtil.placeBlock(Blocks.AIR.getDefaultState(), world, pos.add(-1, 1, 2));

                        RewardsUtil.placeBlock(Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), world, pos.add(0, 0, 2));
                        RewardsUtil.placeBlock(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.STRAIGHT), world, pos.add(1, 0, 2));
                        RewardsUtil.placeBlock(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.SHAPE, BlockStairs.EnumShape.STRAIGHT), world, pos.add(-1, 0, 2));
                        break;
                    }
                }

                if (stage < 4)
                    nextStep(stage + 1, world, pos);
            }

        };

        Scheduler.scheduleTask(task);
    }

    @Override
    public void trigger(Location location, Player player) {
        RewardsUtil.sendMessageToAllPlayers(world, "(╯°□°）╯︵ ┻━┻)");
        this.nextStep(0, world, pos);
    }
}