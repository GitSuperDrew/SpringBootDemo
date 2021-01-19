package com.study.module.springbootmp.service;

/**
 * @author zl
 * @date 2021/1/18 19:08
 **/
public interface SecretProcess {
    /**
     * <p>数据加密</p>
     *
     * @param data 待加密数据
     * @return String 加密结果
     */
    String encrypt(String data);

    /**
     * j
     * <p>数据解密</p>
     *
     * @param data 待解密数据
     * @return String 解密后的数据
     */
    String decrypt(String data);

    /**
     * <p>加密算法格式：算法[/模式/填充]</p>
     *
     * @return String
     */
    String getAlgorithm();

    /**
     * 对数据进行16进制转换
     */
    class Hex {

        private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        public static byte[] decode(CharSequence s) {
            int nChars = s.length();
            if (nChars % 2 != 0) {
                throw new IllegalArgumentException("16进制数据错误");
            }
            byte[] result = new byte[nChars / 2];
            for (int i = 0; i < nChars; i += 2) {
                int msb = Character.digit(s.charAt(i), 16);
                int lsb = Character.digit(s.charAt(i + 1), 16);
                if (msb < 0 || lsb < 0) {
                    throw new IllegalArgumentException("Detected a Non-hex character at " + (i + 1) + " or " + (i + 2) + " position");
                }
                result[i / 2] = (byte) ((msb << 4) | lsb);
            }
            return result;
        }

        public static String encode(byte[] buf) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0, len = buf.length; i < len; i++) {
                sb.append(HEX[(buf[i] & 0xF0) >>> 4]).append(HEX[buf[i] & 0x0F]);
            }
            return sb.toString();
        }

    }
}