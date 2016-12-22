package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DiscoReward implements IChanceCubeReward {

    private Random rand = new Random();

    @Override
    public int getChanceValue() {
        return 40;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Disco";
    }

    @Override
    public void trigger(Location location, Player player) {
        for (int xx = -4; xx < 5; xx++)
            for (int zz = -4; zz < 5; zz++)
                RewardsUtil.placeBlock(Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.byMetadata(rand.nextInt(16))), world, pos.add(xx, -1, zz));

        for (int i = 0; i < 10; i++) {
            EntitySheep sheep = new EntitySheep(world);
            sheep.setCustomNameTag("jeb_");
            sheep.setLocationAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), 0, 0);
            world.spawnEntityInWorld(sheep);
        }

        RewardsUtil.placeBlock(CCubesBlocks.CHANCE_ICOSAHEDRON.getDefaultState(), world, pos.add(0, 3, 0));

        RewardsUtil.sendMessageToNearPlayers(world, pos, 32, "Disco Party!!!!");
    }

}