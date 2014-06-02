package Android.Arduino.Bluetooth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.*;
import android.os.SystemClock;

public class ButtonSurfaceA extends SurfaceView implements Runnable, SurfaceHolder.Callback {


	private SurfaceHolder mSurfaceHolder;
	private Thread mRunningThread;
	private boolean mIsRunning=false;
	private boolean mFlipColor=false;
	private long mElapsedTime;
	
	public ButtonSurfaceA(Context context){
		super(context);
		Init();
	}
	
	public ButtonSurfaceA(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init();
	}
	
	public ButtonSurfaceA(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //getHolder().addCallback(this);
        // TODO Auto-generated constructor stub
        Init();
    }
	
	private void Init(){
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mRunningThread = new Thread(this);
		mElapsedTime =  SystemClock.elapsedRealtime();
		//mRunningThread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(mIsRunning){
			long time = SystemClock.elapsedRealtime();

			mSurfaceHolder = getHolder();
			if(!mSurfaceHolder.getSurface().isValid())
				continue;
			
			//Log.i("running","thread");
			/** Start editing pixels in this surface.*/
			Canvas canvas = mSurfaceHolder.lockCanvas();
			
			//ALL PAINT-JOB MAKE IN draw(canvas); method.
			Draw(canvas, time);
			
			// End of painting to canvas. system will paint with this canvas,to the surface.
			mSurfaceHolder.unlockCanvasAndPost(canvas);
		}
	}
	/**This method deals with paint-works. Also will paint something in background*/
	private void Draw(Canvas canvas, long time) {
		// paint a background color
		boolean flipme=false;
		if(time-mElapsedTime>=100)
		{
			flipme=true;
			mElapsedTime = time;
		}
		canvas.drawColor(Color.BLACK);

		// paint a rectangular shape that fill the surface.
		int border = 20;
		RectF r = new RectF(border, border, canvas.getWidth()-20, canvas.getHeight()-20);
		Paint paint = new Paint();
		if (mFlipColor)
		{
			paint.setARGB(200, 200, 15, 1); //paint color GRAY+SEMY TRANSPARENT
			if(flipme)	mFlipColor=false;
		}
		else
		{
			paint.setARGB(200, 20, 150, 1); //paint color GRAY+SEMY TRANSPARENT
			if(flipme) mFlipColor=true;
		}
		canvas.drawRect(r , paint );
		
		//canvas.drawCircle(canvas.getWidth()/4*3, canvas.getHeight()/2, radiusWhite, paint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		//Log.i("surfaceChanged","width="+Integer.toString(width)+",height="+Integer.toString(height) );
		mIsRunning = true;
		mRunningThread.start();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.i("surfaceDestroyed call","");
		mIsRunning = false;
		boolean retry = true;
		
		while (retry) {
			try {
				mRunningThread.join();
				retry = false;
			} 
			catch (InterruptedException e) {
			}
		}
	}
	
	
}
