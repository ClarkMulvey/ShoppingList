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
    public void testTwo() {
        assertEquals(3, 1 + 2);
    }

    @Test
    public void testThree() {

        String word = "Dad";
        assertEquals("Dad", word);
    }

    /*
    @Test
    public void testLocalStorage() {

        ShoppingListDefault list = new ShoppingListDefault("testDefault");
        list.addItem(new ShoppingListItem("MtnDew", 10);
        // Create data handlerobject

        //dataHandler.saveList(list);

        //assertEquals(list, word);
    };
    */

}