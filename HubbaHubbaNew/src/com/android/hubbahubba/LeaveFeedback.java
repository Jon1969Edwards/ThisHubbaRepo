package com.android.hubbahubba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LeaveFeedback extends Activity {
	
	EditText username;
	EditText subject;
	EditText messageBody;
	Button cancelButton;
	Button submitButton;
	
	String usernameText;
	String subjectText;
	String messageBodyText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leave_feedback);
		
		username = (EditText) findViewById(R.id.username);
		subject = (EditText) findViewById(R.id.subject);
		messageBody = (EditText) findViewById(R.id.messageBody);
		cancelButton = (Button) findViewById(R.id.cancelButton);
		submitButton = (Button) findViewById(R.id.submitButton);
		
		// cancel form
		cancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});
		
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				usernameText = username.getText().toString();
				subjectText = subject.getText().toString();
				messageBodyText = messageBody.getText().toString();
				
				if(usernameText.equals("") || messageBodyText.equals("")){
					Toast.makeText(getApplicationContext(), "Please fill out required fields", Toast.LENGTH_LONG).show();
				}
				else{
					Intent i = new Intent(Intent.ACTION_SEND);
					i.setType("message/rfc822");
					
					// TODO: NOT TO ME!
					i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"robsmall@umich.edu"});
					i.putExtra(Intent.EXTRA_SUBJECT, "Hubba Hubba Feedback From " + usernameText + ": " + subjectText);
					i.putExtra(Intent.EXTRA_TEXT   , messageBodyText);
					try {
                        // use bogus request code
					    startActivityForResult(Intent.createChooser(i, "Send mail..."), 0);
					} catch (android.content.ActivityNotFoundException ex) {
					    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}

    // Quit activity when coming back from mail reguardless
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}
