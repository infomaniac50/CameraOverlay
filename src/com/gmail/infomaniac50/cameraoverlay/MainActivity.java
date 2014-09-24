package com.gmail.infomaniac50.cameraoverlay;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity {
	final static String TAG = "CameraOverlay";
	SensorManager sensorManager;

	int orientationSensor;
	float headingAngle;
	float pitchAngle;
	float rollAngle;

	int accelerometerSensor;
	float xAxis;
	float yAxis;
	float zAxis;

	LocationManager locationManager;
	double latitude;
	double longitude;
	double altitude;

	SurfaceView cameraPreview;
	SurfaceHolder previewHolder;
	Camera camera;
	boolean inPreview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inPreview = false;

		cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
		previewHolder = cameraPreview.getHolder();
		previewHolder.addCallback(surfaceCallback);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// TODO: Add sensor reading and rotate the address based on current
		// position and orientation

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		orientationSensor = Sensor.TYPE_ORIENTATION;
		accelerometerSensor = Sensor.TYPE_ACCELEROMETER;

		sensorManager.registerListener(sensorEventListener,
				sensorManager.getDefaultSensor(orientationSensor),
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(sensorEventListener,
				sensorManager.getDefaultSensor(accelerometerSensor),
				SensorManager.SENSOR_DELAY_NORMAL);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				2000, 2, locationListener);
	}

	LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			altitude = location.getAltitude();

			Log.d(TAG, "Latitude: " + String.valueOf(latitude));
			Log.d(TAG, "Longitude: " + String.valueOf(longitude));
			Log.d(TAG, "Altitude: " + String.valueOf(altitude));
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

	};

	final SensorEventListener sensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
				headingAngle = sensorEvent.values[0];
				pitchAngle = sensorEvent.values[1];
				rollAngle = sensorEvent.values[2];

				Log.d(TAG, "Heading: " + String.valueOf(headingAngle));
				Log.d(TAG, "Pitch: " + String.valueOf(pitchAngle));
				Log.d(TAG, "Roll: " + String.valueOf(rollAngle));
			} else if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				xAxis = sensorEvent.values[0];
				yAxis = sensorEvent.values[1];
				zAxis = sensorEvent.values[2];

				Log.d(TAG, "X Axis: " + String.valueOf(xAxis));
				Log.d(TAG, "Y Axis: " + String.valueOf(yAxis));
				Log.d(TAG, "Z Axis: " + String.valueOf(zAxis));

			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// Not used
		}
	};

	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				2000, 2, locationListener);
		sensorManager.registerListener(sensorEventListener,
				sensorManager.getDefaultSensor(orientationSensor),
				SensorManager.SENSOR_DELAY_NORMAL);

		sensorManager.registerListener(sensorEventListener,
				sensorManager.getDefaultSensor(accelerometerSensor),
				SensorManager.SENSOR_DELAY_NORMAL);

		camera = Camera.open();
	}

	protected void onPause() {
		if (inPreview) {
			camera.stopPreview();
		}
		
		locationManager.removeUpdates(locationListener);
		sensorManager.unregisterListener(sensorEventListener);
		camera.release();
		camera = null;
		inPreview = false;

		super.onPause();
	}

	SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// not used
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera.setPreviewDisplay(previewHolder);
			} catch (Throwable t) {
				Log.e(MainActivity.class.getName(),
						"Exception in setPreviewDisplay", t);
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			Camera.Parameters parameters = camera.getParameters();
			Camera.Size size = getBestPreviewSize(width, height, parameters);

			if (size != null) {
				parameters.setPreviewSize(size.width, size.height);
				camera.setParameters(parameters);
				camera.startPreview();
				inPreview = true;
			}
		}
	};

	private Camera.Size getBestPreviewSize(int width, int height,
			Camera.Parameters parameters) {
		Camera.Size result = null;

		for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
			if (size.width <= width && size.height <= height) {
				if (result == null) {
					result = size;
				} else {
					int resultArea = result.width * result.height;
					int newArea = size.width * size.height;

					if (newArea > resultArea) {
						result = size;
					}
				}
			}
		}
		return result;
	}

}
