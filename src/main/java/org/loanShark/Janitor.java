package org.loanShark;

import org.exceptions.HasAlreadyDebtException;
import org.exceptions.NoDebtException;
import org.exceptions.TooLowAmountException;
import org.exceptions.TooMutchPaybackException;
import org.player.Player;

public class Janitor implements LoanShark {
    private final double intrestCharges;

    private final Player player;

    private long debt;

    private int timer;

    private int breachOfTrust;

    public Janitor (Player player){
        this.intrestCharges = 1.05;
        this.player = player;
        this.debt = 0;
        this.timer = 0;
        this.breachOfTrust = 0;
    }

    @Override
    public long getDebt() {
        return this.debt;
    }

    @Override
    public void borrowMoney(long amount) throws TooLowAmountException, HasAlreadyDebtException {
        if(debt > 0){
            throw new HasAlreadyDebtException();
        }
        if(amount < 1000){
            throw new TooLowAmountException();
        }
        debt += (long) (amount*intrestCharges);
        player.addCash(amount);
        timer = 15;
    }

    @Override
    public void payBackDebt(long amount) throws NoDebtException, TooMutchPaybackException {
        if(debt == 0){
            throw new NoDebtException();
        }
        if(debt < amount){
            throw new TooMutchPaybackException();
        }
        this.debt -= amount;
        if(this.debt == 0){
            this.timer = 0;
        }
    }

    @Override
    public void beginningOfDay() {
        this.timer--;
        this.debt *= intrestCharges;
        if(timer < 0){
            ambushPlayer();
        }
    }

    private void ambushPlayer() {
        breachOfTrust++;
        for(int times = 0; times < breachOfTrust; times++){
            player.reduceHealth(25);
        }
        timer = 7;
    }

    public Player getPlayer() {
        return player;
    }

    public void setDebt(long debt) {
        this.debt = debt;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
}
