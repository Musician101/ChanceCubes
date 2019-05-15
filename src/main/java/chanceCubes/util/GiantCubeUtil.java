package chanceCubes.util;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.metadata.ChanceCubeMetadataValue;
import chanceCubes.metadata.GiantCubeMetadataValue;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class GiantCubeUtil
{
	/**
	 * Check that structure is properly formed
	 *
	 * @param location
	 * @param build
	 *            if the giant cube should be built if the structure is valid
	 * @return if there is a valid 3x3x3 configuration
	 */
	public static boolean checkMultiBlockForm(Location location, boolean build)
	{
		Location bottomLeft = findBottomCorner(location);
		int cx = bottomLeft.getBlockX();
		int cy = bottomLeft.getBlockY();
		int cz = bottomLeft.getBlockZ();
		int i = 0;
		// Scan a 3x3x3 area, starting with the bottom left corner
		for(int x = cx; x < cx + 3; x++)
		{
			for(int y = cy; y < cy + 3; y++)
			{
				for(int z = cz; z < cz + 3; z++)
				{
					Block block = location.clone().add(x, y, z).getBlock();
					if(CCubesBlocks.isChanceCube(block))
						i++;
				}
			}
		}
		// check if there are 27 blocks present (3*3*3) and if a giant cube should be built
		if(build)
		{
			if(i > 26)
			{
				setupStructure(new Location(location.getWorld(), cx, cy, cz),true);
				return true;
			}
			return false;
		}
		else
		{
			return i > 26;
		}
	}

	/** Setup all the blocks in the structure */
	public static void setupStructure(Location location, boolean areCoordsCorrect)
	{
		int cx = location.getBlockX();
		int cy = location.getBlockY();
		int cz = location.getBlockZ();

		if(!areCoordsCorrect)
		{
			Location bottomLeft = findBottomCorner(location);
			cx = bottomLeft.getBlockX();
			cy = bottomLeft.getBlockY();
			cz = bottomLeft.getBlockZ();
		}

		int i = 0;
		for(int x = cx; x < cx + 3; x++)
		{
			for(int z = cz; z < cz + 3; z++)
			{
				for(int y = cy; y < cy + 3; y++)
				{
					i++;
					Location newLocation = new Location(location.getWorld(), x, y, z);
					RewardsUtil.placeBlock(Material.LAPIS_BLOCK.createBlockData(), newLocation, i == 27, false);
					Block block = newLocation.getBlock();
					block.removeMetadata("ChanceCubes", CCubesCore.getInstance());
					GiantCubeMetadataValue tile = new GiantCubeMetadataValue();
					// Check if block is bottom center block
					boolean master = (x == cx && y == cy + 1 && z == cz);
					tile.setMasterLocation(new Location(location.getWorld(), cx + 1, cy + 1, cz + 1));
					tile.setHasMaster(true);
					tile.setIsMaster(master);
					block.setMetadata("ChanceCubes", tile);
				}
			}
		}

		location.getWorld().playSound(location, Sound.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	public static Location findBottomCorner(Location location)
	{
		int cx = location.getBlockX();
		int cy = location.getBlockY();
		int cz = location.getBlockZ();
		Block block = location.clone().add(0, -1, 0).getBlock();
		while(CCubesBlocks.isChanceCube(block))
		{
			location = location.add(0, -1, 0);
			cy--;
		}

		block = location.clone().add(-1, 0, 0).getBlock();
		while(CCubesBlocks.isChanceCube(block))
		{
			location = location.add(-1, 0, 0);
			cx--;
		}

		block = location.clone().add(0, 0, -1).getBlock();
		while(CCubesBlocks.isChanceCube(block))
		{
			location = location.add(0, 0, -1);
			cz--;
		}

		return new Location(location.getWorld(), cx, cy, cz);
	}

	/** Reset all the parts of the structure */
	public static void resetStructure(Vector pos, World world)
	{
		for(int x = pos.getBlockX() - 1; x < pos.getX() + 2; x++)
			for(int y = pos.getBlockY() - 1; y < pos.getY() + 2; y++)
				for(int z = pos.getBlockZ() - 1; z < pos.getZ() + 2; z++)
				{
					Block block = world.getBlockAt(x, y, z);
					Optional<GiantCubeMetadataValue> optional = block.getMetadata("ChanceCubes").stream().filter(metadataValue -> metadataValue.getOwningPlugin() instanceof CCubesCore && metadataValue instanceof GiantCubeMetadataValue).map(GiantCubeMetadataValue.class::cast).findFirst();
					if (block.getType() == Material.LAPIS_BLOCK && optional.isPresent())
					{
						optional.get().reset();
						block.removeMetadata("ChanceCubes", CCubesCore.getInstance());
						block.setMetadata("ChanceCubes", new ChanceCubeMetadataValue());
					}
				}
	}

	/** Reset all the parts of the structure */
	public static void removeStructure(Location location)
	{
		for(int x = location.getBlockX() - 1; x < location.getX() + 2; x++)
			for(int y = location.getBlockY() - 1; y < location.getY() + 2; y++)
				for(int z = location.getBlockZ() - 1; z < location.getZ() + 2; z++)
				{
					Block block = location.getWorld().getBlockAt(x, y, z);
					Optional<GiantCubeMetadataValue> optional = block.getMetadata("ChanceCubes").stream().filter(metadataValue -> metadataValue.getOwningPlugin() instanceof CCubesCore && metadataValue instanceof GiantCubeMetadataValue).map(GiantCubeMetadataValue.class::cast).findFirst();
					if(block.getType() == Material.LAPIS_BLOCK && optional.isPresent())
					{
						optional.get().reset();
						block.removeMetadata("ChanceCubes", CCubesCore.getInstance());
						block.setType(Material.AIR);
					}
				}
	}
}
