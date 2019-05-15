package chanceCubes.rewards.rewardtype;

import chanceCubes.rewards.rewardparts.ParticlePart;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class ParticleEffectRewardType extends BaseRewardType<ParticlePart>
{
	public ParticleEffectRewardType(ParticlePart... effects)
	{
		super(effects);
	}

	@Override
	public void trigger(ParticlePart part, Location location, Player player)
	{
		location.getWorld().spawnParticle(Particle.valueOf(part.getParticleName().toUpperCase()), location.clone().add(Math.random(), Math.random(), Math.random()), 1);
	}
}
