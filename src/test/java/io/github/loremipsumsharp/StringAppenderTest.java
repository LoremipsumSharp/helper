package io.github.loremipsumsharp;

import org.junit.Assert;
import org.junit.Test;


public class StringAppenderTest {
    @Test
    public void testAppend() {
        StringAppender appender = new StringAppender("Hello");
        appender.append("World");

        Assert.assertTrue(appender.toString().equals("HelloWorld"));
    }

    @Test
    public void testAppendIf() {
        StringAppender appender = new StringAppender("Hello");
        appender.appendIf(()->true, "World");
        Assert.assertTrue(appender.toString().equals("HelloWorld"));
        appender = new StringAppender("Hello");
        appender.appendIf(()->false, "Hello");
        Assert.assertTrue(appender.toString().equals("Hello"));
    }

    @Test
    public void testPrepend() {
        StringAppender appender = new StringAppender("World");
        appender.prepend("Hello");

        Assert.assertTrue(appender.toString().equals("HelloWorld"));
    }

    @Test
    public void testPrependIf() {
        StringAppender appender = new StringAppender("World");
        appender.prependIf(()->true, "Hello");
        Assert.assertTrue(appender.toString().equals("HelloWorld"));
        appender = new StringAppender("World");
        appender.prependIf(()->false, "Hello");
        Assert.assertTrue(appender.toString().equals("World"));
    }
}
