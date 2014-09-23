package com.gmail.infomaniac50.cameraoverlay;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	private ARView arView;
	private CameraView cameraView;
	private ARItemList arItemList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// When working with the camera, it's useful to stick to one orientation.       
		setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );

		// Next, we disable the application's title bar...
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		// ...and the notification bar. That way, we can use the full screen.
		getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN );
		
		arItemList = new ARItemList();
		arItemList.add(new ARItemModel("Death Star", "Alderann System", 2));
		arItemList.add(new ARItemModel("Rebel Base", "Hoth System", 4));
		
		// TODO: Add sensor reading and rotate the address based on current position and orientation
		
		arView = new ARView(this);
		setContentView(arView);
		
		// Now also create a view which contains the camera preview...
	    cameraView = new CameraView( this );
	    // ...and add it, wrapping the full screen size.
	    addContentView( cameraView, new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT ) );
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
