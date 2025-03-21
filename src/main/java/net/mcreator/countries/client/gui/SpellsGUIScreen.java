package net.mcreator.countries.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.countries.world.inventory.SpellsGUIMenu;
import net.mcreator.countries.network.SpellsGUIButtonMessage;
import net.mcreator.countries.ClMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class SpellsGUIScreen extends AbstractContainerScreen<SpellsGUIMenu> {
	private final static HashMap<String, Object> guistate = SpellsGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_fire;
	Button button_water;
	Button button_air;
	Button button_earth;
	Button button_lightning;
	Button button_poison;

	public SpellsGUIScreen(SpellsGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 164;
		this.imageHeight = 142;
	}

	private static final ResourceLocation texture = new ResourceLocation("cl:textures/screens/spells_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.cl.spells_gui.label_choose_your_power"), 38, 18, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_fire = Button.builder(Component.translatable("gui.cl.spells_gui.button_fire"), e -> {
			if (true) {
				ClMod.PACKET_HANDLER.sendToServer(new SpellsGUIButtonMessage(0, x, y, z));
				SpellsGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 8, this.topPos + 40, 46, 20).build();
		guistate.put("button:button_fire", button_fire);
		this.addRenderableWidget(button_fire);
		button_water = Button.builder(Component.translatable("gui.cl.spells_gui.button_water"), e -> {
			if (true) {
				ClMod.PACKET_HANDLER.sendToServer(new SpellsGUIButtonMessage(1, x, y, z));
				SpellsGUIButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 106, this.topPos + 40, 51, 20).build();
		guistate.put("button:button_water", button_water);
		this.addRenderableWidget(button_water);
		button_air = Button.builder(Component.translatable("gui.cl.spells_gui.button_air"), e -> {
			if (true) {
				ClMod.PACKET_HANDLER.sendToServer(new SpellsGUIButtonMessage(2, x, y, z));
				SpellsGUIButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 60, this.topPos + 40, 40, 20).build();
		guistate.put("button:button_air", button_air);
		this.addRenderableWidget(button_air);
		button_earth = Button.builder(Component.translatable("gui.cl.spells_gui.button_earth"), e -> {
			if (true) {
				ClMod.PACKET_HANDLER.sendToServer(new SpellsGUIButtonMessage(3, x, y, z));
				SpellsGUIButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 23, this.topPos + 68, 51, 20).build();
		guistate.put("button:button_earth", button_earth);
		this.addRenderableWidget(button_earth);
		button_lightning = Button.builder(Component.translatable("gui.cl.spells_gui.button_lightning"), e -> {
			if (true) {
				ClMod.PACKET_HANDLER.sendToServer(new SpellsGUIButtonMessage(4, x, y, z));
				SpellsGUIButtonMessage.handleButtonAction(entity, 4, x, y, z);
			}
		}).bounds(this.leftPos + 44, this.topPos + 97, 72, 20).build();
		guistate.put("button:button_lightning", button_lightning);
		this.addRenderableWidget(button_lightning);
		button_poison = Button.builder(Component.translatable("gui.cl.spells_gui.button_poison"), e -> {
			if (true) {
				ClMod.PACKET_HANDLER.sendToServer(new SpellsGUIButtonMessage(5, x, y, z));
				SpellsGUIButtonMessage.handleButtonAction(entity, 5, x, y, z);
			}
		}).bounds(this.leftPos + 87, this.topPos + 68, 56, 20).build();
		guistate.put("button:button_poison", button_poison);
		this.addRenderableWidget(button_poison);
	}
}
