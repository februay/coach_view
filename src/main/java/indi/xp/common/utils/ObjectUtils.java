
package indi.xp.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import indi.xp.coachview.common.BusinessErrorCodeEnum;
import indi.xp.common.exception.BusinessException;

/**
 * change this template use File | Settings | File Templates.
 */
public final class ObjectUtils {

    private static final Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    private static final int INITIAL_HASH = 7;
    private static final int MULTIPLIER = 31;

    private static final String EMPTY_STRING = "";
    private static final String NULL_STRING = "null";
    private static final String ARRAY_START = "{";
    private static final String ARRAY_END = "}";
    private static final String EMPTY_ARRAY = ARRAY_START + ARRAY_END;
    private static final String ARRAY_ELEMENT_SEPARATOR = ", ";

    @SuppressWarnings("rawtypes")
    public static Boolean isWrapClass(Class clazz) {
        try {
            return ((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 对象克隆，支持单个对象，数组与集合
     *
     * @param src
     * @return
     */
    public static Object byteClone(Object src) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(src);
            out.close();
            ByteArrayInputStream bin = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bin);
            Object clone = in.readObject();
            in.close();
            return (clone);
        } catch (ClassNotFoundException e) {
            throw new InternalError(e.toString());
        } catch (StreamCorruptedException e) {
            throw new InternalError(e.toString());
        } catch (IOException e) {
            throw new InternalError(e.toString());
        }
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) throws Exception {
        if (map == null) {
            return null;
        }
        T obj = beanClass.newInstance();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null && map.containsKey(property.getName())) {
                try {
                    setter.invoke(obj, map.get(property.getName()));
                } catch (Exception e) {
                    logger.error(property.getName() + " value " + map.get(property.getName()) + " type not match", e);
                }
            }
        }
        return obj;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(obj) : null;
            map.put(key, value);
        }
        return map;
    }

    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Collection objects) {
        return objects == null || objects.size() == 0;
    }

    /**
     * Return whether the given throwable is a checked exception: that is,
     * neither a RuntimeException nor an Error.
     *
     * @param ex
     *            the throwable to check
     * @return whether the throwable is a checked exception
     * @see Exception
     * @see RuntimeException
     * @see Error
     */
    public static boolean isCheckedException(Throwable ex) {
        return !(ex instanceof RuntimeException || ex instanceof Error);
    }

    /**
     * Check whether the given exception is compatible with the exceptions
     * declared in a throws clause.
     *
     * @param ex
     *            the exception to checked
     * @param declaredExceptions
     *            the exceptions declared in the throws clause
     * @return whether the given exception is compatible
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static boolean isCompatibleWithThrowsClause(Throwable ex, Class[] declaredExceptions) {
        if (!isCheckedException(ex)) {
            return true;
        }
        if (declaredExceptions != null) {
            int i = 0;
            while (i < declaredExceptions.length) {
                if (declaredExceptions[i].isAssignableFrom(ex.getClass())) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    /**
     * Determine whether the given object is an array: either an Object array or
     * a primitive array.
     *
     * @param obj
     *            the object to check
     */
    public static boolean isArray(Object obj) {
        return (obj != null && obj.getClass().isArray());
    }

    /**
     * Determine whether the given array is empty: i.e. <code>null</code> or of
     * zero length.
     *
     * @param array
     *            the array to check
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * Check whether the given array contains the given element.
     *
     * @param array
     *            the array to check (may be <code>null</code>, in which case
     *            the return value will always be <code>false</code>)
     * @param element
     *            the element to check for
     * @return whether the element has been found in the given array
     */
    public static boolean containsElement(Object[] array, Object element) {
        if (array == null) {
            return false;
        }
        for (Object arrayEle : array) {
            if (nullSafeEquals(arrayEle, element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given array of enum constants contains a constant with
     * the given name, ignoring case when determining a match.
     *
     * @param enumValues
     *            the enum values to check, typically the reports of a call to
     *            MyEnum.values()
     * @param constant
     *            the constant name to find (must not be null or empty string)
     * @return whether the constant has been found in the given array
     */
    public static boolean containsConstant(Enum<?>[] enumValues, String constant) {
        return containsConstant(enumValues, constant, false);
    }

    /**
     * Check whether the given array of enum constants contains a constant with
     * the given name.
     *
     * @param enumValues
     *            the enum values to check, typically the reports of a call to
     *            MyEnum.values()
     * @param constant
     *            the constant name to find (must not be null or empty string)
     * @param caseSensitive
     *            whether case is significant in determining a match
     * @return whether the constant has been found in the given array
     */
    public static boolean containsConstant(Enum<?>[] enumValues, String constant, boolean caseSensitive) {
        for (Enum<?> candidate : enumValues) {
            if (caseSensitive ? candidate.toString().equals(constant)
                : candidate.toString().equalsIgnoreCase(constant)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Case insensitive alternative to {@link Enum#valueOf(Class, String)}.
     *
     * @param <E>
     *            the concrete Enum type
     * @param enumValues
     *            the array of all Enum constants in question, usually per
     *            Enum.values()
     * @param constant
     *            the constant to get the enum value of
     * @throws IllegalArgumentException
     *             if the given constant is not found in the given array of enum
     *             values. Use {@link #containsConstant(Enum[], String)} as a
     *             guard to avoid this exception.
     */
    public static <E extends Enum<?>> E caseInsensitiveValueOf(E[] enumValues, String constant) {
        for (E candidate : enumValues) {
            if (candidate.toString().equalsIgnoreCase(constant)) {
                return candidate;
            }
        }
        throw new IllegalArgumentException(String.format("constant [%s] does not exist in enum type %s", constant,
            enumValues.getClass().getComponentType().getName()));
    }

    /**
     * Append the given object to the given array, returning a new array
     * consisting of the input array contents plus the given object.
     *
     * @param array
     *            the array to append to (can be <code>null</code>)
     * @param obj
     *            the object to append
     * @return the new array (of the same component type; never
     *         <code>null</code>)
     */
    public static <A, O extends A> A[] addObjectToArray(A[] array, O obj) {
        Class<?> compType = Object.class;
        if (array != null) {
            compType = array.getClass().getComponentType();
        } else if (obj != null) {
            compType = obj.getClass();
        }
        int newArrLength = (array != null ? array.length + 1 : 1);
        @SuppressWarnings("unchecked")
        A[] newArr = (A[]) Array.newInstance(compType, newArrLength);
        if (array != null) {
            System.arraycopy(array, 0, newArr, 0, array.length);
        }
        newArr[newArr.length - 1] = obj;
        return newArr;
    }

    /**
     * Convert the given array (which may be a primitive array) to an object
     * array (if necessary of primitive wrapper objects).
     * <p>
     * A <code>null</code> source value will be converted to an empty Object
     * array.
     *
     * @param source
     *            the (potentially primitive) array
     * @return the corresponding object array (never <code>null</code>)
     * @throws IllegalArgumentException
     *             if the parameter is not an array
     */
    @SuppressWarnings("rawtypes")
    public static Object[] toObjectArray(Object source) {
        if (source instanceof Object[]) {
            return (Object[]) source;
        }
        if (source == null) {
            return new Object[0];
        }
        if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        }
        int length = Array.getLength(source);
        if (length == 0) {
            return new Object[0];
        }
        Class wrapperType = Array.get(source, 0).getClass();
        Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
        for (int i = 0; i < length; i++) {
            newArray[i] = Array.get(source, i);
        }
        return newArray;
    }

    // ---------------------------------------------------------------------
    // Convenience methods for content-based equality/hash-code handling
    // ---------------------------------------------------------------------

    /**
     * Determine if the given objects are equal, returning <code>true</code> if
     * both are <code>null</code> or <code>false</code> if only one is
     * <code>null</code>.
     * <p>
     * Compares arrays with <code>Arrays.equals</code>, performing an equality
     * check based on the array elements rather than the array reference.
     *
     * @param o1
     *            first Object to compare
     * @param o2
     *            second Object to compare
     * @return whether the given objects are equal
     * @see java.util.Arrays#equals
     */
    public static boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }
        if (o1.getClass().isArray() && o2.getClass().isArray()) {
            if (o1 instanceof Object[] && o2 instanceof Object[]) {
                return Arrays.equals((Object[]) o1, (Object[]) o2);
            }
            if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
                return Arrays.equals((boolean[]) o1, (boolean[]) o2);
            }
            if (o1 instanceof byte[] && o2 instanceof byte[]) {
                return Arrays.equals((byte[]) o1, (byte[]) o2);
            }
            if (o1 instanceof char[] && o2 instanceof char[]) {
                return Arrays.equals((char[]) o1, (char[]) o2);
            }
            if (o1 instanceof double[] && o2 instanceof double[]) {
                return Arrays.equals((double[]) o1, (double[]) o2);
            }
            if (o1 instanceof float[] && o2 instanceof float[]) {
                return Arrays.equals((float[]) o1, (float[]) o2);
            }
            if (o1 instanceof int[] && o2 instanceof int[]) {
                return Arrays.equals((int[]) o1, (int[]) o2);
            }
            if (o1 instanceof long[] && o2 instanceof long[]) {
                return Arrays.equals((long[]) o1, (long[]) o2);
            }
            if (o1 instanceof short[] && o2 instanceof short[]) {
                return Arrays.equals((short[]) o1, (short[]) o2);
            }
        }
        return false;
    }

    /**
     * Return as hash code for the given object; typically the value of
     * <code>{@link Object#hashCode()}</code>. If the object is an array, this
     * method will delegate to any of the <code>nullSafeHashCode</code> methods
     * for arrays in this class. If the object is <code>null</code>, this method
     * returns 0.
     *
     * @see #nullSafeHashCode(Object[])
     * @see #nullSafeHashCode(boolean[])
     * @see #nullSafeHashCode(byte[])
     * @see #nullSafeHashCode(char[])
     * @see #nullSafeHashCode(double[])
     * @see #nullSafeHashCode(float[])
     * @see #nullSafeHashCode(int[])
     * @see #nullSafeHashCode(long[])
     * @see #nullSafeHashCode(short[])
     */
    public static int nullSafeHashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj.getClass().isArray()) {
            if (obj instanceof Object[]) {
                return nullSafeHashCode((Object[]) obj);
            }
            if (obj instanceof boolean[]) {
                return nullSafeHashCode((boolean[]) obj);
            }
            if (obj instanceof byte[]) {
                return nullSafeHashCode((byte[]) obj);
            }
            if (obj instanceof char[]) {
                return nullSafeHashCode((char[]) obj);
            }
            if (obj instanceof double[]) {
                return nullSafeHashCode((double[]) obj);
            }
            if (obj instanceof float[]) {
                return nullSafeHashCode((float[]) obj);
            }
            if (obj instanceof int[]) {
                return nullSafeHashCode((int[]) obj);
            }
            if (obj instanceof long[]) {
                return nullSafeHashCode((long[]) obj);
            }
            if (obj instanceof short[]) {
                return nullSafeHashCode((short[]) obj);
            }
        }
        return obj.hashCode();
    }

    /**
     * Return a hash code based on the contents of the specified array. If
     * <code>array</code> is <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(Object[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + nullSafeHashCode(array[i]);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If
     * <code>array</code> is <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(boolean[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + hashCode(array[i]);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If
     * <code>array</code> is <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(byte[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + array[i];
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If
     * <code>array</code> is <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(char[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + array[i];
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If
     * <code>array</code> is <code>null</code>, this method returns 0.
     */
    @SuppressWarnings("unused")
    public static int nullSafeHashCode(double[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (double anArray : array) {
            hash = MULTIPLIER * hash + hashCode(anArray);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If
     * <code>array</code> is <code>null</code>, this method returns 0.
     */
    @SuppressWarnings("unused")
    public static int nullSafeHashCode(float[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (float anArray : array) {
            hash = MULTIPLIER * hash + hashCode(anArray);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If
     * <code>array</code> is <code>null</code>, this method returns 0.
     */
    @SuppressWarnings("unused")
    public static int nullSafeHashCode(int[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int anArray : array) {
            hash = MULTIPLIER * hash + anArray;
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If
     * <code>array</code> is <code>null</code>, this method returns 0.
     */
    @SuppressWarnings("unused")
    public static int nullSafeHashCode(long[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (long anArray : array) {
            hash = MULTIPLIER * hash + hashCode(anArray);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If
     * <code>array</code> is <code>null</code>, this method returns 0.
     */
    @SuppressWarnings("unused")
    public static int nullSafeHashCode(short[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (short anArray : array) {
            hash = MULTIPLIER * hash + anArray;
        }
        return hash;
    }

    /**
     * Return the same value as <code>{@link Boolean#hashCode()}</code>.
     *
     * @see Boolean#hashCode()
     */
    public static int hashCode(boolean bool) {
        return bool ? 1231 : 1237;
    }

    /**
     * Return the same value as <code>{@link Double#hashCode()}</code>.
     *
     * @see Double#hashCode()
     */
    public static int hashCode(double dbl) {
        long bits = Double.doubleToLongBits(dbl);
        return hashCode(bits);
    }

    /**
     * Return the same value as <code>{@link Float#hashCode()}</code>.
     *
     * @see Float#hashCode()
     */
    public static int hashCode(float flt) {
        return Float.floatToIntBits(flt);
    }

    /**
     * Return the same value as <code>{@link Long#hashCode()}</code>.
     *
     * @see Long#hashCode()
     */
    public static int hashCode(long lng) {
        return (int) (lng ^ (lng >>> 32));
    }

    // ---------------------------------------------------------------------
    // Convenience methods for toString output
    // ---------------------------------------------------------------------

    /**
     * Return a String representation of an object's overall identity.
     *
     * @param obj
     *            the object (may be <code>null</code>)
     * @return the object's identity as String representation, or an empty
     *         String if the object was <code>null</code>
     */
    public static String identityToString(Object obj) {
        if (obj == null) {
            return EMPTY_STRING;
        }
        return obj.getClass().getName() + "@" + getIdentityHexString(obj);
    }

    /**
     * Return a hex String form of an object's identity hash code.
     *
     * @param obj
     *            the object
     * @return the object's identity code in hex notation
     */
    public static String getIdentityHexString(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    /**
     * Return a content-based String representation if <code>obj</code> is not
     * <code>null</code>; otherwise returns an empty String.
     * <p>
     * Differs from {@link #nullSafeToString(Object)} in that it returns an
     * empty String rather than "null" for a <code>null</code> value.
     *
     * @param obj
     *            the object to build a display String for
     * @return a display String representation of <code>obj</code>
     * @see #nullSafeToString(Object)
     */
    public static String getDisplayString(Object obj) {
        if (obj == null) {
            return EMPTY_STRING;
        }
        return nullSafeToString(obj);
    }

    /**
     * Determine the class name for the given object.
     * <p>
     * Returns <code>"null"</code> if <code>obj</code> is <code>null</code>.
     *
     * @param obj
     *            the object to introspect (may be <code>null</code>)
     * @return the corresponding class name
     */
    public static String nullSafeClassName(Object obj) {
        return (obj != null ? obj.getClass().getName() : NULL_STRING);
    }

    /**
     * Return a String representation of the specified Object.
     * <p>
     * Builds a String representation of the contents in case of an array.
     * Returns <code>"null"</code> if <code>obj</code> is <code>null</code>.
     *
     * @param obj
     *            the object to build a String representation for
     * @return a String representation of <code>obj</code>
     */
    public static String nullSafeToString(Object obj) {
        if (obj == null) {
            return NULL_STRING;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Object[]) {
            return nullSafeToString((Object[]) obj);
        }
        if (obj instanceof boolean[]) {
            return nullSafeToString((boolean[]) obj);
        }
        if (obj instanceof byte[]) {
            return nullSafeToString((byte[]) obj);
        }
        if (obj instanceof char[]) {
            return nullSafeToString((char[]) obj);
        }
        if (obj instanceof double[]) {
            return nullSafeToString((double[]) obj);
        }
        if (obj instanceof float[]) {
            return nullSafeToString((float[]) obj);
        }
        if (obj instanceof int[]) {
            return nullSafeToString((int[]) obj);
        }
        if (obj instanceof long[]) {
            return nullSafeToString((long[]) obj);
        }
        if (obj instanceof short[]) {
            return nullSafeToString((short[]) obj);
        }
        String str = obj.toString();
        return (str != null ? str : EMPTY_STRING);
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements,
     * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are
     * separated by the characters <code>", "</code> (a comma followed by a
     * space). Returns <code>"null"</code> if <code>array</code> is
     * <code>null</code>.
     *
     * @param array
     *            the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(Object[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(String.valueOf(array[i]));
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements,
     * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are
     * separated by the characters <code>", "</code> (a comma followed by a
     * space). Returns <code>"null"</code> if <code>array</code> is
     * <code>null</code>.
     *
     * @param array
     *            the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(boolean[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }

            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements,
     * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are
     * separated by the characters <code>", "</code> (a comma followed by a
     * space). Returns <code>"null"</code> if <code>array</code> is
     * <code>null</code>.
     *
     * @param array
     *            the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(byte[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements,
     * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are
     * separated by the characters <code>", "</code> (a comma followed by a
     * space). Returns <code>"null"</code> if <code>array</code> is
     * <code>null</code>.
     *
     * @param array
     *            the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(char[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append("'").append(array[i]).append("'");
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements,
     * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are
     * separated by the characters <code>", "</code> (a comma followed by a
     * space). Returns <code>"null"</code> if <code>array</code> is
     * <code>null</code>.
     *
     * @param array
     *            the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(double[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }

            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements,
     * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are
     * separated by the characters <code>", "</code> (a comma followed by a
     * space). Returns <code>"null"</code> if <code>array</code> is
     * <code>null</code>.
     *
     * @param array
     *            the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(float[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }

            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements,
     * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are
     * separated by the characters <code>", "</code> (a comma followed by a
     * space). Returns <code>"null"</code> if <code>array</code> is
     * <code>null</code>.
     *
     * @param array
     *            the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(int[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements,
     * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are
     * separated by the characters <code>", "</code> (a comma followed by a
     * space). Returns <code>"null"</code> if <code>array</code> is
     * <code>null</code>.
     *
     * @param array
     *            the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(long[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements,
     * enclosed in curly braces (<code>"{}"</code>). Adjacent elements are
     * separated by the characters <code>", "</code> (a comma followed by a
     * space). Returns <code>"null"</code> if <code>array</code> is
     * <code>null</code>.
     *
     * @param array
     *            the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(short[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(ARRAY_START);
            } else {
                sb.append(ARRAY_ELEMENT_SEPARATOR);
            }
            sb.append(array[i]);
        }
        sb.append(ARRAY_END);
        return sb.toString();
    }

    /**
     * 公共的销毁对象方法，用于内存回收
     */
    public static void destroy(Object obj) {
        obj = null;
    }

    /**
     * 安全关闭流
     */
    public static void safeClose(Object... objects) {
        boolean debugEnabled = logger.isDebugEnabled();

        if (objects == null || objects.length == 0) {
            logger.info("safeClose(...) was invoked with null or empty array: {}", objects);
            return;
        }

        for (Object obj : objects) {
            if (obj != null) {
                if (debugEnabled) {
                    logger.debug("Trying to safely close {}", obj);
                }

                if (obj instanceof Flushable) {
                    try {
                        ((Flushable) obj).flush();
                    } catch (Exception e) {
                        if (debugEnabled) {
                            logger.debug("Flushing Flushable failed", e);
                        }
                    }
                }

                if (obj instanceof Closeable) {
                    try {
                        ((Closeable) obj).close();
                    } catch (IOException e) {
                        if (debugEnabled) {
                            logger.debug("Closing Closeable failed", e);
                        }
                    }
                } else if (obj instanceof Connection) {
                    try {
                        ((Connection) obj).close();
                    } catch (Exception e) {
                        if (debugEnabled) {
                            logger.debug("Closing Connection failed", e);
                        }
                    }
                } else if (obj instanceof Statement) {
                    try {
                        ((Statement) obj).close();
                    } catch (Exception e) {
                        if (debugEnabled) {
                            logger.debug("Closing Statement failed", e);
                        }
                    }
                } else if (obj instanceof ResultSet) {
                    try {
                        ((ResultSet) obj).close();
                    } catch (Exception e) {
                        if (debugEnabled) {
                            logger.debug("Closing ResultSet failed", e);
                        }
                    }
                } else {
                    logger.info("obj was neither Closeable, Connection, Statement or ResultSet.");

                    try {
                        Method method = obj.getClass().getMethod("close", new Class[0]);
                        if (method == null) {
                            logger.info("obj did not have a close() method, ignoring");
                        } else {
                            method.setAccessible(true);
                            method.invoke(obj);
                        }
                    } catch (InvocationTargetException e) {
                        logger.warn("Invoking close() by reflection threw exception", e);
                    } catch (Exception e) {
                        logger.warn("Could not invoke close() by reflection", e);
                    }
                }
            }
        }
    }

    /**
     * 将对象列表转为带表头的二维数据列表（第一行为表头）
     * 
     * @param objList
     *            对象列表
     * @param headerList
     *            表头列表
     * @param nameToPropertyMapping
     *            表头和对象属性名对应关系
     * @return
     * @date: 2018年8月10日
     * @author peng.xu
     */
    public static <T> List<List<String>> parseToRowList(List<T> objList, List<String> headerList,
        Map<String, String> nameToPropertyMapping) {
        List<List<String>> rowList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(objList)) {
            for (T obj : objList) {
                try {
                    List<String> row = new ArrayList<String>();
                    Map<String, Object> objMap = ObjectUtils.objectToMap(obj);
                    for (String header : headerList) {
                        String property = nameToPropertyMapping.get(header);
                        if (objMap.get(property) != null
                            && StringUtils.isNotBlank(String.valueOf(objMap.get(property)))) {
                            row.add(String.valueOf(objMap.get(property)));
                        } else {
                            row.add("");
                        }
                    }
                    rowList.add(row);
                } catch (Exception e) {
                    logger.error("parse object<{}> to row error, member=" + JSON.toJSONString(obj),
                        obj.getClass().getName(), e);
                }
            }
        }
        return rowList;
    }

    /**
     * 将带表头的二维数据列表（第一行为表头）转为对象列表
     * 
     * @param rowList
     * @param nameToPropertyMapping
     * @param beanClass
     * @return
     * @date: 2018年8月10日
     * @author peng.xu
     */
    public static <T> List<T> parseToObjectList(List<List<String>> rowList, Map<String, String> nameToPropertyMapping,
        Class<T> beanClass) {
        List<T> objList = new ArrayList<>();
        boolean mismatch = true; // 不匹配： 没有列能够匹配上
        if (CollectionUtils.isNotEmpty(rowList) && rowList.size() > 1) {
            // 第一行为表头信息
            List<String> headerList = rowList.get(0);
            for (int i = 1; i < rowList.size(); i++) {
                List<String> row = rowList.get(i);
                try {
                    Map<String, Object> objMap = new HashMap<String, Object>();
                    for (int j = 0; j < headerList.size(); j++) {
                        String header = headerList.get(j);
                        header = header != null ? header.trim() : null;
                        if (nameToPropertyMapping.containsKey(header)) {
                            mismatch = false;
                            String property = nameToPropertyMapping.get(header);
                            if (StringUtils.isNotBlank(property)) {
                                objMap.put(property, StringUtils.isNotBlank(row.get(j)) ? row.get(j) : null);
                            }
                        }
                    }
                    // T obj = ObjectUtils.mapToObject(objMap, beanClass);
                    T obj = JSON.parseObject(JSON.toJSONString(objMap), beanClass);
                    objList.add(obj);
                } catch (Exception e) {
                    logger.error("parse row to object<{}> error, row=" + JSON.toJSONString(row), beanClass.getName(),
                        e);
                }
            }
            if (mismatch) {
                throw new BusinessException(BusinessErrorCodeEnum.IMPORT_FILE_NOT_MATCH_OBJECT);
            }
        } 
        return objList;
    }

}
