package com.bertazoli.charity.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ValidationException extends Exception implements IsSerializable {

    public ValidationException(String message) {
        super(message);
    }

    private static final long serialVersionUID = 6682925921465669816L;

}
