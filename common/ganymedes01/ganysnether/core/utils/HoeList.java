package ganymedes01.ganysnether.core.utils;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ItemApi;
import cpw.mods.fml.common.Loader;

/**
 * Gany's Nether
 * 
 * @author ganymedes01
 * 
 */

public class HoeList {

	private static ArrayList<Item> hoesThatCanTillNetherrack;

	public static void init() {
		hoesThatCanTillNetherrack = new ArrayList<Item>();
		addHoe(Item.hoeGold);
		addHoe(Item.hoeDiamond);
	}

	public static void addHoe(ItemStack hoe) {
		if (hoe != null)
			addHoe(hoe.getItem());
	}

	private static void addHoe(Item hoe) {
		if (hoe != null && !hoesThatCanTillNetherrack.contains(hoe))
			hoesThatCanTillNetherrack.add(hoe);
	}

	public static boolean canTillNetherrack(ItemStack hoe) {
		return hoe != null ? canTillNetherrack(hoe.getItem()) : false;
	}

	public static boolean canTillNetherrack(Item hoe) {
		registerThaumcraftHoes();
		return hoesThatCanTillNetherrack.contains(hoe);
	}

	public static void registerThaumcraftHoes() {
		if (Loader.isModLoaded("Thaumcraft")) {
			addHoe(ItemApi.getItem("itemHoeThaumium", 0));
			addHoe(ItemApi.getItem("itemHoeElemental", 0));
		}
	}
}