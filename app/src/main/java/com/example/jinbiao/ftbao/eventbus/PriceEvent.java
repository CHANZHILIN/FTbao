package com.example.jinbiao.ftbao.eventbus;

/**
 * Created by CHEN_ on 2017/6/20.
 */

public class PriceEvent {
   private double price;
    private boolean isSelectedAll;
    public PriceEvent(double price) {
        this.price = price;
    }

    public PriceEvent(boolean isSelectedAll) {
        this.isSelectedAll = isSelectedAll;
    }

    public boolean getSelectedAll() {
        return isSelectedAll;
    }

    public void setSelectedAll(boolean selectedAll) {
        isSelectedAll = selectedAll;
    }


    public double getPrice(){
        price = Double.parseDouble(String.format("%.2f",price));
        return  price;
    }
}
