package ru.Kirill112002.sd.lab6.exception;

import ru.Kirill112002.sd.lab6.tokenizer.Token;

public class UnexpectedTokenException extends IllegalStateException {
    public UnexpectedTokenException(Token token) {
        super("Unexpected token: " + token + ".");
    }
}
