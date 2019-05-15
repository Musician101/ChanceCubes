package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JukeboxReward extends BaseCustomReward
{
	private ItemStack[] discs = new ItemStack[] { new ItemStack(Material.MUSIC_DISC_11), new ItemStack(Material.MUSIC_DISC_13), new ItemStack(Material.MUSIC_DISC_BLOCKS), new ItemStack(Material.MUSIC_DISC_CAT), new ItemStack(Material.MUSIC_DISC_CHIRP), new ItemStack(Material.MUSIC_DISC_FAR), new ItemStack(Material.MUSIC_DISC_MALL), new ItemStack(Material.MUSIC_DISC_MELLOHI), new ItemStack(Material.MUSIC_DISC_STAL), new ItemStack(Material.MUSIC_DISC_STRAD), new ItemStack(Material.MUSIC_DISC_WAIT), new ItemStack(Material.MUSIC_DISC_WARD) };

	public JukeboxReward()
	{
		super(CCubesCore.MODID + ":Juke_Box", 5);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		RewardsUtil.placeBlock(Material.JUKEBOX.createBlockData(), location);
		Jukebox jukebox = (Jukebox) location.getBlock().getState();
		ItemStack disc = discs[RewardsUtil.rand.nextInt(discs.length)];
		jukebox.setRecord(disc);
	}
}
