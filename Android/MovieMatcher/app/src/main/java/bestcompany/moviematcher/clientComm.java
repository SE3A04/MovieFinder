package bestcompany.moviematcher;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class clientComm extends AppCompatActivity {
    private static final String SERVER_ADDRESS = "13.82.49.1";

    private Protocol protocol = null;

    private class ConnectTask extends AsyncTask<Void, Void, Protocol> {

        @Override
        protected Protocol doInBackground(Void... params) {
            Protocol p = null;
            try {
                InputStream caCert = getAssets().open("ca.crt");
                p = new Protocol(caCert, SERVER_ADDRESS);
            } catch (Exception e) {}

            return p;
        }

        @Override
        protected void onPostExecute(Protocol p) {
            if (p != null) {
                protocol = p;
            }

            new GetMoviesTask().execute();
        }
    }

    private class GetMoviesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String data = "";
            Map<String, String> map = new HashMap<>();
            map.put("E1", params[0]);
            map.put("E2", params[1]);
            map.put("E3", params[2]);

            try {
                data = protocol.securePostRequest("MovieMatcherServer/serverControl.php", true, map);
            } catch (Exception e) {}

            return data;
        }

    }

}
