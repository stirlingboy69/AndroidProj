package Android.Arduino.Bluetooth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.util.*;

public class ButtonSurfaceA extends SurfaceView implements Runnable, SurfaceHolder.Callback {


	private SurfaceHolder mSurfaceHolder;
	private Thread mRunningThread;
	private boolean mIsRunning=false;
	
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
		mRunningThread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(mIsRunning){
			mSurfaceHolder = getHolder();
			if(!mSurfaceHolder.getSurface().isValid())
				continue;
			
			//Log.i("running","thread");
			/** Start editing pixels in this surface.*/
			Canvas canvas = mSurfaceHolder.lockCanvas();
			
			//ALL PAINT-JOB MAKE IN draw(canvas); method.
			Draw(canvas);
			
			// End of painting to canvas. system will paint with this canvas,to the surface.
			mSurfaceHolder.unlockCanvasAndPost(canvas);
		}
	}
	/**This method deals with paint-works. Also will paint something in background*/
	private void Draw(Canvas canvas) {
		// paint a background color
		canvas.drawColor(Color.BLACK);
		
		// paint a rectangular shape that fill the surface.
		int border = 20;
		RectF r = new RectF(border, border, canvas.getWidth()-20, canvas.getHeight()-20);
		Paint paint = new Paint();		
		paint.setARGB(200, 200, 15, 1); //paint color GRAY+SEMY TRANSPARENT 
		canvas.drawRect(r , paint );
		
		//canvas.drawCircle(canvas.getWidth()/4*3, canvas.getHeight()/2, radiusWhite, paint);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Log.i("onDraw","drawingxx");
		canvas.drawColor(Color.WHITE);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Log.i("surfaceChanged","width="+Integer.toString(width)+",height="+Integer.toString(height) );
		 Canvas c = getHolder().lockCanvas();
			int border = 20;
			RectF r = new RectF(border, border, c.getWidth()-20, c.getHeight()-20);
			Paint paint = new Paint();		
			paint.setARGB(200, 1, 135, 1); //paint color GRAY+SEMY TRANSPARENT 
			c.drawRect(r , paint );
		    getHolder().unlockCanvasAndPost(c);	 
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		 mIsRunning=true;
		 Log.i("surfaceCreated","mIsRunning");
	       //SurfaceHolder holder = getHolder();
	        Canvas canvas = holder.lockCanvas();

	        holder.unlockCanvasAndPost(canvas);

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	
}
