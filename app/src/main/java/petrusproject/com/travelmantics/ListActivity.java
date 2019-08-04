package petrusproject.com.travelmantics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {

    ArrayList<TravelDeal> theDealsList;
    private FirebaseDatabase myDataBase;
    private DatabaseReference theRef;
    private ChildEventListener theListener;
    private RecyclerView theRecycleView;
    private DealAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.insert_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.insert:
                Intent intent = new Intent(this, DealActivity.class);
                startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUtil.detacchListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.openFbReferance("traveldeals",this);
        theRecycleView = findViewById(R.id.rvDeals);
        adapter = new DealAdapter();
        theRecycleView.setAdapter(adapter);

        LinearLayoutManager dealsLinerLayoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        theRecycleView.setLayoutManager(dealsLinerLayoutManager);

        FirebaseUtil.attachListener();
    }
}
