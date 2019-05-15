package chanceCubes.rewards.variableTypes;

import chanceCubes.rewards.variableParts.StringPart;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.v1_13_R2.MojangsonParser;
import net.minecraft.server.v1_13_R2.NBTTagCompound;

public class NBTVar extends CustomVar
{
	public NBTVar()
	{

	}

	public NBTVar(NBTTagCompound val)
	{
		if(val == null)
			val = new NBTTagCompound();
		super.addPart(new StringPart(val.toString()));
	}

	public NBTVar(String val)
	{
		this.addPart(new StringPart(val));
	}

	public NBTTagCompound getNBTValue()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		String val = super.getValue();
		if(val.isEmpty())
			return nbt;
		try
		{
			nbt = MojangsonParser.parse(val);
		} catch(CommandSyntaxException e)
		{
			e.printStackTrace();
		}
		return nbt;
	}
}
