package kz.qstore.shafran;


import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    private RecyclerView RV1, RV2, RV3, RV4;
    private MenuAdapter adapter1, adapter2, adapter3, adapter4;
    private LinearLayoutManager llm, llm2, llm3, llm4;
    private TextView t1, t2, t3, t4;

    List<foodItem> foodList, foodList2, foodList3, foodList4;

    String JSONfirst, JSONsecond, JSONsalads, JSONdrinks;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        t1 = (TextView)  view.findViewById(R.id.firstTextView);
        t2 = (TextView)  view.findViewById(R.id.secondTextView);
        t3 = (TextView)  view.findViewById(R.id.thirdTextView);
        t4 = (TextView)  view.findViewById(R.id.fourthTextView);


        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");

        t1.setTypeface(tf);
        t2.setTypeface(tf);
        t3.setTypeface(tf);
        t4.setTypeface(tf);

        final String url_first = "http://qstore.kz/first";
        final String url_second = "http://qstore.kz/second";
        final String url_salads = "http://qstore.kz/salads";
        final String url_drinks = "http://qstore.kz/drinks";


        try {
            new AsyncHttpTask().execute(url_first, url_second, url_salads, url_drinks);
        }
        catch (Exception e){
            Log.d("HGF", e.getLocalizedMessage());
        }


        RV1 = (RecyclerView) view.findViewById(R.id.cardList);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        RV1.setLayoutManager(llm);
        foodList = new ArrayList<>();


        //adapter1 = new MenuAdapter(foodList);

        RV1.setAdapter(adapter1);
        /*foodList.add(new foodItem("Блюдо 1", "http://192.168.0.101/1.jpg", 1, new BigDecimal(1000)));
        foodList.add(new foodItem("Блюдо 2", "http://192.168.0.101/1.jpg", 2, new BigDecimal(1000)));*/
        RV2 = (RecyclerView) view.findViewById(R.id.cardList2);
        llm2 = new LinearLayoutManager(getActivity());
        llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
        RV2.setLayoutManager(llm2);
        foodList2 = new ArrayList<>();
        /*foodList2.add(new foodItem("Блюдо 1", "http://192.168.0.101/1.jpg", 3, new BigDecimal(1000)));
        foodList2.add(new foodItem("Блюдо 2", "http://192.168.0.101/1.jpg", 4, new BigDecimal(1000)));
        foodList2.add(new foodItem("Блюдо 3", "http://192.168.0.101/1.jpg", 5, new BigDecimal(1000)));
        foodList2.add(new foodItem("Блюдо 4","http://192.168.0.101/1.jpg", 6, new BigDecimal(1000)));
        foodList2.add(new foodItem("Цомян Лагман", "http://192.168.0.101/1.jpg", 7,new BigDecimal(1000)));*/
        RV2.setAdapter(adapter2);
        foodList3 = new ArrayList<>();
        /*foodList3.add(new foodItem("Блюдо 100500", "http://192.168.0.101/1.jpg", 8, new BigDecimal(1000)));
        foodList3.add(new foodItem("Цомян Лагман", "http://192.168.0.101/1.jpg", 7, new BigDecimal(1000)));
        foodList3.add(new foodItem("Блюдо 100500", "http://192.168.0.101/1.jpg", 8, new BigDecimal(1000)));*/
        RV3 = (RecyclerView) view.findViewById(R.id.cardList3);
        llm3 = new LinearLayoutManager(getActivity());
        llm3.setOrientation(LinearLayoutManager.HORIZONTAL);
        RV3.setLayoutManager(llm3);
        RV3.setAdapter(adapter3);

        foodList4 = new ArrayList<>();
       /* foodList4.add(new foodItem("Блюдо 100500", "http://192.168.0.101/1.jpg", 8, new BigDecimal(1000)));
        foodList4.add(new foodItem("Цомян Лагман", "http://192.168.0.101/1.jpg", 7, new BigDecimal(1000)));
        foodList4.add(new foodItem("Блюдо 100500", "http://192.168.0.101/1.jpg", 8, new BigDecimal(1000)));*/
        RV4 = (RecyclerView) view.findViewById(R.id.cardList4);
        llm4 = new LinearLayoutManager(getActivity());
        llm4.setOrientation(LinearLayoutManager.HORIZONTAL);
        RV4.setLayoutManager(llm4);
        RV4.setAdapter(adapter4);

        /*Log.d("SZ", foodList.size()+"");
        Log.d("SZ", foodList2.size()+"");
        Log.d("SZ", foodList3.size()+"");
        Log.d("SZ", foodList4.size()+"");*/

