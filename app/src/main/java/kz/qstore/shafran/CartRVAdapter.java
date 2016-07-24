package kz.qstore.shafran;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tonyvu.sc.model.Cart;
import com.android.tonyvu.sc.util.CartHelper;

import java.util.ArrayList;

/**
 * Created by Taldy on 11.07.2016.
 */
public class CartRVAdapter extends RecyclerView.Adapter<CartRVAdapter.ItemViewHolder>{

    ArrayList<foodItem> foodArrayList;

    CartRVAdapter(ArrayList<foodItem> foodArrayList){
        this.foodArrayList = foodArrayList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
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
        Cart cart = CartHelper.getCart();
        itemViewHolder.foodName.setText(foodArrayList.get(i).getName());
        itemViewHolder.itemPrice.setText(foodArrayList.get(i).getPrice()+"");
        itemViewHolder.itemTotalPrice.setText(foodArrayList.get(i).getPrice().intValue() * cart.getQuantity(foodArrayList.get(i)) + "");
        itemViewHolder.itemCount.setText(cart.getQuantity(foodArrayList.get(i))+"");
    }


}
