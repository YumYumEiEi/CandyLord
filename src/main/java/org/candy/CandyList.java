package org.candy;

import org.exceptions.NotEnougthItemsException;
import org.exceptions.TooManyItemsException;

import java.util.HashMap;

public class CandyList implements CandyContainer {
    private final HashMap<Candy, Integer> candyList;

    private int capacity;

    public CandyList(){
        candyList = new HashMap();
        capacity = 10;
    }
    @Override
    public void addCandy(Candy candy, int amount) throws TooManyItemsException {
        if(!isThereEnoughtSpace(amount)){
            throw new TooManyItemsException();
        }
        int candyAmount = candyList.get(candy);
        candyList.put(candy, candyAmount + amount);
    }

    private boolean isThereEnoughtSpace(int amount) {
        int storedItems = 0;
        for (int value : candyList.values()) {
            storedItems += value;
        }
        return capacity >= storedItems + amount;
    }

    @Override
    public int getAmountOfCandy(Candy candy) {
        return candyList.get(candy);
    }

    @Override
    public void removeCandy(Candy candy, int amount) throws NotEnougthItemsException {
        try {
            if(areEnougthItemsAvilable(candy, amount)){
                addCandy(candy, -amount);
            }else{
                throw new NotEnougthItemsException();
            }
        } catch (TooManyItemsException e) {

        }
    }

    private boolean areEnougthItemsAvilable(Candy candy, int amount) {
        int currendItems = candyList.get(candy);
        return currendItems >= amount;
    }

    @Override
    public void addNewCandy(Candy candy) {
        candyList.put(candy, 0);
    }
}
