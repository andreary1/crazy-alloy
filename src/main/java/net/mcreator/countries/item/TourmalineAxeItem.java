
package net.mcreator.countries.item;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.AxeItem;

import net.mcreator.countries.init.ClModItems;

public class TourmalineAxeItem extends AxeItem {
	public TourmalineAxeItem() {
		super(new Tier() {
			public int getUses() {
				return 1700;
			}

			public float getSpeed() {
				return 12f;
			}

			public float getAttackDamageBonus() {
				return 8f;
			}

			public int getLevel() {
				return 4;
			}

			public int getEnchantmentValue() {
				return 18;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(ClModItems.TOURMALINE_GEM.get()));
			}
		}, 1, -3f, new Item.Properties());
	}
}
