package my.fox.flowers;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    private MainPresenter mPresenter;

    ImageView mImage;
    TextView mResult;
    TextView mVariant1;
    TextView mVariant2;
    TextView mVariant3;
    TextView mVariant4;

    TextView mEndResult;
    Button mReplay;
    LinearLayout mLayoutResult;

    List<Flower> mFlowers;
    int mSuccess;
    int mPosition;
    List<TextView> mListVariant = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        mPresenter = new MainPresenter(this);
        mPresenter.init(this);
    }

    private void initView() {
        setContentView(R.layout.activity_main);

        mImage = (ImageView) findViewById(R.id.image_flower);
        mResult = (TextView) findViewById(R.id.result);
        mVariant1 = (TextView) findViewById(R.id.text_variant1);
        mVariant2 = (TextView) findViewById(R.id.text_variant2);
        mVariant3 = (TextView) findViewById(R.id.text_variant3);
        mVariant4 = (TextView) findViewById(R.id.text_variant4);
        mEndResult = (TextView) findViewById(R.id.end_result);
        mReplay = (Button)  findViewById(R.id.button_replay);
        mLayoutResult = (LinearLayout)findViewById(R.id.layout_result);

        mListVariant.add(mVariant1);
        mListVariant.add(mVariant2);
        mListVariant.add(mVariant3);
        mListVariant.add(mVariant4);

        mVariant1.setOnClickListener(this);
        mVariant2.setOnClickListener(this);
        mVariant3.setOnClickListener(this);
        mVariant4.setOnClickListener(this);
        mReplay.setOnClickListener(this);
    }


    @Override
    public void onFlowersReady(List<Flower> flowers) {
        mResult.setText(String.format("Результат %s/%s","0","0"));
        mLayoutResult.setVisibility(View.GONE);

        mSuccess = 0;
        mPosition = 0;

        mFlowers = flowers;
        mPresenter.getNextFlower();
    }

    @Override
    public void onNextFlower(Flower flower,int position) {

        Log.d("FLOWERS",flower.getName());
        ((BitmapDrawable)mImage.getDrawable()).getBitmap().recycle();
        mImage.setTag(flower);
        mImage.setImageResource(flower.getImage());

        TextView textView = mListVariant.get(getRandom(0,4));
        textView.setTag(flower);
        textView.setText(flower.getName());

        for(TextView tv : mListVariant) {
            if(tv.getTag() == null) {
                String name = getNameFlower();
                tv.setText(name);
            }
        }
    }

    @Override
    public void onComplete() {
        Toast.makeText(this,"Тест завершен",Toast.LENGTH_LONG).show();
        mLayoutResult.setVisibility(View.VISIBLE);
        mEndResult.setText(String.format("Результат %s/%s",mSuccess,mPosition));

    }

    public String getNameFlower() {

        Flower fl = mFlowers.get(getRandom(0,mFlowers.size() - 1));

        for(TextView tv : mListVariant) {
            if(tv.getText().equals(fl.getName())) {
                return getNameFlower();
            }
        }
        return fl.getName();
    }

    public static int getRandom(int from, int to) {
        if (from < to)
            return from + new Random().nextInt(Math.abs(to - from));
        return from - new Random().nextInt(Math.abs(to - from));
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.button_replay) {
            mPresenter.init(this);
            return;
        }

        Flower flower = (Flower) mImage.getTag();
        Flower flowerSelect = (Flower)view.getTag();

        if(flowerSelect != null && flower.getName().equals(flowerSelect.getName())) {
            view.setBackgroundColor(Color.GREEN);
            mSuccess++;
        } else {
            view.setBackgroundColor(Color.RED);
            for(TextView tv : mListVariant) {
                if(tv.getTag() != null) {
                    tv.setBackgroundColor(Color.GREEN);
                }
            }
        }

        mPosition++;
        mResult.setText(String.format("Результат %s/%s",mSuccess,mPosition));

        view.postDelayed(new Runnable() {
            @Override
            public void run() {

                for(TextView tv : mListVariant) {
                    tv.setTag(null);
                    tv.setBackgroundColor(Color.WHITE);
                }

                mPresenter.getNextFlower();
            }
        },1000);
    }
}
