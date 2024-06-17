package com.receipt.processor.services.impl;

import com.receipt.processor.data.IdOfReceipt;
import com.receipt.processor.data.ReceiptStore;
import com.receipt.processor.data.Receipt;
import com.receipt.processor.services.receiptStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class receiptStorageServiceImpl implements receiptStorageService {

    @Autowired
    ReceiptStore receiptStore;

    /**
     * Store the receipt and return the generated ID
     * @param receipt Receipt object
     * @return ID generated
     */
    @Override
    public IdOfReceipt storeReceipt(Receipt receipt){
        final UUID id = UUID.randomUUID();
        receiptStore.getStore().put(id.toString(),receipt);
        return IdOfReceipt.builder().id(id.toString()).build();
    }

    /**
     * Retreives the receipt corresponding to the generated ID
     * @param id the ID associated with the stored receipt
     * @return the receipt corresponding to the generated ID
     */
    @Override
    public Receipt loadReceipt(String id){
        return receiptStore.getStore().get(id);
    }
}
