package com.study.module.util;


import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 主要用于判断文件的类型
 * 通常，在WEB系统中，上传文件时都需要做文件的类型校验，大致有如下几种方法：
 * <p>
 * 1. 通过后缀名，如exe,jpg,bmp,rar,zip等等。
 * <p>
 * 2. 通过读取文件，获取文件的Content-type来判断。
 * <p>
 * 3. 通过读取文件流，根据文件流中特定的一些字节标识来区分不同类型的文件。
 * <p>
 * 4. 若是图片，则通过缩放来判断，可以缩放的为图片，不可以的则不是。
 * <p>
 * 然而，在安全性较高的业务场景中，1，2两种方法的校验会被轻易绕过。
 * <p>
 * 1. 伪造后缀名，如图片的，非常容易修改。
 * <p>
 * 2. 伪造文件的Content-type，这个稍微复杂点，
 * <p>
 * 3.较安全，但是要读取文件，并有16进制转换等操作，性能稍差，但能满足一定条件下对安全的要求，所以建议使用。
 * <p>
 * 但是文件头的信息也可以伪造，截图如下，对于图片可以采用图片缩放或者获取图片宽高的方法避免伪造头信息漏洞。
 *
 * @author drew
 */
public class FileTools {

    public static void main(String[] args) {
        File file = new File("D:/demo-no-paging.pdf");
        FileInputStream is;
        try {
            is = new FileInputStream(file);
            byte[] b = new byte[16];
            is.read(b, 0, b.length);
            String filetypeHex = String.valueOf(bytesToHexString(b));
            System.out.println("文件类型的十六进制: " + filetypeHex);
            String fileName = file.getName();
            System.out.println("通过读取文件头部获得文件类型：" + getFileType(file));
            System.out.println("直接提取文件后缀：" + fileName.substring(fileName.indexOf(".") + 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<>();

    /**
     * -----------------------------目前可以识别的类型----------------------------
     */
    private static void getAllFileType() {
        FILE_TYPE_MAP.put("jpg", "FFD8FF");
        //PNG
        FILE_TYPE_MAP.put("png", "89504E47");
        //GIF
        FILE_TYPE_MAP.put("gif", "47494638");
        //TIFF
        FILE_TYPE_MAP.put("tif", "49492A00");
        //Windows Bitmap
        FILE_TYPE_MAP.put("bmp", "424D");
        //CAD
        FILE_TYPE_MAP.put("dwg", "41433130");
        //HTML
        FILE_TYPE_MAP.put("html", "68746D6C3E");
        //Rich Text Format
        FILE_TYPE_MAP.put("rtf", "7B5C727466");
        FILE_TYPE_MAP.put("xml", "3C3F786D6C");
        FILE_TYPE_MAP.put("zip", "504B0304");
        FILE_TYPE_MAP.put("rar", "52617221");
        //PhotoShop
        FILE_TYPE_MAP.put("psd", "38425053");
        //Email [thorough only]
        FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A");
        //Outlook Express
        FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F");
        //Outlook
        FILE_TYPE_MAP.put("pst", "2142444E");
        //office类型，包括doc、xls和ppt
        FILE_TYPE_MAP.put("office", "D0CF11E0");
        //MS Access
        FILE_TYPE_MAP.put("mdb", "000100005374616E64617264204A");
        //WordPerfect
        FILE_TYPE_MAP.put("wpd", "FF575043");
        FILE_TYPE_MAP.put("eps", "252150532D41646F6265");
        FILE_TYPE_MAP.put("ps", "252150532D41646F6265");
        //Adobe Acrobat
        FILE_TYPE_MAP.put("pdf", "255044462D312E");
        //Quicken
        FILE_TYPE_MAP.put("qdf", "AC9EBD8F");
        //Windows Password
        FILE_TYPE_MAP.put("pwl", "E3828596");
        //Wave
        FILE_TYPE_MAP.put("wav", "57415645");
        FILE_TYPE_MAP.put("avi", "41564920");
        //Real Audio
        FILE_TYPE_MAP.put("ram", "2E7261FD");
        //Real Media
        FILE_TYPE_MAP.put("rm", "2E524D46");
        FILE_TYPE_MAP.put("mpg", "000001BA");
        //Quicktime
        FILE_TYPE_MAP.put("mov", "6D6F6F76");
        //Windows Media
        FILE_TYPE_MAP.put("asf", "3026B2758E66CF11");
        //MIDI (mid)
        FILE_TYPE_MAP.put("mid", "4D546864");
    }

    /**
     * 通过读取文件头部获得文件类型
     *
     * @param file 需要校验的文件
     * @return 文件类型
     * @throws Exception
     */
    public static String getFileType(File file) throws Exception {
        getAllFileType();
        String fileExtendName = null;
        FileInputStream is;
        try {
            is = new FileInputStream(file);
            byte[] b = new byte[16];
            is.read(b, 0, b.length);
            String filetypeHex = String.valueOf(bytesToHexString(b));
            for (Entry<String, String> entry : FILE_TYPE_MAP.entrySet()) {
                String fileTypeHexValue = entry.getValue();
                if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
                    fileExtendName = entry.getKey();
                    if ("office".equals(fileExtendName)) {
                        fileExtendName = getOfficeFileType(is);
                    }
                    is.close();
                    break;
                }
            }

            // 如果不是上述类型，则判断扩展名
            if (fileExtendName == null) {
                String fileName = file.getName();
                // 如果无扩展名，则直接返回空串
                if (!fileName.contains(".")) {
                    return "";
                }
                // 如果有扩展名，则返回扩展名
                return fileName.substring(fileName.indexOf(".") + 1);
            }
            is.close();
            return fileExtendName;
        } catch (Exception exception) {
            throw new Exception(exception.getMessage(), exception);
        }
    }

    /**
     * 判断office文件的具体类型
     *
     * @param fileInputStream 文件流
     * @return office文件具体类型
     * @throws Exception
     */
    private static String getOfficeFileType(FileInputStream fileInputStream) throws Exception {
        String officeFileType = "doc";
        byte[] b = new byte[512];
        try {
            fileInputStream.read(b, 0, b.length);
            String filetypeHex = String.valueOf(bytesToHexString(b));
            String flagString = filetypeHex.substring(992, filetypeHex.length());
            if (flagString.toLowerCase().startsWith("eca5c")) {
                officeFileType = "doc";
            } else if (flagString.toLowerCase().startsWith("fdffffff09")) {
                officeFileType = "xls";
            } else if (flagString.toLowerCase().startsWith("09081000000")) {
                officeFileType = "xls";
            } else {
                officeFileType = "ppt";
            }
            return officeFileType;
        } catch (Exception exception) {
            throw new Exception(exception.getMessage(), exception);
        }
    }

    /**
     * 获得文件头部字符串
     *
     * @param src 文件流
     * @return 文件头部字符串
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


}
