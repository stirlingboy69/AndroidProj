package Android.Arduino.Bluetooth;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ButtonSurfaceA extends SurfaceView implements Runnable {

	SurfaceHolder mSurfaceHolder;
	Thread mRunningThread;
	boolean mIsRunning=true;
	
	public ButtonSurfaceA(Context context){
		super(context);
		mSurfaceHolder = getHolder();
		mRunningThread = new Thread(this);
		mRunningThread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(mIsRunning){
			if(!mSurfaceHolder.getSurface().isValid())
				continue;
			
			
		}
		
		
	}

}
