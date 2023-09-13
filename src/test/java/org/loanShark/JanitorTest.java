package org.loanShark;

import org.exceptions.HasAlreadyDebtException;
import org.exceptions.NoDebtException;
import org.exceptions.TooLowAmountException;
import org.exceptions.TooMutchPaybackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.util.TestUtil;

import static org.junit.jupiter.api.Assertions.*;
class JanitorTest {

    Janitor testJanitor;
    @BeforeEach
    public void setUp(){
        testJanitor = new Janitor(TestUtil.getTestPlayer());
    }

    @Test
    void ifPlayerBorrowsMoneyTheDebtIncreasesByAmountTimesInteresCharges() throws TooLowAmountException, HasAlreadyDebtException {
        //Default is 1.05 for InterestCharge
        testJanitor.borrowMoney(1000);
        assertEquals(1050, testJanitor.getDebt());
    }

    @Test
    void thereShouldBeAMinimumOf1000IfAPlayerBorrowsMoney(){
        assertThrows(TooLowAmountException.class, () -> testJanitor.borrowMoney(100));
    }

    @Test
    void ifMoneyIsBorrowedThePlayerShouldGetTheMoney() throws TooLowAmountException, HasAlreadyDebtException {
        long money = testJanitor.getPlayer().getCash();
        testJanitor.borrowMoney(1000);
        assertEquals(money+1000, testJanitor.getPlayer().getCash());
    }

    @Test
    void shouldNotBePossibleToBorrowMoneyIfDebtIsNotZero() throws TooLowAmountException, HasAlreadyDebtException {
        testJanitor.borrowMoney(1000);
        assertThrows(HasAlreadyDebtException.class, () -> testJanitor.borrowMoney(1000));
    }

    @Test
    void shouldSetTimerIfMoneyIsBorrowd() throws TooLowAmountException, HasAlreadyDebtException {
        testJanitor.borrowMoney(1000);
        assertEquals(15, testJanitor.getTimer());
    }

    @Test
    void shouldBePossibleToPayBeckDebt() throws NoDebtException, TooMutchPaybackException {
        testJanitor.setDebt(1000);
        testJanitor.payBackDebt(1000);
        assertEquals(0, testJanitor.getDebt());
    }

    @Test
    void shouldNotBePossibleToPayBackDebtIfDebtIsZero(){
        assertThrows(NoDebtException.class, () -> testJanitor.payBackDebt(1000));
    }

    @Test
    void shouldNotBePossibleToPayBackMoreThanTheDebtamount(){
        testJanitor.setDebt(10);
        assertThrows(TooMutchPaybackException.class, () -> testJanitor.payBackDebt(100));
    }

    @Test
    void ifPlayerDebtReachesZeroTheDebtTimerAlsoBecomesZero() throws Exception {
        testJanitor.borrowMoney(1000);
        testJanitor.payBackDebt(1050);
        assertEquals(0, testJanitor.getTimer());
    }

    @Test
    void ifDebtTimerReachesMinusOnePlayerGetsAbushed() {
        testJanitor.setDebt(1000);
        testJanitor.beginningOfDay();
        assertEquals(75, testJanitor.getPlayer().getHealth());
    }

    @Test
    void ifPlayerGetsAmbushedDebtTimerIsSetToSeven() {
        testJanitor.setDebt(1000);
        testJanitor.beginningOfDay();
        assertEquals(7, testJanitor.getTimer());
    }

    @Test
    void ifPlayerGetsAmbushedAnotherTimeHeGetsHitThatManyTimes() {
        testJanitor.setDebt(1000);
        testJanitor.beginningOfDay();
        testJanitor.setTimer(0);
        testJanitor.beginningOfDay();
        assertEquals(25, testJanitor.getPlayer().getHealth());
    }

}