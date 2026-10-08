package com.crazyalloy.revival.entity;

/** A creature whose flavour is a variant (Ice Cream Zombie, Living Ice Cream); the renderer picks its texture from it. */
public interface FlavoredMob {
    IceCreamFlavor flavor();
}
