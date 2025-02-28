package net.mcreator.countries.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.countries.world.inventory.MineralConverterGUIMenu;
import net.mcreator.countries.network.MineralConverterGUIButtonMessage;
import net.mcreator.countries.ClMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class MineralConverterGUIScreen extends AbstractContainerScreen<MineralConverterGUIMenu> {
	private final static HashMap<String, Object> guistate = MineralConverterGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_convert;

	public MineralConverterGUIScreen(MineralConverterGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 192;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("cl:textures/screens/mineral_converter_gui.png");

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

		guiGraphics.blit(new ResourceLocation("cl:textures/screens/arrow.png"), this.leftPos + 80, this.topPos + 25, 0, 0, 32, 32, 32, 32);

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
		guiGraphics.drawString(this.font, Component.translatable("gui.cl.mineral_converter_gui.label_add_ores_to_convert_them_to_mone"), 10, 10, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_convert = Button.builder(Component.translatable("gui.cl.mineral_converter_gui.button_convert"), e -> {
			if (true) {
				ClMod.PACKET_HANDLER.sendToServer(new MineralConverterGUIButtonMessage(0, x, y, z));
				MineralConverterGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 65, this.topPos + 59, 61, 20).build();
		guistate.put("button:button_convert", button_convert);
		this.addRenderableWidget(button_convert);
	}
}
