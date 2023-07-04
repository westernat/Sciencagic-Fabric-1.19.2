package org.mesdag.scma.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import org.mesdag.scma.client.render.block.PestleDishRenderer;
import org.mesdag.scma.client.render.entity.AmiyaRenderer;
import org.mesdag.scma.client.render.entity.model.AmiyaModel;
import org.mesdag.scma.registry.BlockRegistry;
import org.mesdag.scma.registry.FluidRegistry;
import org.mesdag.scma.registry.ServerEntityRegistry;
import org.mesdag.scma.util.SCMAIdentifier;

import static net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler.WATER_FLOWING;
import static net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler.WATER_STILL;
import static org.mesdag.scma.client.render.block.ModelLayers.amiya_model_layer;

@Environment(EnvType.CLIENT)
public class SCMAClient implements ClientModInitializer {
    private static <E extends Entity> void clientEntityRegister(EntityType<? extends E> entityType, EntityRendererFactory<E> entityRendererFactory, EntityModelLayer modelLayer, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityRendererRegistry.register(entityType, entityRendererFactory);
        EntityModelLayerRegistry.registerModelLayer(modelLayer, provider);
    }

    private static void clientCustomFluidRegister(String id, FlowableFluid still, FlowableFluid flowing) {
        Identifier stillTexture = new SCMAIdentifier("block/still_" + id);
        Identifier flowingTexture = new SCMAIdentifier("block/flowing_" + id);
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(stillTexture);
            registry.register(flowingTexture);
        });
        FluidRenderHandlerRegistry.INSTANCE.register(still, flowing, new SimpleFluidRenderHandler(stillTexture, flowingTexture));
    }

    private static void clientSimpleFluidRegister(FlowableFluid still, FlowableFluid flowing, int color) {
        FluidRenderHandlerRegistry.INSTANCE.register(still, flowing, new SimpleFluidRenderHandler(WATER_STILL, WATER_FLOWING, color));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), still, flowing);
    }

    @Override
    public void onInitializeClient() {
        // Entity
        clientEntityRegister(ServerEntityRegistry.amiya, AmiyaRenderer::new, amiya_model_layer, AmiyaModel::getTexturedModelData);


        // Model -> EntityModelLayerRegistry.registerModelLayer();


        // Renderer
        BlockEntityRendererRegistry.register(BlockRegistry.pestle_dish_entity, PestleDishRenderer::new);
        /*BlockEntityRendererRegistry.register(BlockRegistry.roller_press_entity, RollerPressRenderer::new);*/


        // NonOpaque -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent());


        // Fluid
        clientCustomFluidRegister("petroleum", FluidRegistry.still_petroleum, FluidRegistry.flowing_petroleum);
        clientSimpleFluidRegister(FluidRegistry.still_naphtha, FluidRegistry.flowing_naphtha, 0xC39B64);
        clientSimpleFluidRegister(FluidRegistry.still_mercury, FluidRegistry.flowing_mercury, 0xCCCCCC);

        /*
        // Screen
        ScreenRegistry.register(SCMA.thermal_generator_handler, ThermalGeneratorScreen::new);
        ScreenRegistry.register(SCMA.roller_press_handler, RollerPressScreen::new);
        ScreenRegistry.register(SCMA.melting_forging_handler, MeltingForgingScreen::new);
         */
    }
}
