package org.util;

import org.candy.Candy;
import org.candy.CandyContainer;
import org.candy.CandyList;

public class TestUtil {

    public static CandyContainer getTestCandyContainer() {
        CandyContainer testContainer = new CandyList();
        testContainer.addNewCandy(Candy.BUBBA_CHUPS);
        return testContainer;
    }
}
