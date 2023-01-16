package ru.Kirill112002.sd.lab6.parser;

import ru.Kirill112002.sd.lab6.tokenizer.Token;

import java.util.List;

public interface Parser {
    List<Token> parse();
}
