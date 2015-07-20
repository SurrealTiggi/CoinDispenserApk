package tiggi.coindispenser;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class Bill extends AsyncTask<Void, Void, Void> {

    private static final String TAG = Bill.class.getSimpleName();
    BufferedReader in = null;
    String billAsStr;

    @Override
    protected Void doInBackground(Void... args) {

        try {

            HttpClient httpClient = new DefaultHttpClient();

            HttpGet request = new HttpGet();
            URI website = new URI("http://surrealtiggi.asuscomm.com:4324/CoinDispenser/rest/bill");
            Log.d(TAG, "Going to attempt to fetch the following >> " + website);
            request.setURI(website);
            HttpResponse response = httpClient.execute(request);
            in = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            String line = in.readLine();
            Log.d(TAG, "I got this from http >> " + line);
            setFinalBill(line);

            return null;
        } catch (Exception e) {
            Log.e(TAG, "Error in Bill class " + e.toString());
            return null;
        }
    }

    public void setFinalBill(String bill) {
        Log.d(TAG, "In setFinalBill() >> " + String.valueOf(bill));

        billAsStr = bill;
    }
}
