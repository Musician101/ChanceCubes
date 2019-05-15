package chanceCubes.rewards.profiles.triggerHooks;

import chanceCubes.rewards.profiles.IProfile;
import chanceCubes.rewards.profiles.ProfileManager;
import chanceCubes.rewards.profiles.triggers.DimensionChangeTrigger;
import chanceCubes.rewards.profiles.triggers.ITrigger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class VanillaTriggerHooks implements Listener
{
	//TODO: Crafting trigger
	@EventHandler
	public void onDimensionChange(PlayerChangedWorldEvent event)
	{
		for(IProfile prof : ProfileManager.getAllProfiles())
		{
			for(ITrigger<?> module : prof.getTriggers())
			{
				if(module instanceof DimensionChangeTrigger)
				{
					DimensionChangeTrigger trigger = (DimensionChangeTrigger) module;
					trigger.onTrigger(event.getPlayer().getWorld().getName(), event.getFrom().getName());
				}
			}
		}
	}
}
