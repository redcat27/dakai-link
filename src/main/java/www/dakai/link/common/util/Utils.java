package www.dakai.link.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.LinkedHashSet;
import java.util.List;

public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static final Integer DATA_HEADER = 0xAABBCCDD;
    public static final Integer DATA_TAIL = 0xDDCCBBAA;

    public static <T> List<T> removeDuplicate(Class<T> t, List<T> list) {
        LinkedHashSet<T> linkedHashSet = new LinkedHashSet<T>(list.size());
        linkedHashSet.addAll(list);
        list.clear();
        list.addAll(linkedHashSet);
        return list;
    }

    public static byte[] outputFormat(Integer command, byte[] body) {
        byte[] data = Encoder.mergeBytes(
                new byte[]{0b00000001},
                Encoder.UInt16ToByteArray(command),
                Encoder.UInt16ToByteArray(body.length),
                body
        );

        return Encoder.mergeBytes(
                Encoder.UInt32ToByteArray(DATA_HEADER),
                data,
                Encoder.UInt16ToByteArray(Utils.getCRC16_CCITT(data)),
                Encoder.UInt32ToByteArray(DATA_TAIL)
        );
    }

    public static byte getXor(byte[] bytes) {
        byte result = 0;
        for (byte b : bytes)
            result ^= b;
        return result;
    }

    public static int getCRC16(byte[] bytes) {
        int len = bytes.length;

        // 预置 1 个 16 位的寄存器为十六进制FFFF, 称此寄存器为 CRC寄存器。
        int crc = 0xFFFF;
        int i, j;
        for (i = 0; i < len; i++) {
            // 把第一个 8 位二进制数据 与 16 位的 CRC寄存器的低 8 位相异或, 把结果放于 CRC寄存器
            crc = ((crc & 0xFF00) | (crc & 0x00FF) ^ (bytes[i] & 0xFF));
            for (j = 0; j < 8; j++) {
                // 把 CRC 寄存器的内容右移一位( 朝低位)用 0 填补最高位, 并检查右移后的移出位
                if ((crc & 0x0001) > 0) {
                    // 如果移出位为 1, CRC寄存器与多项式A001进行异或
                    crc = crc >> 1;
                    crc = crc ^ 0xA001;
                } else
                    // 如果移出位为 0,再次右移一位
                    crc = crc >> 1;
            }
        }
        return crc;
    }

    /**
     * x16 + x12 + x5 + 1
     *
     * @param bytes
     * @return
     */
    public static int getCRC16_CCITT(byte[] bytes) {
        int crc = 0x0000; // initial value
        int polynomial = 0x8408;// poly value reversed 0x1021;

        int i, j;
        for (i = 0; i < bytes.length; i++) {
            crc ^= ((int) bytes[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((crc & 0x00000001) != 0) {
                    crc >>= 1;
                    crc ^= polynomial;
                } else {
                    crc >>= 1;
                }
            }
        }
        return crc;
    }

    public static String getCaptcha(String input) {
        String output = DigestUtils.md5DigestAsHex(("xc" + input).getBytes()).toUpperCase();
        return output.substring(0, 6);
    }

    public static String[] stringSplitToArray(String src, int length) {
        //检查参数是否合法
        if (null == src || src.equals("")) {
            return null;
        }

        if (length <= 0) {
            return null;
        }
        int n = (src.length() + length - 1) / length; //获取整个字符串可以被切割成字符子串的个数
        String[] split = new String[n];
        for (int i = 0; i < n; i++) {
            if (i < (n - 1)) {
                split[i] = src.substring(i * length, (i + 1) * length);
            } else {
                split[i] = src.substring(i * length);
            }
        }
        return split;
    }

}
