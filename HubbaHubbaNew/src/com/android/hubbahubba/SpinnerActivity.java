package com.android.hubbahubba;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerActivity extends Activity implements OnItemSelectedListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_location);
		
		Spinner spinner = (Spinner) findViewById(R.id.spotTypeSpinner);
	    spinner.setOnItemSelectedListener(this);
	    
	    ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
	    		R.array.spotTypes, android.R.layout.simple_spinner_item);
	    
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    // Apply the adapter to the spinner
	    spinner.setAdapter(dataAdapter);
	    
	}
	
    public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    	
    	Spinner spin = (Spinner) findViewById(R.id.spotTypeSpinner);
	    spin.setOnItemSelectedListener(this);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}