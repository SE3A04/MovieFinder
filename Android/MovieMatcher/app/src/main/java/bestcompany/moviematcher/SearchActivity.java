package bestcompany.moviematcher;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Result;

public class SearchActivity extends AppCompatActivity {

    private static final String SERVER_ADDRESS = "13.82.49.1";
    private Protocol protocol = null;


    String names= "";

    //Declaring all the designs to be implemented throughout the class
    Button searchButton;
    TextView titleText;
    TextView titleDescriptionText;
    TextView castText;
    TextView quotesText;
    TextView genreText;
    EditText castEditText;
    EditText quotesEditText;
    EditText genreEditText;

    String castNames = "";
    String quoteNames = "";
    String genreName = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        importcontent();

        contentImplementation();


//        Log.v("This",x[1]);
   //     Log.v("This",x[2]);
    }

    /*
    Method that imports, finds and assigns all the layout buttons,texts,textviews...
     */
    public void importcontent()
    {
        //Finding the designs in the layout to add the functionality to.
        searchButton = (Button) findViewById(R.id.SearchButton);
        titleText = (TextView) findViewById(R.id.TitleTextView);
        titleDescriptionText = (TextView) findViewById(R.id.TitleDescription);
        castText = (TextView) findViewById(R.id.CastTextView);
        castEditText = (EditText) findViewById(R.id.CastEditText);
        genreText = (TextView) findViewById(R.id.GenreTextView);
        genreEditText = (EditText) findViewById(R.id.GenreEditText);
        quotesText = (TextView) findViewById(R.id.QuotesTextView);
        quotesEditText = (EditText) findViewById(R.id.QuotesEditText);
    }


    public void contentImplementation()
    {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Retrieving the information from the editTexts
                castNames = castEditText.getText().toString();
                genreName = genreEditText.getText().toString();
                quoteNames = quotesEditText.getText().toString();

                new ConnectTask().execute();



            }
        });
    }


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
            map.put("E1", castNames);
            map.put("E2", genreName);
            map.put("E3", quoteNames);

            try {
                data = protocol.securePostRequest("MovieMatcherServer/serverControl.php", true, map);
                Log.v("Here","meow");
                Log.v("Here:", data);
            } catch (Exception e) {
                Log.v("Here",e.toString());
            }

            names = data;
            return data;

        }

        @Override
        protected void onPostExecute(String data) {
            if (data.length() > 0) {
                String [] x = names.split("%");
                Log.v("This",x.length+"");
                Log.v("This",x[0]);
                Log.v("This",x[1]);
                Log.v("This",x[2]);

                Log.v("This",data);
                Intent intent = new Intent(SearchActivity.this, ResultsActivity.class);
                intent.putExtra("Name1",x[0]);
                intent.putExtra("Name2",x[1]);
                intent.putExtra("Name3",x[2]);
                startActivity(intent);

                }
                // add markers here

             else {
                // failed to get locations
            }
        }

    }


}
