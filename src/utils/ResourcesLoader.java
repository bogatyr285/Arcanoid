package utils;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class ResourcesLoader {
	private ResourcesLoader() {
		// don't instantiate
	}

	private static final String DRAWABLES_BASE_PATH = "/resources/drawable/";

	public static Image loadDrawableIgnoreErrors(String _drawableName) {
		String fullPath = DRAWABLES_BASE_PATH + _drawableName;
		try {
			return ImageIO.read(ResourcesLoader.class.getResource(fullPath));
		} catch (IOException e) {
			System.out.println("Failed to load drawable, path=" + fullPath + ", exception=" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
