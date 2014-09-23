package com.gmail.infomaniac50.cameraoverlay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class ARViewItem extends SurfaceView 
	implements Callback {
	
	private ARItemModel arItemModel;
	private Paint paint;
	private float fontHeight;
	private float lineHeight;
	private RectF bounds;
	
	public ARViewItem(Context context, ARItemModel arItemModel) {
		super(context);
		this.arItemModel = arItemModel;
	}
	
	private static RectF calculateBounds(ARItemModel arItemModel, Paint paint) {
		RectF bounds = new RectF();
		
		bounds.union(new RectF(0.0F, 0.0F, paint.measureText(arItemModel.getRatingText()), paint.getFontSpacing()));
		
		bounds.offset(0.0F, paint.getFontSpacing());
		bounds.union(new RectF(0.0F, 0.0F, paint.measureText(arItemModel.getAddress()), paint.getFontSpacing()));
		
		bounds.offset(0.0F, paint.getFontSpacing());
		bounds.union(new RectF(0.0F, 0.0F, paint.measureText(arItemModel.getName()), paint.getFontSpacing()));

		return bounds;
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.clipRect(bounds);
		RectF inset = new RectF(bounds);
		inset.inset(1.0F, 1.0F);
		
		paint.setColor(Color.BLACK);
		canvas.drawRoundRect(bounds, 2.0F, 2.0F, paint);
		
		paint.setColor(Color.GRAY);
		canvas.drawRoundRect(inset, 2.0F, 2.0F, paint);
		
		paint.setColor(Color.WHITE);
		
		drawTextLine(canvas, 1.0F, arItemModel.getName());
		drawTextLine(canvas, 2.0F, arItemModel.getAddress());
		drawTextLine(canvas, 3.0F, arItemModel.getRatingText());
	}
	
	public void drawTextLine(Canvas canvas, float line, String text) {
		canvas.drawText(text, 0.0F, line * lineHeight, paint);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		bounds = calculateBounds(this.arItemModel, this.paint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		fontHeight = paint.ascent() + paint.descent();
		lineHeight = paint.getFontSpacing();			
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
}
