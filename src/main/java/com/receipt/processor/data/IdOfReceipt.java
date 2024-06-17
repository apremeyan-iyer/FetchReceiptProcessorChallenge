package com.receipt.processor.data;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;

/**
 * Class for returning the ID of the receipts
 */
@Builder
@Getter
public class IdOfReceipt {

    @NonNull
    String id;
}
