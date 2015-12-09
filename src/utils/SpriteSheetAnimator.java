package utils;

import java.awt.Graphics;

public class SpriteSheetAnimator {
	public interface SpriteSheetAnimatorEventsListener {
		public void onAnimationFinished();
	}

	private final SpriteSheet spriteSheet;
	private final SpriteSheetAnimatorEventsListener listener;
	private int frame;

	public SpriteSheetAnimator(SpriteSheet _spriteSheet, SpriteSheetAnimatorEventsListener _listener) {
		spriteSheet = _spriteSheet;
		listener = _listener;
	}

	protected void drawCurrentFrame(Graphics _graphics, int _x, int _y) {
		int sourceX = frame % spriteSheet.framesPerRow * spriteSheet.frameWidth;
		int sourceY = frame / spriteSheet.framesPerRow * spriteSheet.frameHeight;
		_graphics.drawImage(spriteSheet.sprite, _x, _y, _x + spriteSheet.frameWidth, _y + spriteSheet.frameHeight,
				sourceX, sourceY, sourceX + spriteSheet.frameWidth, sourceY + spriteSheet.frameHeight, null);
	}

	public void drawNextFrame(Graphics _graphics, int _x, int _y) {
		drawCurrentFrame(_graphics, _x, _y);
		frame++;
		if (frame == spriteSheet.framesTotal) {
			frame = 0;
			if (listener != null) {
				listener.onAnimationFinished();
			}
		}
	}
}
