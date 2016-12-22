package chanceCubes.registry;

import chanceCubes.CCubesCore;
import chanceCubes.config.CCubesSettings;
import chanceCubes.config.ConfigLoader;
import chanceCubes.rewards.defaultRewards.BasicReward;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.giantRewards.ChunkFlipReward;
import chanceCubes.rewards.giantRewards.ChunkReverserReward;
import chanceCubes.rewards.giantRewards.FloorIsLavaReward;
import chanceCubes.rewards.giantRewards.FluidTowerReward;
import chanceCubes.rewards.giantRewards.OrePillarReward;
import chanceCubes.rewards.giantRewards.OreSphereReward;
import chanceCubes.rewards.giantRewards.PotionsReward;
import chanceCubes.rewards.giantRewards.TNTSlingReward;
import chanceCubes.rewards.giantRewards.ThrowablesReward;
import chanceCubes.rewards.type.SchematicRewardType;
import chanceCubes.util.FileUtil;
import chanceCubes.util.RewardData;
import chanceCubes.util.SchematicUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

public class GiantCubeRegistry implements IRewardRegistry {

    public static GiantCubeRegistry INSTANCE = new GiantCubeRegistry();

    private Map<String, IChanceCubeReward> nameToReward = Maps.newHashMap();
    private List<IChanceCubeReward> sortedRewards = Lists.newArrayList();

    /**
     * loads the default rewards of the Chance Cube
     */
    public static void loadDefaultRewards() {
        if (!CCubesSettings.enableHardCodedRewards)
            return;

        INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Village", 0, new SchematicRewardType(SchematicUtil.loadCustomSchematic(FileUtil.JSON_PARSER.parse(RewardData.VILLAGE_SCHEMATIC), 0, -1, 0, 0.05f, false, false, false))));

        INSTANCE.registerReward(new BioDomeReward());
        INSTANCE.registerReward(new TNTSlingReward());
        INSTANCE.registerReward(new ThrowablesReward());
        INSTANCE.registerReward(new OrePillarReward());
        INSTANCE.registerReward(new ChunkReverserReward());
        INSTANCE.registerReward(new FloorIsLavaReward());
        INSTANCE.registerReward(new ChunkFlipReward());
        INSTANCE.registerReward(new OreSphereReward());
        INSTANCE.registerReward(new PotionsReward());
        INSTANCE.registerReward(new FluidTowerReward());
    }

    public void ClearRewards() {
        this.sortedRewards.clear();
        this.nameToReward.clear();
    }

    @Override
    public IChanceCubeReward getRewardByName(String name) {
        return nameToReward.get(name);
    }

    private void redoSort(@Nullable IChanceCubeReward newReward) {
        if (newReward != null)
            sortedRewards.add(newReward);

        Collections.sort(sortedRewards, new Comparator<IChanceCubeReward>() {
            public int compare(IChanceCubeReward o1, IChanceCubeReward o2) {
                return o1.getChanceValue() - o2.getChanceValue();
            }

            ;
        });
    }

    @Override
    public void registerReward(IChanceCubeReward reward) {
        if (ConfigLoader.config.getBoolean(reward.getName(), ConfigLoader.giantRewardCat, true, "") && !this.nameToReward.containsKey(reward.getName())) {
            nameToReward.put(reward.getName(), reward);
            redoSort(reward);
        }
    }

    @Override
    public void triggerRandomReward(World world, BlockPos pos, EntityPlayer player, int chance) {
        if (pos == null)
            return;
        if (this.sortedRewards.size() == 0) {
            CCubesCore.logger.log(Level.WARN, "There are no registered rewards with the Giant Chance Cubes and no reward was able to be given");
            return;
        }

        int pick = world.rand.nextInt(sortedRewards.size());
        CCubesCore.logger.log(Level.INFO, "Triggered the reward with the name of: " + sortedRewards.get(pick).getName());
        sortedRewards.get(pick).trigger(world, pos, player);
    }

    @Override
    public boolean unregisterReward(String name) {
        Object o = nameToReward.remove(name);
        if (o != null)
            return sortedRewards.remove(o);
        return false;
    }
}