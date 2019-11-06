package chanceCubes.blocks;

import chanceCubes.rewards.rewardparts.OffsetBlock;
import net.minecraft.server.v1_14_R1.Block;
import net.minecraft.server.v1_14_R1.BlockFalling;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.Blocks;
import net.minecraft.server.v1_14_R1.EntityFallingBlock;
import net.minecraft.server.v1_14_R1.EnumMoveType;
import net.minecraft.server.v1_14_R1.GameRules;
import net.minecraft.server.v1_14_R1.IBlockData;
import net.minecraft.server.v1_14_R1.ItemStack;
import net.minecraft.server.v1_14_R1.Material;
import net.minecraft.server.v1_14_R1.NBTBase;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.TileEntity;
import net.minecraft.server.v1_14_R1.World;
import org.bukkit.Location;

//TODO need to find a way to make this not rely on NMS
@Deprecated
public class BlockFallingCustom extends EntityFallingBlock
{
	private int normY;
	private OffsetBlock osb;

	public BlockFallingCustom(World world, double x, double y, double z, IBlockData state, int normY, OffsetBlock osb)
	{
		super(world, x, y, z, state);
		this.normY = normY;
		this.osb = osb;
	}

	public void onUpdate()
	{
		Block block = this.getBlock().getBlock();

		if(getBlock().getMaterial() == Material.AIR)
		{
			this.die();
		}
		else
		{
			this.lastX = this.locX;
			this.lastY = this.locY;
			this.lastZ = this.locZ;

			if(this.ticksLived++ == 0)
			{
				BlockPosition blockPos = new BlockPosition(this);

				if(this.world.i(blockPos).getBlock() == block)
				{
					this.world.setTypeUpdate(blockPos, Blocks.AIR.getBlockData());
				}
				else if(this.world.isClientSide)
				{
					this.die();
					return;
				}
			}

			setMot(getMot().a(0, -0.04, 0));
			this.move(EnumMoveType.SELF, getMot());
			setMot(getMot().a(0.98, 0.98, 0.98));
			if(!this.world.isClientSide)
			{
				BlockPosition blockpos1 = new BlockPosition(this);

				if(this.onGround)
				{
					IBlockData iblockstate = this.world.i(blockpos1);
					setMot(getMot().a(0.7, -0.5, 0.7));
					if(iblockstate.getBlock() != Blocks.PISTON_HEAD)
					{
						this.die();
						// if(!super.canSetAsBlock)
						// {
						if(block instanceof BlockFalling)
							osb.placeInWorld(new Location(world.getWorld(), blockpos1.getX(), blockpos1.getY(), blockpos1.getZ()), false);

						if(this.tileEntityData != null && block.isTileEntity())
						{
							TileEntity tileentity = this.world.getTileEntity(blockpos1);
							if(tileentity != null)
							{
								NBTTagCompound nbttagcompound = new NBTTagCompound();
								tileentity.save(nbttagcompound);

								for(String s : this.tileEntityData.getKeys())
								{
									NBTBase nbtbase = this.tileEntityData.get(s);

									if(!s.equals("x") && !s.equals("y") && !s.equals("z"))
										nbttagcompound.set(s, nbtbase.clone());
								}

								tileentity.load(nbttagcompound);
								tileentity.update();
							}
						}
						// }
						// else if(this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops"))
						// {
						// this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
						// }
					}
				}
				else if(this.ticksLived > 100 && !this.world.isClientSide && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.ticksLived > 600)
				{
					if(this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS))
						this.a(new ItemStack(block), 0.0F);

					this.die();
				}
				else if(normY == blockpos1.getY() || getMot().y == 0)
				{
					this.die();
					osb.placeInWorld(new Location(world.getWorld(), blockpos1.getX(), blockpos1.getY(), blockpos1.getZ()), false);
				}
			}
		}
	}
}
