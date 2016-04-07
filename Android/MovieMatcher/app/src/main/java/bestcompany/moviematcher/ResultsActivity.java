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

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    ListView resultList;
    String name1 = "";
    String name2 = "";
    String name3 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        name1 = intent.getStringExtra("Name1");
        name2 = intent.getStringExtra("Name2");
        name3 = intent.getStringExtra("Name3");

        importcontent();
    }


    public void importcontent()
    {
        resultList= (ListView) findViewById(R.id.resultList);
        ArrayList<String> x = new ArrayList<>();
        x.add(name1);
        x.add(name2);
        x.add(name3);
        ListViewAdapter adapter = new ListViewAdapter(x,this);
        resultList.setAdapter(adapter);
    }



}
