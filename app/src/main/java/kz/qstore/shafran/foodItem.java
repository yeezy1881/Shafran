package kz.qstore.shafran;

import com.android.tonyvu.sc.model.Saleable;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Taldy on 04.07.2016.
 */
public class foodItem implements Saleable{
    private String itemName;
    private String itemImage;
    private int id;
    private BigDecimal price;
    private String description;
    private String category;
    private String ingredients;

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public foodItem(String itemName, String itemImage, int id, BigDecimal price) {
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.id = id;
        this.price = price;
    }

    public foodItem(){

    }

    public String getName() {
        return itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public int getId() {
        return id;
    }


    ArrayList<foodItem> foodItems;

    /*public foodItem getFoodItem(int id){
        foodItems = new ArrayList<>();
        foodItems.add(new foodItem("Блюдо 1", "http://192.168.0.101/1.jpg", 1, new BigDecimal(1000)));
        foodItems.add(new foodItem("Блюдо 2", "http://192.168.0.101/1.jpg", 2, new BigDecimal(1000)));
        foodItems.add(new foodItem("Блюдо 1", "http://192.168.0.101/1.jpg", 3, new BigDecimal(1000)));
        foodItems.add(new foodItem("Блюдо 2", "http://192.168.0.101/1.jpg", 4, new BigDecimal(1000)));
        foodItems.add(new foodItem("Блюдо 3", "http://192.168.0.101/1.jpg", 5, new BigDecimal(1000)));
        foodItems.add(new foodItem("Блюдо 4", "http://192.168.0.101/1.jpg", 6, new BigDecimal(1000)));
        foodItems.add(new foodItem("Цомян Лагман", "http://192.168.0.101/1.jpg", 7, new BigDecimal(1000)));
        foodItems.add(new foodItem("Блюдо 100500", "http://192.168.0.101/1.jpg", 8, new BigDecimal(1000)));
        return foodItems.get(id-1);
    }*/
}