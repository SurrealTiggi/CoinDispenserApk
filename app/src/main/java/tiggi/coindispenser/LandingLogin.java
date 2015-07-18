package tiggi.coindispenser;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                int counter = 0;
                if(user.equals("") || pass.equals("")) {
                    Toast.makeText(getApplicationContext(),"You have not entered any credentials, please try again...",Toast.LENGTH_SHORT).show();
                    counter++;
                } else if (mLetMeIn.auth(user, pass)) {
                    Toast.makeText(getApplicationContext(), "Congrats! Going through.", Toast.LENGTH_SHORT).show();
                    counter = 0;
                    startBill();
                } else {
                    Toast.makeText(LandingLogin.this, "Askies, wrong credentials.", Toast.LENGTH_SHORT).show();
                    // Clear text fields;
                    counter++;
                }
            }
        });
    }

    private void startBill() {
        Intent billIntent = new Intent(this, BillActivity.class);
        startActivity(billIntent);
    }
}
