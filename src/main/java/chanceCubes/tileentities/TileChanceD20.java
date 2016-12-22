package chanceCubes.tileentities;

import chanceCubes.config.CCubesSettings;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.sounds.CCubesSounds;
import java.util.Random;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.model.TRSRTransformation;

public class TileChanceD20 extends TileEntity implements ITickable {

    private static final Random random = new Random();
    public float rotation = 0, rotationDelta = 0, rotationInc = 0, wave = 0;
    public TRSRTransformation transform = TRSRTransformation.identity();
    private boolean breaking = false;
    private int chance;
    private boolean isScanned = false;
    private EntityPlayer player;
    private int stage = 0;

    public TileChanceD20() {
        if (!CCubesSettings.d20UseNormalChances) {
            this.chance = random.nextBoolean() ? -100 : 100;
        }
        else {
            this.chance = Math.round((float) (random.nextGaussian() * 40));
            while (this.chance > 100 || this.chance < -100)
                this.chance = Math.round((float) (random.nextGaussian() * 40));
        }
    }

    public TileChanceD20(int initialChance) {
        this.chance = initialChance;
    }

    public int getChance() {
        return this.chance;
    }

    public void setChance(int newChance) {
        this.chance = newChance;
    }

    public int getStage() {
        return this.stage;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound syncData = new NBTTagCompound();
        this.writeSyncableDataToNBT(syncData);
        return new SPacketUpdateTileEntity(this.pos, 1, syncData);
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean isScanned) {
        this.isScanned = isScanned;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readSyncableDataFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readSyncableDataFromNBT(nbt);
    }

    private void readSyncableDataFromNBT(NBTTagCompound nbt) {
        this.chance = nbt.getInteger("chance");
    }

    public void startBreaking(EntityPlayer player) {
        if (!breaking) {
            if (!player.worldObj.isRemote) {
                player.worldObj.playSound(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), CCubesSounds.D20_BREAK.getSoundEvent(), CCubesSounds.D20_BREAK.getSoundCategory(), 1.0F, 1.0F);
                this.player = player;
            }
            breaking = true;
            stage = 0;
        }
    }

    @Override
    public void update() {
        if (breaking)
            stage++;
        if (stage > 200) {
            breaking = false;
            if (!this.worldObj.isRemote) {
                this.worldObj.setBlockToAir(this.pos);
                this.worldObj.removeTileEntity(this.pos);
                ChanceCubeRegistry.INSTANCE.triggerRandomReward(this.worldObj, this.pos, player, this.getChance());
            }
        }
        else if (worldObj.isRemote) {
            AxisAngle4d yaw = new AxisAngle4d(0, 1, 0, Math.toRadians((Minecraft.getSystemTime() % 10000F) / 10000F * 360F) + (0.4 + Math.pow(1.02, getStage() + 1)));
            AxisAngle4d pitch = new AxisAngle4d(1, 0, 0, 0F);

            // Translation
            Vector3f offset = new Vector3f(0.5F, 0.5F + wave * 0.15f, 0.5F);

            Matrix4f matrix = new Matrix4f();
            matrix.setIdentity();
            matrix.setTranslation(offset);

            if (breaking) {
                Quat4f rot = new Quat4f(0, 0, 0, 1);
                Quat4f yawQuat = new Quat4f();
                Quat4f pitchQuat = new Quat4f();
                yawQuat.set(yaw);
                rot.mul(yawQuat);
                pitchQuat.set(pitch);
                rot.mul(pitchQuat);
                matrix.setRotation(rot);
            }

            transform = new TRSRTransformation(matrix);
            this.worldObj.markBlockRangeForRenderUpdate(this.pos, this.pos);
        }
    }

    private NBTTagCompound writeSyncableDataToNBT(NBTTagCompound syncData) {
        syncData.setInteger("chance", this.getChance());
        return syncData;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        return this.writeSyncableDataToNBT(super.writeToNBT(nbt));
    }
}
