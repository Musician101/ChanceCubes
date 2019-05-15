package chanceCubes.util;

import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.config.CCubesSettings;
import chanceCubes.metadata.ChanceCubeMetadataValue;
import chanceCubes.metadata.D20MetadataValue;
import chanceCubes.rewards.rewardparts.ChanceCubeOffsetBlock;
import chanceCubes.rewards.rewardparts.CommandPart;
import chanceCubes.rewards.rewardparts.EntityPart;
import chanceCubes.rewards.rewardparts.ItemPart;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.rewards.rewardparts.ParticlePart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.server.v1_13_R2.CommandListenerWrapper;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.MinecraftServer;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.Vec2F;
import net.minecraft.server.v1_13_R2.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

public class RewardsUtil
{
	private static List<Material> oredicts = new ArrayList<>();
	private static List<String> fluids = new ArrayList<>();

	public static final Random rand = new Random();

	public static List<Material> getOreDicts()
	{
		return oredicts;
	}

	public static List<String> getFluids()
	{
		return fluids;
	}

	public static void initData()
	{
		oredicts.add(Material.COAL_ORE);
		oredicts.add(Material.DIAMOND_ORE);
		oredicts.add(Material.EMERALD_ORE);
		oredicts.add(Material.GOLD_ORE);
		oredicts.add(Material.IRON_ORE);
		oredicts.add(Material.LAPIS_ORE);
		oredicts.add(Material.NETHER_QUARTZ_ORE);
		oredicts.add(Material.REDSTONE_ORE);
	}

	public static boolean isAir(Block block) {
		switch(block.getType()) {
			case AIR:
			case CAVE_AIR:
			case VOID_AIR:
				return true;
		}

		return false;
	}

	public static boolean isAir(ItemStack itemStack) {
		if (itemStack == null) {
			return true;
		}

		switch(itemStack.getType()) {
			case AIR:
			case CAVE_AIR:
			case VOID_AIR:
				return true;
		}

		return false;
	}

	/**
	 *
	 * @param xSize
	 * @param ySize
	 * @param zSize
	 * @param block
	 * @param xOff
	 * @param yOff
	 * @param zOff
	 * @param falling
	 * @param delay
	 * @param causesUpdate
	 * @param relativeToPlayer
	 * @return
	 */
	public static OffsetBlock[] fillArea(int xSize, int ySize, int zSize, Material block, int xOff, int yOff, int zOff, boolean falling, int delay, boolean causesUpdate, boolean relativeToPlayer)
	{
		List<OffsetBlock> toReturn = new ArrayList<>();

		for(int y = 0; y < ySize; y++)
			for(int z = 0; z < zSize; z++)
				for(int x = 0; x < xSize; x++)
					toReturn.add(new OffsetBlock(x + xOff, y + yOff, z + zOff, block, falling, delay).setCausesBlockUpdate(causesUpdate).setRelativeToPlayer(relativeToPlayer));

		return toReturn.toArray(new OffsetBlock[0]);
	}

	public static ChanceCubeOffsetBlock[] fillArea(int xSize, int ySize, int zSize, int xOff, int yOff, int zOff, int delay, boolean causesUpdate, boolean relativeToPlayer) {
		List<ChanceCubeOffsetBlock> toReturn = new ArrayList<>();

		for(int y = 0; y < ySize; y++)
			for(int z = 0; z < zSize; z++)
				for(int x = 0; x < xSize; x++)
					toReturn.add((ChanceCubeOffsetBlock) new ChanceCubeOffsetBlock(x + xOff, y + yOff, z + zOff, CCubesBlocks.getChanceCube(1), delay).setCausesBlockUpdate(causesUpdate).setRelativeToPlayer(relativeToPlayer));

		return toReturn.toArray(new ChanceCubeOffsetBlock[0]);
	}

	public static OffsetBlock[] addBlocksLists(OffsetBlock[]... lists)
	{
		int size = 0;
		for(OffsetBlock[] list : lists)
			size += list.length;

		OffsetBlock[] toReturn = new OffsetBlock[size];

		int i = 0;
		for(OffsetBlock[] list : lists)
		{
			for(OffsetBlock osb : list)
			{
				toReturn[i] = osb;
				i++;
			}
		}

		return toReturn;
	}

	public static boolean isAir(BlockData blockdata)
	{
		switch(blockdata.getMaterial()) {
			case AIR:
			case CAVE_AIR:
			case VOID_AIR:
				return true;
		}

		return false;
	}

