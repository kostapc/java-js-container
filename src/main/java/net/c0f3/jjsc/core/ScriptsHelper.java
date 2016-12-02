package net.c0f3.jjsc.core;

import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.NativeJavaObject;


import java.lang.reflect.Array;

/**
 * User: KPC
 * Date: 23.04.13 - 20:50
 */
public class ScriptsHelper {
    private static Object[] emptyArray = new Object[0];

    public static Object[] convertArray(NativeArray jsArray) {
        if(jsArray.getLength()>Integer.MAX_VALUE) {
            throw new IndexOutOfBoundsException();
        }
        if(jsArray.getLength()==0) {
            return emptyArray;
        }
        Object first = ((NativeJavaObject) jsArray.get(0,null)).unwrap();
        Class clazz = first.getClass();
        Object[] retArray = (Object[]) Array.newInstance(clazz, (int) jsArray.getLength());
        retArray[0] = first;
        for(int i=1;i<jsArray.getLength();i++) {
            retArray[i] = ((NativeJavaObject) jsArray.get(i,null)).unwrap();
        }
        return retArray;
    }

    public static Object convertType(Object value) {
        if(value instanceof NativeArray) {
            return convertArray((NativeArray) value);
        }
        return value;
    }
}
