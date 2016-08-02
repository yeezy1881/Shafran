package kz.qstore.shafran;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public ArrayList<String> x;

   public ArrayList<Integer>shoppingCart;

    int a[];
    MenuItem cartItem;
    int badgeCount;

    int checked;

    Activity act;
    Cart cart;

    Button login;

    SharedPreferences userInfo;
    SharedPreferences.Editor userInfoEditor;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        act = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);
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


        userInfo = this.getSharedPreferences("userInfo", MODE_PRIVATE);

        String userId = userInfo.getString("userId", null);
        if(userId == null){
            Log.d(" ", "User id not found, logging in ");
            VKSdk.login(this, "5565431", "email", "phone");
            //
        }
        else{
            Log.d("HAVE USER ALREADY: ", userId);

        }


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

    public void onLogin(View v){
        VKSdk.login(act,"5565431");
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


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.d("asdf", "asdf");
        ActionItemBadge.update(cartItem, cart.getTotalQuantity());
        Log.d("LOG", ""+cart.getTotalQuantity());
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        updateCartIcon();
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
// Пользователь успешно авторизовался
                Log.d("USER ID", VKSdk.getAccessToken().userId);

                String url = "https://api.vk.com/method/users.get?user_ids="+VKSdk.getAccessToken().userId;

// Request a string response
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String response) {

                                // Result handling
                                System.out.println(response);

                                try{


                                    JSONObject jsonObject = new JSONObject(response);

                                    Log.d("JSON", "1");

                                JSONArray jsonArray = jsonObject.getJSONArray("response");
                                    Log.d("JSON", "2");


                                    String userId = VKSdk.getAccessToken().userId;
                                Log.d("USER EMAIL", VKSdk.getAccessToken().email);
                                    Log.d("USER ID", userId);
                                    SharedPreferences.Editor userInfoEditor = userInfo.edit();
                                    userInfoEditor.putString("userId", userId);
                                    userInfoEditor.putString("email", VKSdk.getAccessToken().email);
                                    userInfoEditor.putString("name", jsonArray.getJSONObject(0).optString("first_name"));
                                    userInfoEditor.putString("surname", jsonArray.getJSONObject(0).optString("last_name"));
                                    userInfoEditor.putString("vk_id", VKSdk.getAccessToken().userId);
                                    userInfoEditor.apply();
                                }
                                    catch (JSONException e){
                                        Log.d("JSON WHEN LOGGING IN", e.getLocalizedMessage());
                                    }




                                String reg_url = "http://qstore.kz/register";
                                StringRequest postRequest = new StringRequest(Request.Method.POST, reg_url,
                                        new Response.Listener<String>()
                                        {
                                            @Override
                                            public void onResponse(String response) {
                                                // response
                                                Log.d("Response", response);

                                                SharedPreferences.Editor userInfoEditor = userInfo.edit();
                                                userInfoEditor.putString("token", response);



                                            }
                                        },
                                        new Response.ErrorListener()
                                        {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // error
                                               Log.d("Error.Response", "ERROR");
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams()
                                    {
                                        Map<String, String>  params = new HashMap<String, String>();

                                        params.put("name", userInfo.getString("name", "NAME"));
                                        params.put("surname", userInfo.getString("surname", "SURNAME"));
                                        params.put("email", userInfo.getString("email", "EMAIL"));
                                        params.put("vk_id", userInfo.getString("vk_id", null));

                                        return params;
                                    }
                                };
                                queue.add(postRequest);

                                    /*JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray;
                                    jsonArray = jsonObject.getJSONArray("response");
                                    TextView text = (TextView) findViewById(R.id.user_name);
                                    text.setText(jsonArray.getJSONObject(0).getString("first_name") + " " + jsonArray.getJSONObject(0).getString("last_name"));
                                }
                                catch (JSONException e){
                                    Log.d("JSON", e.getLocalizedMessage());*/


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Error handling
                        System.out.println("Something went wrong!");
                        error.printStackTrace();

                    }
                });

// Add the request to the queue
                Volley.newRequestQueue(getBaseContext()).add(stringRequest);

            }
            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
