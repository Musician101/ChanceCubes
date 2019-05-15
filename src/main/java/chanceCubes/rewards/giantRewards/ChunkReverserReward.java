package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.BaseCustomReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ChunkReverserReward extends BaseCustomReward
{

	private List<Entry<Material, Material>> swappedMap = new ArrayList<>();

	public ChunkReverserReward()
	{
		super(CCubesCore.MODID + ":Chuck_Reverse", 0);
		swappedMap.add(new SimpleEntry<>(Material.STONE, Material.DIRT));
		swappedMap.add(new SimpleEntry<>(Material.DIRT, Material.COBBLESTONE));
		swappedMap.add(new SimpleEntry<>(Material.GRASS, Material.STONE));
		swappedMap.add(new SimpleEntry<>(Material.GRAVEL, Material.SAND));
		swappedMap.add(new SimpleEntry<>(Material.SAND, Material.GRAVEL));
		swappedMap.add(new SimpleEntry<>(Material.IRON_ORE, Material.GOLD_ORE));
		swappedMap.add(new SimpleEntry<>(Material.COAL_ORE, Material.DIAMOND_ORE));
		swappedMap.add(new SimpleEntry<>(Material.DIAMOND_ORE, Material.COAL_ORE));
		swappedMap.add(new SimpleEntry<>(Material.GOLD_ORE, Material.IRON_ORE));
		swappedMap.add(new SimpleEntry<>(Material.LAVA, Material.WATER));
		swappedMap.add(new SimpleEntry<>(Material.WATER, Material.LAVA));
		swappedMap.add(new SimpleEntry<>(Material.OAK_LOG, Material.OAK_LEAVES));
		swappedMap.add(new SimpleEntry<>(Material.OAK_LEAVES, Material.OAK_LOG));
		swappedMap.add(new SimpleEntry<>(Material.DARK_OAK_LOG, Material.DARK_OAK_LEAVES));
		swappedMap.add(new SimpleEntry<>(Material.DARK_OAK_LEAVES, Material.DARK_OAK_LOG));
		swappedMap.add(new SimpleEntry<>(Material.ACACIA_LOG, Material.ACACIA_LEAVES));
		swappedMap.add(new SimpleEntry<>(Material.ACACIA_LEAVES, Material.ACACIA_LOG));
		swappedMap.add(new SimpleEntry<>(Material.BIRCH_LOG, Material.BIRCH_LEAVES));
		swappedMap.add(new SimpleEntry<>(Material.BIRCH_LEAVES, Material.BIRCH_LOG));
		swappedMap.add(new SimpleEntry<>(Material.JUNGLE_LOG, Material.JUNGLE_LEAVES));
		swappedMap.add(new SimpleEntry<>(Material.JUNGLE_LEAVES, Material.JUNGLE_LOG));
	}

	@Override
	public void trigger(Location location, Player player)
	{
		player.sendMessage("Initiating Block Inverter");
		List<OffsetBlock> blocks = new ArrayList<>();
		int delay = 0;
		for(int yy = 256; yy > 0; yy--)
		{
			int xx = 0, zz = 0, dx = 0, dy = -1;
			int t = 16;
			int maxI = t * t;

			for(int i = 0; i < maxI; i++)
			{
				if((-16 / 2 <= xx) && (xx <= 16 / 2) && (-16 / 2 <= zz) && (zz <= 16 / 2))
				{
					Block blockAt = new Location(location.getWorld(), location.getX() + xx, yy, location.getZ() + zz).getBlock();
					Optional<Material> toSwapTo = swappedMap.stream().filter(entry -> entry.getKey().equals(blockAt.getType())).map(Entry::getValue).findFirst();
					if (toSwapTo.isPresent()) {
						blocks.add(new OffsetBlock(xx, yy - location.getBlockY(), zz, toSwapTo.get(), false, delay / 5));
						delay++;
					}
				}

				if((xx == zz) || ((xx < 0) && (xx == -zz)) || ((xx > 0) && (xx == 1 - zz)))
				{
					t = dx;
					dx = -dy;
					dy = t;
				}

				xx += dx;
				zz += dy;
			}
		}

		player.sendMessage("Inverting " + blocks.size() + " Material... May take a minute...");
		blocks.forEach(b -> b.spawnInWorld(location));
	}
}
