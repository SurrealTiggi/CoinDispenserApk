package tiggi.coindispenser;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class BillActivity extends ActionBarActivity {

    private static final String TAG = BillActivity.class.getSimpleName();
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

        mBill.execute();
        try {
            mBill.get(10000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            Log.e(TAG, String.valueOf(e));
        }

        final String finalBill = mBill.billAsStr;

        mBillView.setText("R" + finalBill);

        mPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paid = mPayment.getText().toString();

                try {
                    Double paidAsDbl = Double.parseDouble(paid);
                    Double billAsDbl = Double.parseDouble(finalBill);

                    if(paidAsDbl < billAsDbl) {
                        Toast.makeText(BillActivity.this, "You haven't entered enough Randelas...", Toast.LENGTH_SHORT).show();
                        mPayment.setText("");
                    }

                    else if (paidAsDbl >= billAsDbl) {
                        Toast.makeText(BillActivity.this, "Thanks, calculating your change...", Toast.LENGTH_SHORT).show();
                        startPayment(finalBill, paid);
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(BillActivity.this, "Can't let you do that...", Toast.LENGTH_SHORT).show();
                }
                /*
                Extra credit) Work out on the fly how much more to add in???
                 */
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
