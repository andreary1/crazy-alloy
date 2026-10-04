package com.crazyalloy.revival.client;

import com.crazyalloy.revival.CrazyAlloyRevival;
import net.minecraft.client.model.geom.ModelLayerLocation;

public final class ModModelLayers {
    public static final ModelLayerLocation CANDY_TUBE_DOG = layer("candy_tube_dog");
    public static final ModelLayerLocation LOLLIPOP_GUY = layer("lollipop_guy");
    public static final ModelLayerLocation GRAPE_SPIDER = layer("grape_spider");
    public static final ModelLayerLocation BROWN_SUGAR_RHINO = layer("brown_sugar_rhino");
    public static final ModelLayerLocation COTTON_CANDY_TORNADO = layer("cotton_candy_tornado");
    public static final ModelLayerLocation BUBBLEGUM = layer("bubblegum");
    public static final ModelLayerLocation GINGERBREAD_WARRIOR = layer("gingerbread_warrior");
    public static final ModelLayerLocation GINGERBREAD_SOLDIER = layer("gingerbread_soldier");
    public static final ModelLayerLocation GINGERBREAD_KING = layer("gingerbread_king");
    public static final ModelLayerLocation JELLY_BUNNY = layer("jelly_bunny");
    public static final ModelLayerLocation JELLY_SNAKE = layer("jelly_snake");
    public static final ModelLayerLocation JELLY_SHARK = layer("jelly_shark");
    public static final ModelLayerLocation ROLL_CAKE_MONSTER = layer("roll_cake_monster");
    public static final ModelLayerLocation ICE_CREAM_VENDOR = layer("ice_cream_vendor");

    private static ModelLayerLocation layer(String name) {
        return new ModelLayerLocation(CrazyAlloyRevival.id(name), "main");
    }

    private ModModelLayers() {}
}
