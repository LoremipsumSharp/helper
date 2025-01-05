package io.github.loremipsumsharp;

import java.util.function.Supplier;

public class StringAppender {
    private final StringBuilder stringBuilder;

    public StringAppender() {
        this.stringBuilder = new StringBuilder();
    }

    public StringAppender(String str) {
        this.stringBuilder = new StringBuilder(str);
    }

    public StringAppender append(String text) {
        this.stringBuilder.append(text);
        return this;
    }

    public StringAppender appendIf(Supplier<Boolean> condition, String text) {
        if (condition.get()) {
            this.append(text);
        }
        return this;
    }

    public StringAppender prepend(String text) {
        this.stringBuilder.insert(0, text);
        return this;
    }

    public StringAppender prependIf(Supplier<Boolean> condition, String text) {
        if (condition.get()) {
            this.prepend(text);
        }
        return this;
    }

    public String toString() {
        return this.stringBuilder.toString();
    }
}
