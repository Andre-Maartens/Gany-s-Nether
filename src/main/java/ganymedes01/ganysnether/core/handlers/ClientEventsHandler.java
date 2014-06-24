package ganymedes01.ganysnether.core.handlers;

import ganymedes01.ganysnether.blocks.SpectreWheatCrop;
import ganymedes01.ganysnether.client.renderer.block.IconSpectreWheat;
import ganymedes01.ganysnether.lib.Reference;
import ganymedes01.ganysnether.lib.Strings;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Gany's Nether
 *
 * @author ganymedes01
 *
 */

@SideOnly(Side.CLIENT)
public class ClientEventsHandler {

	private static final ArrayList<String> usersWithCapes = new ArrayList<String>();
	private static BufferedImage CAPE_DATA = null;
	private static BufferedImage JEBJEB_CAPE_DATA = null;
	private static BufferedImage KPR_CAPE_DATA = null;
	private static boolean started = false;

	@SubscribeEvent
	public void onPreRenderSpecials(RenderPlayerEvent.Specials.Pre event) {
		if (!started) {
			downloadCapes();
			started = true;
			return;
		}

		if (event.entityPlayer instanceof AbstractClientPlayer)
			if (usersWithCapes.contains(event.entityPlayer.getCommandSenderName())) {
				AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;

				if (!player.getTextureCape().isTextureUploaded())
					if (event.entityPlayer.getCommandSenderName().equals("Jeb_Jeb"))
						player.getTextureCape().setBufferedImage(JEBJEB_CAPE_DATA);
					else if (event.entityPlayer.getCommandSenderName().equals("KingPurpleRaptor"))
						player.getTextureCape().setBufferedImage(KPR_CAPE_DATA);
					else
						player.getTextureCape().setBufferedImage(CAPE_DATA);
				event.renderCape = true;
			}
	}

	private void downloadCapes() {
		new Thread(new CapeDownlaoder(), "CapeDownloader").start();
	}

	private static class CapeDownlaoder implements Runnable {

		@Override
		public void run() {
			try {
				Scanner scanner = new Scanner(new URL(Reference.USERS_WITH_CAPES_FILE).openStream());
				while (scanner.hasNext())
					usersWithCapes.add(scanner.nextLine());
				scanner.close();
			} catch (IOException e) {
			}

			try {
				CAPE_DATA = ImageIO.read(new URL(Reference.CAPE_IMAGE_FILE));
			} catch (IOException e) {
			}

			try {
				JEBJEB_CAPE_DATA = ImageIO.read(new URL(Reference.JEBJEB_CAPE_IMAGE_FILE));
			} catch (IOException e) {
			}

			try {
				KPR_CAPE_DATA = ImageIO.read(new URL(Reference.KPR_CAPE_IMAGE_FILE));
			} catch (IOException e) {
			}
		}
	}

	@SubscribeEvent
	public void loadTextures(TextureStitchEvent.Pre event) {
		if (event.map.getTextureType() == 0) {
			SpectreWheatCrop.icons = new IIcon[8];

			for (int i = 0; i < 8; i++)
				SpectreWheatCrop.icons[i] = IconSpectreWheat.getIcon(event.map, Blocks.wheat, i, Strings.Blocks.SPECTRE_WHEAT_BLOCK_NAME + "_stage_" + i);
		}
	}
}