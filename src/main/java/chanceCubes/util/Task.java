package chanceCubes.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class Task
{
	public String name;
	public int delayLeft;
	private boolean forever = false;

	public int updateTick;

	public Task(String name, int delay)
	{
		this(name, delay, -1);
	}

	public Task(String name, int delay, int updateTick)
	{
		this.name = name;
		this.delayLeft = delay;
		if(delay == -1)
			forever = true;
		this.updateTick = updateTick;
	}

	public boolean shouldUpdate()
	{
		return this.updateTick != -1 && (delayLeft % updateTick) == 0;
	}

	public abstract void callback();

	public boolean tickTask()
	{
		this.delayLeft--;
		return this.delayLeft <= 0 && !forever;
	}

	public void update()
	{

	}

	public void showTimeLeft(Player player)
	{
		player.sendTitle(ChatColor.RED + "" + ChatColor.BOLD + (delayLeft / 20), "", 0, 20, 0);
	}
}
