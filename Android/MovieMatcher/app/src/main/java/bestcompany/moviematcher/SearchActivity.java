package bestcompany.moviematcher;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SearchActivity extends AppCompatActivity {

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        importcontent();


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



}
