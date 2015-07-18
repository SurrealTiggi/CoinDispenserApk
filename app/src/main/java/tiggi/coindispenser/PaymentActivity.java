package tiggi.coindispenser;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends ActionBarActivity {

    private Payment mPayment = new Payment();
    private TextView mBreakdown;
    private Button mExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mExit = (Button) findViewById(R.id.exitButton);
        mBreakdown = (TextView) findViewById(R.id.changeTextView);

        Intent intent = getIntent();
        String bill = intent.getStringExtra(getString(R.string.key_bill));
        String payment = intent.getStringExtra(getString(R.string.key_payment));
        String msg = "Your bill is: " + bill + "\nYour payment is: " + payment;
        Toast.makeText(PaymentActivity.this, msg, Toast.LENGTH_SHORT).show();

        // Pull denominations breakdown from backend
        final String change = mPayment.getMyChange(bill, payment);

        mBreakdown.setText(change);

        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToStart();
            }
        });
    }

    private void backToStart() {
        Intent intent = new Intent(this, LandingLogin.class);
        startActivity(intent);
    }
}