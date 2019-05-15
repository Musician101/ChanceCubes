package chanceCubes.rewards.rewardparts;

import chanceCubes.rewards.variableTypes.IntVar;

public class ExperiencePart extends BasePart
{
	private IntVar amount = new IntVar(0);
	private IntVar orbs = new IntVar(1);

	public ExperiencePart(int amount)
	{
		this(new IntVar(amount));
	}

	public ExperiencePart(IntVar amount)
	{
		this.amount = amount;
	}

	public ExperiencePart(int amount, int delay)
	{
		this(new IntVar(amount), new IntVar(delay));
	}

	public ExperiencePart(IntVar amount, IntVar delay)
	{
		this.amount = amount;
		this.setDelay(delay);
	}

	public int getAmount()
	{
		return amount.getIntValue();
	}

	public int getNumberofOrbs()
	{
		return orbs.getIntValue();
	}

	public ExperiencePart setNumberofOrbs(int orbs)
	{
		return this.setNumberofOrbs(new IntVar(orbs));
	}

	public ExperiencePart setNumberofOrbs(IntVar orbs)
	{
		this.orbs = orbs;
		return this;
	}
}
