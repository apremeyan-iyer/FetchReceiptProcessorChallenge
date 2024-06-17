package com.receipt.processor.services;

import com.receipt.processor.data.Points;
import com.receipt.processor.data.Receipt;

public interface pointCalculatorService {
    Points doTheMath(Receipt receipt);
}
