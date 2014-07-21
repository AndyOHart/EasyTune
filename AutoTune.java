package com.example.easytune;



import martin.andy.easytunetuner.R;
import org.puredata.core.PdListener;

import android.view.View;
import android.widget.TextView;

public class AutoTune extends Tune {
	
	/*
	 * Different pitch values and also an array of string names to compare
	 */
	private int [] lowNotes =  {31,32,33,34,35,36,37,38,39,40,41,42};
	private int [] bassNotes = {43,44,45,46,47,48,49,50,51,52,53,54};
	private int [] middleNotes = {55,56,57,58,59,60,61,62,63,64,65,66};
	private int [] trebleNotes = {67,68,69,70,71,72,73,74,75,76,77,78};
	private int [] highNotes = {79,80,81,82,83,84,85,86,87,88,89,90};
	private String [] noteNames = {"G ","G# ", "A ", "A# ", "B ", "C ", "C# ", "D ", "D# ", "E ", "F ", "F# "};
	private double Bound = 0.5;

	/*
	 * Initiliazes the UI
	 */
	@Override
	public void TuneGui() {
		setContentView(R.layout.auto_tune);
		pitchLabel = (TextView) findViewById(R.id.pitch_label);
		currentPitch = (TextView) findViewById(R.id.current_pitch);
		pitchNum = (TextView) findViewById(R.id.pitch_num);	
	}
	/*
	 * Goes through the array, checks the bounds, finds closest note
	 * and prints the current note along with its
	 * pitch and prints your current pitch
	 */
	
	private void pitchNotes(int [] octave, float x)
	{
		for(int i = 0; i < octave.length; i++)
		{
			if(x >octave[i]-Bound && x <=octave[i]+Bound)
			{
				 pitchLabel.setText(noteNames[i]);
				 pitchNum.setText(""+ octave[i]);
				 getTune(octave[i]);//returns the pitch to be tuned too
				 formatCurrentPitch(x);
				 inTune(x,tunePitch);
			}
		}
	}
	
	
	@Override
	public void onClick(View v) {
		//This method never gets called.
	}

	/*
	 * Finds out what array of notes to go through
	 */
	private void whatNote(float x) {
		
		if(x >lowNotes[0]-Bound && x <=lowNotes[lowNotes.length-1]+Bound)
		{
			pitchNotes(lowNotes, x);
		}
		else
			if(x >bassNotes[0]-Bound && x <=bassNotes[bassNotes.length-1]+Bound)
			{
				pitchNotes(bassNotes, x);
			}
			else
				if(x >middleNotes[0]-Bound && x <=middleNotes[middleNotes.length-1]+Bound)
				{
					pitchNotes(middleNotes, x);
				}
				else
					if(x >trebleNotes[0]-Bound && x <=trebleNotes[trebleNotes.length-1]+Bound)
					{
						pitchNotes(trebleNotes, x);
					}
					else
						if(x >highNotes[0]-Bound && x <=highNotes[highNotes.length-1]+Bound)
						{
							pitchNotes(highNotes, x);
						}	
	}

	@Override
	public void dispatcherListener() {
		myDispatcher.addListener("freq", new PdListener.Adapter() {
			public void receiveFloat(String source, final float x) {
				whatNote(x);
				
			}
		});
	}
}
