package com.example.easytune;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import martin.andy.easytunetuner.R;

public class MainActivity extends Activity {
	
	Button PlayNote;
	Button SelectTuning;
	Button AutoTune;
	Button d;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		PlayNote = (Button)findViewById(R.id.PlayNote);
	    
		 PlayNote.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openPlayNote = new Intent(MainActivity.this, PlayNote.class);
					startActivity(openPlayNote);
			}
		});
		 
		 SelectTuning = (Button)findViewById(R.id.SelectTuning);
		    
		 SelectTuning.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openSelectTuning = new Intent(MainActivity.this, StandardTune.class);
					startActivity(openSelectTuning);	
			}	
		});
		 
		 AutoTune = (Button)findViewById(R.id.AutoTune);
		    
		 AutoTune.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openAutoTune = new Intent(MainActivity.this, AutoTune.class);
					startActivity(openAutoTune);	
			}	
		});	 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
