package io.github.loremipsumsharp;

import java.lang.reflect.Array;


public final class CollectionUtils {

    public static <T> T[] toArray(java.util.Collection<? extends T> collection, Class<T> itemClass) {
        if (collection != null && !collection.isEmpty()) {
            @SuppressWarnings("unchecked")
            T[] array = (T[]) Array.newInstance(itemClass, collection.size());
            return collection.toArray(array);
        }
        return null;
    }
}
