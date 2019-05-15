package chanceCubes.util;

import chanceCubes.CCubesCore;
import chanceCubes.config.CCubesSettings;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

/**
 * Handles modifications to {@link CCubesSettings#nonReplaceableBlocks} after blocks have been added
 * by IMC messages.
 */
public class NonReplaceableBlockOverride
{
	public BlockData overriddenBlock;
	public OverrideType overrideType;

	public NonReplaceableBlockOverride()
	{
		overriddenBlock = Material.AIR.createBlockData();
		overrideType = OverrideType.ADD;
	}

	/**
	 * Parses compatible strings into a {@link java.util.List} of {@link NonReplaceableBlockOverride
	 * NonreplaceableBlockOverrides}.
	 *
	 * @param strings
	 *            An array of {@link java.lang.String Strings} to attempt to parse.
	 * @return The resulting {@link java.util.List} of {@link NonReplaceableBlockOverride} objects.
	 */
	public static List<NonReplaceableBlockOverride> parseStrings(String[] strings)
	{
		List<NonReplaceableBlockOverride> overrides = new ArrayList<>();
		for(String string : strings)
		{
			NonReplaceableBlockOverride toAdd = parseString(string);
			if(toAdd != null)
			{
				overrides.add(toAdd);
			}
		}
		return overrides;
	}

	/**
	 * Parses a compatible string into a {@link NonReplaceableBlockOverride}.
	 *
	 * @param string
	 *            The string to parse.
	 * @return The resulting {@link NonReplaceableBlockOverride}.<
	 */
	@Nullable
	private static NonReplaceableBlockOverride parseString(@Nonnull String string)
	{
		try
		{
			switch(string.toCharArray()[0])
			{
				case '+':
				{
					return addBlock(string.substring(1));
				}
				case '-':
				{
					return removeBlock(string.substring(1));
				}
				case '#':
					return null;
				default:
				{
					return addBlock(string);
				}
			}
		} catch(Exception ex)
		{
			CCubesCore.getInstance().getLogger().warning("Error adding block: " + ex.getMessage());
			CCubesCore.getInstance().getLogger().warning("Could not add override for specified block \"" + string + "\", skipping.");
			return null;
		}
	}

	/**
	 * Creates a {@link NonReplaceableBlockOverride} with the {@link OverrideType#REMOVE REMOVE}
	 * {@link OverrideType} from the given Block ID.
	 *
	 * @param substring
	 *            The Block ID.
	 * @return The {@link NonReplaceableBlockOverride} produced.
	 */
	private static NonReplaceableBlockOverride removeBlock(String substring)
	{
		NonReplaceableBlockOverride output = new NonReplaceableBlockOverride();

		output.overrideType = OverrideType.REMOVE;
		Material blockActual = Material.matchMaterial(substring);
		output.overriddenBlock = blockActual.createBlockData();

		//		if(substring.matches(".*:.*:[0-9]*"))
		//		{
		//			output.overrideType = OverrideType.REMOVE;
		//			String block;
		//			String damage;
		//			block = substring.substring(0, substring.lastIndexOf(':') - 1);
		//			damage = substring.substring(substring.lastIndexOf(':') + 1);
		//			Block blockActual = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(block));
		//			output.overriddenBlock = blockActual.getDefaultState();
		//		}
		//		else
		//		{
		//			output.overrideType = OverrideType.REMOVE;
		//			Block blockActual = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(substring));
		//			output.overriddenBlock = blockActual.getDefaultState();
		//		}
		return output;
	}

	/**
	 * Creates a {@link NonReplaceableBlockOverride} with the {@link OverrideType#ADD ADD}
	 * {@link OverrideType} from the given Block ID.
	 *
	 * @param substring
	 *            The Block ID.
	 * @return The {@link NonReplaceableBlockOverride} produced.
	 */
	private static NonReplaceableBlockOverride addBlock(String substring)
	{
		NonReplaceableBlockOverride output = new NonReplaceableBlockOverride();
		output.overrideType = OverrideType.ADD;
		Material blockActual = Material.matchMaterial(substring);
		output.overriddenBlock = blockActual.createBlockData();
//		if(substring.matches(".*:.*:[0-9]*"))
//		{
//			output.overrideType = OverrideType.ADD;
//			String block;
//			String damage;
//			int damageValue;
//			IBlockState blockState;
//			block = substring.substring(0, substring.lastIndexOf(':'));
//			damage = substring.substring(substring.lastIndexOf(':') + 1);
//			damageValue = Integer.parseInt(damage);
//			Block blockActual = Block.getBlockFromName(block);
//			blockState = RewardsUtil.getBlockStateFromBlockMeta(blockActual, damageValue);
//			output.overriddenBlock = blockState;
//		}
//		else
//		{
//			output.overrideType = OverrideType.ADD;
//			IBlockState blockState;
//			Block blockActual = Block.getBlockFromName(substring);
//			blockState = RewardsUtil.getBlockStateFromBlockMeta(blockActual, 0);
//			output.overriddenBlock = blockState;
//		}
		return output;
	}

