package SShop.mono.application.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

public class ObjectUtils {
  public static void copyNonNullProperties(Object source, Object target) {
    BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
  }

  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper wrapper = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = wrapper.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<>();
    for (java.beans.PropertyDescriptor pd : pds) {
      Object srcValue = wrapper.getPropertyValue(pd.getName());
      if (srcValue == null) emptyNames.add(pd.getName());
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  public static boolean hasValue(List<?> list) {
    return list != null && !list.isEmpty();
  }

  public static boolean hasValue(String list) {
    return list != null && !list.isEmpty();
  }

  @SafeVarargs
  public static <T> T getFirstNonNull(T... objects) {
    for (T obj : objects) {
      if (obj != null) {
        return obj;
      }
    }
    return null;
  }

  public static <T> boolean hasDuplicates(List<T> list) {
    Set<T> set = new HashSet<>();
    for (T item : list) {
      if (!set.add(item)) {
        return true;
      }
    }
    return false;
  }
}
