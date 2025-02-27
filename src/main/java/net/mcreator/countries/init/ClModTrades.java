
/*
*	MCreator note: This file will be REGENERATED on each build.
*/
package net.mcreator.countries.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.common.BasicItemListing;

import net.minecraft.world.item.ItemStack;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClModTrades {
	@SubscribeEvent
	public static void registerTrades(VillagerTradesEvent event) {
		if (event.getType() == ClModVillagerProfessions.ICE_CREAM_MAN.get()) {
			event.getTrades().get(1).add(new BasicItemListing(new ItemStack(ClModItems.MONEY.get(), 3), new ItemStack(ClModItems.COIN.get(), 5), new ItemStack(ClModItems.CHOCOLATE_ICE_CREAM.get()), 30, 5, 0.05f));
			event.getTrades().get(2).add(new BasicItemListing(new ItemStack(ClModItems.MONEY.get(), 3), new ItemStack(ClModItems.COIN.get(), 5), new ItemStack(ClModItems.VANILLA_ICE_CREAM.get()), 30, 6, 0.05f));
			event.getTrades().get(3).add(new BasicItemListing(new ItemStack(ClModItems.MONEY.get(), 3), new ItemStack(ClModItems.COIN.get(), 5), new ItemStack(ClModItems.STRAWBERRY_ICE_CREAM.get()), 30, 7, 0.05f));
			event.getTrades().get(4).add(new BasicItemListing(new ItemStack(ClModItems.MONEY.get(), 3), new ItemStack(ClModItems.COIN.get(), 5), new ItemStack(ClModItems.MINT_ICE_CREAM.get()), 30, 8, 0.05f));
			event.getTrades().get(5).add(new BasicItemListing(new ItemStack(ClModItems.ULTIMATE_ICE_CREAM.get(), 10),

					new ItemStack(ClModItems.ICE_CREAM_DIMENSION.get()), 1, 5, 0.05f));
		}
	}
}
