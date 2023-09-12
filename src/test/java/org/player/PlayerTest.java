package org.player;

import org.candy.Candy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.location.School;
import org.util.TestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    Player testPlayer;

    @BeforeEach
    public void setUp(){
        testPlayer = new Player("TestPlayer", new School(), TestUtil.getTestCandyContainer());
    }

    @Test
    void ifAPlayerBuysAnAmountOfCandyItIsAddedToTheCandyContainer(){
        testPlayer.buyCandy(Candy.BUBBA_CHUPS, 10);
        assertEquals(10, testPlayer.getCandyAmount(Candy.BUBBA_CHUPS));
    }

    @Test
    void ifAPlayerSellsAnAmountOfCandyItIsRemovedFromTheCandyContainer(){
        testPlayer.buyCandy(Candy.BUBBA_CHUPS, 10);
        testPlayer.sellCandy(Candy.BUBBA_CHUPS, 10);
        assertEquals(0, testPlayer.getCandyAmount(Candy.BUBBA_CHUPS));
    }

}