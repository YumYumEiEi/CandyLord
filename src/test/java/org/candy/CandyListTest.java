package org.candy;

import org.exceptions.NotEnougthItemsException;
import org.exceptions.TooManyItemsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.util.TestUtil;

import static org.junit.jupiter.api.Assertions.*;

class CandyListTest {
    CandyList testList;

    @BeforeEach
    public void setUp(){
        testList = new CandyList();
        itShouldBeAbleToAddACandyCategoryToTheCandyList();
    }

    @Test
    public void shouldBeAbleToAddCandy() throws TooManyItemsException {
        testList.addCandy(Candy.BUBBA_CHUPS, 10);
        assertEquals(10, testList.getAmountOfCandy(Candy.BUBBA_CHUPS));
    }

    @Test
    public void ifCandyGetsAddedASecondTimeTheCandyAmountIncreases() throws TooManyItemsException {
        testList.addCandy(Candy.BUBBA_CHUPS, 5);
        testList.addCandy(Candy.BUBBA_CHUPS, 5);
        assertEquals(10, testList.getAmountOfCandy(Candy.BUBBA_CHUPS));
    }

    @Test
    public void ifCandyGetsRemovedTheAmountOfCandyShouldBeReducedByAnAmount() throws TooManyItemsException, NotEnougthItemsException {
        testList.addCandy(Candy.BUBBA_CHUPS, 10);
        testList.removeCandy(Candy.BUBBA_CHUPS, 10);
        assertEquals(0, testList.getAmountOfCandy(Candy.BUBBA_CHUPS));
    }

    @Test
    public void itShouldBeAbleToAddACandyCategoryToTheCandyList(){
        testList.addNewCandy(Candy.BUBBA_CHUPS);
    }

    @Test
    public void itShouldNotBePossibleToAddMoreCandyThanTheCapacityOfTheContainer(){
        assertThrows(TooManyItemsException.class, () -> testList.addCandy(Candy.BUBBA_CHUPS, 15));
    }

    @Test
    public void itShouldNotBePossibleToAddMoreCandyThanTheCapacityOfTheContainerWithMultipleCandies(){
        assertThrows(TooManyItemsException.class, () -> {
            testList.addCandy(Candy.BUBBA_CHUPS, 7);
            testList.addCandy(Candy.CHOCOLATE_BAR, 5);
        });
    }

    @Test
    public void itShouldNotBePossibleToRemoveMoreCandyThanTheCurrendAmount(){
        assertThrows(NotEnougthItemsException.class, () -> testList.removeCandy(Candy.BUBBA_CHUPS, 1));
    }
}