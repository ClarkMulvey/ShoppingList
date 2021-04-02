package com.example.shoppinglist.interfaces;

/**
 * This Interface defines a custom button listener to be used in the list view adapters
 *
 * The custom button listener is used to enable the option to click a FAB in order to edit the
 * specific list
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public interface CustomButtonListener {
    public void clickEditList(Integer position);
}