	/**
	 * Produces an array of {@link java.lang.String Strings} from a {@link java.util.List} of
	 * {@link NonReplaceableBlockOverride NonreplaceableBlockOverrides}.
	 *
	 * @param overrides
	 *            The {@link java.util.List} of {@link NonReplaceableBlockOverride
	 *            NonReplaceableBlockOverrides}.
	 * @return The {@link java.lang.String String} array produced.
	 */
	public static String[] parseOverrides(List<NonReplaceableBlockOverride> overrides)
	{
		List<String> strings = new ArrayList<>();
		for(NonReplaceableBlockOverride override : overrides)
		{
			String toAdd = parseOverride(override);
			if(toAdd != null)
			{
				strings.add(toAdd);
			}
		}

		return strings.toArray(new String[0]);
	}

	/**
	 * Produces a{@link java.lang.String} from a {@link NonReplaceableBlockOverride
	 * NonreplaceableBlockOverride}.
	 *
	 * @param override
	 *            The {@link NonReplaceableBlockOverride NonReplaceableBlockOverride}.
	 * @return The {@link java.lang.String String} produced or null if an error occurs.
	 */
	@Nullable
	private static String parseOverride(@Nonnull NonReplaceableBlockOverride override)
	{
		switch(override.overrideType)
		{
			case ADD:
			{
				return "+" + override.overriddenBlock.getAsString();
			}
			case REMOVE:
			{
				return "-" + override.overriddenBlock.getAsString();
			}
			default:
			{
				CCubesCore.getInstance().getLogger().severe("I have no idea how this managed to fall through to the default...");
				return null;
			}
		}
	}

	/**
	 * Reloads config file and rebuilds {@link CCubesSettings#nonReplaceableBlocks} from
	 * {@link CCubesSettings#nonReplaceableBlocksIMC} and
	 * {@link CCubesSettings#nonReplaceableBlocksOverrides}.
	 */
	public static void loadOverrides()
	{
		try
		{
			purgeOverrides();
			CCubesSettings.nonReplaceableBlocks = CCubesSettings.nonReplaceableBlocksIMC;
			for(NonReplaceableBlockOverride override : CCubesSettings.nonReplaceableBlocksOverrides)
			{
				switch(override.overrideType)
				{
					case ADD:
					{
						if(!CCubesSettings.nonReplaceableBlocks.contains(override.overriddenBlock))
						{
							CCubesSettings.nonReplaceableBlocks.add(override.overriddenBlock);
							CCubesCore.getInstance().getLogger().info("Adding " + override.overriddenBlock.getMaterial().getKey() + " to NRB array.");
						}
						else
						{
							CCubesCore.getInstance().getLogger().info(override.overriddenBlock.getMaterial().getKey() + " already exists in the NRB array, skipping.");
						}
						break;
					}
					case REMOVE:
					{
						if(CCubesSettings.nonReplaceableBlocks.contains(override.overriddenBlock))
						{
							CCubesSettings.nonReplaceableBlocks.remove(override.overriddenBlock);
							CCubesCore.getInstance().getLogger().info("Removing " + override.overriddenBlock.getMaterial().getKey() + " from NRB array.");
						}
						else
						{
							CCubesCore.getInstance().getLogger().info(override.overriddenBlock.getMaterial().getKey() + " has already been removed from the NRB array, skipping.");
						}
						break;
					}
					default:
					{
						CCubesCore.getInstance().getLogger().severe("Something has gone horribly awry, #BlameDaemonumbra!");
					}
				}
			}
		} catch(Exception ex)
		{
			CCubesCore.getInstance().getLogger().warning("Whoops, something went wrong with loading the config, replacing NRB array with safety template...");
			CCubesSettings.nonReplaceableBlocks = CCubesSettings.nonReplaceableBlocksIMC;
			CCubesSettings.nonReplaceableBlocks.addAll(CCubesSettings.backupNRB);
		}
	}

	/**
	 * Purges {@link CCubesSettings#nonReplaceableBlocks} of blocks that are no longer added by
	 * {@link CCubesSettings#nonReplaceableBlocksOverrides}.
	 */
	private static void purgeOverrides()
	{
		List<BlockData> blocksToRemove = new ArrayList<>();
		for(BlockData toRemove : CCubesSettings.nonReplaceableBlocks)
		{
			if(noLongerExists(toRemove))
			{
				blocksToRemove.add(toRemove);
				CCubesCore.getInstance().getLogger().info("Removing " + toRemove.getMaterial().getKey() + " from Overrides list.");
			}
		}
		CCubesSettings.nonReplaceableBlocks.removeAll(blocksToRemove);
	}

	/**
	 * Determines if a {@link org.bukkit.block.data.BlockData} is still present in the
	 * {@link CCubesSettings#nonReplaceableBlocksOverrides} list.
	 *
	 * @param toDetect
	 *            The {@link org.bukkit.block.data.BlockData} to check for.
	 * @return Should be self explanatory...
	 */
	private static boolean noLongerExists(BlockData toDetect)
	{
		for(NonReplaceableBlockOverride override : CCubesSettings.nonReplaceableBlocksOverrides)
		{
			if(override.overriddenBlock == toDetect)
			{
				return false;
			}
		}
		return true;
	}

	public enum OverrideType
	{
		ADD, REMOVE, MODIFY // Not used, here just in case
	}
}
