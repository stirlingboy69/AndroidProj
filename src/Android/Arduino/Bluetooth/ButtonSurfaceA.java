package Android.Arduino.Bluetooth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.ContextMenu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ButtonSurfaceA extends SurfaceView implements Runnable, SurfaceHolder.Callback {

	private SurfaceHolder mSurfaceHolder;
	private Thread mRunningThread;
	private boolean mIsRunning=true;
	
	public ButtonSurfaceA(Context context){
		super(context);
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mRunningThread = new Thread(this);
		mRunningThread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(mIsRunning){
			if(!mSurfaceHolder.getSurface().isValid())
				continue;
			
			/** Start editing pixels in this surface.*/
			Canvas canvas = mSurfaceHolder.lockCanvas();
			
			//ALL PAINT-JOB MAKE IN draw(canvas); method.
			myDraw(canvas);
			
			// End of painting to canvas. system will paint with this canvas,to the surface.
			mSurfaceHolder.unlockCanvasAndPost(canvas);
		}
	}
	/**This method deals with paint-works. Also will paint something in background*/
	private void myDraw(Canvas canvas) {
		// paint a background color
		canvas.drawColor(Color.BLACK);
		
		// paint a rectangular shape that fill the surface.
		int border = 20;
		RectF r = new RectF(border, border, canvas.getWidth()-20, canvas.getHeight()-20);
		Paint paint = new Paint();		
		paint.setARGB(200, 1, 135, 1); //paint color GRAY+SEMY TRANSPARENT 
		canvas.drawRect(r , paint );
		
		//canvas.drawCircle(canvas.getWidth()/4*3, canvas.getHeight()/2, radiusWhite, paint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	
}
