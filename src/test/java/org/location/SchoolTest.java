package org.location;

import org.candy.Candy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {
    School testSchool;
    @BeforeEach
    public void setUp(){
        testSchool = new School("Test", 0);
    }
    @Test
    public void shouldBePossibleToSetAPriceForAGivenCandy(){
        testSchool.setCandyPrice(Candy.BUBBA_CHUPS, 1000);
        assertEquals(1000, testSchool.getPriceForCandy(Candy.BUBBA_CHUPS));
    }

}