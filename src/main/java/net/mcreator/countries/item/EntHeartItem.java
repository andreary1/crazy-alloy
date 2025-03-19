
package net.mcreator.countries.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class EntHeartItem extends Item {
	public EntHeartItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
