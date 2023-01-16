package ru.Kirill112002.sd.lab6.visitor;

import ru.Kirill112002.sd.lab6.parser.Parser;
import ru.Kirill112002.sd.lab6.tokenizer.Token;
import ru.Kirill112002.sd.lab6.tokenizer.Tokenizer;

import java.util.Iterator;
import java.util.function.Supplier;

public abstract class ParseableVisitor extends TokenVisitor {
    private final Supplier<Iterator<Token>> tokenIterator;

    public ParseableVisitor(Parser parser) {
        this.tokenIterator = parser.parse()::iterator;
    }

    public ParseableVisitor(CharSequence charSequence) {
        this.tokenIterator = () -> new Tokenizer(charSequence).iterator();
    }

    public Iterator<Token> getIterator() {
        return tokenIterator.get();
    }
}
