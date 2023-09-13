package org.player;

import org.candy.Candy;
import org.candy.CandyContainer;
import org.exceptions.*;
import org.items.Bully;
import org.location.Location;

import java.util.ArrayList;

public class Player {

    private final String name;

    private Location location;

    private long cash;

    private long debt;

    private int debtTimer;

    private long piggyBank;

    private CandyContainer candyContainer;

    private ArrayList<Bully> bullies;

    private int health;
    private int ambushAmount;

    public Player(String name, Location location, CandyContainer candyContainer) {
        this.cash = 500;
        this.name = name;
        this.location = location;
        this.health = 100;
        this.candyContainer = candyContainer;
        debt = 0;
        debtTimer = 0;
        bullies = new ArrayList<>();
        piggyBank = 0;
        ambushAmount = 0;
    }

    public void buyCandy() {

    }

    public void sellCandy() {
    }

    public void buyCandy(Candy candy, int amount) throws NotEnoughtMoneyException {
        long price = location.getPriceForCandy(candy) * amount;
        if (price <= cash) {
            try {
                candyContainer.addCandy(candy, amount);
                cash -= price;
            } catch (TooManyItemsException e) {
            }
        } else {
            throw new NotEnoughtMoneyException();
        }
    }

    public int getCandyAmount(Candy candy) {
        return candyContainer.getAmountOfCandy(candy);
    }

    public void sellCandy(Candy candy, int amount) {
        try {
            candyContainer.removeCandy(candy, amount);
            cash += location.getPriceForCandy(candy) * amount;
        } catch (NotEnougthItemsException e) {
        }
    }

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }

    public long getLocalCandyPrice(Candy candy) {
        return location.getPriceForCandy(candy);
    }

    public void switchLocation(Location anotherSchool) throws NotEnoughtMoneyException {
        if (cash >= anotherSchool.getTravelCost()) {
            this.location = anotherSchool;
            cash -= anotherSchool.getTravelCost();
        } else {
            throw new NotEnoughtMoneyException();
        }
    }

    public String getLocationName() {
        return location.getName();
    }

    public long getPiggyCash() {
        return piggyBank;
    }

    public void putCashInPiggyBank(long cash) throws NotEnoughtMoneyException {
        if (this.cash >= cash) {
            piggyBank += cash;
            this.cash -= cash;
        } else {
            throw new NotEnoughtMoneyException();
        }
    }

    public void retrieveCashFromPiggyBank(long amount) throws NotEnoughtMoneyExceptionPiggyBank {
        if (piggyBank < amount) {
            throw new NotEnoughtMoneyExceptionPiggyBank();
        }
        try {
            putCashInPiggyBank(-amount);
        } catch (NotEnoughtMoneyException e) {

        }
    }

    public void setPiggyBank(long piggyBank) {
        this.piggyBank = piggyBank;
    }

    public void borrowMoneyFromJanitor(long amount) throws Exception {
        if (debt != 0) {
            throw new HasAlreadyDebtException();
        }
        if (amount < 1000) {
            throw new TooLowAmountException();
        }
        cash += amount;
        debt += amount * 1.05;
        debtTimer = 15;
    }

    public void payBackJanitor(long amount) throws Exception {
        if (cash < amount) {
            throw new NotEnoughtMoneyException();
        }
        if (debt == 0) {
            throw new NoDebtException();
        }
        if (debt < amount) {
            throw new TooMutchPaybackException();
        }
        cash -= amount;
        debt -= amount;

        if (debt == 0) {
            debtTimer = 0;
        }
    }

    public long getDebt() {
        return this.debt;
    }

    public void setDebt(long debt) {
        this.debt = debt;
    }

    public void raiseDebt() {
        debt *= 1.05;
    }

    public int getDebtTimer() {
        return debtTimer;
    }

    public void beginningOfDay() {
        raiseDebt();
        debtTimer--;
        if (debtTimer < 0) {
            ambush();
        }
    }

    private void ambush() {
        this.ambushAmount++;
        for (int amount = 0; amount < ambushAmount; amount++) {
            this.health -= 25;
        }
        this.debtTimer = 7;
    }

    public int getHealth() {
        return this.health;
    }

    public void setDebtTimer(int timer) {
        this.debtTimer = timer;
    }
}
