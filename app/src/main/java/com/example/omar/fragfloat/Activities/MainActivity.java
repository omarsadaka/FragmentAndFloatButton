package com.example.omar.fragfloat.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omar.fragfloat.Fragment.CakeFragment;
import com.example.omar.fragfloat.Fragment.CartFragment;
import com.example.omar.fragfloat.Fragment.MenuFragment;
import com.example.omar.fragfloat.Fragment.MoreFragment;
import com.example.omar.fragfloat.Fragment.OfferFragment;
import com.example.omar.fragfloat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout menu, cart, cake, offer, more ,menuDrawer , cartDrawer , cakeDrawer , offerDrawer , moreDrawer;
    private TextView menuTitle, cartTitle, cakeTitle, offerTitle, moreTitle;
    private ImageView back, add, search;
    public EditText search_view;
    private TextView titleToolbar;
    private AlertDialog alertDialog;
    private EditText name, email, password, job, phone;
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private ProgressDialog progressDialog;
    private DrawerLayout drawerLayout;
    private ImageView openDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createViews();
        createClicks();
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mReference = mDatabase.getReference().child("Users");

        startFragment(new MenuFragment());
        showClickedItem(menuTitle);
    }

    private void createViews() {
        menu = findViewById(R.id.menu);
        menuTitle = findViewById(R.id.menu_title);
        cart = findViewById(R.id.cart);
        cartTitle = findViewById(R.id.cart_title);
        cake = findViewById(R.id.cake);
        cakeTitle = findViewById(R.id.cake_title);
        offer = findViewById(R.id.offer);
        offerTitle = findViewById(R.id.offer_title);
        more = findViewById(R.id.more);
        moreTitle = findViewById(R.id.more_title);
        back = findViewById(R.id.back);
        search = findViewById(R.id.search);
        search_view = findViewById(R.id.search_view);
        titleToolbar = findViewById(R.id.title_toolbar);
        add = findViewById(R.id.add);
        drawerLayout = findViewById(R.id.deawer_layout);
        openDrawer = findViewById(R.id.openDrawer);
        menuDrawer = findViewById(R.id.menuDrawer);
        cartDrawer = findViewById(R.id.cartDrawer);
        cakeDrawer = findViewById(R.id.cakeDrawer);
        offerDrawer = findViewById(R.id.offerDrawer);
        moreDrawer = findViewById(R.id.moreDrawer);
    }

    private void createClicks() {
        menu.setOnClickListener(this);
        cart.setOnClickListener(this);
        cake.setOnClickListener(this);
        offer.setOnClickListener(this);
        more.setOnClickListener(this);
        back.setOnClickListener(this);
        search.setOnClickListener(this);
        add.setOnClickListener(this);
        openDrawer.setOnClickListener(this);
        menuDrawer.setOnClickListener(this);
        cartDrawer.setOnClickListener(this);
        cakeDrawer.setOnClickListener(this);
        offerDrawer.setOnClickListener(this);
        moreDrawer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.menu:
                searchVisibility();
                showClickedItem(menuTitle);
                startFragment(new MenuFragment());
                break;

            case R.id.cart:
                searchVisibility();
                add.setVisibility(View.VISIBLE);
                showClickedItem(cartTitle);
                startFragment(new CartFragment());
                break;

            case R.id.cake:
                searchVisibility();
                showClickedItem(cakeTitle);
                startFragment(new CakeFragment());
                break;

            case R.id.offer:
                //searchVisibility();
                specialVisibility();
                showClickedItem(offerTitle);
                startFragment(new OfferFragment());
                break;

            case R.id.more:
                //searchVisibility();
                specialVisibility();
                showClickedItem(moreTitle);
                startFragment(new MoreFragment());
                break;

            case R.id.add:
                createPopupDialog();
                break;

            case R.id.back:
                searchVisibility();
                break;

            case R.id.search:
                add.setVisibility(View.GONE);
                titleToolbar.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                openDrawer.setVisibility(View.GONE);
                search_view.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                break;
            case R.id.openDrawer:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menuDrawer:
                drawerLayout.closeDrawer(GravityCompat.START);
                searchVisibility();
                showClickedItem(menuTitle);
                startFragment(new MenuFragment());
                break;
            case R.id.cartDrawer:
                drawerLayout.closeDrawer(GravityCompat.START);
                searchVisibility();
                showClickedItem(cartTitle);
                startFragment(new CartFragment());
                break;
            case R.id.cakeDrawer:
                drawerLayout.closeDrawer(GravityCompat.START);
                searchVisibility();
                showClickedItem(cakeTitle);
                startFragment(new CakeFragment());
                break;
            case R.id.offerDrawer:
                drawerLayout.closeDrawer(GravityCompat.START);
                searchVisibility();
                showClickedItem(offerTitle);
                startFragment(new OfferFragment());
                break;
            case R.id.moreDrawer:
                drawerLayout.closeDrawer(GravityCompat.START);
                searchVisibility();
                showClickedItem(moreTitle);
                startFragment(new MoreFragment());
                break;

        }
    }

    private void createPopupDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        View view = getLayoutInflater().inflate(R.layout.popup, null);
        View view = View.inflate(this,R.layout.popup, null);
        name = view.findViewById(R.id.nameET);
        email = view.findViewById(R.id.emailET);
        password = view.findViewById(R.id.passwordET);
        job = view.findViewById(R.id.jobET);
        phone = view.findViewById(R.id.phoneET);
        Button button = view.findViewById(R.id.saveBtn);
        dialogBuilder.setView(view);
        alertDialog = dialogBuilder.create();
        alertDialog.show();

        button.setOnClickListener((v) -> {
            check();
            createNewAccount();
        });

    }

    private void createNewAccount() {
        final String Name = name.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();
        final String Job = job.getText().toString().trim();
        final String Phone = phone.getText().toString().trim();

        if (Password.length() >= 6) {
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(MainActivity.this,
                    task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Account Created successfully", Toast.LENGTH_LONG).show();
                            if (mAuth.getCurrentUser() != null) {
                                String userID = mAuth.getCurrentUser().getUid();
                                DatabaseReference currentUserId = mReference.child(userID);
                                currentUserId.child("UserName").setValue(Name);
                                currentUserId.child("Job").setValue(Job);
                                currentUserId.child("Phone").setValue(Phone);
                                currentUserId.child("Id").setValue(userID);
                            }
                            progressDialog.dismiss();
                            alertDialog.dismiss();
                        } else if (!task.isSuccessful()) {
                            Log.e("Failed", String.valueOf(task.getException()));
                            Toast.makeText(MainActivity.this, "Failed To Create Account" + task.getException(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();

                        }

                    });
        } else {
            Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
        }


    }

    public void check() {
        String UserName = name.getText().toString();
        String UserEmail = email.getText().toString();
        String UserPassword = password.getText().toString();
        String UserJob = job.getText().toString();
        String UserPhone = phone.getText().toString();

        if (TextUtils.isEmpty(UserName)) {
            name.setError("Can't Be Empty");
        } else if (TextUtils.isEmpty(UserEmail)) {
            email.setError("Can't Be Empty");
        } else if (TextUtils.isEmpty(UserPassword)) {
            password.setError("Can't Be Empty");
        } else if (TextUtils.isEmpty(UserJob)) {
            job.setError("Can't Be Empty");
        } else if (TextUtils.isEmpty(UserPhone)) {
            phone.setError("Can't Be Empty");
        }

    }

    private void searchVisibility() {
        search_view.setVisibility(View.GONE);
        search_view.setText("");
        back.setVisibility(View.GONE);
        search.setVisibility(View.VISIBLE);
        titleToolbar.setVisibility(View.VISIBLE);
        openDrawer.setVisibility(View.VISIBLE);
    }
    private void specialVisibility(){
        search_view.setVisibility(View.GONE);
        search_view.setText("");
        back.setVisibility(View.GONE);
        add.setVisibility(View.GONE);
        search.setVisibility(View.GONE);
        titleToolbar.setVisibility(View.VISIBLE);
        openDrawer.setVisibility(View.VISIBLE);
    }

    private void startFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    private void showClickedItem(TextView clickedItem) {
        menuTitle.setVisibility(View.GONE);
        cartTitle.setVisibility(View.GONE);
        cakeTitle.setVisibility(View.GONE);
        offerTitle.setVisibility(View.GONE);
        moreTitle.setVisibility(View.GONE);
        clickedItem.setVisibility(View.VISIBLE);
    }
}
