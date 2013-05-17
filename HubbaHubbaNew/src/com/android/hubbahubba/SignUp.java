package com.android.hubbahubba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class SignUp extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        //create buttons
        Button logInButton = (Button)findViewById(R.id.SignUpButton);
        		
        //login Button takes you to the map view
        logInButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        		Intent intent = new Intent(SignUp.this, ActionBarActivity.class);
        		startActivity(intent);
        	}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sign_up, menu);
        return true;
    }
}
