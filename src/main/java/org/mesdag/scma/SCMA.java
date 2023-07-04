package org.mesdag.scma;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.mesdag.scma.entity.Amiya;
import org.mesdag.scma.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class SCMA implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("scma");

    @Override
    public void onInitialize() {
        // Initialize
        FluidRegistry.initialize();
        BlockRegistry.initialize();
        ItemRegistry.initialize();
        StatusEffectRegistry.initialize();
        OreFeatureRegistry.initialize();
        CustomFeatureRegistry.initialize();
        /*EventRegistry.initialize();*/
        GeckoLib.initialize();


        // Entity
        FabricDefaultAttributeRegistry.register(ServerEntityRegistry.amiya, Amiya.createAmiyaAttributes());


        // Command
        /*CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> Debugger.register(dispatcher));*/
    }

    /*
    // Screen
    public static final ScreenHandlerType<ThermalGeneratorHandler> thermal_generator_handler = ScreenHandlerRegistry.registerSimple(new SCMAIdentifier("thermal_generator_handler"), ThermalGeneratorHandler::new);
    public static final ScreenHandlerType<RollerPressHandler> roller_press_handler = ScreenHandlerRegistry.registerSimple(new SCMAIdentifier("roller_press_handler"), RollerPressHandler::new);
    public static final ScreenHandlerType<MeltingForgingHandler> melting_forging_handler = ScreenHandlerRegistry.registerSimple(new SCMAIdentifier("melting_forging_handler"), MeltingForgingHandler::new);
    */
}