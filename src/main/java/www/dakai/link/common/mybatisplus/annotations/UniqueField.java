package www.dakai.link.common.mybatisplus.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * className:UniqueKeyAnnotation
 * package:com.pi2star.airmon.agent.common.mybatisplus.annotations
 * Description:
 *
 * @date: 2021/11/24 11:48 上午
 * @author: tangchengzao
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {

    /**
     * 表中unique字段名，目前仅支持varchar类型，用于delete破坏唯一索引
     *
     * @return
     */
    String uniqueFields() default "";

}
