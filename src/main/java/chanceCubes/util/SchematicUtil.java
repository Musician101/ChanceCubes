package chanceCubes.util;

import chanceCubes.config.ConfigLoader;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.rewards.rewardparts.OffsetTileEntity;
import chanceCubes.rewards.variableTypes.BoolVar;
import chanceCubes.rewards.variableTypes.FloatVar;
import chanceCubes.rewards.variableTypes.IntVar;
import chanceCubes.rewards.variableTypes.NBTVar;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.v1_14_R1.MojangsonParser;
import net.minecraft.server.v1_14_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

public class SchematicUtil
{
	public static CustomSchematic loadWorldEditSchematic(String fileName, int xOff, int yOff, int zOff, FloatVar spacingDelay, BoolVar falling, BoolVar relativeToPlayer, BoolVar includeAirBlocks, BoolVar playSound, IntVar delay)
	{
		File schematic = new File(ConfigLoader.folder.getAbsolutePath() + "/CustomRewards/Schematics/" + fileName);
		NBTTagCompound nbtData;
		try
		{
			FileInputStream is = new FileInputStream(schematic);
			nbtData = NBTCompressedStreamTools.a(is);
			is.close();
		} catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return loadWorldEditSchematic(nbtData, xOff, yOff, zOff, spacingDelay, falling, relativeToPlayer, includeAirBlocks, playSound, delay);
	}

	public static CustomSchematic loadWorldEditSchematic(NBTTagCompound nbtData, int xOff, int yOff, int zOff, FloatVar spacingDelay, BoolVar falling, BoolVar relativeToPlayer, BoolVar includeAirBlocks, BoolVar playSound, IntVar delay)
	{
		short width = nbtData.getShort("Width");
		short height = nbtData.getShort("Height");
		short length = nbtData.getShort("Length");
		int paletteMax = nbtData.getInt("PaletteMax");
		NBTTagCompound paletteNBT = nbtData.getCompound("Palette");
		if (paletteMax != paletteNBT.d()) {
			return null;
		}

		Map<Integer, BlockData> palette = new HashMap<>();
		for(String part : paletteNBT.getKeys())
		{
			try
			{
				BlockData blockData = Bukkit.createBlockData(part);
				int id = paletteNBT.getInt(part);
				palette.put(id, blockData);
			}
			catch(IllegalArgumentException e)
			{
				return null;
			}
		}

		byte[] blockData = nbtData.getByteArray("BlockData");
		NBTTagList tileEntities = nbtData.getList("TileEntities", 10);
		int i = 0;
		short halfLength = (short) (length / 2);
		short halfWidth = (short) (width / 2);
		List<OffsetBlock> offsetBlocks = new ArrayList<>();
		for(int yy = 0; yy < height; yy++)
		{
			for(int zz = 0; zz < length; zz++)
			{
				for(int xx = 0; xx < width; xx++)
				{
					int j = blockData[i];
					if(j < 0)
						j = 128 + (128 + j);

					BlockData b = palette.get(j);
					if(!RewardsUtil.isAir(b))
					{
						OffsetBlock block = new OffsetBlock(halfWidth - xx + xOff, yy + yOff, halfLength - zz + zOff, b.getMaterial(), falling);
						block.setBlockData(b);
						NBTTagCompound nbt = getTileEntity(xx, yy, zz, tileEntities);
						if (nbt != null) {
							block = new OffsetTileEntity(halfWidth - xx + xOff, yy + yOff, halfLength - zz + zOff, b, nbt, falling);
						}
						block.setRelativeToPlayer(relativeToPlayer);
						block.setPlaysSound(playSound);
						offsetBlocks.add(block);
					}
					i++;
				}
			}
		}

		return new CustomSchematic(offsetBlocks, width, height, length, relativeToPlayer, includeAirBlocks, spacingDelay, delay);
	}

	private static NBTTagCompound getTileEntity(int x, int y, int z, NBTTagList tileEntities) {
		for (int i = 0; i < tileEntities.size(); i++) {
			NBTTagCompound nbt = tileEntities.getCompound(i);
			if (nbt.getInt("x") == x && nbt.getInt("y") == y && nbt.getInt("z") == z) {
				return nbt;
			}
		}

		return null;
	}

	public static CustomSchematic loadCustomSchematic(String file, int xOffSet, int yOffSet, int zOffSet, FloatVar spacingDelay, BoolVar falling, BoolVar relativeToPlayer, BoolVar includeAirBlocks, BoolVar playSound, IntVar delay)
	{
		JsonElement elem = FileUtil.readJsonfromFile(ConfigLoader.folder.getAbsolutePath() + "/CustomRewards/Schematics/" + file);
		return loadCustomSchematic(elem, xOffSet, yOffSet, zOffSet, spacingDelay, falling, relativeToPlayer, includeAirBlocks, playSound, delay);
	}

