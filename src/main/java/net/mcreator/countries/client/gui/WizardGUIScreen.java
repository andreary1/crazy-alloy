package net.mcreator.countries.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.countries.world.inventory.WizardGUIMenu;
import net.mcreator.countries.network.WizardGUIButtonMessage;
import net.mcreator.countries.ClMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class WizardGUIScreen extends AbstractContainerScreen<WizardGUIMenu> {
	private final static HashMap<String, Object> guistate = WizardGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_trade;

	public WizardGUIScreen(WizardGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 182;
		this.imageHeight = 195;
	}

	private static final ResourceLocation texture = new ResourceLocation("cl:textures/screens/wizard_gui.png");

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

		guiGraphics.blit(new ResourceLocation("cl:textures/screens/arrow.png"), this.leftPos + 80, this.topPos + 23, 0, 0, 32, 32, 32, 32);

		guiGraphics.blit(new ResourceLocation("cl:textures/screens/entheart.png"), this.leftPos + 38, this.topPos + 5, 0, 0, 16, 16, 16, 16);

		guiGraphics.blit(new ResourceLocation("cl:textures/screens/shadowshard.png"), this.leftPos + 15, this.topPos + 6, 0, 0, 16, 16, 16, 16);

		guiGraphics.blit(new ResourceLocation("cl:textures/screens/arrow.png"), this.leftPos + 81, this.topPos + 61, 0, 0, 32, 32, 32, 32);

		guiGraphics.blit(new ResourceLocation("cl:textures/screens/bluemushroomitem.png"), this.leftPos + 19, this.topPos + 60, 0, 0, 16, 16, 16, 16);

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
		guiGraphics.drawString(this.font, Component.translatable("gui.cl.wizard_gui.label_32"), 41, 21, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.cl.wizard_gui.label_64"), 18, 21, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.cl.wizard_gui.label_empty"), 141, 19, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.cl.wizard_gui.label_empty1"), 141, 56, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.cl.wizard_gui.label_321"), 22, 76, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
		button_trade = Button.builder(Component.translatable("gui.cl.wizard_gui.button_trade"), e -> {
			if (true) {
				ClMod.PACKET_HANDLER.sendToServer(new WizardGUIButtonMessage(0, x, y, z));
				WizardGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 69, this.topPos + 5, 51, 20).build();
		guistate.put("button:button_trade", button_trade);
		this.addRenderableWidget(button_trade);
	}
}
