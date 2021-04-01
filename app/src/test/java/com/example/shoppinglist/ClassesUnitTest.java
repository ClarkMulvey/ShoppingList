package com.example.shoppinglist;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ClassesUnitTest {


    /*
    @Test
    public void testLocalStorage() {
        // Create dataHandler object
        DataHandler dataHandler = new DataHandler();
        ShoppingList list = new ShoppingList("testDefault");
        list.addItem(new ShoppingListItem("MtnDew", 10));

        dataHandler.saveList(list);

        assertEquals(list, dataHandler.loadList(list));
    }

     */

    @Test
    public void testCreatingShoppingListItem() {
        ShoppingListItem shoppingListItem = new ShoppingListItem("MtnDew",10);
        assertEquals(shoppingListItem.getName(), "MtnDew");
        assertEquals(java.util.Optional.of(shoppingListItem.getQuantity()), java.util.Optional.of(10));
        assertEquals(shoppingListItem.isCompleted(), false);

    }

    @Test
    public void testShoppingListItemIsAddedToShoppingList() {
        //Create an ArrayList
        ArrayList<ShoppingListItem> shoppingListItems = new ArrayList<>();
        //Create a ShoppingListItem
        ShoppingListItem shoppingListItem = new ShoppingListItem();
        shoppingListItem.setName("MtnDew");
        shoppingListItem.setQuantity(10);
        shoppingListItem.setCompleted(false);
        //Add shoppingListItem to the shoppingListItemsArrayList
        shoppingListItems.add(shoppingListItem);
        // create ShoppingList
        ShoppingList testDefaultList = new ShoppingList();
        //Add shoppingListItems to ShoppingList
        testDefaultList.setItems(shoppingListItems);

        // compare ShoppingList ArrayList to the ShoppingListItem
        assertEquals(shoppingListItem, testDefaultList.getItems().get(0));

    }

    @Test
    public void testDeletingShoppingListItemFromShoppingList(){
        //Create a ShoppingListItem
        ShoppingListItem shoppingListItem1 = new ShoppingListItem();
        shoppingListItem1.setName("MtnDew");
        shoppingListItem1.setQuantity(10);
        shoppingListItem1.setCompleted(false);
        //Create a Second ShoppingListItem
        ShoppingListItem shoppingListItem2 = new ShoppingListItem();
        shoppingListItem2.setName("Milk");
        shoppingListItem2.setQuantity(2);
        shoppingListItem2.setCompleted(false);
        // create ShoppingList
        ShoppingList testDefaultList = new ShoppingList();
        //Add 1st ShoppingList Item to ShoppingList
        testDefaultList.addItem(shoppingListItem1);
        //Add 2nd ShoppingList Item to ShoppingList
        testDefaultList.addItem(shoppingListItem2);
        //Delete 1st ShoppingListItem from ShoppingList
        testDefaultList.deleteItem(shoppingListItem1);
        //Compare the remaining ShoppingListItem name to ShoppingListItem2 name
        assertEquals(shoppingListItem2.getName(), testDefaultList.getItems().get(0).getName());
        //Compare the remaining ShoppingListItem Quantity to ShoppingListItem2 Quantity
        assertEquals(shoppingListItem2.getQuantity(), testDefaultList.getItems().get(0).getQuantity());
        //Compare the remaining ShoppingListItem Complete to ShoppingListItem2 Complete
        assertEquals(shoppingListItem2.isCompleted(), testDefaultList.getItems().get(0).isCompleted());
    }



    /*

    @Test
    public void testDefaultListAddedToUpcomingShoppingTripListDoesntChangeDefaultList() {
        // create default list

        // create upcoming shopping trip list

        // add something to upcoming shopping trip list

        // compare upcoming to default to make sure they do not match
    }

     */


}