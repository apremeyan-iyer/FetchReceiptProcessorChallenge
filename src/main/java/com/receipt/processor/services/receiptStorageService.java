package com.receipt.processor.services;

import com.receipt.processor.data.IdOfReceipt;
import com.receipt.processor.data.Receipt;

public interface receiptStorageService {
    IdOfReceipt storeReceipt(Receipt receipt);
    Receipt loadReceipt(String id);
}
