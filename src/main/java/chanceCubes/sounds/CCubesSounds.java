package chanceCubes.sounds;

public enum CCubesSounds {
    /*D20_BREAK(SoundCategory.BLOCKS, "d20_Break"),
    GIANT_CUBE_SPAWN(SoundCategory.BLOCKS, "giant_Cube_Spawn");

    private ResourceLocation resourceLocation;
    private SoundCategory soundCategory;
    private SoundEvent soundEvent = null;

    private CCubesSounds(SoundCategory soundCategory, String name) {
        this.soundCategory = soundCategory;
        this.resourceLocation = new ResourceLocation(CCubesCore.MODID, name);
    }

    public static void loadSounds() {
        for (CCubesSounds sound : values())
            sound.soundEvent = new SoundEvent(sound.resourceLocation);
    }

    public static SoundEvent registerSound(String name) {
        if (name.contains(":")) {
            int loc = name.indexOf(":");
            return new SoundEvent(new ResourceLocation(name.substring(0, loc), name.substring(loc + 1)));
        }
        else {
            return new SoundEvent(new ResourceLocation(name));
        }


    }

    public SoundCategory getSoundCategory() {
        return soundCategory;
    }

    public SoundEvent getSoundEvent() {
        return soundEvent;
    }*/
}
