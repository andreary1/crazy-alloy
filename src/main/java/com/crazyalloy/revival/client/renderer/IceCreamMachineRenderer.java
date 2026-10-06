package com.crazyalloy.revival.client.renderer;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.block.IceCreamMachineBlock;
import com.crazyalloy.revival.block.entity.IceCreamMachineBlockEntity;
import com.crazyalloy.revival.client.ModModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

/**
 * Draws the moving parts of the Ice Cream Machine: the lever at the end of the dispensing bar, and the ice cream on
 * the tray. When a serving starts the lever is pulled down, the ice cream swirls up from the tray while the progress
 * runs, and the lever springs back at the end. A finished ice cream waiting in the output sits on the tray.
 */
public class IceCreamMachineRenderer implements BlockEntityRenderer<IceCreamMachineBlockEntity, IceCreamMachineRenderer.State> {
    private static final Identifier LEVER_TEXTURE = CrazyAlloyRevival.id("textures/entity/ice_cream_machine_lever.png");
    private static final float LEVER_UP = 0.55F;
    private static final float LEVER_DOWN = -0.45F;

    private final ModelPart lever;
    private final ItemModelResolver itemModelResolver;

    public IceCreamMachineRenderer(BlockEntityRendererProvider.Context context) {
        this.lever = context.bakeLayer(ModModelLayers.ICE_CREAM_MACHINE_LEVER).getChild("lever");
        this.itemModelResolver = context.itemModelResolver();
    }

    /** Lever in block pixels (y up), facing north: hinged on the front of the black bar at its east end. */
    public static LayerDefinition createLeverLayer() {
        MeshDefinition mesh = new MeshDefinition();
        mesh.getRoot().addOrReplaceChild("lever", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-0.5F, -0.5F, -4.0F, 1, 1, 4)
                .texOffs(0, 6).addBox(-1.0F, -1.0F, -6.0F, 2, 2, 2),
                PartPose.offset(12.5F, 10.0F, 2.0F));
        return LayerDefinition.create(mesh, 16, 16);
    }

    public static class State extends BlockEntityRenderState {
        Direction facing = Direction.NORTH;
        float serve = -1.0F;
        final ItemStackRenderState serving = new ItemStackRenderState();
        final ItemStackRenderState tray = new ItemStackRenderState();
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(IceCreamMachineBlockEntity machine, State state, float partialTicks, Vec3 cameraPosition,
                                   ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(machine, state, partialTicks, cameraPosition, breakProgress);
        state.facing = machine.getBlockState().getValue(IceCreamMachineBlock.FACING);
        state.serve = machine.serveProgress(partialTicks);
        int seed = (int) machine.getBlockPos().asLong();
        this.itemModelResolver.updateForTopItem(state.serving, state.serve >= 0.0F ? machine.animItem() : ItemStack.EMPTY,
                ItemDisplayContext.FIXED, machine.getLevel(), null, seed);
        this.itemModelResolver.updateForTopItem(state.tray, machine.trayItem(), ItemDisplayContext.FIXED, machine.getLevel(), null, seed + 1);
    }

    @Override
    public void submit(State state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.0F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - state.facing.toYRot()));
        poseStack.translate(-0.5F, 0.0F, -0.5F);

        float p = state.serve;
        float angle = LEVER_UP;
        if (p >= 0.0F) {
            if (p < 0.15F) {
                angle = Mth.lerp(p / 0.15F, LEVER_UP, LEVER_DOWN);
            } else if (p < 0.85F) {
                angle = LEVER_DOWN + 0.04F * Mth.sin(p * 60.0F);
            } else {
                // Springs back with a little overshoot.
                float k = (p - 0.85F) / 0.15F;
                angle = Mth.lerp(k, LEVER_DOWN, LEVER_UP) + 0.15F * Mth.sin(k * Mth.PI);
            }
        }
        this.lever.xRot = angle;
        collector.submitModelPart(this.lever, poseStack, RenderTypes.entityCutout(LEVER_TEXTURE), state.lightCoords, OverlayTexture.NO_OVERLAY, null);

        // The ice cream on the tray, under the nozzle, facing the serving side.
        ItemStackRenderState item = null;
        float scale = 0.42F;
        float spin = 0.0F;
        if (p >= 0.0F && !state.serving.isEmpty()) {
            item = state.serving;
            float fill = Mth.clamp((p - 0.15F) / 0.7F, 0.0F, 1.0F);
            scale = 0.12F + 0.30F * fill;
            spin = (1.0F - fill) * 360.0F * 1.5F;
        } else if (!state.tray.isEmpty()) {
            item = state.tray;
        }
        if (item != null) {
            poseStack.pushPose();
            poseStack.translate(0.5F, 2.0F / 16.0F + scale * 0.5F, 2.5F / 16.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F + spin));
            poseStack.scale(scale, scale, scale);
            item.submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
        poseStack.popPose();
    }
}
