package com.github.pedrobacchini.springionicdomain.resource.exception;

import java.io.Serializable;

class StandardError implements Serializable {

    private static final long serialVersionUID = 1284470769804215207L;

    private Integer status;
    private String message;
    private Long timestamp;

    public StandardError(Integer status, String message, Long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
