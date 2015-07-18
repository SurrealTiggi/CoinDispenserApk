package tiggi.coindispenser;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


public class PaymentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        String bill = intent.getStringExtra(getString(R.string.key_bill));
        String payment = intent.getStringExtra(getString(R.string.key_payment));
    }
}