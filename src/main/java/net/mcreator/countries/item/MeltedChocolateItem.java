
package net.mcreator.countries.item;

import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BucketItem;

import net.mcreator.countries.init.ClModFluids;

public class MeltedChocolateItem extends BucketItem {
	public MeltedChocolateItem() {
		super(ClModFluids.MELTED_CHOCOLATE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).rarity(Rarity.COMMON));
	}
}
