package com.mert.example.tr;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener
{

	private SensorManager sensManager;
	private Sensor Accelerometer;
	private Sensor Gyro;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialize variables here
		sensManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		List<Sensor> sensorenListe = sensManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if(sensorenListe.size() > 0)
		{
			Accelerometer = sensManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		else
		{
			setValue(R.id.acc_x);
			setValue(R.id.acc_y);
			setValue(R.id.acc_z);
		}
		
		sensorenListe = sensManager.getSensorList(Sensor.TYPE_GYROSCOPE);
		if(sensorenListe.size() > 0)
		{
			Gyro = sensManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		}
		else
		{
			setValue(R.id.gyro_x);
			setValue(R.id.gyro_y);
			setValue(R.id.gyro_z);
		}
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		// REGISTER SENSOR HERE DONT FORGOT TO UNREGISTER IT.
		// ACCELATOR
		sensManager.registerListener(MainActivity.this, Accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		// GYRO
		sensManager.registerListener(MainActivity.this, Gyro,
				SensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	protected void onPause()
	{
		super.onPause();
		// UNREGISTER SENSOR HERE
		sensManager.unregisterListener(this);

	}

	@Override
	protected void onResume()
	{
		// REGISTER SENSOR HERE DONT FORGOT TO UNREGISTER IT.
		// ACCELATOR
		sensManager.registerListener(MainActivity.this, Accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		// GYRO
		sensManager.registerListener(MainActivity.this, Gyro,
				SensorManager.SENSOR_DELAY_NORMAL);

		super.onResume();
	}

	@Override
	protected void onDestroy()
	{

		// UNREGISTER SENSOR HERE
		sensManager.unregisterListener(this);

		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{

	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		// you could check for sensor if you use more then one at the same time
		switch (event.sensor.getType())
		{
			case Sensor.TYPE_ACCELEROMETER:
				// DO ACC STUFF HERE
				
				setValue(R.id.acc_x, event.values[0]);
				setValue(R.id.acc_y, event.values[1]);
				setValue(R.id.acc_z, event.values[2]);
				
				Log.i("ACCELEROMETER", "X/Y/Z: " + event.values[0] + "/" + event.values[1] + "/" + event.values[2] + "/" );
				break;
			
			case Sensor.TYPE_GYROSCOPE:
				// DO GYRO STUFF HERE
				
				setValue(R.id.gyro_x, event.values[0]);
				setValue(R.id.gyro_y, event.values[1]);
				setValue(R.id.gyro_z, event.values[2]);
				
				Log.i("GYROSCOPE", "X/Y/Z: " + event.values[0] + "/" + event.values[1] + "/" + event.values[2] + "/" );
				break;
		}
	}

	
	private void setValue(int id)
	{
		TextView textView = (TextView) findViewById(id);
		textView.setText("N.A.");
	}
	
	private void setValue(int id, float value)
	{
		TextView textView = (TextView) findViewById(id);
		textView.setText("" + value);
	}
}
