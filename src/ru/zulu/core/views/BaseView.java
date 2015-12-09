package ru.zulu.core.views;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import ru.zulu.utils.ResizeListener;

public class BaseView extends JPanel {
	// serialization magic
	private static final long serialVersionUID = 157841L;

	public interface EventsListener {
		void onDraw(Graphics _graphics);
		//void drawStick(Graphics _graphics);//

		void onResize(int _width, int _height);
	}

	// =============================================================================================
	// FIELDS
	// =============================================================================================
	private EventsListener eventsListener;

	// =============================================================================================
	// GETTERS/SETTERS
	// =============================================================================================
	public void setEventsListener(EventsListener _eventsListener) {
		eventsListener = _eventsListener;
	}

	// =============================================================================================
	// CONSTRUCTOR
	// =============================================================================================
	public BaseView() {
		addComponentListener(resizeListener);
	}

	// =============================================================================================
	// RESIZE HANDLER
	// =============================================================================================
	private final ResizeListener resizeListener = new ResizeListener() {
		@Override
		public void onResize(int _width, int _height) {
			if (eventsListener != null) {
				eventsListener.onResize(_width, _height);
			}
		}
	};

	// =============================================================================================
	// OVERRIDDEN METHODS
	// =============================================================================================
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (eventsListener != null) {
			eventsListener.onDraw(g);
			//eventsListener.drawStick(g);
		}
		g.dispose();
	}
}
