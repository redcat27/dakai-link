package www.dakai.link.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName: TestStatusEnum
 * @Description:
 * @Author: jiangdakai
 * @Since: 2022/3/4 11:55 上午
 * @Version
 */
@Getter
@AllArgsConstructor
public enum TestStatusEnum {
    PASSED("passed", "通过"),
    FAIL("fail", "未通过"),
    TESTING("testing", "测试中"),
    ;

    private final String code;
    private final String name;
}
