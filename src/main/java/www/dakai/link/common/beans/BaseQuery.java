package www.dakai.link.common.beans;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.base.CaseFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import www.dakai.link.common.beans.annotations.FieldQueryType;
import www.dakai.link.common.beans.annotations.OrderType;
import www.dakai.link.common.exceptions.InternalException;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * className:BaseQuery
 * package:com.pi2star.airmon.agent.common.beans
 * Description:
 *
 * @date: 2021/11/28 10:15 下午
 * @author: tangchengzao
 */
@Data
@Slf4j
public abstract class BaseQuery {

    /**
     * 组装查询条件
     *
     * @param <T>
     * @return
     */
    public <T> QueryWrapper<T> buildQuery() {
        return buildQuery(true);
    }

    /**
     * 组装查询条件
     *
     * @param <T>
     * @return
     */
    public <T> QueryWrapper<T> buildQuery(boolean setOrderBy) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", false);

        if (setOrderBy) {
            OrderType orderType = this.getClass().getAnnotation(OrderType.class);
            if (orderType == null) {
                queryWrapper.orderByDesc("create_time");
            } else {
                switch (orderType.orderType()) {
                    case ASC:
                        queryWrapper.orderByAsc(Arrays.asList(orderType.fields()));
                        break;
                    case DESC:
                        queryWrapper.orderByDesc(Arrays.asList(orderType.fields()));
                        break;
                    default:
                        break;
                }
            }
        }

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("page") || field.getName().equals("size")) {
                continue;
            }
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                field.setAccessible(false);
                if (value == null) {
                    continue;
                }
                if (field.getType() == String.class && StringUtils.isBlank(value.toString())) {
                    continue;
                }

                FieldQueryType queryType = field.getAnnotation(FieldQueryType.class);
                if (queryType == null) {
                    if (field.getName().endsWith("Start")) {
                        queryWrapper.ge(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName().replace("Start", "")), value);
                    } else if (field.getName().endsWith("End")) {
                        queryWrapper.le(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName().replace("End", "")), value);
                    } else {
                        queryWrapper.eq(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()), value);
                    }
                } else {
                    String fieldName = StringUtils.isBlank(queryType.fieldName()) ? CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()) : queryType.fieldName();
                    switch (queryType.queryType()) {
                        case EQ:
                            queryWrapper.eq(fieldName, value);
                            break;
                        case GE:
                            queryWrapper.ge(fieldName, value);
                            break;
                        case GT:
                            queryWrapper.gt(fieldName, value);
                            break;
                        case LE:
                            queryWrapper.le(fieldName, value);
                            break;
                        case LT:
                            queryWrapper.lt(fieldName, value);
                            break;
                        case LIKE:
                            queryWrapper.like(fieldName, "%" + value.toString() + "%");
                            break;
                        default:
                            break;
                    }
                }
            } catch (IllegalAccessException e) {
                log.error("组装查询条件失败，class:{}, value:{}", this.getClass().getName(), JSON.toJSONString(this), e);
                throw new InternalException(ResultCodeEnum.ERROR);
            }
        }
        return queryWrapper;
    }

    /**
     * 组装更新wrapper
     *
     * @param <T>
     * @return
     */
    public <T> Wrapper<T> buildUpdateWrapper() {
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                field.setAccessible(false);
                if (field.getName().equals("id")) {
                    updateWrapper.eq("id", value);
                    continue;
                }
                updateWrapper.set(camelToUnderscore(field.getName()), value);
            } catch (IllegalAccessException e) {
                log.error("组装更新条件失败，class:{}, value:{}, field:{}", this.getClass().getName(), JSON.toJSONString(this), field.getName(), e);
                throw new InternalException(ResultCodeEnum.ERROR);
            }
        }
        return updateWrapper;
    }

    /**
     * 组装更新wrapper
     *
     * @param <T>
     * @return
     */
    public <T> Wrapper<T> buildUpdateWrapperIgnoreNull() {
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);
                field.setAccessible(false);
                if (value == null) {
                    continue;
                }
                if (field.getName().equals("id")) {
                    updateWrapper.eq("id", value);
                    continue;
                }
                updateWrapper.set(camelToUnderscore(field.getName()), value);
            } catch (IllegalAccessException e) {
                log.error("组装更新条件失败，class:{}, value:{}, field:{}", this.getClass().getName(), JSON.toJSONString(this), field.getName(), e);
                throw new InternalException(ResultCodeEnum.ERROR);
            }
        }
        return updateWrapper;
    }

    /**
     * 驼峰转下划线
     *
     * @param key
     * @return
     */
    private String camelToUnderscore(String key) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, key);
    }
}
