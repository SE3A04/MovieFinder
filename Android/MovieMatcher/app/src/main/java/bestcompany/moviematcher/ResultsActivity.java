package bestcompany.moviematcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ResultsActivity extends AppCompatActivity {

    ListView resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        importcontent();
    }


    public void importcontent()
    {
        resultList= (ListView) findViewById(R.id.resultList);
        Button next = (Button) findViewById(R.id.resultNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(ResultsActivity.this,MapsActivity.class);
                startActivity(x);
            }
        });
    }

}
