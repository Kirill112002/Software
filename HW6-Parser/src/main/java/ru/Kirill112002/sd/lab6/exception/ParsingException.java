package ru.Kirill112002.sd.lab6.exception;

public class ParsingException extends IllegalStateException {
    public ParsingException() {
        super("Unknown parsing exception.");
    }
}
