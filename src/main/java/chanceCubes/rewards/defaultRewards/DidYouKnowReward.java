package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class DidYouKnowReward extends BaseCustomReward
{
	private List<String> dyk = new ArrayList<>();

	public DidYouKnowReward()
	{
		super(CCubesCore.MODID + ":Did_You_Know", 0);
		dyk.add("The nuke reward that says 'May death rain upon them' is a reference to the Essentials Bukkit plugin?");
		dyk.add("The real reason his name is pickles is because a user from Wyld's Twtich chat suggested the reward.");
		dyk.add("Funwayguy created the original D20 model and animation.");
		dyk.add("Glenn is NOT a refference to the TV show 'The Walking Dead', but is instead a reference to the streamer Sevadus.");
		dyk.add("Today is Darkosto's Birthday!");
	}

	@Override
	public void trigger(Location location, Player player)
	{
		String fact = ChatColor.BLACK + "Did you know?\n" + dyk.get(RewardsUtil.rand.nextInt(dyk.size()));
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();
		meta.setAuthor("Chance Cubes");
		meta.setPages(fact);
		meta.setTitle("Did You Know?");
		book.setItemMeta(meta);
		location.getWorld().dropItem(location, book);
	}
}
