package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.CCubesCommandSender;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DidYouKnowReward implements IChanceCubeReward {

    private List<String> dyk = new ArrayList<String>();
    private Random random = new Random();

    public DidYouKnowReward() {
        dyk.add("The nuke reward that says\"May death rain upon them\" is a reference to the Essentials Bukkit plugin?");
        dyk.add("The real reason his name is pickles is because a user from Wyld's Twtich chat suggested the reward.");
        dyk.add("Funwayguy created the original D20 model and animation.");
        dyk.add("Glenn is NOT a refference to the TV show \"The Walking Dead\", but is instead a reference to the streamer Sevadus.");
        dyk.add("");
    }

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Did_You_Know";
    }

    @Override
    public void trigger(Location location, Player player) {
        String meme = "Did you know?\n" + dyk.get(random.nextInt(dyk.size()));
        MinecraftServer server = world.getMinecraftServer();
        Boolean rule = server.worldServers[0].getGameRules().getBoolean("commandBlockOutput");
        server.worldServers[0].getGameRules().setOrCreateGameRule("commandBlockOutput", "false");
        String command = "/summon Item ~ ~1 ~ {Item:{id:written_book,Count:1,tag:{title:\"Did You Know?\",author:\"Chance Cubes\",generation:0,pages:[\"{text:\\\"" + meme + "\\\",color:black}\"]}}}";
        CCubesCommandSender sender = new CCubesCommandSender(player, pos);
        server.getCommandManager().executeCommand(sender, command);
        server.worldServers[0].getGameRules().setOrCreateGameRule("commandBlockOutput", rule.toString());
    }
}
