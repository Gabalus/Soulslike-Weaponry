package net.soulsweaponry.datagen;

import net.fabricmc.loader.api.FabricLoader;

public class DatagenUtil {

    public static boolean isDatagenRunning() {
        return FabricLoader.getInstance().isDevelopmentEnvironment() &&
                System.getProperty("fabric-api.datagen") != null;
    }
}