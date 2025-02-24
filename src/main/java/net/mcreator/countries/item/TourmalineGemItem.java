
package net.mcreator.countries.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class TourmalineGemItem extends Item {
	public TourmalineGemItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.RARE));
	}
}
