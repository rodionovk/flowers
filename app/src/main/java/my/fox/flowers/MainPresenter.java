package my.fox.flowers;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by rodionov on 28.11.2016. flowers
 */

public class MainPresenter {

    private final static String FILE_PATH = "flowers.txt";

    private MainView mMainView;
    private List<Flower> mFlowers;
    private int mPosition;

    public MainPresenter(MainView view) {
        mMainView = view;
    }

    public void init(Context context) {

        mFlowers = new ArrayList<>();
        mPosition = 0;

        try {
            InputStream is = context.getAssets().open(FILE_PATH);

            BufferedReader in = new BufferedReader(new InputStreamReader(is, "windows-1251"));
            String str;
            while ((str = in.readLine()) != null) {
                int delim = str.indexOf(";");
                String name = str.substring(0,delim);
                String fileName = str.substring(delim + 1,str.length()).toLowerCase();
                int fileres = context.getResources().getIdentifier(fileName,"drawable",context.getPackageName());

                Flower flower = new Flower(name,fileres);
                mFlowers.add(flower);
            }
            in.close();

            long seed = System.nanoTime();
            Collections.shuffle(mFlowers, new Random(seed));

            mMainView.onFlowersReady(mFlowers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getNextFlower() {
        if(mPosition > mFlowers.size() - 1) {
            mMainView.onComplete();
            return;
        }
        mMainView.onNextFlower(mFlowers.get(mPosition),mPosition);
        mPosition++;

    }
}