	public static EntityPart[] spawnXEntities(NBTTagCompound entityNbt, int amount)
	{
		EntityPart[] toReturn = new EntityPart[amount];
		for(int i = 0; i < amount; i++)
			toReturn[i] = new EntityPart(entityNbt);
		return toReturn;
	}

	public static CommandPart[] executeXCommands(String command, int amount)
	{
		CommandPart[] toReturn = new CommandPart[amount];
		for(int i = 0; i < amount; i++)
			toReturn[i] = new CommandPart(command);
		return toReturn;
	}

	public static CommandPart[] executeXCommands(String command, int amount, int delay)
	{
		CommandPart[] toReturn = new CommandPart[amount];
		for(int i = 0; i < amount; i++)
		{
			CommandPart part = new CommandPart(command);
			part.setDelay(delay);
			toReturn[i] = part;
		}
		return toReturn;
	}

	public static ParticlePart[] spawnXParticles(int particle, int amount)
	{
		ParticlePart[] toReturn = new ParticlePart[amount];
		for(int i = 0; i < amount; i++)
			toReturn[i] = new ParticlePart(particle);
		return toReturn;
	}

	public static ItemPart[] generateItemParts(ItemStack... stacks)
	{
		ItemPart[] toReturn = new ItemPart[stacks.length];
		for(int i = 0; i < stacks.length; i++)
			toReturn[i] = new ItemPart(stacks[i]);
		return toReturn;
	}

	public static ItemPart[] generateItemParts(Material... items)
	{
		ItemPart[] toReturn = new ItemPart[items.length];
		for(int i = 0; i < items.length; i++)
			toReturn[i] = new ItemPart(new ItemStack(items[i]));
		return toReturn;
	}

	public static void sendMessageToNearPlayers(Location location, int distance, String message)
	{
		location.getWorld().getPlayers().stream().filter(player -> location.distance(player.getLocation()) <= distance).forEach(player -> player.sendMessage(message));
	}

	public static void sendMessageToAllPlayers(World world, String message)
	{
		world.getPlayers().forEach(player -> player.sendMessage(message));
	}

	public static ItemStack getItemStack(String mod, String itemName, int size)
	{
		Material item = Material.matchMaterial(mod + ":" + itemName);
		return item == null ? new ItemStack(Material.AIR) : new ItemStack(item, size);
	}

	public static Material getBlock(String mod, String blockName)
	{
		return Material.matchMaterial(mod + ":" + blockName);
	}

	public static boolean placeD20(Location location) {
		boolean success = RewardsUtil.placeBlock(Material.LAPIS_BLOCK.createBlockData(), location);
		location.getBlock().setMetadata("ChanceCubes", new D20MetadataValue());
		return success;
	}

	public static boolean placeChanceCube(Location location)
	{
		boolean success = placeBlock(Material.LAPIS_BLOCK.createBlockData(), location);
		location.getBlock().setMetadata("ChanceCubes", new ChanceCubeMetadataValue());
		return success;
	}

	public static boolean placeBlock(BlockData b, Location location)
	{
		return RewardsUtil.placeBlock(b, location, true, false);
	}

	public static boolean placeBlock(BlockData b, Location location, boolean ignoreUnbreakable)
	{
		return RewardsUtil.placeBlock(b, location, true, ignoreUnbreakable);
	}

	public static boolean placeBlock(BlockData b, Location location, boolean update, boolean ignoreUnbreakable)
	{
		if(!RewardsUtil.isBlockUnbreakable(location) || ignoreUnbreakable)
		{
			BlockState state = location.getBlock().getState();
			state.setBlockData(b);
			state.update(update);
			return true;
		}
		return false;
	}

	public static boolean isBlockUnbreakable(Location location)
	{
		return location.getBlock().getState().getType().getHardness() == -1 || CCubesSettings.nonReplaceableBlocks.contains(location.getBlock().getState().getBlockData());
	}

	public static Material getRandomOre()
	{
		return oredicts.get(rand.nextInt(oredicts.size()));
	}

	public static Material getRandomBlock()
	{
		return randomRegistryEntry(Stream.of(Material.values()).filter(Material::isBlock).collect(Collectors.toList()), Material.COBBLESTONE);
	}

