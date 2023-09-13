package org.location;

import org.candy.Candy;

public interface Location {
    void setCandyPrice(Candy candy, long price);

    long getPriceForCandy(Candy candy);

    long getTravelCost();

    String getName();
}
