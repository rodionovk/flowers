package my.fox.flowers;

/**
 * Created by rodionov on 28.11.2016. flowers
 */

public class Flower {

    private String mName;
    private int mImage;

    public Flower(String name,int image) {
        mName = name;
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        mImage = image;
    }


}
