
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
	public static final RegistryObject<SoundEvent> ICECREAMDRAGONAMBIENT = REGISTRY.register("icecreamdragonambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("cl", "icecreamdragonambient")));
	public static final RegistryObject<SoundEvent> ICECREAMDRAGONHURT = REGISTRY.register("icecreamdragonhurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("cl", "icecreamdragonhurt")));
	public static final RegistryObject<SoundEvent> ICECREAMDRAGONDEATH = REGISTRY.register("icecreamdragondeath", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("cl", "icecreamdragondeath")));
}
