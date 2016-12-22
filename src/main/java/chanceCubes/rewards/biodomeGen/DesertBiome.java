package chanceCubes.rewards.biodomeGen;

import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.Blocks;
import org.bukkit.Location;

public class DesertBiome implements IBioDomeBiome {

    @Override
    public Block getFloorBlock() {
        return Blocks.SANDSTONE;
    }

    public void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay) {
        if (y != 0)
            return;

        if (dist < 0 && rand.nextInt(50) == 0) {
            delay++;
            OffsetBlock osb = new OffsetBlock(x, y, z, Blocks.SAND, false, (delay / BioDomeReward.delayShorten) + 1);
            blocks.add(osb);
            osb = new OffsetBlock(x, y - 1, z, Blocks.SANDSTONE, false, (delay / BioDomeReward.delayShorten));
            blocks.add(osb);
            delay++;
            osb = new OffsetBlock(x, y + 1, z, Blocks.DEADBUSH, false, (delay / BioDomeReward.delayShorten));
            blocks.add(osb);
            delay++;
        }

        if (dist < 0 && rand.nextInt(60) == 0) {
            delay++;
            OffsetBlock osb = new OffsetBlock(x, y, z, Blocks.SAND, false, (delay / BioDomeReward.delayShorten) + 1);
            blocks.add(osb);
            osb = new OffsetBlock(x, y - 1, z, Blocks.SANDSTONE, false, (delay / BioDomeReward.delayShorten));
            blocks.add(osb);
            delay++;
            osb = new OffsetBlock(x, y + 1, z, Blocks.CACTUS, false, (delay / BioDomeReward.delayShorten));
            blocks.add(osb);
            delay++;
            osb = new OffsetBlock(x, y + 2, z, Blocks.CACTUS, false, (delay / BioDomeReward.delayShorten));
            blocks.add(osb);
            delay++;
            osb = new OffsetBlock(x, y + 3, z, Blocks.CACTUS, false, (delay / BioDomeReward.delayShorten));
            blocks.add(osb);
            delay++;
        }
    }

    @Override
    public void spawnEntities(Location location) {

    }
}