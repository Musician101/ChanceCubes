package chanceCubes.rewards.profiles.triggers;

import chanceCubes.rewards.profiles.IProfile;
import chanceCubes.rewards.profiles.ProfileManager;

public class DimensionChangeTrigger implements ITrigger<String>
{
	private IProfile prof;
	private String world;

	public DimensionChangeTrigger(IProfile prof, String world)
	{
		this.prof = prof;
		this.world = world;
	}

	@Override
	public void onTrigger(String... args)
	{
		if(args.length == 2)
		{
			if(args[0].equals(world))
				ProfileManager.enableProfile(prof);
			else if(args[1].equals(world))
				ProfileManager.disableProfile(prof);
		}
	}
}
