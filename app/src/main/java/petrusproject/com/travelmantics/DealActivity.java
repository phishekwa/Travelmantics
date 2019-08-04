package petrusproject.com.travelmantics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DealActivity extends AppCompatActivity {

    private MenuInflater menuInflater;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    TravelDeal deal;

    EditText theTitle, theDescription, thePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUtil.openFbReferance("traveldeals", this);
        firebaseDatabase = FirebaseUtil.theDatabase;
        databaseReference = FirebaseUtil.theReferance;

        theTitle = findViewById(R.id.txtTitle);
        theDescription = findViewById(R.id.txtDescription);
        thePrice = findViewById(R.id.txtPrice);

        Intent intent = getIntent();
        TravelDeal deal = (TravelDeal) intent.getSerializableExtra("Deal");

        if(deal == null){
            deal = new TravelDeal();
        }
        this.deal = deal;
        theTitle.setText(deal.getTitle());
        thePrice.setText(deal.getPrice());
        theDescription.setText(deal.getDescription());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.save_menu:
                saveDeal();
                Toast.makeText(this, "Deal Saved", Toast.LENGTH_LONG).show();
                clean();
                backToList();
                return true;
            case R.id.delete_menu:
                deleteDeal();
                Toast.makeText(this,"Deal Deleted!", Toast.LENGTH_LONG).show();
                backToList();
                return true;
            default:
                 return super.onOptionsItemSelected(item);
        }

    }

    private void clean() {

        thePrice.setText("");
        theDescription.setText("");
        theTitle.setText("");
        theTitle.requestFocus();
    }

    private void saveDeal() {

        deal.setTitle(theTitle.getText().toString());
        deal.setPrice(thePrice.getText().toString());
        deal.setDescription(theDescription.getText().toString());

        if(deal.getId() == null){
            databaseReference.push().setValue(deal);
        }
        else{
            databaseReference.child(deal.getId()).setValue(deal);
        }


    }

    private void deleteDeal(){
        if(deal.getId() == null) {
            Toast.makeText(this, "Deal Doesn't exist", Toast.LENGTH_LONG).show();
            return;
        }
        else
            databaseReference.child(deal.getId()).removeValue();
    }

    private void backToList(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

}
