package Android.Arduino.Bluetooth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ButtonSurfaceViewPalette extends SurfaceView implements SurfaceHolder.Callback {

    volatile boolean mTouched = false;
    volatile float mTouched_x, mTouched_y;
	
	public ButtonSurfaceViewPalette(Context context){
		super(context);
		Init();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		RectF r = new RectF(0, 0, canvas.getWidth()-20, canvas.getHeight()-20);
		Paint paint = new Paint();
		paint.setARGB(255, 10, 15, 200); //paint color GRAY+SEMY TRANSPARENT
		canvas.drawRect(r , paint );
		
		this.refreshDrawableState();
	}

	public ButtonSurfaceViewPalette(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init();
	}
	
	public ButtonSurfaceViewPalette(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //getHolder().addCallback(this);
        // TODO Auto-generated constructor stub
        Init();
    }
	
	private void Init()
	{
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		   mTouched_x = event.getX();
		   mTouched_y = event.getY();
		    
		   int action = event.getAction();
		   switch(action)
		   {
		   	case MotionEvent.ACTION_DOWN:
		   		mTouched = true;
		    break;
		   	case MotionEvent.ACTION_MOVE:
			   mTouched = true;
		    break;
		   	case MotionEvent.ACTION_UP:
			   mTouched = false;
		    break;
		   	case MotionEvent.ACTION_CANCEL:
			   mTouched = false;
		    break;
		   	case MotionEvent.ACTION_OUTSIDE:
			   mTouched = false;
		    break;
		    default:
		   }
		return super.onTouchEvent(event);
	}

	private void Draw(SurfaceHolder surfaceHolder)
	{
		//Log.i("running","thread");
		/** Start editing pixels in this surface.*/
		Canvas canvas = surfaceHolder.lockCanvas();
		
		RectF r = new RectF(0, 0, canvas.getWidth()-20, canvas.getHeight()-20);
		Paint paint = new Paint();
		paint.setARGB(255, 10, 15, 200); //paint color GRAY+SEMY TRANSPARENT
		canvas.drawRect(r , paint );
		
		// End of painting to canvas. system will paint with this canvas,to the surface.
		surfaceHolder.unlockCanvasAndPost(canvas);
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Draw(holder);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Draw(holder);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}
