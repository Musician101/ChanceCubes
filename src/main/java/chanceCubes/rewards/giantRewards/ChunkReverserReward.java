package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.CustomEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ChunkReverserReward implements IChanceCubeReward {

    private List<Entry<Block, Block>> swappedMap = new ArrayList<Entry<Block, Block>>();

    public ChunkReverserReward() {
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.STONE, Blocks.DIRT));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.DIRT, Blocks.COBBLESTONE));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.GRASS, Blocks.STONE));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.GRAVEL, Blocks.SAND));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.SAND, Blocks.GRAVEL));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.IRON_ORE, Blocks.GOLD_ORE));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.COAL_ORE, Blocks.DIAMOND_ORE));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.DIAMOND_ORE, Blocks.COAL_ORE));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.GOLD_ORE, Blocks.IRON_ORE));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.LAVA, Blocks.WATER));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.WATER, Blocks.LAVA));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.LOG, Blocks.LEAVES));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.LOG2, Blocks.LEAVES2));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.LEAVES, Blocks.LOG));
        swappedMap.add(new CustomEntry<Block, Block>(Blocks.LEAVES2, Blocks.LOG2));
    }

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.MODID + ":Chuck_Reverse";
    }

    @Override
    public void trigger(World world, BlockPos pos, EntityPlayer player) {
        player.addChatMessage(new TextComponentString("Initiating Block Inverter"));
        List<OffsetBlock> blocks = new ArrayList<OffsetBlock>();
        int delay = 0;
        for (int yy = 256; yy > 0; yy--) {
            int xx = 0, zz = 0, dx = 0, dy = -1;
            int t = 16;
            int maxI = t * t;

            for (int i = 0; i < maxI; i++) {
                if ((-16 / 2 <= xx) && (xx <= 16 / 2) && (-16 / 2 <= zz) && (zz <= 16 / 2)) {
                    Block blockAt = world.getBlockState(new BlockPos(pos.getX() + xx, yy, pos.getZ() + zz)).getBlock();
                    Block toSwapTo = null;
                    for (Entry<Block, Block> blockSwap : swappedMap) {
                        if (blockSwap.getKey().equals(blockAt)) {
                            toSwapTo = blockSwap.getValue();
                        }
                    }
                    if (toSwapTo != null) {
                        blocks.add(new OffsetBlock(xx, yy - pos.getY(), zz, toSwapTo, false, delay / 5));
                        delay++;
                    }
                }

                if ((xx == zz) || ((xx < 0) && (xx == -zz)) || ((xx > 0) && (xx == 1 - zz))) {
                    t = dx;
                    dx = -dy;
                    dy = t;
                }
                xx += dx;
                zz += dy;
            }
        }

        player.addChatMessage(new TextComponentString("Inverting " + blocks.size() + " Blocks... May take a minute..."));
        for (OffsetBlock b : blocks)
            b.spawnInWorld(world, pos.getX(), pos.getY(), pos.getZ());
    }

}