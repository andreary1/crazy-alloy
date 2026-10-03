package com.crazyalloy.revival.client.screen;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.menu.ChocolateFactoryMenu;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class ChocolateFactoryScreen extends AbstractContainerScreen<ChocolateFactoryMenu> {
    private static final Identifier TEXTURE = CrazyAlloyRevival.id("textures/gui/container/chocolate_factory.png");

    // Positions inside the 176x166 background
    private static final int FLAME_X = 50, FLAME_Y = 37, FLAME_SIZE = 14;
    private static final int ARROW_X = 85, ARROW_Y = 35, ARROW_W = 24, ARROW_H = 16;
    private static final int HELP_X = 158, HELP_Y = 5, HELP_SIZE = 12;

    private static final int FLAME_COLOR = 0xFFE8862A;
    private static final int ARROW_COLOR = 0xFFC9782F;

    public ChocolateFactoryScreen(ChocolateFactoryMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int x = this.leftPos;
        int y = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        if (menu.isBurning()) {
            int h = Math.max(1, menu.burnScaled(FLAME_SIZE));
            graphics.fill(x + FLAME_X, y + FLAME_Y + FLAME_SIZE - h, x + FLAME_X + FLAME_SIZE, y + FLAME_Y + FLAME_SIZE, FLAME_COLOR);
        }
        int w = menu.progressScaled(ARROW_W);
        if (w > 0) {
            graphics.fill(x + ARROW_X, y + ARROW_Y + 4, x + ARROW_X + w, y + ARROW_Y + ARROW_H - 4, ARROW_COLOR);
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        int relX = mouseX - this.leftPos;
        int relY = mouseY - this.topPos;
        if (inside(relX, relY, ARROW_X, ARROW_Y, ARROW_W, ARROW_H)) {
            int percent = menu.progressScaled(100);
            graphics.setTooltipForNextFrame(this.font,
                    Component.translatable("gui.crazyalloy_revival.chocolate_factory.progress", percent), mouseX, mouseY);
        } else if (inside(relX, relY, FLAME_X, FLAME_Y, FLAME_SIZE, FLAME_SIZE)) {
            graphics.setTooltipForNextFrame(this.font, Component.translatable(menu.isBurning()
                    ? "gui.crazyalloy_revival.chocolate_factory.burning"
                    : "gui.crazyalloy_revival.chocolate_factory.no_fuel"), mouseX, mouseY);
        } else if (inside(relX, relY, HELP_X, HELP_Y, HELP_SIZE, HELP_SIZE)) {
            graphics.setComponentTooltipForNextFrame(this.font, List.of(
                    Component.translatable("gui.crazyalloy_revival.chocolate_factory.help.title"),
                    Component.translatable("gui.crazyalloy_revival.chocolate_factory.help.1"),
                    Component.translatable("gui.crazyalloy_revival.chocolate_factory.help.2"),
                    Component.translatable("gui.crazyalloy_revival.chocolate_factory.help.3")), mouseX, mouseY);
        }
    }

    private static boolean inside(int x, int y, int left, int top, int w, int h) {
        return x >= left && x < left + w && y >= top && y < top + h;
    }
}
