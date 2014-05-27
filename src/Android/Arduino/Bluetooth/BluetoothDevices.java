package Android.Arduino.Bluetooth;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class BluetoothDevices extends ListActivity {

	List<String> blueDevices;
	BluetoothAdapter bluetoothAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.bluetoothdevices);
    	Log.i("bluetooth","onCreate1");
	
		//setContentView(R.layout.bluetoothdevices);
    	Log.i("bluetooth","onCreate2");
		
		bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
		
    	blueDevices = new ArrayList<String>();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
    	Log.i("bluetooth","onCreate3");

        if( pairedDevices.size()>0 )
        {
	        for(BluetoothDevice device : pairedDevices)
	        {
	        	blueDevices.add(device.getName());
	        	//PopulateBluetoothNames(device.getName());
	        }
	    	Log.i("bluetooth","onCreate4");

			setListAdapter(new ArrayAdapter<String>(BluetoothDevices.this, 
					android.R.layout.simple_expandable_list_item_1,
					blueDevices));
	    	Log.i("bluetooth","onCreate5");

        }
        else
        {
        	//finish();
        }
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		String deviceName = blueDevices.toArray()[position].toString();

		//GlobalBTDeviceNames.deviceName = deviceName;
		
		Intent intent = new Intent(getApplicationContext()/*BluetoothDevices.this*/, BluetoothTest.class );
		
		intent.putExtra("deviceName", deviceName);
		//startActivityForResult(intent, BluetoothTest.sDEVICE_FOUND);
		//startActivity(intent);
		//intent.
		
		Log.i("bluetooth","onCreate5:"+deviceName);
		setResult(RESULT_OK,intent); 
		finish();
	}
}
