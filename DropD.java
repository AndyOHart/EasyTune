package com.example.easytune;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import martin.andy.easytunetuner.R;

public class DropD extends Tune {
	int[] pitches = { 38, 45, 50, 55, 59, 64 };

	public void TuneGui() {
		setContentView(R.layout.drop_d);
		string6 = (Button) findViewById(R.id.string6);
		string6.setOnClickListener(this);
		string5 = (Button) findViewById(R.id.string5);
		string5.setOnClickListener(this);
		string4 = (Button) findViewById(R.id.string4);
		string4.setOnClickListener(this);
		string3 = (Button) findViewById(R.id.string3);
		string3.setOnClickListener(this);
		string2 = (Button) findViewById(R.id.string2);
		string2.setOnClickListener(this);
		string1 = (Button) findViewById(R.id.string1);
		string1.setOnClickListener(this);
		pitchLabel = (TextView) findViewById(R.id.pitch_label);
		currentPitch = (TextView) findViewById(R.id.current_pitch);
		pitchNum = (TextView) findViewById(R.id.pitch_num);
		changeTuning = (Button) findViewById(R.id.changeTuning);
		changeTuning.setOnClickListener(this);
		onButtonClick(0, 38);
	}

	@Override
	public void onClick(View v) {
		selectTuningOnClick(v, pitches);
	}
}
