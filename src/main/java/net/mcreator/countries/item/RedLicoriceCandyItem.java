
package net.mcreator.countries.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class RedLicoriceCandyItem extends Item {
	public RedLicoriceCandyItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3f).build()));
	}
}
