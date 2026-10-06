package com.crazyalloy.revival.client.screen;

import com.crazyalloy.revival.CrazyAlloyRevival;
import com.crazyalloy.revival.block.entity.IceCreamMachineBlockEntity;
import com.crazyalloy.revival.menu.IceCreamMachineMenu;
import com.crazyalloy.revival.registry.ModItems;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * Ice Cream Machine screen: cone and flavour on the left, the milk tank (one stripe per serving) with its bucket slot,
 * the serving arrow, and the output. Under the arrow it names the flavour chosen; while the output is empty a faded
 * picture of the ice cream that flavour makes sits in the output slot.
 */
public class IceCreamMachineScreen extends AbstractContainerScreen<IceCreamMachineMenu> {
    private static final Identifier TEXTURE = CrazyAlloyRevival.id("textures/gui/container/ice_cream_machine.png");

    // Positions inside the 176x166 background
    private static final int TANK_X = 62, TANK_Y = 18, TANK_W = 14, TANK_H = 31;
    private static final int ARROW_X = 88, ARROW_Y = 37, ARROW_W = 24, ARROW_H = 16;
    private static final int HELP_X = 158, HELP_Y = 5, HELP_SIZE = 12;
    /** The flavour line wraps inside the column right of the milk slot, under the output slot. */
    private static final int LABEL_X = 128, LABEL_Y = 54, LABEL_WIDTH = 86;

    private static final int MILK_COLOR = 0xFFFBF6EE;
    private static final int MILK_LINE = 0xFFD9CFC0;
    private static final int ARROW_COLOR = 0xFFF06A9A;
    private static final int GHOST = 0x998B8B8B;

    public IceCreamMachineScreen(IceCreamMachineMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        int x = this.leftPos;
        int y = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight, 256, 256);

        // Milk tank: filled from the bottom, a darker line between servings.
        int milk = menu.milk();
        int max = menu.milkMax();
        int h = milk * TANK_H / max;
        if (h > 0) {
            graphics.fill(x + TANK_X, y + TANK_Y + TANK_H - h, x + TANK_X + TANK_W, y + TANK_Y + TANK_H, MILK_COLOR);
            for (int i = 1; i < milk; i++) {
                int ly = y + TANK_Y + TANK_H - i * TANK_H / max;
                graphics.fill(x + TANK_X, ly, x + TANK_X + TANK_W, ly + 1, MILK_LINE);
            }
        }
        int w = menu.progressScaled(ARROW_W);
        if (w > 0) {
            graphics.fill(x + ARROW_X, y + ARROW_Y + 4, x + ARROW_X + w, y + ARROW_Y + ARROW_H - 4, ARROW_COLOR);
        }

        // Faded hints in empty slots: a cone, a milk bucket, and the ice cream the current flavour makes.
        ghost(graphics, IceCreamMachineBlockEntity.SLOT_CONE, new ItemStack(ModItems.WAFER_CONE.get()), IceCreamMachineMenu.CONE_X, IceCreamMachineMenu.CONE_Y);
        ghost(graphics, IceCreamMachineBlockEntity.SLOT_MILK, new ItemStack(Items.MILK_BUCKET), IceCreamMachineMenu.MILK_X, IceCreamMachineMenu.MILK_Y);
        ItemStack result = menu.flavorResult();
        if (!result.isEmpty()) {
            ghost(graphics, IceCreamMachineBlockEntity.SLOT_OUTPUT, result, IceCreamMachineMenu.OUTPUT_X, IceCreamMachineMenu.OUTPUT_Y);
        }
    }

    private void ghost(GuiGraphicsExtractor graphics, int slot, ItemStack stack, int sx, int sy) {
        if (!menu.slotItem(slot).isEmpty()) {
            return;
        }
        int x = this.leftPos + sx;
        int y = this.topPos + sy;
        graphics.fakeItem(stack, x, y);
        graphics.fill(x, y, x + 16, y + 16, GHOST);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        super.extractLabels(graphics, mouseX, mouseY);
        ItemStack result = menu.flavorResult();
        Component label = result.isEmpty()
                ? Component.translatable("gui.crazyalloy_revival.ice_cream_machine.no_flavor")
                : Component.translatable("gui.crazyalloy_revival.ice_cream_machine.flavor", result.getHoverName());
        int y = LABEL_Y;
        for (FormattedCharSequence line : this.font.split(label, LABEL_WIDTH)) {
            graphics.text(this.font, line, LABEL_X - this.font.width(line) / 2, y, 0xFF8A3A5E, false);
            y += this.font.lineHeight;
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);

        int relX = mouseX - this.leftPos;
        int relY = mouseY - this.topPos;
        if (inside(relX, relY, ARROW_X, ARROW_Y, ARROW_W, ARROW_H)) {
            graphics.setTooltipForNextFrame(this.font,
                    Component.translatable("gui.crazyalloy_revival.ice_cream_machine.progress", menu.progressScaled(100)), mouseX, mouseY);
        } else if (inside(relX, relY, TANK_X, TANK_Y, TANK_W, TANK_H)) {
            graphics.setTooltipForNextFrame(this.font,
                    Component.translatable("gui.crazyalloy_revival.ice_cream_machine.milk", menu.milk(), menu.milkMax()), mouseX, mouseY);
        } else if (inside(relX, relY, HELP_X, HELP_Y, HELP_SIZE, HELP_SIZE)) {
            graphics.setComponentTooltipForNextFrame(this.font, List.of(
                    Component.translatable("gui.crazyalloy_revival.ice_cream_machine.help.title"),
                    Component.translatable("gui.crazyalloy_revival.ice_cream_machine.help.1"),
                    Component.translatable("gui.crazyalloy_revival.ice_cream_machine.help.2"),
                    Component.translatable("gui.crazyalloy_revival.ice_cream_machine.help.3")), mouseX, mouseY);
        }
    }

    private static boolean inside(int x, int y, int left, int top, int w, int h) {
        return x >= left && x < left + w && y >= top && y < top + h;
    }
}
