package www.dakai.link.common.beans.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * className:FieldQueryType
 * package:com.pi2star.airmon.agent.common.beans.annotations
 * Description:
 *
 * @date: 2021/11/28 10:24 下午
 * @author: tangchengzao
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldQueryType {

    /**
     * 数据库字段名
     *
     * @return
     */
    String fieldName() default "";

    /**
     * 查询类型，默认等于
     *
     * @return
     */
    FieldQueryTypeEnum queryType() default FieldQueryTypeEnum.EQ;
}
