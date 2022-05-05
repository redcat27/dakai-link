package www.dakai.link.common.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.base.CaseFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import www.dakai.link.common.beans.ResultCodeEnum;
import www.dakai.link.common.exceptions.InternalException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * className:BeanUtil
 * package:com.pi2star.iot.common.util
 * Description:
 *
 * @date: 2021/12/16 6:28 PM
 * @author: tangchengzao
 */
public class BeanUtil {

    /**
     * 对象拷贝
     *
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T transformObject(Object source, Class<T> clazz) {
        return transformObject(source, clazz, true);
    }

    /**
     * 对象拷贝
     *
     * @param source
     * @param clazz
     * @param returnNull
     * @param <T>
     * @return
     */
    public static <T> T transformObject(Object source, Class<T> clazz, boolean returnNull) {
        if (returnNull && source == null) {
            return null;
        }
        try {
            T target = clazz.getConstructor().newInstance();
            transformObject(source, target);
            return target;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InternalException(ResultCodeEnum.ERROR, e);
        }
    }

    /**
     * 数组拷贝
     *
     * @param sourceList
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> transformObjects(List<?> sourceList, Class<T> clazz) {
        return transformObjects(sourceList, clazz, true);
    }

    /**
     * 数组拷贝
     *
     * @param sourceList
     * @param clazz
     * @param returnNull
     * @param <T>
     * @return
     */
    public static <T> List<T> transformObjects(List<?> sourceList, Class<T> clazz, boolean returnNull) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return returnNull ? null : new ArrayList<>();
        }

        List<T> targetList = new ArrayList<>();
        sourceList.forEach(source -> targetList.add(transformObject(source, clazz)));
        return targetList;
    }

    /**
     * 对象拷贝
     *
     * @param source
     * @param target
     */
    public static void transformObject(Object source, Object target) {
        if (source == null) {
            return;
        }
        BeanUtils.copyProperties(source, target);
    }

    public static <T> T countResultToObject(List<Map<String, Object>> result, String fieldKey, Class<T> clazz) {
        return countResultToObject(result, fieldKey, "count", clazz);
    }

    public static <T> T countResultToObject(List<Map<String, Object>> result, String fieldKey, String countKey, Class<T> clazz) {
        Map<String, Long> countMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(result)) {
            result.forEach(map -> {
                String key = (String) map.get(fieldKey);
                if (!StringUtils.isCamel(key)) {
                    key = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
                }
                countMap.put(key, (Long) map.get(countKey));
            });
        }
        try {
            T target = clazz.getConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType() == Long.class) {
                    field.setAccessible(true);
                    field.set(target, countMap.getOrDefault(field.getName(), 0L));
                    field.setAccessible(false);
                }
            }
            return target;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InternalException(ResultCodeEnum.ERROR, e);
        }
    }

}
