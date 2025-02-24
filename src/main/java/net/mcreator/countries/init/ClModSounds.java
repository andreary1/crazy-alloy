
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;

import net.mcreator.countries.ClMod;

public class ClModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ClMod.MODID);
	public static final RegistryObject<SoundEvent> AK47SHOT = REGISTRY.register("ak47shot", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("cl", "ak47shot")));
}
