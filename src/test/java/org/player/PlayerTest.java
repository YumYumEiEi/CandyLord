package org.player;

import org.candy.Candy;
import org.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.location.Location;
import org.util.TestUtil;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player testPlayer;

    @BeforeEach
    public void setUp() {
        testPlayer = new Player("TestPlayer", TestUtil.getTestLocation(), TestUtil.getTestCandyContainer());
        testPlayer.setCash(100000);
    }

    @Test
    void ifAPlayerBuysAnAmountOfCandyItIsAddedToTheCandyContainer() throws NotEnoughtMoneyException {
        testPlayer.buyCandy(Candy.BUBBA_CHUPS, 10);
        assertEquals(10, testPlayer.getCandyAmount(Candy.BUBBA_CHUPS));
    }

    @Test
    void ifAPlayerSellsAnAmountOfCandyItIsRemovedFromTheCandyContainer() throws NotEnoughtMoneyException {
        testPlayer.buyCandy(Candy.BUBBA_CHUPS, 10);
        testPlayer.sellCandy(Candy.BUBBA_CHUPS, 10);
        assertEquals(0, testPlayer.getCandyAmount(Candy.BUBBA_CHUPS));
    }

    @Test
    void shouldNotBeAbleToBuyCandyIfHeHasNotEnoughtMoney() {
        testPlayer.setCash(0);
        assertThrows(NotEnoughtMoneyException.class, () -> testPlayer.buyCandy(Candy.BUBBA_CHUPS, 1));

    }

    @Test
    void shouldRemoveCashIfCandyWasBought() throws NotEnoughtMoneyException {
        testPlayer.setCash(10000);
        testPlayer.buyCandy(Candy.BUBBA_CHUPS, 1);
        long expectedCash = 10000 - testPlayer.getLocalCandyPrice(Candy.BUBBA_CHUPS);
        assertEquals(expectedCash, testPlayer.getCash());

    }

    @Test
    void shouldGetCashIfCandyWasSold() throws NotEnoughtMoneyException {
        testPlayer.setCash(1000);
        testPlayer.buyCandy(Candy.BUBBA_CHUPS, 1);
        testPlayer.sellCandy(Candy.BUBBA_CHUPS, 1);
        assertEquals(1000, testPlayer.getCash());
    }

    @Test
    void playerCanSwitchLocation() throws NotEnoughtMoneyException {
        Location anotherSchool = TestUtil.getAnotherTestLocation();
        testPlayer.switchLocation(anotherSchool);
        assertEquals(anotherSchool.getName(), testPlayer.getLocationName());
    }

    @Test
    void playerReducesCashIfHeSwitchesLocation() throws NotEnoughtMoneyException {
        testPlayer.setCash(100);
        testPlayer.switchLocation(TestUtil.getAnotherTestLocation());
        assertEquals(0, testPlayer.getCash());
    }

    @Test
    void playerShouldNotBeAbleToSwitchLocationIfNotEnoughtCashIsAvailible() {
        testPlayer.setCash(0);
        assertThrows(NotEnoughtMoneyException.class, () -> testPlayer.switchLocation(TestUtil.getAnotherTestLocation()));

    }

    @Test
    void playerCanPutCashInThePiggyBank() throws NotEnoughtMoneyException {
        testPlayer.putCashInPiggyBank(1000);
        assertEquals(1000, testPlayer.getPiggyCash());
    }

    @Test
    void playerCanRetriveCashFromPiggyBank() throws NotEnoughtMoneyException, NotEnoughtMoneyExceptionPiggyBank {
        testPlayer.putCashInPiggyBank(1000);
        testPlayer.retrieveCashFromPiggyBank(1000);
        assertEquals(0, testPlayer.getPiggyCash());

    }

    @Test
    void playerCantPutMoreCashInPiggyBankThanHeHas() {
        testPlayer.setCash(0);
        assertThrows(NotEnoughtMoneyException.class, () -> testPlayer.putCashInPiggyBank(100));
    }

    @Test
    void playerCantRetrieveMoreMonneyThanTheyHaveInPiggyBank() throws NotEnoughtMoneyException {
        assertThrows(NotEnoughtMoneyExceptionPiggyBank.class, () -> testPlayer.retrieveCashFromPiggyBank(100));
    }

    @Test
    void cashShouldBeLowerdIfMoneyIsPutInThePiggyBank() throws NotEnoughtMoneyException {
        testPlayer.setCash(1000);
        testPlayer.putCashInPiggyBank(1000);
        assertEquals(0, testPlayer.getCash());
    }

    @Test
    void cashShouldBeRaisedIfMoneyIsRetrievedFromPiggyBank() throws NotEnoughtMoneyExceptionPiggyBank {
        testPlayer.setPiggyBank(1000);
        testPlayer.setCash(0);
        testPlayer.retrieveCashFromPiggyBank(1000);
        assertEquals(1000, testPlayer.getCash());
    }

    @Test
    void playerShouldBeAbleToBurrowMoneyFromTheJanitorAndTheCashRises() throws Exception {
        testPlayer.setCash(0);
        testPlayer.borrowMoneyFromJanitor(1000);
        assertEquals(1000, testPlayer.getCash());
    }

    @Test
    void ifMoneyIsBorrowedThePlayerGetsDebtEqualsToFivePercentExtra() throws Exception {
        testPlayer.borrowMoneyFromJanitor(1000);
        assertEquals(1050, testPlayer.getDebt());
    }

    @Test
    void playerMustBorrowAtLeastThousandCent() {
        assertThrows(TooLowAmountException.class, () -> testPlayer.borrowMoneyFromJanitor(100));
    }

    @Test
    void ifPlayerPaysJanitorBackDebtShouldBeLowered() throws Exception {
        testPlayer.setDebt(1050);
        testPlayer.payBackJanitor(500);
        assertEquals(550, testPlayer.getDebt());

    }

    @Test
    void playerShouldNotBeAbleToPayBackJanitorMoreMoneyThanTheyHave() {
        testPlayer.setDebt(1050);
        testPlayer.setCash(0);
        assertThrows(NotEnoughtMoneyException.class, () -> testPlayer.payBackJanitor(100));
    }

    @Test
    void playerCantPayBackIfHeHasNoDebt() {
        assertThrows(NoDebtException.class, () -> testPlayer.payBackJanitor(100));
    }

    @Test
    void playerCantPayBackMoreThanHeHasDebt() {
        testPlayer.setDebt(100);
        assertThrows(TooMutchPaybackException.class, () -> testPlayer.payBackJanitor(1000));
    }

    @Test
    void debtIsRaisedAtTheBeginningOfTheDay() {
        //Debt should be raised by 105%
        testPlayer.setDebt(1000);
        testPlayer.beginningOfDay();
        assertEquals(1050, testPlayer.getDebt());
    }

    @Test
    void ifPlayerBorrowsMoneyHeGetsATimerOfFifteenDaysToPayBack() throws Exception {
        testPlayer.borrowMoneyFromJanitor(1000);
        assertEquals(15, testPlayer.getDebtTimer());
    }

    @Test
    void debtTimerShouldReduceAtTheBeginningOfDay() throws Exception {
        testPlayer.borrowMoneyFromJanitor(1000);
        testPlayer.beginningOfDay();
        assertEquals(14, testPlayer.getDebtTimer());
    }

    @Test
    void playerShouldNotBeAbleToBorrowMoneyIfHeAlreadyHasDebt() {
        testPlayer.setDebt(1000);
        assertThrows(HasAlreadyDebtException.class, () -> testPlayer.borrowMoneyFromJanitor(1000));
    }

    @Test
    void ifPlayerDebtReachesZeroTheDebtTimerAlsoBecomesZero() throws Exception {
        testPlayer.borrowMoneyFromJanitor(1000);
        testPlayer.payBackJanitor(1050);
        assertEquals(0, testPlayer.getDebtTimer());
    }

    @Test
    void ifDebtTimerReachesMinusOnePlayerGetsAbushed() {
        testPlayer.setDebt(1000);
        testPlayer.beginningOfDay();
        assertEquals(75, testPlayer.getHealth());
    }

    @Test
    void ifPlayerGetsAmbushedDebtTimerIsSetToSeven() {
        testPlayer.setDebt(1000);
        testPlayer.beginningOfDay();
        assertEquals(7, testPlayer.getDebtTimer());
    }

    @Test
    void ifPlayerGetsAmbushedAnotherTimeHeGetsHitThatManyTimes() {
        testPlayer.setDebt(1000);
        testPlayer.beginningOfDay();
        testPlayer.setDebtTimer(0);
        testPlayer.beginningOfDay();
        assertEquals(25, testPlayer.getHealth());
    }
}