	public static CustomSchematic loadCustomSchematic(JsonElement elem, int xOffSet, int yOffSet, int zOffSet, float spacingDelay, boolean falling, boolean relativeToPlayer, boolean includeAirBlocks, boolean playSound, int delay)
	{
		return loadCustomSchematic(elem, xOffSet, yOffSet, zOffSet, new FloatVar(spacingDelay), new BoolVar(falling), new BoolVar(relativeToPlayer), new BoolVar(includeAirBlocks), new BoolVar(playSound), new IntVar(delay));
	}

	public static CustomSchematic loadCustomSchematic(JsonElement elem, int xOffSet, int yOffSet, int zOffSet, FloatVar spacingDelay, BoolVar falling, BoolVar relativeToPlayer, BoolVar includeAirBlocks, BoolVar playSound, IntVar delay)
	{
		if(elem == null)
			return null;
		JsonObject json = elem.getAsJsonObject();
		List<OffsetBlock> offsetBlocks = new ArrayList<>();
		JsonObject info = json.get("Schematic Data").getAsJsonObject();
		int xSize = info.get("xSize").getAsInt();
		int ySize = info.get("ySize").getAsInt();
		int zSize = info.get("zSize").getAsInt();
		List<Entry<Integer, String>> blockDataIds = new ArrayList<>();

		JsonArray blockDataArray = json.get("Block Data").getAsJsonArray();
		for(JsonElement i : blockDataArray)
		{
			JsonObject index = i.getAsJsonObject();
			for(Entry<String, JsonElement> obj : index.entrySet())
				blockDataIds.add(new SimpleEntry<>(obj.getValue().getAsInt(), obj.getKey()));
		}

		int index = 0;
		List<Integer> blockArray = new ArrayList<>();
		for(JsonElement ids : json.get("Blocks").getAsJsonArray())
		{
			String entry = ids.getAsString();
			String[] parts = entry.split("x");
			int id = Integer.parseInt(parts[0]);
			int recurse = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;
			for(int i = 0; i < recurse; i++)
				blockArray.add(id);
		}
		for(int yOff = 0; yOff < ySize; yOff++)
		{
			for(int xOff = (xSize / 2) - xSize; xOff < (xSize / 2); xOff++)
			{
				for(int zOff = (zSize / 2) - zSize; zOff < (zSize / 2); zOff++)
				{
					int id = blockArray.get(index);
					String blockData = "";
					for (Entry<Integer, String> entry : blockDataIds)
					{
						if(entry.getKey() == id)
						{
							blockData = entry.getValue();
							break;
						}
					}
					String[] dataParts = blockData.split(":");
					Material material = Material.matchMaterial(dataParts[0] + ":" + dataParts[1]);
					OffsetBlock osb = new OffsetBlock(xOff + xOffSet, yOff + yOffSet, zOff + zOffSet, material, falling, new IntVar(0));
					// TODO: Find better way?
					//osb.setBlockState(RewardsUtil.getBlockStateFromBlockMeta(b, Integer.parseInt(dataParts[2])));
					osb.setRelativeToPlayer(relativeToPlayer);
					osb.setPlaysSound(playSound);
					offsetBlocks.add(osb);
					index++;
				}
			}
		}

		JsonArray teArray = json.get("TileEntities").getAsJsonArray();
		for(JsonElement i : teArray)
		{
			for(Entry<String, JsonElement> obj : i.getAsJsonObject().entrySet())
			{
				String teData = obj.getKey();
				for(JsonElement ids : obj.getValue().getAsJsonArray())
				{
					int id = ids.getAsInt();
					OffsetBlock osb = offsetBlocks.get(id);
					OffsetTileEntity oste = OffsetBlockToTileEntity(osb, teData);
					if(oste != null)
						offsetBlocks.set(id, oste);
				}
			}
		}

		return new CustomSchematic(offsetBlocks, xSize, ySize, zSize, relativeToPlayer, includeAirBlocks, spacingDelay, delay);
	}

	public static OffsetTileEntity OffsetBlockToTileEntity(OffsetBlock osb, String nbt)
	{
		try
		{
			return OffsetBlockToTileEntity(osb, MojangsonParser.parse(nbt));
		} catch(CommandSyntaxException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static OffsetTileEntity OffsetBlockToTileEntity(OffsetBlock osb, NBTVar nbt)
	{
		OffsetTileEntity oste = new OffsetTileEntity(osb.xOff, osb.yOff, osb.zOff, osb.getBlockData(), nbt, osb.isFallingVar(), osb.getDelayVar());
		oste.setBlockData(osb.getBlockData());
		oste.setDelay(osb.getDelay());
		oste.setRelativeToPlayer(osb.isRelativeToPlayer());
		oste.setFalling(osb.isFalling());
		return oste;
	}

	public static OffsetTileEntity OffsetBlockToTileEntity(OffsetBlock osb, NBTTagCompound nbt)
	{
		OffsetTileEntity oste = new OffsetTileEntity(osb.xOff, osb.yOff, osb.zOff, osb.getBlockData(), new NBTVar(nbt), osb.isFallingVar(), osb.getDelayVar());
		oste.setBlockData(osb.getBlockData());
		oste.setDelay(osb.getDelay());
		oste.setRelativeToPlayer(osb.isRelativeToPlayer());
		oste.setFalling(osb.isFalling());
		return oste;
	}
}
