package com.example.easytune;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import martin.andy.easytunetuner.R;

public class SelectTuning extends Activity {
	
	Button StandardTune;
	Button DropD;
	Button DropC;
	Button OpenA;
	Button OpenB;
	Button OpenC;
	Button OpenD;
	Button OpenE;
	Button OpenF;
	Button OpenG;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_tuning);
		
		
		StandardTune = (Button)findViewById(R.id.StandardTuning);
		StandardTune.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openStandardTune = new Intent(SelectTuning.this, StandardTune.class);
					startActivity(openStandardTune);
			}
		});
		
		DropD = (Button)findViewById(R.id.DropD);
		DropD.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openDropD = new Intent(SelectTuning.this, DropD.class);
					startActivity(openDropD);
			}
		});
		
		DropC= (Button)findViewById(R.id.DropC);
		DropC.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openDropC = new Intent(SelectTuning.this, DropC.class);
					startActivity(openDropC);
			}
		});
		
		OpenA= (Button)findViewById(R.id.OpenA);
		OpenA.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openOpenA = new Intent(SelectTuning.this, OpenA.class);
					startActivity(openOpenA);
			}
		});
		
		OpenB= (Button)findViewById(R.id.OpenB);
		OpenB.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openOpenB = new Intent(SelectTuning.this, OpenB.class);
					startActivity(openOpenB);
			}
		});
		
		OpenC= (Button)findViewById(R.id.OpenC);
		OpenC.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openOpenC = new Intent(SelectTuning.this, OpenC.class);
					startActivity(openOpenC);
			}
		});
		
		OpenD= (Button)findViewById(R.id.OpenD);
		OpenD.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openOpenD = new Intent(SelectTuning.this, OpenD.class);
					startActivity(openOpenD);
			}
		});
		
		OpenE= (Button)findViewById(R.id.OpenE);
		OpenE.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openOpenE = new Intent(SelectTuning.this, OpenE.class);
					startActivity(openOpenE);
			}
		});
		
		OpenF= (Button)findViewById(R.id.OpenF);
		OpenF.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openOpenF = new Intent(SelectTuning.this, OpenF.class);
					startActivity(openOpenF);
			}
		});
		
		OpenG= (Button)findViewById(R.id.OpenG);
		OpenG.setOnClickListener(new View.OnClickListener(){	
			public void onClick(View v){
					Intent openOpenG = new Intent(SelectTuning.this, OpenG.class);
					startActivity(openOpenG);
			}
		});
	}
}
