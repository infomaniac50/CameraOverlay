package com.gmail.infomaniac50.cameraoverlay;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView implements
		SurfaceHolder.Callback {
	// Variables go here
	private Camera camera;
	private SurfaceHolder surfaceHolder;
	private boolean previewing;

	public CameraView(Context context) {
		super(context);
		surfaceHolder = getHolder();
		// We're implementing the Callback interface and want to get notified
		// about certain surface events.
		surfaceHolder.addCallback(this);
		// We're changing the surface to a PUSH surface, meaning we're receiving
		// all buffer data from another component - the camera, in this case.
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// Once the surface is created, simply open a handle to the camera
		// hardware.
		camera = Camera.open();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		if (previewing) {
			camera.stopPreview();
			previewing = false;
		}

		if (camera != null) {
			try {
				camera.setPreviewDisplay(surfaceHolder);
				camera.startPreview();
				previewing = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.stopPreview();
		camera.release();
		camera = null;
		previewing = false;
	}
}