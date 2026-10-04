package com.crazyalloy.revival.client.model;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class CandyTubeDogRenderState extends LivingEntityRenderState {
    public boolean sitting;
    public boolean tame;
    /** -1 to 1: how far the head is tilted to one side (curious about sweets, or an idle head tilt). */
    public float headTilt;
}
