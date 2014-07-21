package com.example.easytune;

import martin.andy.easytunetuner.R;
import org.puredata.core.PdBase;
import android.view.View;
import android.widget.Button;

public class PlayNote extends Tune {
	private Button a, aSharp, b, c, cSharp, d, dSharp, e, f, fSharp, g, gSharp;
	private Button[] allButtons;
	//int [] imgIds = {R.id.a_button,R.id.aSharp_button,R.id.b_button,R.id.c_button,
	//R.id.cSharp_button,R.id.d_button,R.id.dSharp_button,R.id.e_button,
	//R.id.f_button,R.id.fSharp_button,R.id.g_button,R.id.gSharp_button};
	
	
	//This method goes through button array and resets them all to white
	public void changeToWhite() {
		allButtons = new Button[] { a, aSharp, b, c, cSharp, d, dSharp, e, f,
				fSharp, g, gSharp };
		for (Button i : allButtons) {
			i.setTextColor(getApplication().getResources().getColor(
					R.color.white));
		}
	}

	// This method takes an integer as a parameter which represents the note to
	// trigger
	// Using puredata, it will send the int as a midinote, and then send a
	// trigger
	// This method is used to make the notes play upon being pushed
	private void triggerNote(int n) {
		PdBase.sendFloat("midisound", n);
		PdBase.sendBang("playmidi");
	}
	
	
	//Takes button and int for id
	//Sets text to orange and then triggers the note
	public void currentButton(Button x, int n){
		x.setTextColor(getApplication().getResources().getColor(R.color.orange));
		triggerNote(n);
	}
	
	//Handles what happens when each button is clicked
	//Gets ID then resets all buttons, and sets the pressed button to orange
	public void onClick(View v) {
		changeToWhite();
		switch(v.getId()){
			case R.id.a_button:
				currentButton(a,81);
				break;
			case R.id.aSharp_button:
				currentButton(aSharp, 82);
				break;
			case R.id.b_button:
				currentButton(b, 83);
				break;
			case R.id.c_button:
				currentButton(c, 72);
				break;
			case R.id.cSharp_button:
				currentButton(cSharp, 73);
				break;
			case R.id.d_button:
				currentButton(d, 74);
				break;
			case R.id.dSharp_button:
				currentButton(dSharp, 75);
				break;
			case R.id.e_button:
				currentButton(e, 76);
				break;
			case R.id.f_button:
				currentButton(f, 77);
				break;
			case R.id.fSharp_button:
				currentButton(fSharp, 78);
				break;
			case R.id.g_button:
				currentButton(g, 79);
				break;
			case R.id.gSharp_button:
				currentButton(gSharp, 80);
				break;
		}
	}

	// This method is used to initiate the GUI
	// Firstly it gets the xml file for PlayNote
	// Then it sets each button and its corrosponding onClickListener
	// This allows for each button triggering a different midinote
	@Override
	public void TuneGui() {
		setContentView(R.layout.play_note);

		a = (Button) findViewById(R.id.a_button);
		a.setOnClickListener(this);

		aSharp = (Button) findViewById(R.id.aSharp_button);
		aSharp.setOnClickListener(this);

		b = (Button) findViewById(R.id.b_button);
		b.setOnClickListener(this);

		c = (Button) findViewById(R.id.c_button);
		c.setOnClickListener(this);

		cSharp = (Button) findViewById(R.id.cSharp_button);
		cSharp.setOnClickListener(this);

		d = (Button) findViewById(R.id.d_button);
		d.setOnClickListener(this);

		dSharp = (Button) findViewById(R.id.dSharp_button);
		dSharp.setOnClickListener(this);

		e = (Button) findViewById(R.id.e_button);
		e.setOnClickListener(this);

		f = (Button) findViewById(R.id.f_button);
		f.setOnClickListener(this);

		fSharp = (Button) findViewById(R.id.fSharp_button);
		fSharp.setOnClickListener(this);

		g = (Button) findViewById(R.id.g_button);
		g.setOnClickListener(this);

		gSharp = (Button) findViewById(R.id.gSharp_button);
		gSharp.setOnClickListener(this);
	}

	@Override
	public void dispatcherListener() {
	}
}