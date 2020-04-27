package chanceCubes.persistance;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.ChanceCubeData;
import chanceCubes.blocks.ChanceCubeData.ChanceCubeType;
import javax.annotation.Nonnull;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ChanceCubeDataType implements PersistentDataType<PersistentDataContainer, ChanceCubeData> {

    private final NamespacedKey chance = new NamespacedKey(CCubesCore.getInstance(), "chance");
    private final NamespacedKey type = new NamespacedKey(CCubesCore.getInstance(), "type");
    private final NamespacedKey isCenter = new NamespacedKey(CCubesCore.getInstance(), "is_center");
    private final NamespacedKey hasCenter = new NamespacedKey(CCubesCore.getInstance(), "has_center");
    private final NamespacedKey isScanned = new NamespacedKey(CCubesCore.getInstance(), "is_scanned");

    @Nonnull
    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Nonnull
    @Override
    public Class<ChanceCubeData> getComplexType() {
        return ChanceCubeData.class;
    }

    @Nonnull
    @Override
    public PersistentDataContainer toPrimitive(@Nonnull ChanceCubeData complex, @Nonnull PersistentDataAdapterContext context) {
        PersistentDataContainer container = context.newPersistentDataContainer();
        container.set(chance, PersistentDataType.DOUBLE, complex.getChance());
        container.set(type, new ChanceCubeTypeDataType(), complex.getType());
        container.set(isCenter, PersistentDataType.BYTE, convert(complex.isCenter()));
        container.set(hasCenter, PersistentDataType.BYTE, convert(complex.hasCenter()));
        container.set(isScanned, PersistentDataType.BYTE, convert(complex.isScanned()));
        return container;
    }

    private byte convert(boolean b) {
        return (byte) (b ? 1 : 0);
    }

    private boolean convert(Byte b) {
        return b == 1;
    }

    @Nonnull
    @Override
    public ChanceCubeData fromPrimitive(@Nonnull PersistentDataContainer primitive, @Nonnull PersistentDataAdapterContext context) {
        Double chance = primitive.get(this.chance, PersistentDataType.DOUBLE);
        ChanceCubeType type = primitive.get(this.type, new ChanceCubeTypeDataType());
        boolean isCenter = convert(primitive.get(this.isCenter, PersistentDataType.BYTE));
        boolean hasCenter = convert(primitive.get(this.hasCenter, PersistentDataType.BYTE));
        boolean isScanned = convert(primitive.get(this.isScanned, PersistentDataType.BYTE));
        return new ChanceCubeData(type, chance, isCenter, hasCenter, isScanned);
    }
}
