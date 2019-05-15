package chanceCubes.rewards.rewardtype;

import chanceCubes.rewards.rewardparts.EffectPart;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EffectRewardType extends BaseRewardType<EffectPart>
{

	public EffectRewardType(EffectPart... effects)
	{
		super(effects);
	}

	@Override
	protected void trigger(EffectPart part, Location location, Player player)
	{
		int radius = part.getRadius().getIntValue();
		location.getWorld().getPlayers().stream().filter(p -> p.getLocation().distance(location) < radius).forEach(p -> p.addPotionEffect(part.getEffect()));
	}

}
