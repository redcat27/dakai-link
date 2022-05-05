package www.dakai.link.common.beans;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全局错误状态码
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {

    // 成功状态码：0
    SUCCESS(0, "成功"),
    ERROR(900, "未知错误"),

    // 操作失败，未知错误：0001
    NO_LOGIN(800, "未登录"),
    NO_PERMISSION(801, "没有权限"),
    TOKEN_INVALID(802, "token失效，请重新登录"),
    PARAM_VALID_ERROR(902, "参数校验失败"),
    DB_ERROR(903, "数据操作失败"),
    NO_APPROPRIATE_DATA(904, "未查询到相应数据"),
    DB_EXIST_ERROR(905, "数据已存在"),
    DELETE_FORBIDDEN(906, "禁止删除"),
    ;


    private final Integer code;
    private final String msg;

}
