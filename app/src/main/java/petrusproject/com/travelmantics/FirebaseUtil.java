package petrusproject.com.travelmantics;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {

    public static FirebaseDatabase theDatabase;
    public static DatabaseReference theReferance;
    public static FirebaseAuth theAuth;
    private static final int RC_SIGN_IN = 123;
    private static Activity caller;
    public static FirebaseAuth.AuthStateListener theAuthListener;
    private static FirebaseUtil firebaseUtil;
    public static ArrayList<TravelDeal> dealArrayList;

    private FirebaseUtil(){}

    public static void openFbReferance(String ref, final Activity callerActivity) {
        if(firebaseUtil == null){
            firebaseUtil = new FirebaseUtil();
            theDatabase = FirebaseDatabase.getInstance();
            caller = callerActivity;
            theAuth = FirebaseAuth.getInstance();
            theAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUtil.signIn();
                    Toast.makeText(callerActivity.getBaseContext(),"Welcome to TravelDeals", Toast.LENGTH_LONG).show();
                    return;
                }
            };

        }
        dealArrayList = new ArrayList<TravelDeal>();
        theReferance = theDatabase.getReference().child(ref);

    }

    public static void signIn(){
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());


// Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    public static void attachListener(){
        theAuth.addAuthStateListener(theAuthListener);
    }

    public static void detacchListener(){
        theAuth.removeAuthStateListener(theAuthListener);
    }
}
