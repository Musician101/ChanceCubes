package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DoubleRainbow implements IChanceCubeReward {

    byte[] colorsMeta = {14, 1, 4, 13, 11, 10};

    @Override
    public int getChanceValue() {
        return 15;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Double_Rainbow";
    }

    @Override
    public void trigger(Location location, Player player) {
        RewardsUtil.sendMessageToNearPlayers(world, pos, 32, "Double Rainbow!");
        OffsetBlock b;
        for (int x = -7; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                float dist = (float) (Math.abs(pos.getDistance(pos.getX() + x, pos.getY() + y, pos.getZ())));
                if (dist > 1 && dist <= 8) {
                    int distIndex = (int) (dist - 2);
                    b = new OffsetBlock(x, y, 0, Blocks.WOOL, false);
                    b.setBlockState(Blocks.WOOL.getStateFromMeta(colorsMeta[distIndex]));
                    b.setDelay((x + 7) * 10);
                    b.spawnInWorld(world, pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }

        for (int x = -17; x < 18; x++) {
            for (int y = 0; y < 18; y++) {
                float dist = (float) (Math.abs(pos.getDistance(pos.getX() + x, pos.getY() + y, pos.getZ())));
                if (dist >= 12 && dist <= 18) {
                    int distIndex = (int) (dist - 12);
                    b = new OffsetBlock(x, y, 0, Blocks.WOOL, false);
                    b.setBlockState(Blocks.WOOL.getStateFromMeta(colorsMeta[distIndex]));
                    b.setDelay((x + 12) * 5);
                    b.spawnInWorld(world, pos.getX(), pos.getY(), pos.getZ());
                }
            }
        }
    }

}
