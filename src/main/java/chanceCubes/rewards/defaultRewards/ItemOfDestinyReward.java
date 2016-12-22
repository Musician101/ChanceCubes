package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemOfDestinyReward implements IChanceCubeReward {

    private Random rand = new Random();

    private void changeEnchantAmount(final EntityItem item, final Player player) {
        Task task = new Task("Item_Of_Destiny_Reward", 50) {
            @Override
            public void callback() {
                int i = rand.nextInt(9);
                int amount = i < 5 ? 1 : i < 8 ? 2 : 3;
                player.addChatMessage(new TextComponentString(amount + " random enchants will be added!"));
                player.addChatMessage(new TextComponentString("Selecting random enchant to give to the item"));
                changeEnchants(item, amount, 0, player);
            }
        };
        Scheduler.scheduleTask(task);
    }

    private void changeEnchants(final EntityItem item, final int enchants, final int iteration, final Player player) {
        Task task = new Task("Item_Of_Destiny_Reward", 50) {
            @Override
            public void callback() {
                if (iteration < enchants) {
                    Enchantment ench = randomEnchantment();
                    int level = ench.getMinLevel() + rand.nextInt(ench.getMaxLevel());
                    item.getEntityItem().addEnchantment(ench, level);
                    player.addChatMessage(new TextComponentString(ench.getTranslatedName(level) + " Has been added to the item!"));
                    changeEnchants(item, enchants, iteration + 1, player);
                }
                else {
                    player.addChatMessage(new TextComponentString("Your item of destiny is complete! Enjoy!"));
                    item.setPickupDelay(0);
                }
            }
        };
        Scheduler.scheduleTask(task);
    }

    private void changeItem(final EntityItem item, final int iteration, final Player player) {
        Task task = new Task("Item_Of_Destiny_Reward", 5) {
            @Override
            public void callback() {
                if (iteration + 1 > 17) {
                    player.addChatMessage(new TextComponentString("Random item selected"));
                    player.addChatMessage(new TextComponentString("Selecting number of enchants to give item"));
                    changeEnchantAmount(item, player);
                }
                else {
                    item.setEntityItemStack(new ItemStack(RewardsUtil.getRandomItem(), 1));
                    changeItem(item, iteration + 1, player);
                }
            }
        };
        Scheduler.scheduleTask(task);
    }

    @Override
    public int getChanceValue() {
        return 40;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Item_Of_Destiny";
    }

    public Enchantment randomEnchantment() {

        Enchantment ench = Enchantment.getEnchantmentByID(rand.nextInt(Enchantment.REGISTRY.getKeys().size()));
        while (ench == null)
            ench = Enchantment.getEnchantmentByID(rand.nextInt(Enchantment.REGISTRY.getKeys().size()));
        return ench;
    }

    @Override
    public void trigger(Location location, final Player player) {
        final EntityItem item = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RewardsUtil.getRandomItem(), 1));
        item.setPickupDelay(100000);
        world.spawnEntityInWorld(item);
        player.addChatMessage(new TextComponentString("Selecting random item"));
        Task task = new Task("Item_Of_Destiny_Reward", 5) {
            @Override
            public void callback() {
                changeItem(item, 0, player);
            }
        };
        Scheduler.scheduleTask(task);
    }

}
