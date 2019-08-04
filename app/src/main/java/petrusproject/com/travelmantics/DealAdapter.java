package petrusproject.com.travelmantics;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.dealsViewHolder> {

    ArrayList<TravelDeal> theDealsList;
    private FirebaseDatabase myDataBase;
    private DatabaseReference theRef;
    private ChildEventListener theListener;

    public DealAdapter(){

        myDataBase = FirebaseUtil.theDatabase;
        theDealsList = FirebaseUtil.dealArrayList;
        theRef = FirebaseUtil.theReferance;

        theListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                TravelDeal theDeal = dataSnapshot.getValue(TravelDeal.class);
                Log.d("Deal: ", theDeal.getTitle());
                theDeal.setId(dataSnapshot.getKey());
                theDealsList.add(theDeal);
                notifyItemInserted(theDealsList.size()-1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        theRef.addChildEventListener(theListener);
    }

    @Override
    public dealsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.rv_row,viewGroup,false);
        return new dealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(dealsViewHolder dealsViewHolder, int i) {
        TravelDeal deal = theDealsList.get(i);
        dealsViewHolder.bind(deal);

    }

    @Override
    public int getItemCount() {
        return theDealsList.size();
    }

    public class dealsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tTitle, tDescription, tPrice;
        public dealsViewHolder(View itemView) {
            super(itemView);
            tTitle = itemView.findViewById(R.id.dealTitle);
            tDescription = itemView.findViewById(R.id.dealDescription);
            tPrice = itemView.findViewById(R.id.dealPrice);
            itemView.setOnClickListener(this);
        }

        public void bind(TravelDeal deal){
            tTitle.setText(deal.getTitle());
            tDescription.setText(deal.getDescription());
            tPrice.setText(deal.getPrice());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("Clicked",String.valueOf(position));
            TravelDeal selectedDeal = theDealsList.get(position);

            Intent intent = new Intent(view.getContext(), DealActivity.class);
            intent.putExtra("Deal", selectedDeal);
            view.getContext().startActivity(intent);
        }
    }
}
