package com.crazyalloy.revival.registry;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Points of interest: lets the Ice Cream Dimension portal find an existing exit portal quickly, like the Nether portal. */
public final class ModPoiTypes {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, CrazyAlloyRevival.MOD_ID);

    public static final DeferredHolder<PoiType, PoiType> ICE_CREAM_PORTAL = POI_TYPES.register("ice_cream_portal",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.ICE_CREAM_PORTAL.get().getStateDefinition().getPossibleStates()), 0, 1));

    private ModPoiTypes() {}
}
