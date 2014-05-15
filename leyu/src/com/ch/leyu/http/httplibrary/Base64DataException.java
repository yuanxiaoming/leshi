package com.ch.leyu.http.httplibrary;

import java.io.IOException;

public class Base64DataException extends IOException {
    public Base64DataException(String detailMessage) {
        super(detailMessage);
    }
}