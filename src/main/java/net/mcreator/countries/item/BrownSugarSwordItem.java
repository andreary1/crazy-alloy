
package net.mcreator.countries.item;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;

import net.mcreator.countries.procedures.BrownSugarSwordToolInHandTickProcedure;

public class BrownSugarSwordItem extends SwordItem {
	public BrownSugarSwordItem() {
		super(new Tier() {
			public int getUses() {
				return 110;
			}

			public float getSpeed() {
				return 4f;
			}

			public float getAttackDamageBonus() {
				return 0f;
			}

			public int getLevel() {
				return 2;
			}

			public int getEnchantmentValue() {
				return 8;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of();
			}
		}, 3, -2.4f, new Item.Properties());
	}

	@Override
	public void onCraftedBy(ItemStack itemstack, Level world, Player entity) {
		super.onCraftedBy(itemstack, world, entity);
		BrownSugarSwordToolInHandTickProcedure.execute(world, itemstack);
	}
}
