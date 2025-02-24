
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.countries.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

import net.mcreator.countries.entity.SegwayEntity;
import net.mcreator.countries.entity.RouladeMonsterEntity;
import net.mcreator.countries.entity.LollipopGuyEntity;
import net.mcreator.countries.entity.JellySnakeEntity;
import net.mcreator.countries.entity.JellySharkEntity;
import net.mcreator.countries.entity.GuardDinosaurEntity;
import net.mcreator.countries.entity.GingerbreadWarriorEntity;
import net.mcreator.countries.entity.GingerbreadSoldierEntity;
import net.mcreator.countries.entity.DeadSnakeProjEntity;
import net.mcreator.countries.entity.CottonCandyTornadoEntity;
import net.mcreator.countries.entity.CottonCandyProjEntity;
import net.mcreator.countries.entity.CandyTubeDogEntity;
import net.mcreator.countries.entity.BulletProjEntity;
import net.mcreator.countries.entity.BubbalooCreeperEntity;
import net.mcreator.countries.entity.BrownSugarRhinoEntity;
import net.mcreator.countries.ClMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ClMod.MODID);
	public static final RegistryObject<EntityType<CandyTubeDogEntity>> CANDY_TUBE_DOG = register("candy_tube_dog",
			EntityType.Builder.<CandyTubeDogEntity>of(CandyTubeDogEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CandyTubeDogEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<JellySharkEntity>> JELLY_SHARK = register("jelly_shark",
			EntityType.Builder.<JellySharkEntity>of(JellySharkEntity::new, MobCategory.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(JellySharkEntity::new)

					.sized(3f, 1.8f));
	public static final RegistryObject<EntityType<LollipopGuyEntity>> LOLLIPOP_GUY = register("lollipop_guy",
			EntityType.Builder.<LollipopGuyEntity>of(LollipopGuyEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(LollipopGuyEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<BrownSugarRhinoEntity>> BROWN_SUGAR_RHINO = register("brown_sugar_rhino",
			EntityType.Builder.<BrownSugarRhinoEntity>of(BrownSugarRhinoEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(BrownSugarRhinoEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<GingerbreadSoldierEntity>> GINGERBREAD_SOLDIER = register("gingerbread_soldier",
			EntityType.Builder.<GingerbreadSoldierEntity>of(GingerbreadSoldierEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(GingerbreadSoldierEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<CottonCandyTornadoEntity>> COTTON_CANDY_TORNADO = register("cotton_candy_tornado",
			EntityType.Builder.<CottonCandyTornadoEntity>of(CottonCandyTornadoEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CottonCandyTornadoEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<GingerbreadWarriorEntity>> GINGERBREAD_WARRIOR = register("gingerbread_warrior",
			EntityType.Builder.<GingerbreadWarriorEntity>of(GingerbreadWarriorEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(GingerbreadWarriorEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<CottonCandyProjEntity>> COTTON_CANDY_PROJ = register("cotton_candy_proj", EntityType.Builder.<CottonCandyProjEntity>of(CottonCandyProjEntity::new, MobCategory.MISC)
			.setCustomClientFactory(CottonCandyProjEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<JellySnakeEntity>> JELLY_SNAKE = register("jelly_snake",
			EntityType.Builder.<JellySnakeEntity>of(JellySnakeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(JellySnakeEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<DeadSnakeProjEntity>> DEAD_SNAKE_PROJ = register("dead_snake_proj",
			EntityType.Builder.<DeadSnakeProjEntity>of(DeadSnakeProjEntity::new, MobCategory.MISC).setCustomClientFactory(DeadSnakeProjEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<RouladeMonsterEntity>> ROULADE_MONSTER = register("roulade_monster",
			EntityType.Builder.<RouladeMonsterEntity>of(RouladeMonsterEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(RouladeMonsterEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<BubbalooCreeperEntity>> BUBBALOO_CREEPER = register("bubbaloo_creeper", EntityType.Builder.<BubbalooCreeperEntity>of(BubbalooCreeperEntity::new, MobCategory.MONSTER)
			.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(BubbalooCreeperEntity::new).fireImmune().sized(0.6f, 1.7f));
	public static final RegistryObject<EntityType<SegwayEntity>> SEGWAY = register("segway",
			EntityType.Builder.<SegwayEntity>of(SegwayEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(SegwayEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<BulletProjEntity>> BULLET_PROJ = register("bullet_proj",
			EntityType.Builder.<BulletProjEntity>of(BulletProjEntity::new, MobCategory.MISC).setCustomClientFactory(BulletProjEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<GuardDinosaurEntity>> GUARD_DINOSAUR = register("guard_dinosaur",
			EntityType.Builder.<GuardDinosaurEntity>of(GuardDinosaurEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(GuardDinosaurEntity::new)

					.sized(0.6f, 1.8f));

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			CandyTubeDogEntity.init();
			JellySharkEntity.init();
			LollipopGuyEntity.init();
			BrownSugarRhinoEntity.init();
			GingerbreadSoldierEntity.init();
			CottonCandyTornadoEntity.init();
			GingerbreadWarriorEntity.init();
			JellySnakeEntity.init();
			RouladeMonsterEntity.init();
			BubbalooCreeperEntity.init();
			SegwayEntity.init();
			GuardDinosaurEntity.init();
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(CANDY_TUBE_DOG.get(), CandyTubeDogEntity.createAttributes().build());
		event.put(JELLY_SHARK.get(), JellySharkEntity.createAttributes().build());
		event.put(LOLLIPOP_GUY.get(), LollipopGuyEntity.createAttributes().build());
		event.put(BROWN_SUGAR_RHINO.get(), BrownSugarRhinoEntity.createAttributes().build());
		event.put(GINGERBREAD_SOLDIER.get(), GingerbreadSoldierEntity.createAttributes().build());
		event.put(COTTON_CANDY_TORNADO.get(), CottonCandyTornadoEntity.createAttributes().build());
		event.put(GINGERBREAD_WARRIOR.get(), GingerbreadWarriorEntity.createAttributes().build());
		event.put(JELLY_SNAKE.get(), JellySnakeEntity.createAttributes().build());
		event.put(ROULADE_MONSTER.get(), RouladeMonsterEntity.createAttributes().build());
		event.put(BUBBALOO_CREEPER.get(), BubbalooCreeperEntity.createAttributes().build());
		event.put(SEGWAY.get(), SegwayEntity.createAttributes().build());
		event.put(GUARD_DINOSAUR.get(), GuardDinosaurEntity.createAttributes().build());
	}
}