	public static Material getRandomItem()
	{
		return randomRegistryEntry(Stream.of(Material.values()).filter(Material::isItem).collect(Collectors.toList()), Material.APPLE);
	}

	public static Enchantment randomEnchantment()
	{
		return randomRegistryEntry(Arrays.asList(Enchantment.values()), Enchantment.PROTECTION_ENVIRONMENTAL);
	}

	public static PotionEffect getRandomPotionEffect()
	{
		PotionEffectType pot = randomRegistryEntry(Arrays.asList(PotionEffectType.values()), PotionEffectType.SPEED);
		int duration = ((int) Math.round(Math.abs(rand.nextGaussian()) * 5) + 3) * 20;
		int amplifier = (int) Math.round(Math.abs(rand.nextGaussian() * 1.5));

		return new PotionEffect(pot, duration, amplifier);
	}

	public static PotionType getRandomPotionType()
	{
		return randomRegistryEntry(Arrays.asList(PotionType.values()), PotionType.AWKWARD);
	}

	public static <T> T randomRegistryEntry(List<T> registry, T defaultReturn)
	{
		T entry = registry.stream().skip(rand.nextInt(registry.size())).findFirst().orElse(null);
		int iteration = 0;
		while(entry == null)
		{
			iteration++;
			if(iteration > 100)
				return defaultReturn;
			entry = registry.stream().skip(rand.nextInt(registry.size())).findFirst().orElse(null);
		}
		return entry;
	}

	public static ItemStack getRandomFirework()
	{
		ItemStack stack = new ItemStack(Material.FIREWORK_ROCKET);
		FireworkMeta data = (FireworkMeta) stack.getItemMeta();
		data.setPower(rand.nextInt(3) + 1);
		for(int i = 0; i <= rand.nextInt(2); i++)
		{
			FireworkEffect.Type[] types = FireworkEffect.Type.values();
			FireworkEffect.Builder explosionData = FireworkEffect.builder().with(types[rand.nextInt(types.length)]).flicker(rand.nextBoolean()).trail(rand.nextBoolean());
			for(int j = 0; j < rand.nextInt(2) + 1; j++)
			{
				explosionData.withColor(getRandomColor());
			}

			for(int j = 0; j < rand.nextInt(2) + 1; j++)
			{
				explosionData.withColor(getRandomColor());
			}

			data.addEffect(explosionData.build());
		}

		stack.setItemMeta(data);
		return stack;
	}

	public static Material getRandomOreDict()
	{
		return RewardsUtil.getOreDicts().get(rand.nextInt(RewardsUtil.getOreDicts().size()));
	}

	public static Material getRandomFluid()
	{
		return Material.WATER;
	}

	public static Color getRandomColor()
	{
		return Color.fromRGB(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
	}

	public static BlockData getRandomWool()
	{
		List<Material> wools = new ArrayList<>(Tag.WOOL.getValues());
		return wools.get(rand.nextInt(wools.size())).createBlockData();
	}

	public static boolean isPlayerOnline(Player player)
	{
		if(player == null)
			return false;

		return player.isOnline();
	}

	public static void executeCommand(World world, Player player, String command)
	{
		Boolean rule = world.getGameRuleValue(GameRule.COMMAND_BLOCK_OUTPUT);
		if (rule == null) {
			rule = false;
		}

		world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
		Location location = player.getLocation();
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
		Vec3D vec3d = new Vec3D(location.getX(), location.getY(), location.getZ());
		CommandListenerWrapper clw = new CommandListenerWrapper(entityPlayer, vec3d, new Vec2F(location.getPitch(), location.getYaw()), ((CraftWorld) world).getHandle(), 2, player.getName(), entityPlayer.getDisplayName(), server, entityPlayer);
		server.getCommandDispatcher().dispatchServerCommand(clw, command);
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
		world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, rule);
	}

	public static void setNearPlayersTitle(World world, String title, String subTitle, int fadeIn, int stay, int fadeOut, Vector pos, int range)
	{
		world.getPlayers().stream().filter(player -> player.getLocation().toVector().distance(pos) <= range).forEach(player -> player.sendTitle(title, subTitle, fadeIn, stay, fadeOut));
	}

	public static void setAllPlayersTitle(World world, String title, String subTitle, int fadeIn, int stay, int fadeOut)
	{
		world.getPlayers().forEach(player -> player.sendTitle(title, subTitle, fadeIn, stay, fadeOut));
	}
}
