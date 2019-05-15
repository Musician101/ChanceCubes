package chanceCubes.rewards.profiles;

import chanceCubes.rewards.profiles.triggers.DifficultyTrigger;
import chanceCubes.rewards.profiles.triggers.ITrigger;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;

public class TriggerHooks
{
	private Difficulty difficulty;

	public TriggerHooks() {
		this.difficulty = Bukkit.getWorlds().get(0).getDifficulty();
	}

	public void run()
	{
		for(IProfile prof : ProfileManager.getAllProfiles())
		{
			for(ITrigger<?> module : prof.getTriggers())
			{
				if(module instanceof DifficultyTrigger)
				{
					DifficultyTrigger trigger = (DifficultyTrigger) module;
					trigger.onTrigger(difficulty, Bukkit.getWorlds().get(0).getDifficulty());
					difficulty = Bukkit.getWorlds().get(0).getDifficulty();
				}
			}
		}
	}
}
