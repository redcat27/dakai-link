package www.dakai.link.common.beans.annotations;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * className:OrderTypeEnum
 * package:com.pi2star.airmon.agent.common.beans.annotations
 * Description:
 *
 * @date: 2021/11/29 10:16 上午
 * @author: tangchengzao
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum {
    ASC("asc", "升序"),
    DESC("desc", "降序"),
    ;

    private String code;
    private String desc;
}
