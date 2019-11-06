package chanceCubes.util;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.IInventory;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.TileEntity;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RewardBlockCache
{
	protected Map<Vector, BlockData> storedBlocks = new HashMap<>();
	protected Map<Vector, NBTTagCompound> storedTE = new HashMap<>();

	private Location origin;
	private Location playerLoc;
	private boolean force = true;

	public RewardBlockCache(Location origin, Location playerLoc)
	{
		this.origin = origin;
		this.playerLoc = playerLoc;
	}

	public void setForce(boolean force)
	{
		this.force = force;
	}

	public void cacheBlock(Vector offset, BlockData newData)
	{
		Location adjPos = origin.clone().add(offset);
		Block block = adjPos.getBlock();
		BlockData oldData = block.getBlockData();
		NBTTagCompound oldNBT = null;
		TileEntity te = ((CraftWorld) origin.getWorld()).getHandle().getTileEntity(new BlockPosition(adjPos.getBlockX(), adjPos.getBlockY(), adjPos.getBlockZ()));
		if(te != null)
		{
			oldNBT = te.b();
			if(te instanceof IInventory)
				((IInventory) te).clear();

		}

		if(RewardsUtil.placeBlock(newData, adjPos, force))
		{
			if(!storedBlocks.containsKey(offset))
			{
				storedBlocks.put(offset, oldData);
				if(oldNBT != null)
					storedTE.put(offset, oldNBT);
			}
		}
	}

	public void restoreBlocks(Player player)
	{
		for(Vector loc : storedBlocks.keySet())
		{
			Location newLoc = origin.clone().add(loc);
			RewardsUtil.placeBlock(storedBlocks.get(loc), newLoc, true);
			if(storedTE.containsKey(loc))
				((CraftWorld) newLoc.getWorld()).getHandle().getTileEntity(new BlockPosition(newLoc.getBlockX(), newLoc.getBlockY(), newLoc.getBlockZ())).load(storedTE.get(loc));

		}

		player.getLocation().clone().add(0.5, 0, 0.5);
	}
}
