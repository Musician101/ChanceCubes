package chanceCubes.rewards.profiles.triggers;

public interface ITrigger<T>
{
	void onTrigger(T... args);
}
