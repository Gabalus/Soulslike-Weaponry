package net.soulsweaponry;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.soulsweaponry.api.trickweapon.TrickWeaponUtil;
import net.soulsweaponry.config.ConfigConstructor;
import net.soulsweaponry.config.MidnightConfig;
import net.soulsweaponry.items.TestItem;
import net.soulsweaponry.items.material.ModToolMaterials;
import net.soulsweaponry.networking.PacketRegistry;
import net.soulsweaponry.registry.*;
import net.soulsweaponry.world.gen.WorldGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

import java.util.ArrayList;

public class SoulsWeaponry implements ModInitializer {

    public static final String ModId = "soulsweapons";
    public static final Logger LOGGER = LoggerFactory.getLogger("Soulslike Weaponry");
    public static final ArrayList<Item> ITEM_GROUP_LIST = new ArrayList<>();

    @Override
    public void onInitialize() {
        long start = System.currentTimeMillis();
        MidnightConfig.init(ModId, ConfigConstructor.class);
        LOGGER.info("Config initialized!");
        GeckoLib.initialize();
        LOGGER.info("Successfully initialized Geckolib!");
        BlockRegistry.init();
        ItemRegistry.init();
        FluidRegistry.init();
        FluidRegistry.registerCauldronBehavior();
        EffectRegistry.init();
        EnchantRegistry.init();
        EntityRegistry.init();
        EventRegistry.init();
        ParticleRegistry.init();
        SpawnInit.init();
        WeaponRegistry.init();
        ArmorRegistry.init();
        GunRegistry.init();
        WorldGen.generateCustomWorldGen();
        LOGGER.info("Successfully registered SoulsWeapons content!");
        PacketRegistry.registerC2SPackets();

        FabricLoader.getInstance().getModContainer(ModId).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(ModId, "2d_weapons"), modContainer, Text.literal("2D Weapon Models"), ResourcePackActivationType.NORMAL);
            LOGGER.info("Successfully registered built-in 2D model resourcepack!");
        });
        FabricLoader.getInstance().getModContainer(ModId).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(ModId, "legacy_2d"), modContainer, Text.literal("Legacy 2D Models"), ResourcePackActivationType.NORMAL);
            LOGGER.info("Successfully registered built-in Legacy 2D Models resourcepack!");
        });
        FabricLoader.getInstance().getModContainer(ModId).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(ModId, "legacy_3d"), modContainer, Text.literal("Legacy 3D Models"), ResourcePackActivationType.NORMAL);
            LOGGER.info("Successfully registered built-in Legacy 3D Models resourcepack!");
        });
        FabricLoader.getInstance().getModContainer(ModId).ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(ModId, "fresh_animations_compat"), modContainer, Text.literal("Fresh Animations Compat."), ResourcePackActivationType.NORMAL);
            LOGGER.info("Successfully registered built-in Fresh Animations Compat. resourcepack!");
        });

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            ItemRegistry.registerItem(new TestItem(ModToolMaterials.MOONSTONE_OR_VERGLAS, 10, -2.4f, new FabricItemSettings().fireproof().rarity(Rarity.RARE)), "test_item");
        }

        Registry.register(Registries.ITEM_GROUP, new Identifier(ModId, "general"),
                FabricItemGroup.builder().displayName(Text.translatable("itemGroup.soulsweapons.general"))
                        .icon(() -> new ItemStack(ItemRegistry.MOONSTONE)).entries(((displayContext, entries) -> {
                            for (Item item : ITEM_GROUP_LIST) {
                                entries.add(item);
                            }
                        })).build());

        ServerLifecycleEvents.SERVER_STARTING.register(TrickWeaponUtil::loadMappings);

        long end = System.currentTimeMillis();
        LOGGER.info("Initializing done, time taken: " + (end - start) + "ms");
    }
}
