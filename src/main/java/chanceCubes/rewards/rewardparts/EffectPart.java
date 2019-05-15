package chanceCubes.rewards.rewardparts;

import chanceCubes.rewards.variableTypes.IntVar;
import chanceCubes.rewards.variableTypes.StringVar;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.MinecraftKey;
import org.bukkit.craftbukkit.v1_13_R2.potion.CraftPotionEffectType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectPart extends BasePart
{
	private IntVar radius = new IntVar(1);

	private StringVar id;
	private IntVar duration;
	private IntVar amplifier;

	public EffectPart(PotionEffectType pot, int duration, int amplifier)
	{
		this(pot, new IntVar(duration), new IntVar(amplifier));
	}

	public EffectPart(String id, int duration, int amplifier)
	{
		this.id = new StringVar(id);
		this.duration = new IntVar(duration);
		this.amplifier = new IntVar(amplifier);
	}

	public EffectPart(PotionEffectType pot, IntVar duration, IntVar amplifier)
	{
		this.id = new StringVar(IRegistry.MOB_EFFECT.getKey(((CraftPotionEffectType) pot).getHandle()).toString());
		this.duration = duration;
		this.amplifier = amplifier;
	}

	public IntVar getRadius()
	{
		return radius;
	}

	public EffectPart setRadius(int radius)
	{
		return this.setRadius(new IntVar(radius));
	}

	public EffectPart setRadius(IntVar radius)
	{
		this.radius = radius;
		return this;
	}

	public PotionEffect getEffect()
	{
		return new PotionEffect(new CraftPotionEffectType(IRegistry.MOB_EFFECT.get(new MinecraftKey(id.getValue()))), duration.getIntValue() * 20, amplifier.getIntValue());
	}
}
