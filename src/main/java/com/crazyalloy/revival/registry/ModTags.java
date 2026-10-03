package com.crazyalloy.revival.registry;

import com.crazyalloy.revival.CrazyAlloyRevival;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class ModTags {
    public static final class Items {
        public static final TagKey<Item> BROWN_SUGAR_REPAIR = TagKey.create(Registries.ITEM, CrazyAlloyRevival.id("brown_sugar_repair_materials"));
        /** Heals and breeds Jelly Bunnies. */
        public static final TagKey<Item> JELLY_BUNNY_FOOD = TagKey.create(Registries.ITEM, CrazyAlloyRevival.id("jelly_bunny_food"));
        public static final TagKey<Item> TOURMALINE_REPAIR = TagKey.create(Registries.ITEM, CrazyAlloyRevival.id("tourmaline_repair_materials"));
        /** Heals and breeds Candy Tube Dogs. */
        public static final TagKey<Item> CANDY_TUBE_DOG_FOOD = TagKey.create(Registries.ITEM, CrazyAlloyRevival.id("candy_tube_dog_food"));
        /** Sweets that Candy Tube Dogs sniff out when dropped on the ground. */
        public static final TagKey<Item> SWEETS = TagKey.create(Registries.ITEM, CrazyAlloyRevival.id("sweets"));

        private Items() {}
    }

    public static final class Blocks {
        /** Jelly Bean Fields ground; Heavy Boots cancel the bounce on these. */
        public static final TagKey<Block> JELLY_BEAN_BLOCKS = TagKey.create(Registries.BLOCK, CrazyAlloyRevival.id("jelly_bean_blocks"));
        public static final TagKey<Block> SWEET_GROUND = TagKey.create(Registries.BLOCK, CrazyAlloyRevival.id("sweet_ground"));

        private Blocks() {}
    }

    private ModTags() {}
}
