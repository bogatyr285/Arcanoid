package utils;

import java.awt.Graphics;
import java.util.Random;

public class SlowSpriteSheetAnimator extends SpriteSheetAnimator {
	private static final Random RAND = new Random();
	
	private final int minSkipFrames;
	private final int maxSkipFrames;
	private int counter;
	
	public SlowSpriteSheetAnimator(SpriteSheet _spriteSheet, SpriteSheetAnimatorEventsListener _listener, int _minSkipFrames, int _maxSkipFrames) {
		super(_spriteSheet, _listener);
		minSkipFrames = _minSkipFrames;
		maxSkipFrames = _maxSkipFrames;
		updateCounter();
	}
	
	private void updateCounter() {
		counter = RAND.nextInt(maxSkipFrames - minSkipFrames) + minSkipFrames;
	}

	@Override
	public void drawNextFrame(Graphics _graphics, int _x, int _y) {
		if (counter-- == 0) {
			updateCounter();
			super.drawNextFrame(_graphics, _x, _y);
		} else {
			drawCurrentFrame(_graphics, _x, _y);
		}
	}
}
