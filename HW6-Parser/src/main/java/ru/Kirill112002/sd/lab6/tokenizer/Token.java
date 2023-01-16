package ru.Kirill112002.sd.lab6.tokenizer;

@SuppressWarnings("unused")
public record Token(TokenType type, String value, int position) {
    public static Token begin() {
        return new Token(TokenType.BEGIN, "", -1);
    }

    public static Token end(int size) {
        return new Token(TokenType.END, "", size);
    }

    public boolean isBegin() {
        return this.type == TokenType.BEGIN;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isEnd() {
        return this.type == TokenType.END;
    }
}
