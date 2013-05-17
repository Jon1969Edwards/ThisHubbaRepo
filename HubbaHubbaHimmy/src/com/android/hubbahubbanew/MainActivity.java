package com.android.hubbahubbanew;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        Button logInButton = (Button)findViewById(R.id.logInButton);
        Button signUpButton = (Button)findViewById(R.id.signUpButton);
        Button connectButton = (Button)findViewById(R.id.connectButton);
        Button viewMapButton = (Button)findViewById(R.id.viewMapButton);
        
        
        
        //log in button opens up logIn activity
        logInButton.setOnClickListener(new View.OnClickListener() {      
        	public void onClick(View view) {
        		Intent intent = new Intent(MainActivity.this, LogIn.class);
        		startActivity(intent);
        	}

        });
        
        //sign up button opens up logIn activity
        signUpButton.setOnClickListener(new View.OnClickListener() {      
        public void onClick(View view) {
           Intent intent = new Intent(MainActivity.this, SignUp.class);
           startActivity(intent);
        }

        });
        
        
        //connect button just connects to facebook for now, will change later
        connectButton.setOnClickListener(new View.OnClickListener() {      
        public void onClick(View view) {
        	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"));
        	startActivity(browserIntent);
        }

        });
        
      //View Map button opens up logIn activity
        viewMapButton.setOnClickListener(new View.OnClickListener() {      
        public void onClick(View view) {
           Intent intent = new Intent(MainActivity.this, ActionBarActivity.class);
           startActivity(intent);
        }

        });
        
        
    }
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
