package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TravellerReward extends BaseCustomReward
{
	public TravellerReward()
	{
		super(CCubesCore.MODID + ":Traveller", 15);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		int x = RewardsUtil.rand.nextInt(1000) + 200;
		int z = RewardsUtil.rand.nextInt(1000) + 200;

		Location newPos = location.clone().add(x, 0, z);
		Chest chestData = (Chest) Material.CHEST.createBlockData();
		chestData.setFacing(BlockFace.WEST);
		RewardsUtil.placeBlock(chestData, newPos);
		org.bukkit.block.Chest chest = (org.bukkit.block.Chest) newPos.getBlock().getState();
		for(int i = 0; i < 10; i++)
			chest.getInventory().setItem(i, new ItemStack(RewardsUtil.getRandomItem()));

		RewardsUtil.sendMessageToNearPlayers(location, 25, "" + newPos.getX() + ", " + newPos.getY() + ", " + newPos.getZ());
	}
}
