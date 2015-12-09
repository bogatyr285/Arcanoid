package utils;

import java.awt.Image;

public final class SpriteSheet {
	public final Image sprite;
	public final int frameWidth;
	public final int frameHeight;
	public final int framesPerRow;
	public final int framesTotal;

	public SpriteSheet(String _spriteName, int _frameWidth, int _frameHeight) {
		sprite = ResourcesLoader.loadDrawableIgnoreErrors(_spriteName);
		frameWidth = _frameWidth;
		frameHeight = _frameHeight;
		framesPerRow = sprite.getWidth(null) / _frameWidth;
		int framesPerCol = sprite.getHeight(null) / _frameHeight;
		framesTotal = framesPerRow * framesPerCol;
	}
}
