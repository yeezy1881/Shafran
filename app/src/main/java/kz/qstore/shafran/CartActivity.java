package kz.qstore.shafran;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.model.Saleable;
import com.android.tonyvu.sc.util.CartHelper;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CartActivity extends AppCompatActivity {

    MenuItem cartItem;
    int badgeCount;
    int cnt = 0;
    Cart cart;
    RecyclerView rv;
    CartRVAdapter adapter;
    ArrayList foodItemList;
    TextView total;
    Button proceed;
    Activity act;

    static int pos[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cart = CartHelper.getCart();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    total = (TextView) findViewById(R.id.total);
        pos = new int[100500];
        getSupportActionBar().setTitle("Корзина");
        act = this;

    rv = (RecyclerView) findViewById(R.id.rv);

        Set<Saleable> s = cart.getProducts();
        foodItemList = new ArrayList();
        foodItemList.addAll(s);

        if(cart.getTotalQuantity() == 1){
            total.setText(1 + "товар");
        }
        else if(cart.getTotalQuantity() >= 2 && cart.getTotalQuantity() <= 4){
            total.setText(cart.getTotalQuantity() + " товара");
        }
        else{
            total.setText(cart.getTotalQuantity() + " товаров");
        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        Button sendBt = (Button) findViewById(R.id.send_bt);
        sendBt.setText(cart.getTotalPrice() + " тг");

        adapter = new CartRVAdapter(foodItemList);



        rv.setAdapter(adapter);


        proceed = (Button) findViewById(R.id.send_bt);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MaterialDialog dialog = new MaterialDialog.Builder(act)
                        .title("Оформить")
                        .customView(R.layout.dialog, true)
                        .positiveText("Оформить")
                        .negativeText("Отмена")
                        .autoDismiss(false)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback(){
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Log.d("POSITIVE", "PRESSED");
                                final EditText name, phone, street, house, flat;
                                name = (EditText) dialog.findViewById(R.id.name);
                                phone = (EditText) dialog.findViewById(R.id.phone);
                                street = (EditText) dialog.findViewById(R.id.street);
                                house = (EditText) dialog.findViewById(R.id.house);
                                flat = (EditText) dialog.findViewById(R.id.flat);
                                if (name.getText().toString().length() == 0 || phone.getText().toString().length() == 0 || street.getText().toString().length() == 0 || house.getText().toString().length() == 0) {
                                    Toast.makeText(dialog.getContext(), "Введите все данные", Toast.LENGTH_LONG).show();
                                } else {
                                    String url = "http://qstore.kz/postorder";
                                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    // response
                                                    Log.d("Response", response);
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // error
                                                    Log.d("Error.Response", "ERROR");
                                                }
                                            }
                                    ) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<String, String>();
                                            ArrayList<Saleable> list = new ArrayList<>();
                                            list.addAll(cart.getProducts());


                                            StringBuilder builder = new StringBuilder();
                                            for (int i = 0; i < list.size(); i++) {
                                                builder.append(list.get(i).getName());
                                                builder.append(" ");
                                                builder.append(cart.getQuantity(list.get(i)));
                                                builder.append("\n");
                                            }
                                            params.put("name", name.getText().toString());
                                            params.put("phone", phone.getText().toString());
                                            params.put("street", street.getText().toString());
                                            params.put("house", house.getText().toString());
                                            params.put("flat", flat.getText().toString());
                                            params.put("body", builder.toString());

                                            return params;
                                        }
                                    };
                                    Volley.newRequestQueue(act).add(postRequest);
                                    Toast.makeText(dialog.getContext(), "Ваш заказ принят", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                                cart.clear();
                                foodItemList.clear();
                                adapter.notifyDataSetChanged();
                                total.setText("0 товаров");
                                proceed.setText("0 тг");
                            }
                        })
                        .show();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);





            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        cartItem = menu.findItem(R.id.cart);
        ActionItemBadge.update(this, menu.findItem(R.id.cart), getResources().getDrawable(R.drawable.ic_shopping_cart_white_24dp), ActionItemBadge.BadgeStyles.RED, cart.getTotalQuantity());
        return true;
    }

    public void updateCartIcon() {
        ActionItemBadge.update(cartItem, cart.getTotalQuantity());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }


    public class CartRVAdapter extends RecyclerView.Adapter<CartRVAdapter.ItemViewHolder>{

        ArrayList<foodItem> foodArrayList;

        CartRVAdapter(ArrayList<foodItem> foodArrayList){
            this.foodArrayList = foodArrayList;
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder{
            CardView cv;
            ImageView foodImage;
            TextView foodName;
            Button plusBT, minusBT;
            TextView itemCount;
            TextView itemPrice, itemTotalPrice;
            ItemViewHolder(View itemView){
                super(itemView);
                cv = (CardView) itemView.findViewById(R.id.cv);
                foodImage = (ImageView) itemView.findViewById(R.id.foodImage);
                foodName = (TextView) itemView.findViewById(R.id.foodName);
                plusBT = (Button) itemView.findViewById(R.id.plusBT);
                minusBT = (Button) itemView.findViewById(R.id.minusBT);
                itemCount = (TextView) itemView.findViewById(R.id.itemCount);
                itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
                itemTotalPrice = (TextView) itemView.findViewById(R.id.itemTotalPrice);
            }
        }

        @Override
        public int getItemCount(){
            return foodArrayList.size();
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_view, viewGroup, false);
            ItemViewHolder ivh = new ItemViewHolder(v);
            return ivh;
        }

        @Override
        public void onBindViewHolder(ItemViewHolder itemViewHolder, int i){
            final int w = i;
            Cart cart = CartHelper.getCart();
            itemViewHolder.foodName.setText(foodArrayList.get(i).getName());
            itemViewHolder.itemPrice.setText(foodArrayList.get(i).getPrice()+"");
            itemViewHolder.itemTotalPrice.setText(foodArrayList.get(i).getPrice().intValue() * cart.getQuantity(foodArrayList.get(i)) + "");
            itemViewHolder.itemCount.setText(cart.getQuantity(foodArrayList.get(i))+"");
            Picasso.with(act)
                    .load(foodArrayList.get(i).getItemImage())
                    .into(itemViewHolder.foodImage);
            pos[i] = i;
            itemViewHolder.plusBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cart cart = CartHelper.getCart();
                    cart.add(foodArrayList.get(w), 1);
                    foodItemList.clear();
                    foodItemList.addAll(cart.getProducts());
                    adapter.notifyDataSetChanged();
                    ActionItemBadge.update(cartItem, cart.getTotalQuantity());
                    proceed.setText(cart.getTotalPrice()+" тг");
                    if(cart.getTotalQuantity() == 1){
                        total.setText(1 + "товар");
                    }
                    else if(cart.getTotalQuantity() >= 2 && cart.getTotalQuantity() <= 4){
                        total.setText(cart.getTotalQuantity() + " товара");
                    }
                    else{
                        total.setText(cart.getTotalQuantity() + " товаров");
                    }
                }
            });
            itemViewHolder.minusBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Cart cart = CartHelper.getCart();
                    int q = cart.getQuantity(foodArrayList.get(w));
                    cart.remove(foodArrayList.get(w), 1);

                    proceed.setText(cart.getTotalPrice()+" тг");

                    if(q > 1){
                        adapter.notifyItemChanged(w);
                    }
                    else{
                        adapter.notifyItemRemoved(w);
                        Log.d("ASDF", foodItemList.size()+"");
                        foodItemList.clear();
                        foodItemList.addAll(cart.getProducts());
                        notifyDataSetChanged();
                    }

                    if(cart.getTotalQuantity() == 1){
                        total.setText(1 + "товар");
                    }
                    else if(cart.getTotalQuantity() >= 2 && cart.getTotalQuantity() <= 4){
                        total.setText(cart.getTotalQuantity() + " товара");
                    }
                    else{
                        total.setText(cart.getTotalQuantity() + " товаров");
                    }

                    ActionItemBadge.update(cartItem, cart.getTotalQuantity());
                }
            });
        }


    }

}