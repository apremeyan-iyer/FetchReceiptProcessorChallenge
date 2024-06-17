package com.receipt.processor.data;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;

/**
 * Class for describing each item
 */
@Builder
@Getter
public class Item {

    /**
     * Description of each item
     */
    @NonNull
    private String shortDescription;

    /**
     * Price of the described item
     */
    @NonNull
    private double price;

}
