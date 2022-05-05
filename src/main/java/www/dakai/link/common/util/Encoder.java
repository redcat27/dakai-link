package www.dakai.link.common.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Encoder {
    private static ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;

    public static byte[] int32ToByteArray(int value) {
        return ByteBuffer.allocate(4).order(BYTE_ORDER).putInt(0, value).array();
    }

    public static byte[] UInt32ToByteArray(long value) {
        return int32ToByteArray((int) (value & 0xFFFFFFFFL));
    }

    public static byte[] int16ToByteArray(short value) {
        return ByteBuffer.allocate(2).order(BYTE_ORDER).putShort(0, value).array();
    }

    public static byte[] UInt16ToByteArray(int value) {
        return int16ToByteArray((short) (value & 0xFFFF));
    }

    public static byte[] int8ToByteArray(byte value) {
        return new byte[]{value};
    }

    public static byte[] UInt8ToByteArray(short value) {
        return new byte[]{(byte) (value & 0xFF)};
    }

    public static int byteArrayToInt32(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes).order(BYTE_ORDER).getInt(index);
    }

    public static long byteArrayToUInt32(byte[] bytes, int index) {
        return byteArrayToInt32(bytes, index) & 0xFFFFFFFFL;
    }

    public static short byteArrayToInt16(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes).order(BYTE_ORDER).getShort(index);
    }

    public static int byteArrayToUInt16(byte[] bytes, int index) {
        return byteArrayToInt16(bytes, index) & 0xFFFF;
    }

    public static byte byteArrayToInt8(byte[] bytes, int index) {
        return bytes[index];
    }

    public static short byteArrayToUInt8(byte[] bytes, int index) {
        return (short) (bytes[index] & 0xFF);
    }

    public static byte[] floatToByteArray(float value) {
        return ByteBuffer.allocate(4).order(BYTE_ORDER).putFloat(0, value).array();
    }

    public static float byteArrayToFloat(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes).order(BYTE_ORDER).getFloat(index);
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = bytes.length - 1; i >= 0; i--) {
            sb.append(String.format("%02x", bytes[i]));
        }
        return "0x" + sb.toString().toUpperCase();
    }

    public static String byteToHex(byte b) {
        StringBuilder sb = new StringBuilder(2);
        sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static byte[] stringToByteArray(String str) {
        return str.getBytes();
    }

    public static String byteArrayToString(byte[] bytes, int start, int end) {
        return new String(bytes, start, end - start);
    }

    public static byte[] mergeBytes(byte[]... values) {
        int lengthByte = 0;
        for (int i = 0; i < values.length; i++) {
            lengthByte += values[i].length;
        }
        byte[] all_byte = new byte[lengthByte];
        int countLength = 0;
        for (int i = 0; i < values.length; i++) {
            byte[] b = values[i];
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }

}
