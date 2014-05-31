package Android.Arduino.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;  
import android.widget.Button;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothTest extends Activity
{
	final public static int sENABLE_BLUETOOTH=0x22;
	final public static int sDEVICE_FOUND=0x33;
	
    TextView myLabel;
    EditText myTextbox;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    ButtonSurfaceA mButtonSurface;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button openButton = (Button)findViewById(R.id.open);
        Button sendButton = (Button)findViewById(R.id.send);
        Button closeButton = (Button)findViewById(R.id.close);
        Button quitAppButton = (Button)findViewById(R.id.quitapp);
        
        myLabel = (TextView)findViewById(R.id.label);
        myTextbox = (EditText)findViewById(R.id.entry);
        
        mButtonSurface = ( ButtonSurfaceA)findViewById(R.id.surfaceView1 );
        
        //Open Button
        openButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
            	Log.i("setOnClickListener","clicked");
               findBT();
            }
        });
        
        //Send Button
        sendButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try 
                {
                    sendData();
                }
                catch (IOException ex) { }
            }
        });
        
        //Close button
        closeButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try 
                {
                    closeBT();
                }
                catch (IOException ex) { }
            }
        });
        
        quitAppButton.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		Log.i("BluetoothTest","onActivityResult:"+ String.valueOf(resultCode)+", "+String.valueOf(resultCode) );

		if(requestCode==sENABLE_BLUETOOTH && resultCode==RESULT_OK)
		{
	        if(mBluetoothAdapter.isEnabled())
	        {
	        	Intent intent = new Intent(this, BluetoothDevices.class);
	        	startActivityForResult(intent, sDEVICE_FOUND);
	        }
		}
		else if( requestCode==sDEVICE_FOUND && resultCode==RESULT_OK)
		{
			String deviceName = data.getStringExtra("deviceName");
			
	        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
	        for(BluetoothDevice device : pairedDevices)
	        {
	        	if(device.getName().equals(deviceName))
	        	{
	        		//blueDevices.add(device.getName());
	        		mmDevice = device;
	        		break;
	        	}
	        }
	        
            try 
            {
				openBT();
		        myLabel.setText("Bluetooth Device Found:"+deviceName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		        myLabel.setText("Bluetooth Not open");
			}
		}
	}
    
    
    void findBT()
    {
    	Log.i("findBT","in findBT");
    	mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	Log.i("findBT","bt adapter set");

        if(mBluetoothAdapter == null)
        {
        	Log.i("findBT","null");
            myLabel.setText("No bluetooth adapter available");
        }
        else
        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, sENABLE_BLUETOOTH);
        }
        else
        {
        	Intent intent = new Intent(this, BluetoothDevices.class);
        	startActivityForResult(intent, sDEVICE_FOUND);
        	Log.i("findBT","doIntent");
        }
       
    }
    
    void openBT() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);   
        Log.i("openBT", "after createRf");

        mmSocket.connect();
        Log.i("openBT", "after connect");
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();
        
        beginListenForData();
        
        //myLabel.setText("Bluetooth Opened");
    }
    
    void beginListenForData()
    {
        final Handler handler = new Handler(); 
        final byte delimiter = 10; //This is the ASCII code for a newline character
        
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {                
               while(!Thread.currentThread().isInterrupted() && !stopWorker)
               {
                    try 
                    {
                        int bytesAvailable = mmInputStream.available();                        
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    
                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            myLabel.setText(data);
                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } 
                    catch (IOException ex) 
                    {
                        stopWorker = true;
                    }
               }
            }
        });

        workerThread.start();
    }
    
    void sendData() throws IOException
    {
        String msg = myTextbox.getText().toString();
        msg += "\r";
        mmOutputStream.write(msg.getBytes());
        myLabel.setText("Data Sent");
    }
    
    void closeBT() throws IOException
    {
        stopWorker = true;
        if(mmOutputStream!=null)
        {
        	mmOutputStream.close();
        }
        if(mmInputStream!=null)
        {
        	mmInputStream.close();
        }
        if(mmSocket!=null)
        {
        	mmSocket.close();
        }
        myLabel.setText("Bluetooth Closed");
    }
}
