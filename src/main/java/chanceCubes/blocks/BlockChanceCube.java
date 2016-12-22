package chanceCubes.blocks;

import chanceCubes.items.CCubesItems;
import chanceCubes.items.ItemChanceCube;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.tileentities.TileChanceCube;
import chanceCubes.util.GiantCubeUtil;
import java.util.Random;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class BlockChanceCube extends BaseChanceBlock implements ITileEntityProvider {

    public static final PropertyEnum<BlockChanceCube.EnumTexture> TEXTURE = PropertyEnum.<BlockChanceCube.EnumTexture>create("texture", BlockChanceCube.EnumTexture.class);

    public static EnumTexture textureToSet = EnumTexture.DEFAULT;

    public BlockChanceCube() {
        super("chance_Cube");
        this.setLightLevel(2);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TEXTURE, textureToSet));
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosion) {
        return false;
    }

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        return false;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{TEXTURE});
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileChanceCube();
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return this.getDefaultState().withProperty(TEXTURE, textureToSet);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TEXTURE, textureToSet);
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {

    }

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState iblockstate = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        return iblockstate.withProperty(TEXTURE, textureToSet);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        GiantCubeUtil.checkMultiBlockForm(pos, worldIn, true);
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public int quantityDropped(Random rand) {
        return 0;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (!world.isRemote && player != null && !(player instanceof FakePlayer)) {
            TileChanceCube te = (TileChanceCube) world.getTileEntity(pos);
            if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem().equals(CCubesItems.silkPendant)) {
                ItemStack stack = new ItemStack(Item.getItemFromBlock(CCubesBlocks.CHANCE_CUBE), 1);
                ((ItemChanceCube) stack.getItem()).setChance(stack, te.isScanned() ? te.getChance() : -101);
                EntityItem blockstack = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                world.setBlockToAir(pos);
                world.removeTileEntity(pos);
                world.spawnEntityInWorld(blockstack);
                return true;
            }

            if (te != null) {
                world.setBlockToAir(pos);
                ChanceCubeRegistry.INSTANCE.triggerRandomReward(world, pos, player, te.getChance());
            }
        }
        return true;
    }

    public static enum EnumTexture implements IStringSerializable {
        // @formatter:off
        DEFAULT("default"), VALENTINES("valentines"), STPATRICKS("stpatricks"),
        HALLOWEEN("halloween"), HOLIDAYS("holidays"), EASTER("easter");
        // @formatter:on

        private final String name;

        private EnumTexture(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }
    }
}