package kz.qstore.shafran;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {

    Intent data;

    int badgeCount;

    MenuItem cartItem;

    foodItem food;

    Cart cart;

    int id;

    String json;
    List<Ingredient> ingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final AppBarLayout appBar = (AppBarLayout) findViewById(R.id.app_bar);

        int width = getWindowManager().getDefaultDisplay().getWidth();
        appBar.setLayoutParams(new CoordinatorLayout.LayoutParams(width, width));

        cart = CartHelper.getCart();

        CollapsingToolbarLayout ct = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
       // ct.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));




        badgeCount = cart.getTotalQuantity();
        //TextView title = (TextView) toolbar.findViewById(R.id.title);


        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);



        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusBarColor));
        }
        Intent intent = getIntent();

        Log.d("SADF", badgeCount+"");
        ActionItemBadge.update(cartItem, badgeCount);
        id = intent.getIntExtra("ID", 999999);

        json = intent.getStringExtra("JSON");


    parseJSON();


        TextView descrText = (TextView) findViewById(R.id.descrText);
        descrText.setText(food.getDescription());



Log.d("JSON", json);

       RecyclerView rv = (RecyclerView) findViewById(R.id.ingrView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setAdapter(new IngrAdapter(ingList));
        //title.setText(food.getName());

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/BebasNeue.ttf");
        //title.setTypeface(tf);
        //title.setTextSize(24);

        //appBar.setBackgroundResource(food.getItemImage());


        TextView title = (TextView) findViewById(R.id.title_text);
        //title.setTypeface(tf);
        ct.setTitle(food.getName());
        ct.setCollapsedTitleTypeface(tf);

        TextView descr = (TextView) findViewById(R.id.descr);

        ct.setExpandedTitleTypeface(tf);
        Picasso.with(this).load(food.getItemImage()).error(R.drawable.placeholder).placeholder(R.drawable.placeholder).into(new Target(){

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                appBar.setBackground(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        });


        getSupportActionBar().setTitle("");





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setImageResource(R.drawable.color);
        fab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "1 блюдо добавлено в корзину", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                cart.add(food, 1);
                badgeCount = cart.getTotalQuantity();
                ActionItemBadge.update(cartItem, cart.getTotalQuantity());

                Log.d("ITEMactivity", ""+cart.getTotalQuantity());
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        cartItem = menu.findItem(R.id.cart);
            ActionItemBadge.update(this, menu.findItem(R.id.cart),getResources().getDrawable(R.drawable.ic_shopping_cart_white_24dp), ActionItemBadge.BadgeStyles.RED, badgeCount);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            setResult(5, data);
            finish();
            return true;
        }

        if (id == R.id.cart) {
            Intent intent = new Intent(this, CartActivity.class);
            startActivityForResult(intent, 200);
            ActionItemBadge.update(cartItem, cart.getTotalQuantity());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showItem(int id){
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    void parseJSON(){
        try {
            JSONArray arr = new JSONArray(json);
            JSONObject obj = arr.getJSONObject(id);
            food = new foodItem();
            food.setItemImage(obj.getString("img"));
            food.setItemName(obj.getString("name"));
            food.setDescription(obj.getString("description"));
            food.setPrice(new BigDecimal(obj.getInt("price")));
            food.setIngredients(obj.getString("ingredients"));
            JSONArray ing = new JSONArray(obj.getString("ingredients"));
            ingList = new ArrayList<>();
            for(int i = 0; i < ing.length(); i++){
                JSONObject o = ing.getJSONObject(i);
                Ingredient in = new Ingredient(o.getString("name"));
                ingList.add(in);
            }
            Log.d("ATTENTION", "ALL OK");
        }
        catch (JSONException e){
            Log.d("JSON", e.getLocalizedMessage());
        }
    }


    class IngrAdapter extends RecyclerView.Adapter<IngrAdapter.IngrItemHolder>{
        private List<Ingredient> ingrList;
        public IngrAdapter(List<Ingredient> ingrList){
            this.ingrList = ingrList;
        }

        @Override
        public int getItemCount(){
            return ingrList.size();
        }

        @Override
        public void onBindViewHolder(IngrItemHolder ingrItemHolder, int i){
            Ingredient f = ingrList.get(i);
            ingrItemHolder.textView.setText(f.getName());
            /*if(i == ingrList.size()-1){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ingrItemHolder.ll.getLayoutParams();
                params.setMargins(0, 0, 0, 10);
                ingrItemHolder.ll.setLayoutParams(params);
            }*/
        }

        @Override
        public IngrItemHolder onCreateViewHolder(ViewGroup viewGroup, int i){
            View itemView = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.ingredient_view, viewGroup, false);
            return new IngrItemHolder(itemView);
        }


        public class IngrItemHolder extends RecyclerView.ViewHolder {
            protected TextView textView;
            protected LinearLayout ll;
            public IngrItemHolder(View v){
                super(v);
                ll = (LinearLayout) v.findViewById(R.id.ingr_ll);
                textView = (TextView) v.findViewById(R.id.ingr_name);
            }
        }
    }


    class Ingredient {
        String name;

        Ingredient(String name){
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
