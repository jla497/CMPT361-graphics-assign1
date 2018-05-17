package client;

import windowing.drawable.Drawable;
import windowing.drawable.DrawableDecorator;

public class ColoredDrawable extends DrawableDecorator {
        final int ARGB_WHITE;
	public ColoredDrawable(Drawable delegate, int ARGB_WHITE) {
		super(delegate);
                this.ARGB_WHITE = ARGB_WHITE;
	}
	
	@Override
	public void setPixel(int x, int y, double z, int argbColor) {
		delegate.setPixel(x, y, z, argbColor);
	}
        
	@Override
	public int getPixel(int x, int y) {
		return delegate.getPixel(x, y);
	}
        
	@Override
	public double getZValue(int x, int y) {
		return delegate.getZValue(x, y);
	}
        
//	@Override
//	public void setPixelWithCoverage(int x, int y, double z, int argbColor, double coverage) {
//		delegate.setPixelWithCoverage(x, y, z, argbColor, coverage);
//	}	
}