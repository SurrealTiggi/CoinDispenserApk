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

public class Payment extends AsyncTask<String, Void, Void> {

    private static final String TAG = Payment.class.getSimpleName();
    BufferedReader in = null;
    String changeAsStr;

    @Override
    protected Void doInBackground(String... args) {
        String bill = args[0];
        String payment = args[1];

        try {

            HttpClient httpClient = new DefaultHttpClient();

            HttpGet request = new HttpGet();
            URI website = new URI("http://surrealtiggi.asuscomm.com:4324/CoinDispenser/rest/payment/" + bill + "&" + payment);
            Log.d(TAG, "Going to attempt to fetch the following >> " + website);
            request.setURI(website);
            HttpResponse response = httpClient.execute(request);
            in = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            String line = in.readLine();
            Log.d(TAG, "I got this from http >> " + line);
            setFinalChange(line);
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Error in Bill class " + e.toString());
            return null;
        }
    }

    public void setFinalChange(String change) {
        Log.d(TAG, "In setFinalChange() >> " + String.valueOf(change));
        changeAsStr = change.replace(",","\n");
    }
}
