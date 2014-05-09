package com.bertazoli.charity.client.application.core.validators;

import java.util.List;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.sencha.gxt.widget.core.client.form.validator.AbstractValidator;

public class MultipleOfValidator extends AbstractValidator<Integer> {
    private int multipleOf;
    private String message;

    public MultipleOfValidator(int multipleOf, String message) {
        this.multipleOf = multipleOf;
        this.message = message;
    }

    @Override
    public List<EditorError> validate(Editor<Integer> field, Integer value) {
        if (value % multipleOf != 0) {
            return createError(field, message, value);
        }
        return null;
    }
}
