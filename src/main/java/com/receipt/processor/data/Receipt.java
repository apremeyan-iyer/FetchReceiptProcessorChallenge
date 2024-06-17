package com.receipt.processor.data;

import lombok.Getter;
import org.springframework.lang.NonNull;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

/**
 * Class for describing each receipt that is top be stored
 */
@Builder
@Getter
public class Receipt {

    /**
     * Name of retailer
     */
    @NonNull
    private String retailer;

    /**
     * Date of the receipt
     */
    @NonNull
    private LocalDate purchaseDate; // change to date type

    /**
     * Time of the receipt
     */
    @NonNull
    private String purchaseTime;

    /**
     * List of the items in the receipt
     */
    @NonNull
    private List<Item> items;

    /**
     * Total cost of the purchase
     */
    @NonNull
    private double total;

}
