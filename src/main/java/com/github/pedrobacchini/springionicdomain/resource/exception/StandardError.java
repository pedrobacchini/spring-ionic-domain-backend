package com.github.pedrobacchini.springionicdomain.resource.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
class StandardError implements Serializable {

    private static final long serialVersionUID = 1284470769804215207L;

    private final Long timestamp;
    private final Integer status;
    private final String error;
    private final String message;
    private final String path;
}
