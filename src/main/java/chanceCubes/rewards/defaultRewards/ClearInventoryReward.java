package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClearInventoryReward extends BaseCustomReward
{

	public ClearInventoryReward()
	{
		super(CCubesCore.MODID + ":Clear_Inventory", -100);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		boolean cubes = false;
		ItemStack[] items = player.getInventory().getStorageContents().clone();
		for(int slotNum = 0; slotNum < items.length; slotNum++)
		{
			ItemStack itemStack = player.getInventory().getItem(slotNum);
			if (CCubesBlocks.isChanceCube(itemStack) || CCubesBlocks.isGiantCube(itemStack) || CCubesBlocks.isD20(itemStack))
				cubes = true;
			else
				player.getInventory().setItem(slotNum, null);
		}
		player.getWorld().playSound(location, Sound.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1f, 1f);

		player.sendMessage("I hope you didn't have anything of value with you :)");
		if(cubes)
			player.sendMessage("Don't worry, I left the cubes for you!");

		if(RewardsUtil.rand.nextInt(5) == 1)
		{
			Scheduler.scheduleTask(new Task("Replace_Inventory", 200)
			{
				@Override
				public void callback()
				{
					player.getInventory().setStorageContents(items);
					player.sendMessage("AHHHHHH JK!! You should have seen your face!");
				}

			});
		}
	}
}
