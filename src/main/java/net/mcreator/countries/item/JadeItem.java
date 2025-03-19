
package net.mcreator.countries.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class JadeItem extends Item {
	public JadeItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.RARE));
	}
}
