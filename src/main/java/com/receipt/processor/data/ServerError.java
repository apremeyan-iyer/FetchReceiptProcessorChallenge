package com.receipt.processor.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Error returned when a request fails
 */
@Builder
@Getter
public class ServerError {

    /**
     * Description of the error returned
     */
    @NonNull private String message;

    /**
     * Error code returned
     */
    final private String code = "400";

}
