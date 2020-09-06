package org.globaltester.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionHelper {

	public static void setField(Class<?> clazz, Object obj, String field, Object value) throws ReflectiveOperationException {
		Field f = clazz.getDeclaredField(field);
		f.setAccessible(true);
		f.set(obj, value);
	}

	public static Object getFieldValue(Class<?> clazz, Object obj, String field) throws ReflectiveOperationException {
		Field f = clazz.getDeclaredField(field);
		f.setAccessible(true);
		return f.get(obj);
	}

	public static Object invoke(Class<?> clazz, Object obj, String string, Class<?>[] parameterTypes, Object... params) throws ReflectiveOperationException {
		Method m = clazz.getDeclaredMethod(string, parameterTypes);
		m.setAccessible(true);
		return m.invoke(obj, params);
		
	}

}
