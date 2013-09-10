package ganymedes01.ganysnether;

import ganymedes01.ganysnether.blocks.ModBlocks;
import ganymedes01.ganysnether.configuration.ConfigurationHandler;
import ganymedes01.ganysnether.core.handlers.HoeEvent;
import ganymedes01.ganysnether.core.handlers.MobDeathEvent;
import ganymedes01.ganysnether.core.proxy.CommonProxy;
import ganymedes01.ganysnether.creativetab.CreativeTabNether;
import ganymedes01.ganysnether.items.ModItems;
import ganymedes01.ganysnether.lib.Reference;
import ganymedes01.ganysnether.recipes.BuildCraftFacadeManager;
import ganymedes01.ganysnether.recipes.ModRecipes;
import ganymedes01.ganysnether.world.CropsGenerator;

import java.io.File;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, dependencies = Reference.DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class GanysNether {

	@Instance(Reference.MOD_ID)
	public static GanysNether instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static CreativeTabs netherTab = new CreativeTabNether();
	public static int sceptreOfConcealmentDurability = 128;
	public static int baseballBatDurability = 256;
	public static boolean shouldGenerateCrops = true;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MASTER + File.separator + Reference.MOD_ID + ".cfg"));
		ModBlocks.init();
		ModItems.init();
		ModRecipes.init();
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		MinecraftForge.EVENT_BUS.register(new HoeEvent());
		MinecraftForge.EVENT_BUS.register(new MobDeathEvent());
		proxy.registerTileEntities();
		proxy.registerRenderers();
		if (shouldGenerateCrops)
			GameRegistry.registerWorldGenerator(new CropsGenerator());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		BuildCraftFacadeManager.init();
	}
}
