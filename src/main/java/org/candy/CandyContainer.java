package org.candy;

import org.exceptions.NotEnougthItemsException;
import org.exceptions.TooManyItemsException;

public interface CandyContainer {
    void addCandy(Candy candy, int amount) throws TooManyItemsException;

    int getAmountOfCandy(Candy candy);

    void removeCandy(Candy candy, int amount) throws NotEnougthItemsException;

    void addNewCandy(Candy candy);
}
