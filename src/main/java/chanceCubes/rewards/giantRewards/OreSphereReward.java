package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.CustomEntry;
import chanceCubes.util.RewardsUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OreSphereReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.MODID + ":Ore_Sphere";
    }

    @Override
    public void trigger(World world, BlockPos pos, EntityPlayer player) {
        List<OffsetBlock> blocks = new ArrayList<OffsetBlock>();

        CustomEntry<Block, Integer> ore = RewardsUtil.getRandomOre();

        int delay = 0;
        for (int i = 0; i < 5; i++) {
            for (int yy = -5; yy < 6; yy++) {
                for (int zz = -5; zz < 6; zz++) {
                    for (int xx = -5; xx < 6; xx++) {
                        BlockPos loc = new BlockPos(xx, yy, zz);
                        double dist = Math.abs(loc.getDistance(0, 0, 0));
                        if (dist <= i && dist > i - 1) {
                            OffsetBlock osb = new OffsetBlock(xx, yy, zz, ore.getKey(), false, delay);
                            osb.setBlockState(ore.getKey().getStateFromMeta(ore.getValue()));
                            blocks.add(osb);
                            delay++;
                        }
                    }
                }
            }
            delay += 10;
        }

        for (OffsetBlock b : blocks)
            b.spawnInWorld(world, pos.getX(), pos.getY(), pos.getZ());
    }

}