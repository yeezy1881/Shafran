package kz.qstore.shafran;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ArrayList<String> x;

   public ArrayList<Integer>shoppingCart;

    int a[];
    MenuItem cartItem;
    int badgeCount;

    int checked;

    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cart = CartHelper.getCart();

        getSupportActionBar().setElevation(0);


        badgeCount = cart.getTotalQuantity();
        shoppingCart = new ArrayList<>();
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment menu = new MenuFragment();
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
        fragmentTransaction.add(R.id.fragment_container, menu, "MENU");
        checked = 0;

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        cartItem = menu.findItem(R.id.cart);
        ActionItemBadge.update(this, menu.findItem(R.id.cart),getResources().getDrawable(R.drawable.ic_shopping_cart_white_24dp), ActionItemBadge.BadgeStyles.RED, badgeCount);
        return true;
    }

    public void updateCartIcon(){
        ActionItemBadge.update(cartItem, cart.getTotalQuantity());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cart) {
            Intent intent = new Intent(this, CartActivity.class);
            startActivityForResult(intent, 200);
            ActionItemBadge.update(cartItem, cart.getTotalQuantity());
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            if(checked != 0){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment menu = new MenuFragment();
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
            fragmentTransaction.replace(R.id.fragment_container, menu, "MENU");

            fragmentTransaction.commit();
            checked = 0;}

        } else if (id == R.id.nav_delivery) {
            if(checked != 1){
                checked = 1;
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = new DeliveryFragment();
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
            fragmentTransaction.replace(R.id.fragment_container, fragment , "DELIVERY");

            fragmentTransaction.commit();}
        } else if (id == R.id.nav_about) {
            if(checked != 2){
                checked = 2;
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = new AboutFragment();
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
            fragmentTransaction.replace(R.id.fragment_container, fragment , "ABOUT");

            fragmentTransaction.commit();}
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showItem(int id, String json){
            Intent intent = new Intent(this, ItemActivity.class);
            intent.putExtra("ID", id);
            intent.putExtra("JSON", json);
            //intent.putExtra("BACKGROUND", background);
            startActivityForResult(intent, 5);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d("asdf", "asdf");
        ActionItemBadge.update(cartItem, cart.getTotalQuantity());
        Log.d("LOG", ""+cart.getTotalQuantity());
    }


}
