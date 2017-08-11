package com.example.admin.zoo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter adapter;
    private DatabaseHelper databaseHelper;
    private List<Zoo> zoolist;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        title = (TextView)findViewById(R.id.total);

        databaseHelper = new DatabaseHelper(this);
        zoolist = new ArrayList<>();
        reloadingDatabase(); //loading table of DB to ListView
    }

    public void reloadingDatabase() {
        zoolist = databaseHelper.getAllZOOs();
        if (zoolist.size() == 0) {
            Toast.makeText(this, "No record found in database!", Toast.LENGTH_SHORT).show();
            title.setVisibility(View.GONE);
        }
        adapter = new ListViewAdapter(this, R.layout.item_listview, zoolist, databaseHelper);
        listView.setAdapter(adapter);
        title.setVisibility(View.VISIBLE);
        title.setText("Total records: " + databaseHelper.getContactsCount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            addingNewFriendDialog();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addingNewFriendDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Add a new Animal");

        LinearLayout layout = new LinearLayout(this);
        layout.setPadding(10, 10, 10, 10);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText nameBox = new EditText(this);
        nameBox.setHint("Name");
        layout.addView(nameBox);

        final EditText locationBox = new EditText(this);
        locationBox.setHint("Location");
        layout.addView(locationBox);

        final EditText sexBox = new EditText(this);
        sexBox.setHint("SEX");
        layout.addView(sexBox);

        final EditText dietBox = new EditText(this);
        dietBox.setHint("Diet");
        layout.addView(dietBox);




        alertDialog.setView(layout);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Zoo zoo = new Zoo(getText(nameBox), getText(locationBox),getText(sexBox), getText(dietBox));
                databaseHelper.addNewZOO(zoo);

                reloadingDatabase(); //reload the db to view
            }
        });

        alertDialog.setNegativeButton("Cancel", null);

        //show alert
        alertDialog.show();
    }

    //get text available in TextView/EditText
    private String getText(TextView textView) {
        return textView.getText().toString().trim();
    }
}
