package www.dakai.link.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @ClassName: TestOptionsEnum
 * @Description:
 * @Author: jiangdakai
 * @Since: 2022/3/4 2:31 下午
 * @Version
 */
@Getter
@AllArgsConstructor
public enum TestOptionsEnum {
    /**
     * 单板测试项
     */
    BOARD_VISUAL_TEST("testVisual", "PCBA目检"),
    BOARD_TEST("testBoard", "单板检测"),
    /**
     * 整机测试项
     */
    SENSOR_BIND("sensorBind", "传感器板绑定"),
    ACQUISITION_BIND("acquisitionBind", "采集板绑定"),
    MASTER_BIND("masterBind", "主控板绑定"),
    WRITE_SERIAL_TEST("writeSerialTest", "烧写产品序列号"),
    AGING_TEST("agingTest", "老化测试"),
    STANDBY_POWER_TEST("standbyPowerTest", "待机功耗测试"),
    NODE_TEST("nodeTest", "整机测试"),
    VIBRATION_PERFORMANCE_TEST("vibrationPerformanceTest", "振动性能测试"),
    FINAL_TEST("finalTest", "终检"),
    ;

    private String code;
    private String name;

    /**
     * 根据code查询name
     *
     * @param code
     * @return
     */
    public static String getOptionName(String code) {
        Optional<String> match = Arrays.stream(TestOptionsEnum.values())
                .filter(testOptionsEnum -> {
                    return testOptionsEnum.getCode().equals(code);
                }).findFirst().map(TestOptionsEnum::getName);
        return match.isEmpty() ? null : match.get();
    }
}
