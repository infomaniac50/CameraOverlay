package com.gmail.infomaniac50.cameraoverlay;

import java.util.Vector;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;

public class ARView extends SurfaceView implements
		SurfaceHolder.Callback {
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private SurfaceHolder surfaceHolder;
	Vector<ARItemModel> viewItems;
	
	public Vector<ARItemModel> getViewItems() {
		return viewItems;
	}

	public void setViewItems(Vector<ARItemModel> viewItems) {
		this.viewItems = viewItems;
	}

	public ARView(Context context) {
		super(context);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT);
	};
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		surfaceHolder = holder;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		surfaceHolder = null;
	}
}