package org.player;

import org.candy.Candy;
import org.candy.CandyContainer;
import org.exceptions.NotEnougthItemsException;
import org.exceptions.TooManyItemsException;
import org.items.Bully;
import org.location.Location;

import java.util.ArrayList;

public class Player {

    private final String name;

    private Location location;

    private int cash;

    private int debt;

    private int piggyBank;

    private CandyContainer candyContainer;

    private ArrayList<Bully> bullies;

    private int health;

    public Player(String name, Location location, CandyContainer candyContainer){
        this.cash = 500;
        this.name = name;
        this.location = location;
        this.health = 100;
        this.candyContainer = candyContainer;
    }

    public void buyCandy (){

    }

    public void sellCandy() {
    }

    public void buyCandy(Candy candy, int amount) {
        try {
            candyContainer.addCandy(candy, amount);
        } catch (TooManyItemsException e) {
        }
    }

    public int getCandyAmount(Candy candy) {
        return candyContainer.getAmountOfCandy(candy);
    }

    public void sellCandy(Candy candy, int amount) {
        try {
            candyContainer.removeCandy(candy, amount);
        } catch (NotEnougthItemsException e) {
        }
    }
}
