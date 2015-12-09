package ru.zulu.utils;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public abstract class ResizeListener implements ComponentListener {
	@Override
	public void componentResized(ComponentEvent e) {
		Component component = e.getComponent();
		onResize(component.getWidth(), component.getHeight());
	}
	
	public abstract void onResize(int _width, int _height);
	
	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	

	@Override
	public void componentShown(ComponentEvent e) {
	}
}
