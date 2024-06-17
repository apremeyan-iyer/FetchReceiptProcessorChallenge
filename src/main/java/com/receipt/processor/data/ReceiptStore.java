package com.receipt.processor.data;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * HashMap of the stored receipts and their IDs
 */
@Component
@Getter
public class ReceiptStore {

    private final HashMap<String, Receipt> store = new HashMap<>();
}
