package chanceCubes.rewards.profiles.triggers;

import chanceCubes.rewards.profiles.IProfile;
import chanceCubes.rewards.profiles.ProfileManager;

public class GameStageTrigger implements ITrigger<String>
{
	private IProfile prof;
	private String stageName;

	public GameStageTrigger(IProfile prof, String stageName)
	{
		this.prof = prof;
		this.stageName = stageName;
	}

	@Override
	public void onTrigger(String[] args)
	{
		if(args.length == 2)
		{
			if(args[0].equals(stageName))
			{
				if(args[1].equals("A"))
					ProfileManager.enableProfile(prof);
				else
					ProfileManager.disableProfile(prof);
			}
		}
	}

}