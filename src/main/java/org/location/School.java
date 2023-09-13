package org.location;

import org.candy.Candy;

import java.util.HashMap;

public class School implements Location {
    HashMap<Candy, Long> priceList;
    private long travelCost;
    private String name;

    public School(String name, long travelCost){
        priceList = new HashMap<>();
        this.name = name;
        this.travelCost = travelCost;
    }

    @Override
    public void setCandyPrice(Candy candy, long price) {
        priceList.put(candy, price);
    }

    @Override
    public long getPriceForCandy(Candy candy) {
        return priceList.get(candy);
    }

    @Override
    public long getTravelCost() {
        return this.travelCost;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
