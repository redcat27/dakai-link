package www.dakai.link.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * className:DuplicateKeyMessageEnum
 * package:com.pi2star.airmon.agent.common.enums
 * Description:
 *
 * @date: 2021/11/24 10:30 上午
 * @author: tangchengzao
 */
@Getter
@AllArgsConstructor
public enum DuplicateKeyMessageEnum {
    DEFAULT("null", "部分数据已存在"),
    UK_NODE_SN("uk_node_sn", "整机序列号已存在"),
    UK_BOARD_SN("uk_board_sn", "单板序列号已存在"),
    UK_ACCOUNT_PHONE("uk_account_phone", "用户手机号已存在"),
    ;

    private final String keyName;
    private final String message;

    /**
     * 获取对应提示信息
     *
     * @param keyName
     * @return
     */
    public static String parseErrorMessage(String keyName) {
        for (DuplicateKeyMessageEnum value : DuplicateKeyMessageEnum.values()) {
            if (value.getKeyName().equals(keyName)) {
                return value.getMessage();
            }
        }
        return DEFAULT.getMessage();
    }
}
