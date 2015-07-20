package tiggi.coindispenser;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class Authenticator extends AsyncTask<String, Void, Void> {

    private static final String TAG = Authenticator.class.getSimpleName();
    BufferedReader in = null;
    String data = null;
    boolean yayOrNay;
    //String line;

    @Override
    protected Void doInBackground(String... creds) {

        try {

            String user = creds[0];
            String pass = creds[1];
            HttpClient httpClient = new DefaultHttpClient();

            HttpGet request = new HttpGet();
            URI website = new URI("http://surrealtiggi.asuscomm.com:4324/CoinDispenser/rest/auth/" + user + "&" + pass);
            Log.d(TAG, "Going to attempt to fetch the following >> " + website);
            request.setURI(website);
            HttpResponse response = httpClient.execute(request);
            in = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            String line = in.readLine();
            Log.d(TAG, "I got this from http >> " + line);
            setFinalAuth(line);

            return null;
        } catch (Exception e) {
            Log.e(TAG, "Error in Auth class " + e.toString());
            return null;
        }
    }

    /*protected void onProgressUpdate(Integer... progress) {
        setProgressPercent(progress[0]);
    }*/

    /*@Override
    protected void onPostExecute(String... result) {
        Log.d(TAG, String.valueOf(result));
        //setFinalAuth(result);
    }*/

    public void setFinalAuth(String result) {
        Log.d(TAG, "In setFinalAuth() >> " + String.valueOf(result));
        if(result.equals("1")) {
            yayOrNay = true;
        } else {
            yayOrNay = false;
        }
    }
}
