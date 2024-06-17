package com.receipt.processor.data;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;

/**
 * Class for returning the calculated points
 */
@Builder
@Getter
public class Points {

    @NonNull
    int points;

}
