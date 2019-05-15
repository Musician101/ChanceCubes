package chanceCubes.util;

import chanceCubes.CCubesCore;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class CubeStorage
{
	private final Map<Location, Type> locations = new HashMap<>();

	public CubeStorage() {
		CCubesCore plugin = CCubesCore.getInstance();
		Logger logger = plugin.getLogger();
		File file = new File(plugin.getDataFolder(), "block_locations.yml");
		try
		{
			if(!file.exists())
			{
				plugin.getDataFolder().mkdirs();
				file.createNewFile();
			}

			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
			yaml.getKeys(false).forEach(worldName -> {
				World world = Bukkit.getWorld(worldName);
				if (world == null) {
					logger.severe(worldName + " is not a valid world name.");
					return;
				}

				ConfigurationSection worldCS = yaml.getConfigurationSection(worldName);
				if (worldCS == null) {
					logger.severe(worldName + " contains invalid data in " + file.getName());
					return;
				}

				worldCS.getKeys(false).forEach(xString -> {
					try {
						int x = Integer.parseInt(xString);
						ConfigurationSection xCS = worldCS.getConfigurationSection(xString);
						if (xCS == null) {
							logger.severe(xString + " contains invalid data in " + file.getName());
							return;
						}

						xCS.getKeys(false).forEach(yString -> {
							try {
								int y = Integer.parseInt(yString);
								ConfigurationSection yCS = worldCS.getConfigurationSection(yString);
								if (yCS == null) {
									logger.severe(yString + " contains invalid data in " + file.getName());
									return;
								}

								yCS.getKeys(false).forEach(zString -> {
									try {
										int z = Integer.parseInt(zString);
										String zCS = worldCS.getString(zString, "NORMAL").toUpperCase();
										locations.put(new Location(world, x, y, z),Type.valueOf(zCS) );
									}
									catch (NumberFormatException e) {
										logger.severe(xString + " is not a whole number.");
									}
								});
							}
							catch (NumberFormatException e) {
								logger.severe(xString + " is not a whole number.");
							}
						});
					}
					catch (NumberFormatException e) {
						logger.severe(xString + " is not a whole number.");
					}
				});
			});
		}
		catch (IOException e) {
			logger.severe("Failed to load block locations!");
			e.printStackTrace();
		}
	}

	public void add(Block block, Type type) {
		locations.put(block.getLocation(), type);
	}

	public void remove(Block block)
	{
		locations.remove(block.getLocation());
	}

	public void save() {
		CCubesCore plugin = CCubesCore.getInstance();
		Logger logger = plugin.getLogger();
		File file = new File(plugin.getDataFolder(), "block_locations.yml");
		try
		{
			if(!file.exists())
			{
				plugin.getDataFolder().mkdirs();
				file.createNewFile();
			}

			YamlConfiguration yaml = new YamlConfiguration();
			locations.forEach((location, type) -> yaml.set(location.getWorld().getName() + "." + location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ(), type.toString()));
			yaml.save(file);
		}
		catch (IOException e) {
			logger.severe("Failed to load block locations!");
			e.printStackTrace();
		}
	}

	public enum Type {
		D20,
		GIANT,
		NORMAL
	}
}
