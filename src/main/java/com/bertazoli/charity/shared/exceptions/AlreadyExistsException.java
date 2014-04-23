package com.bertazoli.charity.shared.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AlreadyExistsException extends Exception implements IsSerializable {
    private static final long serialVersionUID = 2322030779900040566L;

    public AlreadyExistsException(String message) {
        super(message);
    }
}
