package chanceCubes.rewards.type;

import chanceCubes.rewards.rewardparts.PotionPart;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;

public class PotionRewardType extends BaseRewardType<PotionPart> {

    public PotionRewardType(PotionPart... effects) {
        super(effects);
    }

    @Override
    public void trigger(final PotionPart part, final World world, final int x, final int y, final int z, final EntityPlayer player) {
        if (part.getDelay() != 0) {
            Task task = new Task("Potion Reward Delay", part.getDelay()) {
                @Override
                public void callback() {
                    triggerPotion(part, world, x, y, z, player);
                }
            };
            Scheduler.scheduleTask(task);
        }
        else {
            triggerPotion(part, world, x, y, z, player);
        }
    }

    public void triggerPotion(PotionPart part, World world, int x, int y, int z, EntityPlayer player) {

        ItemStack potion = new ItemStack(Items.SPLASH_POTION);

        List<PotionEffect> effects = new ArrayList<PotionEffect>();
        effects.add(part.getEffect());
        PotionUtils.appendEffects(potion, effects);

        EntityPotion entity = new EntityPotion(world, player, potion);
        entity.posX = player.posX;
        entity.posY = player.posY + 2;
        entity.posZ = player.posZ;
        entity.motionX = 0;
        entity.motionY = 0.1;
        entity.motionZ = 0;

        world.spawnEntityInWorld(entity);
    }
}
