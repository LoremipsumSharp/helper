package io.github.loremipsumsharp;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;



public class UtilsTest {
    @Test
    public void testToArray() {
        Collection<Integer> intCollection = new ArrayList<>();
        intCollection.add(1);
        intCollection.add(2);

        Integer[] intArr = CollectionUtils.toArray(intCollection, Integer.class);
        Assert.assertTrue(intArr.length == 2);
    }
}
