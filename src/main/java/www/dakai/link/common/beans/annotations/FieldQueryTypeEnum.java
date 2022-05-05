package www.dakai.link.common.beans.annotations;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * className:FieldQueryTypeEnum
 * package:com.pi2star.airmon.agent.common.beans.annotations
 * Description:
 *
 * @date: 2021/11/28 10:25 下午
 * @author: tangchengzao
 */
@Getter
@AllArgsConstructor
public enum FieldQueryTypeEnum {
    EQ("equal", "等于"),
    LIKE("like", "模糊查询"),
    GT("great_than", "大于"),
    GE("great_than_or_equal", "大于大于"),
    LT("less_than", "小于"),
    LE("less_than_or_equal", "小于等于"),
    ;

    private String code;
    private String desc;
}
