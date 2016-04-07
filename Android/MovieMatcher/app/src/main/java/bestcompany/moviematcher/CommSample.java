package bestcompany.moviematcher;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommSample extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String SERVER_ADDRESS = "13.82.49.1";

    private Protocol protocol = null;

    private TextView statusText;
    private TextView selectionText;
    private Spinner employeeSelection;
    private ArrayAdapter<String> empAdapter;
    private Button dataButton;
    private TextView dataText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm_sample);

        statusText = (TextView)findViewById(R.id.statusText);
        selectionText = (TextView)findViewById(R.id.selectionText);
        employeeSelection = (Spinner)findViewById(R.id.employeeSelection);
        empAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        empAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSelection.setAdapter(empAdapter);
        employeeSelection.setOnItemSelectedListener(this);
        dataButton = (Button)findViewById(R.id.dataButton);
        dataButton.setOnClickListener(this);
        dataText = (TextView)findViewById(R.id.dataText);

        new ConnectTask().execute();

        Button nextButton = (Button) findViewById(R.id.nextActivityButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommSample.this, SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comm_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (parent.getId() == R.id.employeeSelection) {
            dataButton.setEnabled(true);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (parent.getId() == R.id.employeeSelection) {
            dataButton.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dataButton) {
            dataButton.setEnabled(false);
            new GetDataTask().execute(employeeSelection.getSelectedItem().toString());
        }
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
                statusText.setText("Status: Connected to server");
            } else {
                statusText.setText("Status: Failed to connect");
            }

            new GetEmployeesTask().execute();
        }
    }

    private class GetEmployeesTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            ArrayList<String> emps = new ArrayList<>();

            try {
                Thread.sleep(5000);

                String resp = protocol.securePostRequest("commsample/getEmployees.php", true);
                if (resp.length() > 0) {
                    emps.addAll(Arrays.asList(resp.split(",")));
                }
            } catch (Exception e) {}

            return emps;
        }

        @Override
        protected void onPostExecute(ArrayList<String> emps) {
            if (emps.size() > 0) {
                statusText.setText("Status: Retrieved employees");

                empAdapter.addAll(emps);
                empAdapter.notifyDataSetChanged();

                selectionText.setVisibility(View.VISIBLE);
                employeeSelection.setVisibility(View.VISIBLE);
            } else {
                statusText.setText("Status: Failed to get employees");
            }
        }
    }

    private class GetDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String data = "";
            Map<String, String> map = new HashMap<>();
            map.put("ID", params[0]);

            try {
                data = protocol.securePostRequest("commsample/getData.php", true, map);
            } catch (Exception e) {}

            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            if (data.length() > 0) {
                statusText.setText("Status: Retrieved data");

                dataText.setText("Data: " + data);
            } else {
                statusText.setText("Status: Failed to get data");
            }
            dataButton.setEnabled(true);
        }
    }
}
