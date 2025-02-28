
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

import net.mcreator.countries.entity.VanillaIceCreamZombieEntity;
import net.mcreator.countries.entity.VanillaIceCreamGuyEntity;
import net.mcreator.countries.entity.StrawberryIceCreamGuyEntity;
import net.mcreator.countries.entity.StrawIceCreamZombieEntity;
import net.mcreator.countries.entity.SegwayEntity;
import net.mcreator.countries.entity.RouladeMonsterEntity;
import net.mcreator.countries.entity.MintIceCreamZombieEntity;
import net.mcreator.countries.entity.MintIceCreamGuyEntity;
import net.mcreator.countries.entity.LollipopGuyEntity;
import net.mcreator.countries.entity.LivingIceCreamEntity;
import net.mcreator.countries.entity.JellySnakeEntity;
import net.mcreator.countries.entity.JellySharkEntity;
import net.mcreator.countries.entity.JellyBunnyEntity;
import net.mcreator.countries.entity.ImpostorCakeEntity;
import net.mcreator.countries.entity.IceCreamZombieEntity;
import net.mcreator.countries.entity.IceCreamGargoyleEntity;
import net.mcreator.countries.entity.IceCreamDragonEntity;
import net.mcreator.countries.entity.IceCreamBeastEntity;
import net.mcreator.countries.entity.GuardDinosaurEntity;
import net.mcreator.countries.entity.GrapeSpiderEntity;
import net.mcreator.countries.entity.GingerbreadWarriorEntity;
import net.mcreator.countries.entity.GingerbreadSoldierEntity;
import net.mcreator.countries.entity.GingerbreadProjEntity;
import net.mcreator.countries.entity.GingerbreadKingEntity;
import net.mcreator.countries.entity.GingerbreadHelperEntity;
import net.mcreator.countries.entity.DeadSnakeProjEntity;
import net.mcreator.countries.entity.CottonCandyTornadoEntity;
import net.mcreator.countries.entity.CottonCandyProjEntity;
import net.mcreator.countries.entity.ChocolateIceCreamGuyEntity;
import net.mcreator.countries.entity.ChocIceCreamZombieEntity;
import net.mcreator.countries.entity.CandyTubeDogEntity;
import net.mcreator.countries.entity.BulletProjEntity;
import net.mcreator.countries.entity.BubblegumEntity;
import net.mcreator.countries.entity.BubbalooCreeperEntity;
import net.mcreator.countries.entity.BrownSugarRhinoEntity;
import net.mcreator.countries.entity.BrownSugarProjEntity;
import net.mcreator.countries.entity.AngryIceCreamConeEntity;
import net.mcreator.countries.ClMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ClMod.MODID);
	public static final RegistryObject<EntityType<CandyTubeDogEntity>> CANDY_TUBE_DOG = register("candy_tube_dog",
			EntityType.Builder.<CandyTubeDogEntity>of(CandyTubeDogEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CandyTubeDogEntity::new)

					.sized(0.6f, 0.8f));
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

					.sized(0.6f, 0.9f));
	public static final RegistryObject<EntityType<CottonCandyTornadoEntity>> COTTON_CANDY_TORNADO = register("cotton_candy_tornado",
			EntityType.Builder.<CottonCandyTornadoEntity>of(CottonCandyTornadoEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CottonCandyTornadoEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<GingerbreadWarriorEntity>> GINGERBREAD_WARRIOR = register("gingerbread_warrior",
			EntityType.Builder.<GingerbreadWarriorEntity>of(GingerbreadWarriorEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(GingerbreadWarriorEntity::new)

					.sized(0.6f, 0.9f));
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
	public static final RegistryObject<EntityType<GingerbreadKingEntity>> GINGERBREADKING = register("gingerbreadking",
			EntityType.Builder.<GingerbreadKingEntity>of(GingerbreadKingEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(GingerbreadKingEntity::new)

					.sized(1f, 2f));
	public static final RegistryObject<EntityType<IceCreamGargoyleEntity>> ICE_CREAM_GARGOYLE = register("ice_cream_gargoyle",
			EntityType.Builder.<IceCreamGargoyleEntity>of(IceCreamGargoyleEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(IceCreamGargoyleEntity::new)

					.sized(0.3f, 1.8f));
	public static final RegistryObject<EntityType<LivingIceCreamEntity>> LIVING_ICE_CREAM = register("living_ice_cream",
			EntityType.Builder.<LivingIceCreamEntity>of(LivingIceCreamEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(LivingIceCreamEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ChocolateIceCreamGuyEntity>> CHOCOLATE_ICE_CREAM_GUY = register("chocolate_ice_cream_guy",
			EntityType.Builder.<ChocolateIceCreamGuyEntity>of(ChocolateIceCreamGuyEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(ChocolateIceCreamGuyEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<StrawberryIceCreamGuyEntity>> STRAWBERRY_ICE_CREAM_GUY = register("strawberry_ice_cream_guy",
			EntityType.Builder.<StrawberryIceCreamGuyEntity>of(StrawberryIceCreamGuyEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(StrawberryIceCreamGuyEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<VanillaIceCreamGuyEntity>> VANILLA_ICE_CREAM_GUY = register("vanilla_ice_cream_guy",
			EntityType.Builder.<VanillaIceCreamGuyEntity>of(VanillaIceCreamGuyEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(VanillaIceCreamGuyEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<MintIceCreamGuyEntity>> MINT_ICE_CREAM_GUY = register("mint_ice_cream_guy",
			EntityType.Builder.<MintIceCreamGuyEntity>of(MintIceCreamGuyEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(MintIceCreamGuyEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<AngryIceCreamConeEntity>> ANGRY_ICE_CREAM_CONE = register("angry_ice_cream_cone",
			EntityType.Builder.<AngryIceCreamConeEntity>of(AngryIceCreamConeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(AngryIceCreamConeEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<BubblegumEntity>> BUBBLEGUM = register("bubblegum",
			EntityType.Builder.<BubblegumEntity>of(BubblegumEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(BubblegumEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<IceCreamZombieEntity>> ICE_CREAM_ZOMBIE = register("ice_cream_zombie",
			EntityType.Builder.<IceCreamZombieEntity>of(IceCreamZombieEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(IceCreamZombieEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ChocIceCreamZombieEntity>> CHOC_ICE_CREAM_ZOMBIE = register("choc_ice_cream_zombie",
			EntityType.Builder.<ChocIceCreamZombieEntity>of(ChocIceCreamZombieEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ChocIceCreamZombieEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<VanillaIceCreamZombieEntity>> VANILLA_ICE_CREAM_ZOMBIE = register("vanilla_ice_cream_zombie",
			EntityType.Builder.<VanillaIceCreamZombieEntity>of(VanillaIceCreamZombieEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(VanillaIceCreamZombieEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<StrawIceCreamZombieEntity>> STRAW_ICE_CREAM_ZOMBIE = register("straw_ice_cream_zombie",
			EntityType.Builder.<StrawIceCreamZombieEntity>of(StrawIceCreamZombieEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(StrawIceCreamZombieEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<MintIceCreamZombieEntity>> MINT_ICE_CREAM_ZOMBIE = register("mint_ice_cream_zombie",
			EntityType.Builder.<MintIceCreamZombieEntity>of(MintIceCreamZombieEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(MintIceCreamZombieEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<IceCreamBeastEntity>> ICE_CREAM_BEAST = register("ice_cream_beast",
			EntityType.Builder.<IceCreamBeastEntity>of(IceCreamBeastEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(IceCreamBeastEntity::new)

					.sized(0.8f, 3f));
	public static final RegistryObject<EntityType<GingerbreadHelperEntity>> GINGERBREAD_HELPER = register("gingerbread_helper",
			EntityType.Builder.<GingerbreadHelperEntity>of(GingerbreadHelperEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(GingerbreadHelperEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<GingerbreadProjEntity>> GINGERBREAD_PROJ = register("gingerbread_proj", EntityType.Builder.<GingerbreadProjEntity>of(GingerbreadProjEntity::new, MobCategory.MISC)
			.setCustomClientFactory(GingerbreadProjEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<BrownSugarProjEntity>> BROWN_SUGAR_PROJ = register("brown_sugar_proj",
			EntityType.Builder.<BrownSugarProjEntity>of(BrownSugarProjEntity::new, MobCategory.MISC).setCustomClientFactory(BrownSugarProjEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<GrapeSpiderEntity>> GRAPE_SPIDER = register("grape_spider",
			EntityType.Builder.<GrapeSpiderEntity>of(GrapeSpiderEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(GrapeSpiderEntity::new)

					.sized(0.6f, 0.9f));
	public static final RegistryObject<EntityType<JellyBunnyEntity>> JELLY_BUNNY = register("jelly_bunny",
			EntityType.Builder.<JellyBunnyEntity>of(JellyBunnyEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(JellyBunnyEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<ImpostorCakeEntity>> IMPOSTOR_CAKE = register("impostor_cake",
			EntityType.Builder.<ImpostorCakeEntity>of(ImpostorCakeEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(ImpostorCakeEntity::new)

					.sized(0.6f, 1f));
	public static final RegistryObject<EntityType<IceCreamDragonEntity>> ICE_CREAM_DRAGON = register("ice_cream_dragon", EntityType.Builder.<IceCreamDragonEntity>of(IceCreamDragonEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
			.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(IceCreamDragonEntity::new).fireImmune().sized(1.5f, 3.6f));

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
			GingerbreadKingEntity.init();
			IceCreamGargoyleEntity.init();
			LivingIceCreamEntity.init();
			ChocolateIceCreamGuyEntity.init();
			StrawberryIceCreamGuyEntity.init();
			VanillaIceCreamGuyEntity.init();
			MintIceCreamGuyEntity.init();
			AngryIceCreamConeEntity.init();
			BubblegumEntity.init();
			IceCreamZombieEntity.init();
			ChocIceCreamZombieEntity.init();
			VanillaIceCreamZombieEntity.init();
			StrawIceCreamZombieEntity.init();
			MintIceCreamZombieEntity.init();
			IceCreamBeastEntity.init();
			GingerbreadHelperEntity.init();
			GrapeSpiderEntity.init();
			JellyBunnyEntity.init();
			ImpostorCakeEntity.init();
			IceCreamDragonEntity.init();
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
		event.put(GINGERBREADKING.get(), GingerbreadKingEntity.createAttributes().build());
		event.put(ICE_CREAM_GARGOYLE.get(), IceCreamGargoyleEntity.createAttributes().build());
		event.put(LIVING_ICE_CREAM.get(), LivingIceCreamEntity.createAttributes().build());
		event.put(CHOCOLATE_ICE_CREAM_GUY.get(), ChocolateIceCreamGuyEntity.createAttributes().build());
		event.put(STRAWBERRY_ICE_CREAM_GUY.get(), StrawberryIceCreamGuyEntity.createAttributes().build());
		event.put(VANILLA_ICE_CREAM_GUY.get(), VanillaIceCreamGuyEntity.createAttributes().build());
		event.put(MINT_ICE_CREAM_GUY.get(), MintIceCreamGuyEntity.createAttributes().build());
		event.put(ANGRY_ICE_CREAM_CONE.get(), AngryIceCreamConeEntity.createAttributes().build());
		event.put(BUBBLEGUM.get(), BubblegumEntity.createAttributes().build());
		event.put(ICE_CREAM_ZOMBIE.get(), IceCreamZombieEntity.createAttributes().build());
		event.put(CHOC_ICE_CREAM_ZOMBIE.get(), ChocIceCreamZombieEntity.createAttributes().build());
		event.put(VANILLA_ICE_CREAM_ZOMBIE.get(), VanillaIceCreamZombieEntity.createAttributes().build());
		event.put(STRAW_ICE_CREAM_ZOMBIE.get(), StrawIceCreamZombieEntity.createAttributes().build());
		event.put(MINT_ICE_CREAM_ZOMBIE.get(), MintIceCreamZombieEntity.createAttributes().build());
		event.put(ICE_CREAM_BEAST.get(), IceCreamBeastEntity.createAttributes().build());
		event.put(GINGERBREAD_HELPER.get(), GingerbreadHelperEntity.createAttributes().build());
		event.put(GRAPE_SPIDER.get(), GrapeSpiderEntity.createAttributes().build());
		event.put(JELLY_BUNNY.get(), JellyBunnyEntity.createAttributes().build());
		event.put(IMPOSTOR_CAKE.get(), ImpostorCakeEntity.createAttributes().build());
		event.put(ICE_CREAM_DRAGON.get(), IceCreamDragonEntity.createAttributes().build());
	}
}
