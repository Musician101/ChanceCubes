package chanceCubes.blocks;

import java.util.Random;
import javax.annotation.Nonnull;

public class ChanceCubeData {

    private static Random RANDOM = new Random();

    private double chance;
    private boolean hasCenter;
    private boolean isCenter;
    private boolean isScanned;
    @Nonnull
    private ChanceCubeType type;

    public ChanceCubeData(@Nonnull ChanceCubeType type) {
        this(type, Math.round((float) RANDOM.nextGaussian() * 40));
    }

    public ChanceCubeData(@Nonnull ChanceCubeType type, double chance) {
        this(type, chance, false, false, false);
    }

    public ChanceCubeData(@Nonnull ChanceCubeType type, double chance, boolean isCenter, boolean hasCenter, boolean isScanned) {
        this.type = type;
        while (chance > 100 || chance < - 100)
            chance = Math.round((float) RANDOM.nextGaussian() * 40);

        this.chance = chance;
        this.isCenter = isCenter;
        this.hasCenter = hasCenter;
        this.isScanned = isScanned;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    @Nonnull
    public ChanceCubeType getType() {
        return type;
    }

    public void setType(@Nonnull ChanceCubeType type) {
        this.type = type;
    }

    public boolean isCenter() {
        return isCenter;
    }

    public void setIsCenter(boolean center) {
        isCenter = center;
    }

    public boolean hasCenter() {
        return hasCenter;
    }

    public void setHasCenter(boolean hasCenter) {
        this.hasCenter = hasCenter;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setIsScanned(boolean scanned) {
        isScanned = scanned;
    }

    public enum ChanceCubeType {
        CHANCE_CUBE,
        COMPACT_GIANT_CHANCE_CUBE,
        GIANT_CHANCE_CUBE,
        D20;
    }
}
