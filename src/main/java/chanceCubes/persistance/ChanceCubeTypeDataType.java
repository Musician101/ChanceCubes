package chanceCubes.persistance;

import chanceCubes.blocks.ChanceCubeData.ChanceCubeType;
import javax.annotation.Nonnull;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class ChanceCubeTypeDataType implements PersistentDataType<String, ChanceCubeType> {

    @Nonnull
    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Nonnull
    @Override
    public Class<ChanceCubeType> getComplexType() {
        return ChanceCubeType.class;
    }

    @Nonnull
    @Override
    public String toPrimitive(@Nonnull ChanceCubeType complex, @Nonnull PersistentDataAdapterContext context) {
        return complex.toString();
    }

    @Nonnull
    @Override
    public ChanceCubeType fromPrimitive(@Nonnull String primitive, @Nonnull PersistentDataAdapterContext context) {
        return ChanceCubeType.valueOf(primitive);
    }
}
