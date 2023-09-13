package org.loanShark;

import org.exceptions.HasAlreadyDebtException;
import org.exceptions.NoDebtException;
import org.exceptions.TooLowAmountException;
import org.exceptions.TooMutchPaybackException;
import org.player.Player;

public interface LoanShark {
    static LoanShark getDefaultLoanShark(Player player) {
        return new Janitor(player);
    }

    long getDebt();

    void borrowMoney(long amount) throws TooLowAmountException, HasAlreadyDebtException;

    void payBackDebt(long amount) throws NoDebtException, TooMutchPaybackException;

    void beginningOfDay();

    void setDebt(long debt);

    int getTimer();

    void setTimer(int timer);
}
