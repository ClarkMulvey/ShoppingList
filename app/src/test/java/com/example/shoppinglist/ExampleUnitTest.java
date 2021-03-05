package com.example.shoppinglist;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testLocalStorage() {
        // Create dataHandler object
        //ShoppingListDefault list = new ShoppingListDefault("testDefault");
        //list.addItem(new ShoppingListItem("MtnDew", 10));

        //dataHandler.saveList(list);

        //assertEquals(list, dataHandler.loadList(list));
    }

    @Test
    public void testDefaultListAddedToUpcomingShoppingTripList() {
        // create default list

        // create upcoming shopping trip list

        // compare upcoming to default to make sure they match
    }

    @Test
    public void testDefaultListAddedToUpcomingShoppingTripListDoesntChangeDefaultList() {
        // create default list

        // create upcoming shopping trip list

        // add something to upcoming shopping trip list

        // compare upcoming to default to make sure they do not match
    }


}