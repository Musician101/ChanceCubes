package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.block.CraftBlock;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;

public class TorchesToCreepers extends BaseCustomReward
{
	public TorchesToCreepers()
	{
		super(CCubesCore.MODID + ":Torches_To_Creepers", -40);
	}

	@Override
	public void trigger(Location location, Player player)
	{
		for(int yy = -32; yy <= 32; yy++)
		{
			for(int xx = -32; xx <= 32; xx++)
			{
				for(int zz = -32; zz <= 32; zz++)
				{
					Location temp = location.clone().add(xx, yy, zz);
					Block block = temp.getBlock();
					if(block.getLightLevel() > 0 && block.getType() != Material.LAVA && !((CraftBlock) block).getNMS().getBlock().isTileEntity())
					{
						RewardsUtil.placeBlock(Material.AIR.createBlockData(), temp);
						temp.getWorld().spawn(temp.clone().add(0.5, 0, 0.5), Creeper.class);
					}
				}
			}
		}
		player.sendMessage("Those lights seem a little weird.... O.o");
	}
}
