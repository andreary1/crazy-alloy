
package net.mcreator.countries.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;

public class LightningBallItem extends Item {
	public LightningBallItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}
}
