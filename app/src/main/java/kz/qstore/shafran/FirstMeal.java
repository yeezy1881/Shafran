package kz.qstore.shafran;

import java.util.ArrayList;

/**
 * Created by Taldy on 04.07.2016.
 */
public class FirstMeal {

    private String mealName;
    private int image;

    public FirstMeal(String mealName, int image) {
        this.mealName = mealName;
        this.image = image;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
