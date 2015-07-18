package tiggi.coindispenser;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class BillActivity extends ActionBarActivity {

    private TextView mBillView;
    private Button mPaymentButton;
    private EditText mPayment;
    private Bill mBill = new Bill();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        mPaymentButton = (Button) findViewById(R.id.okButton);
        mBillView = (TextView) findViewById(R.id.billTextView);
        mPayment = (EditText) findViewById(R.id.payment);
        final String finalBill = mBill.getMyBill();

        mBillView.setText(finalBill);

        mPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paid = mPayment.getText().toString();
                /* If exceptions, send to new intent if conditions are satisfied
                1) Is payment empty
                2) Is payment too small
                Extra credit) Work out on the fly how much more to add in???
                 */

                // If conditions are all met
                startPayment(finalBill, paid);
            }
        });
    }

    private void startPayment(String bill, String paid) {
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(getString(R.string.key_bill), bill);
        intent.putExtra(getString(R.string.key_payment), paid);
        startActivity(intent);
    }
}
