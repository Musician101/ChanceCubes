package chanceCubes.registry;

import chanceCubes.CCubesCore;
import chanceCubes.config.CCubesSettings;
import chanceCubes.rewards.IChanceCubeReward;
import chanceCubes.rewards.defaultRewards.BasicReward;
import chanceCubes.rewards.giantRewards.BeaconArenaReward;
import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.giantRewards.BlockInfectionReward;
import chanceCubes.rewards.giantRewards.BlockThrowerReward;
import chanceCubes.rewards.giantRewards.ChunkFlipReward;
import chanceCubes.rewards.giantRewards.ChunkReverserReward;
import chanceCubes.rewards.giantRewards.FireworkShowReward;
import chanceCubes.rewards.giantRewards.FloorIsLavaReward;
import chanceCubes.rewards.giantRewards.FluidSphereReward;
import chanceCubes.rewards.giantRewards.MixedFluidSphereReward;
import chanceCubes.rewards.giantRewards.OrePillarReward;
import chanceCubes.rewards.giantRewards.OreSphereReward;
import chanceCubes.rewards.giantRewards.PotionsReward;
import chanceCubes.rewards.giantRewards.RandomExplosionReward;
import chanceCubes.rewards.giantRewards.SphereSnakeReward;
import chanceCubes.rewards.giantRewards.TNTSlingReward;
import chanceCubes.rewards.giantRewards.ThrowablesReward;
import chanceCubes.rewards.rewardtype.SchematicRewardType;
import chanceCubes.util.RewardData;
import chanceCubes.util.SchematicUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GiantCubeRegistry implements IRewardRegistry
{
	public static GiantCubeRegistry INSTANCE = new GiantCubeRegistry();

	private Map<String, IChanceCubeReward> nameToReward = Maps.newHashMap();
	private List<IChanceCubeReward> sortedRewards = Lists.newArrayList();

	/**
	 * loads the default rewards of the Chance Cube
	 */
	public static void loadDefaultRewards()
	{
		if(!CCubesSettings.enableHardCodedRewards)
			return;

		INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Village", 0, new SchematicRewardType(SchematicUtil.loadCustomSchematic(RewardData.getVillageSchematic(), 0, -1, 0, 0.1f, false, false, false, true, 0))));
		INSTANCE.registerReward(new BasicReward(CCubesCore.MODID + ":Woodland_Mansion", 0, new SchematicRewardType(SchematicUtil.loadCustomSchematic(RewardData.getWoodlandMansionSchematic(), 0, -1, 0, 0.05f, false, false, true, true, 0))));

		INSTANCE.registerReward(new BioDomeReward());
		INSTANCE.registerReward(new TNTSlingReward());
		INSTANCE.registerReward(new ThrowablesReward());
		INSTANCE.registerReward(new OrePillarReward());
		INSTANCE.registerReward(new ChunkReverserReward());
		INSTANCE.registerReward(new FloorIsLavaReward());
		INSTANCE.registerReward(new ChunkFlipReward());
		INSTANCE.registerReward(new OreSphereReward());
		INSTANCE.registerReward(new PotionsReward());
		INSTANCE.registerReward(new FluidSphereReward());
		INSTANCE.registerReward(new MixedFluidSphereReward());
		INSTANCE.registerReward(new FireworkShowReward());
		INSTANCE.registerReward(new SphereSnakeReward());
		INSTANCE.registerReward(new RandomExplosionReward());
		INSTANCE.registerReward(new BeaconArenaReward());
		INSTANCE.registerReward(new BlockInfectionReward());
		INSTANCE.registerReward(new BlockThrowerReward());
	}

	@Override
	public void registerReward(IChanceCubeReward reward)
	{
		//TODO:
		if(/*
			 * ConfigLoader.config.getBoolean(reward.getName(), ConfigLoader.giantRewardCat, true,
			 * "") &&
			 */ !this.nameToReward.containsKey(reward.getName()))
		{
			nameToReward.put(reward.getName(), reward);
			redoSort(reward);
		}
	}

	@Override
	public boolean unregisterReward(String name)
	{
		IChanceCubeReward reward = nameToReward.remove(name);
		if(reward != null)
			return sortedRewards.remove(reward);
		return false;
	}

	@Override
	public IChanceCubeReward getRewardByName(String name)
	{
		return nameToReward.get(name);
	}

	@Override
	public void triggerRandomReward(Location location, Player player, int chance)
	{
		if(location == null)
			return;

		Logger logger = CCubesCore.getInstance().getLogger();
		if(this.sortedRewards.size() == 0)
		{
			logger.warning("There are no registered rewards with the Giant Chance Cubes and no reward was able to be given");
			return;
		}

		int pick = new Random().nextInt(sortedRewards.size());
		logger.info("Triggered the reward with the name of: " + sortedRewards.get(pick).getName());
		sortedRewards.get(pick).trigger(location, player);
	}

	private void redoSort(@Nullable IChanceCubeReward newReward)
	{
		if(newReward != null)
			sortedRewards.add(newReward);

		sortedRewards.sort(Comparator.comparingInt(IChanceCubeReward::getChanceValue));
	}

	public void ClearRewards()
	{
		this.sortedRewards.clear();
		this.nameToReward.clear();
	}
}
