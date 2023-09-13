package org.util;

import org.candy.Candy;
import org.candy.CandyContainer;
import org.candy.CandyList;
import org.location.Location;
import org.location.School;

public class TestUtil {
    public static final long TEST_CANDY_PRICE = 100;
    public static final long TEST_LOCATION_TRAVEL_PRICE = 100;

    public static CandyContainer getTestCandyContainer() {
        CandyContainer testContainer = new CandyList();
        testContainer.addNewCandy(Candy.BUBBA_CHUPS);
        return testContainer;
    }

    public static Location getTestLocation(){
        Location testLocation = new School("Test School", TEST_LOCATION_TRAVEL_PRICE);
        testLocation.setCandyPrice(Candy.BUBBA_CHUPS, TEST_CANDY_PRICE);
        return testLocation;
    }

    public static School getAnotherTestLocation() {
        return new School("Another Test School", TEST_LOCATION_TRAVEL_PRICE);
    }
}
