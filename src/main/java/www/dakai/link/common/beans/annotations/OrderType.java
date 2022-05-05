package www.dakai.link.common.beans.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * className:OrderType
 * package:com.pi2star.airmon.agent.common.beans.annotations
 * Description:
 *
 * @date: 2021/11/29 10:15 上午
 * @author: tangchengzao
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderType {

    String[] fields() default "create_time";

    OrderTypeEnum orderType() default OrderTypeEnum.DESC;

}
