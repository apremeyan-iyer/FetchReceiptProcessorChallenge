package com.receipt.processor.controllers;

import com.receipt.processor.data.IdOfReceipt;
import com.receipt.processor.data.Points;
import com.receipt.processor.data.Receipt;
import com.receipt.processor.data.ServerError;
import com.receipt.processor.exception.ServerErrorException;
import com.receipt.processor.services.impl.pointCalculatorServiceImpl;
import com.receipt.processor.services.impl.receiptStorageServiceImpl;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    public final receiptStorageServiceImpl receiptStorageService;
    public final pointCalculatorServiceImpl pointCalculatorService;


    public ReceiptController(receiptStorageServiceImpl receiptStorageService, pointCalculatorServiceImpl pointCalculatorService) {
        this.receiptStorageService = receiptStorageService;
        this.pointCalculatorService = pointCalculatorService;
    }

    /**
     * /receipts/process API endpoint
     * @param receipt Receipt type object
     * @return id of the stored receipt
     * @throws ServerErrorException when server error occurs
     */
    @PostMapping("/process")
    public ResponseEntity<IdOfReceipt> receiptProcessor(@RequestBody Receipt receipt) throws ServerErrorException {
        try {
            final IdOfReceipt id = receiptStorageService.storeReceipt(receipt);
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(id);
        } catch (Exception e) {
            final String errorMessage = "Invalid receipt or server down";
            final ServerError error = ServerError.builder()
                    .message(errorMessage)
                    .build();
            throw new ServerErrorException(errorMessage, error);
        }
    }

    /**
     * /receipt/{id}/points API endpoint
     * @param receiptId String ID of the receipt for which points are to be calculated
     * @return the points of the receipt whose ID is provided
     * @throws ServerErrorException when invalid ID or server is down
     */
    @GetMapping("/{id}/points")
    public ResponseEntity<Points> receiptPoints(@PathVariable("id") String receiptId) throws ServerErrorException {
        try {
            final Receipt receipt = receiptStorageService.loadReceipt(receiptId);
            final Points points = pointCalculatorService.doTheMath(receipt);
            return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(points);
        } catch (Exception e) {
            final String errorMessage = "Invalid receipt ID or server down";
            final ServerError error = ServerError.builder()
                    .message(errorMessage)
                    .build();
            throw new ServerErrorException(errorMessage, error);
        }

    }
}
