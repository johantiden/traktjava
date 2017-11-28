package com.github.johantiden.traktjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDto {
    public final String error;
    public final String errorDescription;

    @JsonCreator

    public ErrorDto(
            @JsonProperty("error") String error,
            @JsonProperty("error_description") String errorDescription
    ) {
        this.error = error;
        this.errorDescription = errorDescription;
    }
}
