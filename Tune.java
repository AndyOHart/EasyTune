package com.example.easytune;

//Importing the Java libraries that are needed.
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

//Importing the puredata libraries that are needed.
import martin.andy.easytunetuner.R;
import org.puredata.android.io.AudioParameters;
import org.puredata.android.io.PdAudio;
import org.puredata.android.service.PdService;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.PdListener;
import org.puredata.core.utils.IoUtils;

//Importing the android libraries that are needed.
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public abstract class Tune extends Activity implements OnClickListener {

	/*
	 * All variables are declared.
	 * Buttons, Textviews and the PdUiDispatcher are all marked as 
	 * protected, because they are accessed across other Java files that
	 * extend the Tune class. 
	 * The File and pdService are marked as private, they are not needed within
	 * any other class.
	 */
	protected Button string1;
	protected Button string2;
	protected Button string3;
	protected Button string4;
	protected Button string5;
	protected Button string6;
	protected Button changeTuning;

	protected TextView pitchLabel;
	protected TextView currentPitch;
	protected TextView pitchNum;
	
	protected PdUiDispatcher myDispatcher;  
	
	protected int tunePitch;
	
	private File dir;
	private File patchFile;
	
	private Button [] selectTuningNotes;
	
	private PdService pdService;
	
	
	/*
	 * Jumps back to main page after hitting back.
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/*
	 * On click method to choose what tuning
	 */
	protected void selectTuningOnClick(View v, int [] pitch){
		changeToWhite();
		switch(v.getId()){
			case R.id.string6:
				onButtonClick(0,pitch [0]);
				break;
			case R.id.string5:
				onButtonClick(1, pitch[1]);
				break;
			case R.id.string4:
				onButtonClick(2, pitch[2]);
				break;
			case R.id.string3:
				onButtonClick(3, pitch[3]);
				break;
			case R.id.string2:
				onButtonClick(4, pitch[4]);
				break;
			case R.id.string1:
				onButtonClick(5, pitch[5]);
				break;
			case R.id.changeTuning:
				Intent openSelectTuning = new Intent(this, SelectTuning.class);
				startActivity(openSelectTuning);				
		}
	}
	
	
	/*
	 * onCreate method sets up full screen, the GUI and binds the service
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		fullScreen();
		TuneGui();
		Bind();
	}
	
	/*
	 * Cleans up before exiting the program
	 */
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();
		stop();	
	}
	
	/*
	 * Starts the audio thread again from a paused state
	 */
	@Override
	protected void onResume() 
	{
		super.onResume();
		PdAudio.startAudio(this);	
	}
	
	/*
	 * Stops the audio thread while the app is not running in the foreground
	 */
	@Override
	protected void onPause() 
	{
		super.onPause();
		PdAudio.stopAudio();	
	}
	/*
	 * Formats the float of the current midipitch 
	 * to two decimal places and displays the number on the
	 * screen with the Textview currentPitch.
	 */
	protected void formatCurrentPitch(float x)
	{
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		currentPitch.setText(""+ df.format(x));
	}
	

	/*
	 * Lets our app display in full screen. Eliminating the native andriod task bar
	 * while the app is open.
	 */
	
	private void fullScreen()
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	/*
	 * Changes the color of the selected note to orange and
	 * also displays its corrosponding midinote value.
	 */
	protected void onButtonClick(int stringNum, int midiNote){
		selectTuningNotes = new Button[]{string6,string5,string4,string3,string2,string1};
		selectTuningNotes[stringNum].setTextColor(getApplication().getResources().getColor(R.color.orange));
		pitchNum.setText("" + midiNote);
		getTune(midiNote);
	}
	
	/*
	 * Changes unselected button text to white
	 */
	protected void changeToWhite(){
		selectTuningNotes = new Button[]{string6,string5,string4,string3,string2,string1};
		for(Button i: selectTuningNotes){
			i.setTextColor(getApplication().getResources().getColor(R.color.white));
		}
	}	
	

	/*
	 * Sets up our application as a background service, this is needed to install the Pure Data
	 * external object, fiddle~. This fiddle object is based on the FFT algorithm, it is a pitch estimator 
	 * and will determine what pitch the current input sound is.
	 */
	private final ServiceConnection pdConnection = new ServiceConnection() 
	{
		
		//@Override
		public void onServiceConnected(ComponentName name, IBinder service) 
		{
			pdService = ((PdService.PdBinder)service).getService();
			initializePd(); //See below.
			loadPatch();	//See below
		}
		
		//@Override
		public void onServiceDisconnected(ComponentName name) 
		{
				Log.e("Error: ", "Pure Data Service connection failure");
		}	
	};
	
	/*
	 *This is were we initialize pure data.
	 *AudioParameters.suggestSampleRate gets the sample rates for our device.
	 *We then call pdService.initAudio(sampleRate, x, y, z);
	 *	sampleRate = given rate.
	 *  x = number of input channel
	 *  y = number of output channels
	 *  z= the desired sampleRate, will try to get as close as possible - we have 5ms
	 */
	private void  initializePd()
	{
		AudioParameters.init(this);
		int sampleRate = AudioParameters.suggestSampleRate();
		
		try 
		{
			pdService.initAudio(sampleRate, 1, 2, 5.0f);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		start(); //see below.

	}
	
	public int getTune(int x)
	{
		tunePitch = x;
		return tunePitch;
	}
	
	/*
	 * Sets up the dispatcher to receive messages.
	 */
	private void setUpDispatcher()
	{
		myDispatcher = new PdUiDispatcher();
		PdBase.setReceiver(myDispatcher);
		dispatcherListener();
		
	}

	/*
	 * Unsubscribes from PD messages and and releases all resources
	 * held by native bindings
	 */
	private void releaseDistpatcher()
	{
		myDispatcher.release();
		PdBase.release();
	}
	
	/*
	 * Loads our pureData patch, calls getPatch to find location and then opens the 
	 * patch using the PdBase library.
	 */
	private void loadPatch() {
		getPatch(); //See below.
		try 
		{
			PdBase.openPatch(patchFile.getAbsolutePath());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Finds the absolute path to the directory containing our pureData patch.
	 * If found it extracts the zip it is contained in and gets passed of the patchFile variable. 
	 * If not found or I/O exception, will be caught.
	 */
	private void getPatch()
	{
		dir = getFilesDir();
		try 
		{
			IoUtils.extractZipResource(getResources().openRawResource(R.raw.midituner), dir, true);
		} 
		catch (NotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		patchFile = new File(dir, "midituner.pd");
	}
	
	/*
	 * Starts message passing
	 */
	private void start() {
		pdService.startAudio();
		setUpDispatcher();
	}
	
	/*
	 * Cleans up, called in OnDestroy.
	 */
	private void stop()
	{
		pdService.stopAudio();
		releaseDistpatcher();
		unBind();
	}


	
	/*
	 * Unbinds the pdService
	 */
	private void unBind()
	{
		unbindService(pdConnection);
	}
	
	/*
	 * Binds the service
	 */
	private void Bind()
	{
		bindService(new Intent(this, PdService.class), pdConnection, BIND_AUTO_CREATE);
	}
	
	/*
	 * Registers a listener for Pure Data messages
	 * the frequency parameter interacts with the fiddle object within our patch.
	 * It then returns a float value of that note.
	 */
	public void dispatcherListener() 
	{
		myDispatcher.addListener("freq", new PdListener.Adapter() 
		{
			public void receiveFloat(String source, final float x) 
			{
				formatCurrentPitch(x);
				inTune(x,tunePitch);
			}
		});
	}
	
	/*
	 * Checks to see if the current pitch is in tune with the pitch
	 * you are tuning against.
	 */
	public void inTune(float x, int tunePitch)
	{
		if(x >= tunePitch - 0.1 && x <= tunePitch + 0.1)
		{
			currentPitch.setTextColor(getApplication().getResources().getColor(R.color.orange));
			currentPitch.setText("In Tune");
			
			
		}
		else
			currentPitch.setTextColor(getApplication().getResources().getColor(R.color.white));
	}
	
	/*
	 * Abstract method to initiate the GUI for each class extending this
	 */
	public abstract void TuneGui();
	
	/*
	 *Abstract method for each button click for each class extending this
	 */
	//@Override
	public abstract void onClick(View v);
}