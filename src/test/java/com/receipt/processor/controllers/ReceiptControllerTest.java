package com.receipt.processor.controllers;

import com.receipt.processor.data.IdOfReceipt;
import com.receipt.processor.data.Item;
import com.receipt.processor.data.Points;
import com.receipt.processor.data.Receipt;
import com.receipt.processor.exception.ServerErrorException;
import com.receipt.processor.services.impl.pointCalculatorServiceImpl;
import com.receipt.processor.services.impl.receiptStorageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ReceiptControllerTest {

    @Mock
    private receiptStorageServiceImpl receiptStorageService;

    @Mock
    private pointCalculatorServiceImpl pointCalculatorService;

    @InjectMocks
    private ReceiptController receiptController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<Item> generateListOfItems(){
        List<Item> items = new ArrayList<>();
        items.add(Item.builder()
                .shortDescription("Mountain Dew 12PK")
                .price(6.49).build());
        items.add(Item.builder()
                .shortDescription("Emils Cheese Pizza")
                .price(12.25).build());
        items.add(Item.builder()
                .shortDescription("Knorr Creamy Chicken")
                .price(1.26).build());
        items.add(Item.builder()
                .shortDescription("Doritos Nacho Cheese")
                .price(3.35).build());
        items.add(Item.builder()
                .shortDescription("   Klarbrunn 12-PK 12 FL OZ  ")
                .price(12.00).build());
        return items;
    }

    private Receipt generateReceipt(){
        return Receipt.builder()
                .retailer("Target")
                .purchaseDate(LocalDate.parse("2022-01-01"))
                .purchaseTime("13:01")
                .items(generateListOfItems())
                .total(35.35)
                .build();
    }

    @Test
    public void testReceiptProcessor_validReceipt() throws ServerErrorException {

        final Receipt receipt = generateReceipt();
        final UUID id = UUID.randomUUID();
        final IdOfReceipt expectedResponse = IdOfReceipt.builder().id(id.toString()).build();
        when(receiptStorageService.storeReceipt(receipt)).thenReturn(IdOfReceipt.builder().id(id.toString()).build());

        // when
        final ResponseEntity<IdOfReceipt> response = receiptController.receiptProcessor(receipt);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getId(), response.getBody().getId());
    }

    @Test
    public void testReceiptProcessor_invalidReceipt_returnsError(){
        final Receipt receipt = generateReceipt();
        when(receiptStorageService.storeReceipt(receipt)).thenThrow(new RuntimeException("Receipt invalid"));

        // When
        final ServerErrorException e = assertThrows(ServerErrorException.class, () -> receiptController.receiptProcessor(receipt));

        // Then
        assertEquals(e.getMessage(), "Invalid receipt or server down");

    }

    @Test
    public void receiptPoints_returnsTotalPoints() throws ServerErrorException {
        final String id = "sampleIdForTest";
        final Receipt receipt = generateReceipt();
        final Points points  = Points.builder().build();
        when(receiptStorageService.loadReceipt(id)).thenReturn(receipt);
        when(pointCalculatorService.doTheMath(receipt)).thenReturn(points);

        // when
        final ResponseEntity<Points> response = receiptController.receiptPoints(id);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(points, response.getBody());
    }

    @Test
    public void receiptPoints_invalidId_returnsError(){
        final String id = "sampleIdForTest";
        when(receiptStorageService.loadReceipt(id)).thenThrow(new RuntimeException("Receipt not found"));

        // When
        final ServerErrorException e = assertThrows(ServerErrorException.class, () -> receiptController.receiptPoints(id));

        // Then
        assertEquals(e.getMessage(), "Invalid receipt ID or server down");
    }
}
