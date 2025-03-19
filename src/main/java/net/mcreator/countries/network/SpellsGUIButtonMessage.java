
package net.mcreator.countries.network;

import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.countries.world.inventory.SpellsGUIMenu;
import net.mcreator.countries.procedures.WaterButtonProcedure;
import net.mcreator.countries.procedures.PoisonButtonProcedure;
import net.mcreator.countries.procedures.LightningButtonProcedure;
import net.mcreator.countries.procedures.FireButtonProcedure;
import net.mcreator.countries.procedures.EarthButtonProcedure;
import net.mcreator.countries.procedures.AirButtonProcedure;
import net.mcreator.countries.ClMod;

import java.util.function.Supplier;
import java.util.HashMap;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SpellsGUIButtonMessage {
	private final int buttonID, x, y, z;

	public SpellsGUIButtonMessage(FriendlyByteBuf buffer) {
		this.buttonID = buffer.readInt();
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
	}

	public SpellsGUIButtonMessage(int buttonID, int x, int y, int z) {
		this.buttonID = buttonID;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static void buffer(SpellsGUIButtonMessage message, FriendlyByteBuf buffer) {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}

	public static void handler(SpellsGUIButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			Player entity = context.getSender();
			int buttonID = message.buttonID;
			int x = message.x;
			int y = message.y;
			int z = message.z;
			handleButtonAction(entity, buttonID, x, y, z);
		});
		context.setPacketHandled(true);
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		HashMap guistate = SpellsGUIMenu.guistate;
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			FireButtonProcedure.execute(world, entity);
		}
		if (buttonID == 1) {

			WaterButtonProcedure.execute(world, entity);
		}
		if (buttonID == 2) {

			AirButtonProcedure.execute(world, entity);
		}
		if (buttonID == 3) {

			EarthButtonProcedure.execute(world, entity);
		}
		if (buttonID == 4) {

			LightningButtonProcedure.execute(world, entity);
		}
		if (buttonID == 5) {

			PoisonButtonProcedure.execute(world, entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		ClMod.addNetworkMessage(SpellsGUIButtonMessage.class, SpellsGUIButtonMessage::buffer, SpellsGUIButtonMessage::new, SpellsGUIButtonMessage::handler);
	}
}
