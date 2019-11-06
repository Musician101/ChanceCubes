package chanceCubes.rewards.rewardparts;

import chanceCubes.rewards.variableTypes.IntVar;
import chanceCubes.rewards.variableTypes.StringVar;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionPart extends BasePart
{
	private StringVar id;
	private IntVar duration;
	private IntVar amplifier;

	public PotionPart(StringVar id, IntVar duration, IntVar amplifier)
	{
		this.id = id;
		this.duration = duration;
		this.amplifier = amplifier;
	}

	public PotionEffect getEffect()
	{
		PotionEffectType pot;
		String val = id.getValue();
		if(IntVar.isInteger(val))
			pot = PotionEffectType.getById(Integer.parseInt(val));
		else
			pot = PotionEffectType.getByName(val);
		return new PotionEffect(pot, duration.getIntValue() * 20, amplifier.getIntValue());
	}
}
