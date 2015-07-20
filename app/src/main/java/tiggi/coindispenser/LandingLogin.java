package tiggi.coindispenser;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* To do
1) Tidy up
2) Integrate non activity classes with backend
2) Ensure any requests that get no data OR timeout to backend have default values to avoid errors
3) Display error for above
 */

public class LandingLogin extends ActionBarActivity {

    // Member variables
    private Authenticator mLetMeIn = new Authenticator();
    private static final String TAG = LandingLogin.class.getSimpleName();
    private Button mLoginButton;
    private EditText mUserName;
    private EditText mPassWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_login);

        mLoginButton = (Button) findViewById(R.id.loginButton);
        mUserName = (EditText) findViewById(R.id.username);
        mPassWord = (EditText) findViewById(R.id.password);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call class to authenticate user, throw exception if empty
                String user = mUserName.getText().toString();
                String pass = mPassWord.getText().toString();

                if(user.equals("") || pass.equals("")) {
                    Toast.makeText(getApplicationContext(),"You have not entered all necessary credentials, please try again...",Toast.LENGTH_SHORT).show();
                    mUserName.setText("");
                    mPassWord.setText("");
                }

                else {
                    //Authenticator response = (Authenticator) new Authenticator().execute(user, pass);
                    Authenticator response = new Authenticator();
                    Log.d(TAG, "Starting auth thread...");
                    response.execute(user, pass);

                    try {
                        response.get(10000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        Log.e(TAG, String.valueOf(e));
                    }

                    Log.d(TAG, "Async task is currently >> " + String.valueOf(response.getStatus()));

                    Log.d(TAG, "Checking auth boolean >> " + response.yayOrNay);

                    if(response.yayOrNay) {
                        Toast.makeText(LandingLogin.this, "Congrats! Going through.", Toast.LENGTH_SHORT).show();
                        startBill();
                    }
                    else {
                        Toast.makeText(LandingLogin.this, "Askies, wrong credentials.", Toast.LENGTH_SHORT).show();
                        mUserName.setText("");
                        mPassWord.setText("");
                    }
                }
            }
        });
    }

    private void startBill() {
        Intent billIntent = new Intent(this, BillActivity.class);
        startActivity(billIntent);
    }
}
