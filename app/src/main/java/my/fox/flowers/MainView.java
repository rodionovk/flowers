package my.fox.flowers;

import java.util.List;

/**
 * Created by rodionov on 28.11.2016. flowers
 */

public interface MainView {
    void onFlowersReady(List<Flower> flowers);
    void onNextFlower(Flower flower, int position);
    void onComplete();
}