//        adapter1.notifyDataSetChanged();

        return view;
    }

    class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.FoodItemHolder>{
        private List<foodItem> foodList;

        public MenuAdapter(List<foodItem> foodList){
            this.foodList = foodList;
        }

        @Override
        public int getItemCount(){
            return foodList.size();
        }

        @Override
        public void onBindViewHolder(FoodItemHolder foodItemHolder, int i){
            foodItem f = foodList.get(i);
            foodItemHolder.textView.setText(f.getName());
            //foodItemHolder.imageView.setImageResource(f.getItemImage());
            Picasso.with(getActivity()).load(f.getItemImage())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(foodItemHolder.imageView);
        }

        @Override
        public FoodItemHolder onCreateViewHolder(ViewGroup viewGroup, int i){
            View itemView = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.menu_card_view, viewGroup, false);
            return new FoodItemHolder(itemView);
        }

        public class FoodItemHolder extends RecyclerView.ViewHolder {
            protected ImageView imageView;
            protected TextView textView;
            protected ImageButton bt;
            public FoodItemHolder(View v){
                super(v);
                imageView = (ImageView) v.findViewById(R.id.imageView);
                textView = (TextView) v.findViewById(R.id.textView);
                Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");
                textView.setTypeface(tf);
                bt = (ImageButton) v.findViewById(R.id.bt);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cart cart = CartHelper.getCart();
                        cart.add(foodList.get(getLayoutPosition()), 1);
                        ((MainActivity)getActivity()).updateCartIcon();
                        Log.d("qe", ""+cart.getTotalQuantity());
                        Log.d("PRICE", ""+cart.getTotalPrice());
                    }
                });
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String cat = foodList.get(getLayoutPosition()).getCategory();
                        String json = "";
                        if(cat == "first"){
                            json = JSONfirst;
                        }
                        else if(cat == "second"){
                            json = JSONsecond;
                        }
                        else if(cat == "salads"){
                            json = JSONsalads;
                        }
                        else if(cat == "drinks"){
                            json = JSONdrinks;
                        }
                        ((MainActivity)getActivity()).showItem(foodList.get(getLayoutPosition()).getId()-1, json);

                        Log.d("asdf",""+foodList.get(getLayoutPosition()).getCategory());
                    }
                });
            }
        }

    }



    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute(){
            Log.d("AsyncTask", "Starting");
        }

        @Override
        protected  Integer doInBackground(String... params){

            Integer result = 0;
            HttpURLConnection urlConnection;
            for(int i = 0; i < params.length; i++){
                try{
                    URL url = new URL(params[i]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    Log.d("ASDF", params[i]);
                    int statusCode = urlConnection.getResponseCode();

                    if(statusCode == 200){
                        BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while((line = r.readLine()) != null){
                            response.append(line);
                        }
                        if(i == 0){
                            JSONfirst = response.toString();
                        }
                        else if(i == 1){
                            JSONsecond = response.toString();
                        }
                        else if(i == 2){
                            JSONsalads = response.toString();
                        }
                        else if(i == 3){
                            JSONdrinks = response.toString();
                        }
                        Log.d("RESPONSE", JSONfirst);
                        parseResult(response.toString(), i+1);
                        result = 1;
                    }
                    else{
                        result = 0;
                    }
                }
                catch (Exception e){
                    Log.d("HTTPTASK", e.getLocalizedMessage());
                }
            }
            Log.d("asd", result+"");
            return result;
        }

        @Override
        protected void onPostExecute(Integer result){
            if(result == 1){
                /*adapter1.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
                adapter3.notifyDataSetChanged();
                adapter4.notifyDataSetChanged();*/

                adapter1 = new MenuAdapter(foodList);
                adapter2 = new MenuAdapter(foodList2);
                adapter3 = new MenuAdapter(foodList3);
                adapter4 = new MenuAdapter(foodList4);

                Log.d("DEBUG", ""+foodList.size());

                Log.d("DEBUG", ""+foodList2.size());
                Log.d("DEBUG", ""+foodList3.size());
                Log.d("DEBUG", ""+foodList4.size());
                RV1.setAdapter(adapter1);
                RV2.setAdapter(adapter2);
                RV3.setAdapter(adapter3);
                RV4.setAdapter(adapter4);
            }
        }
    }


    private void parseResult(String result, int x){
        if(x == 1){
            try{
                JSONArray response = new JSONArray(result);
                //foodList = new ArrayList<>();
                for(int i = 0; i < response.length(); i++){
                    JSONObject item = response.optJSONObject(i);
                    foodItem food = new foodItem();
                    food.setItemName(item.optString("name"));
                    food.setDescription(item.optString("description"));
                    food.setPrice(new BigDecimal(item.optInt("price")));
                    food.setItemImage(item.optString("img"));
                    food.setCategory("first");
                    food.setId(item.getInt("id"));
                    food.setIngredients(item.getString("ingredients"));
                    Log.d("Food " + i, food.getName()+" "+food.getPrice());
                    foodList.add(food);
                }
                Log.d("SIZE", ""+foodList.size());
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
        else if(x == 2){
            try{
                JSONArray response = new JSONArray(result);
                //foodList2 = new ArrayList<>();
                for(int i = 0; i < response.length(); i++){
                    JSONObject item = response.optJSONObject(i);
                    foodItem food = new foodItem();
                    food.setItemName(item.optString("name"));
                    food.setDescription(item.optString("description"));
                    food.setPrice(new BigDecimal(item.optInt("price")));
                    food.setItemImage(item.optString("img"));
                    food.setCategory("second");
                    food.setId(item.getInt("id"));
                    foodList2.add(food);
                    Log.d("Food " + i, food.getName()+" "+food.getPrice());
                }
                Log.d("SIZE", ""+foodList2.size());
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
        else if(x == 3){
            try{
                JSONArray response = new JSONArray(result);
                //foodList3 = new ArrayList<>();
                for(int i = 0; i < response.length(); i++){
                    JSONObject item = response.optJSONObject(i);
                    foodItem food = new foodItem();
                    food.setItemName(item.optString("name"));
                    food.setDescription(item.optString("description"));
                    food.setPrice(new BigDecimal(item.optInt("price")));
                    food.setItemImage(item.optString("img"));
                    food.setId(item.getInt("id"));
                    foodList3.add(food);
                    food.setCategory("salads");
                    Log.d("Food " + i, food.getName()+" "+food.getPrice());
                }
                Log.d("SIZE", ""+foodList3.size());
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
        else if(x == 4){
            try{
                JSONArray response = new JSONArray(result);
                //foodList4 = new ArrayList<>();
                for(int i = 0; i < response.length(); i++){
                    JSONObject item = response.optJSONObject(i);
                    foodItem food = new foodItem();
                    food.setItemName(item.optString("name"));
                    food.setDescription(item.optString("description"));
                    food.setPrice(new BigDecimal(item.optInt("price")));
                    food.setItemImage(item.optString("img"));
                    food.setId(item.getInt("id"));
                    foodList4.add(food);
                    food.setCategory("drinks");
                    Log.d("Food " + i, food.getName()+" "+food.getPrice());
                }
                Log.d("SIZE", ""+foodList4.size());
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
    }


}
