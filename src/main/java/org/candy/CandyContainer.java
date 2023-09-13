package org.candy;

import org.exceptions.NotEnougthItemsException;
import org.exceptions.TooManyItemsException;

public interface CandyContainer {
    public static CandyContainer getDefaultCandyContainer(){
        try {
            return CandyList.getFilledList();
        } catch (TooManyItemsException e) {
            throw new RuntimeException(e);
        }
    }
    void addCandy(Candy candy, int amount) throws TooManyItemsException;

    int getAmountOfCandy(Candy candy);

    void removeCandy(Candy candy, int amount) throws NotEnougthItemsException;

    void addNewCandy(Candy candy);
}
