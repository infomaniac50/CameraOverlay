package com.gmail.infomaniac50.cameraoverlay;

import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView.Renderer;
import javax.microedition.khronos.egl.EGLConfig;

public class GLClearRenderer implements Renderer {
    public void onDrawFrame( GL10 gl ) {
        // This method is called per frame, as the name suggests.
        // For demonstration purposes, I simply clear the screen with a random translucent gray.
        float c = 1.0f / 256 * ( System.currentTimeMillis() % 256 );
        
        gl.glClearColor( c, c, c,  0.5f );
        gl.glClear( GL10.GL_COLOR_BUFFER_BIT );
    }

    public void onSurfaceChanged( GL10 gl, int width, int height ) {
        // This is called whenever the dimensions of the surface have changed.
        // We need to adapt this change for the GL viewport.
    	gl.glViewport(0, 0, width, height);

        // make adjustments for screen ratio
        float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);  // apply the projection matrix
    }

    public void onSurfaceCreated( GL10 gl, EGLConfig config ) {
        // No need to do anything here.
    }
}
