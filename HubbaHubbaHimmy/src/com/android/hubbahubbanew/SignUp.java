package com.android.hubbahubbanew;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class SignUp extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        //create back button
        Button backButton = (Button)findViewById(R.id.backButton);
        Button logInButton = (Button)findViewById(R.id.SignUpButton);
        		
        //back button goes back to the main page
        backButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(SignUp.this, MainActivity.class);
        		startActivity(intent);
        	}

        });
        
        //login Button takes you to the map view
        logInButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        		Intent intent = new Intent(SignUp.this, ListViewHubba.class);
        		startActivity(intent);
        	}
        });
    }

}
