package net.mcreator.countries.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.mcreator.countries.world.inventory.ChocolateFactoryGUIMenu;
import net.mcreator.countries.network.ChocolateFactoryGUIButtonMessage;
import net.mcreator.countries.ClMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class ChocolateFactoryGUIScreen extends AbstractContainerScreen<ChocolateFactoryGUIMenu> {
	private final static HashMap<String, Object> guistate = ChocolateFactoryGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_make_chocolate;

	public ChocolateFactoryGUIScreen(ChocolateFactoryGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("cl:textures/screens/chocolate_factory_gui.png");

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

		guiGraphics.blit(new ResourceLocation("cl:textures/screens/arrow.png"), this.leftPos + 73, this.topPos + 20, 0, 0, 32, 32, 32, 32);

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
	}

	@Override
	public void init() {
		super.init();
		button_make_chocolate = Button.builder(Component.translatable("gui.cl.chocolate_factory_gui.button_make_chocolate"), e -> {
			if (true) {
				ClMod.PACKET_HANDLER.sendToServer(new ChocolateFactoryGUIButtonMessage(0, x, y, z));
				ChocolateFactoryGUIButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 39, this.topPos + 55, 98, 20).build();
		guistate.put("button:button_make_chocolate", button_make_chocolate);
		this.addRenderableWidget(button_make_chocolate);
	}
}
