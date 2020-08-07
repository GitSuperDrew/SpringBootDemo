package com.jiangfeixiang.mpdemo.springbootmp.util;

import cn.hutool.core.util.ReflectUtil;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2020/8/6 下午 5:18
 */
@Component
public class PdfFormatter {
    public static class PdfFormater {
        //pdf模板和结果路径相关设置
        private String templateDir;

        private String basePath;

        private String templateFileFo;

        //需填充数据
        private Map dataMap;

        private String barcodeString;

        private String tempFileName;

        private String resultFileName;
        //动态数据
        private List dynData;

        /**
         * 构造器，生成PDF引擎实例，并引入相应模板文件XXX.FO、路径和报表数据HashMap
         *
         * @param templateDir
         *            模板文件所在目录
         * @param basePath
         *            模板文件工作副本及结果PDF文件所在工作目录
         * @param templateFileFo
         *            模板文件名，推荐格式为“XXXTemplate.FO”， 其文件由word模板文档在设计时转换而成
         * @param dataMap
         *            对应模板的数据HashMap，由调用该打印引擎的里程根据模板格式和约定进行准备
         */
        public PdfFormater(String templateDir, String basePath,
                           String templateFileFo, Map dataMap) {
            this.templateDir = templateDir;
            this.basePath = basePath;
            this.templateFileFo = templateFileFo;
            this.dataMap = dataMap;
        }

        private BaseFont getBaseFont(Object obj) {
            // 需要根据不同的模板返回字体
            BaseFont bf = null;
            try {
            /*bf = BaseFont.createFont("simsun.ttf",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);*/
                bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bf;
        }

        public String doTransform() {
            long name = System.currentTimeMillis();
            tempFileName = "temp" + name + ".pdf";
            resultFileName = "best" + name + ".pdf";

            try {
                PdfReader reader;
                PdfStamper stamper;

                reader = new PdfReader(templateDir + "/" + templateFileFo);
                stamper = new PdfStamper(reader, new FileOutputStream(basePath + "/" + tempFileName));

                AcroFields form = stamper.getAcroFields();
                form.addSubstitutionFont(getBaseFont(""));
                transformRegular(form);
                stamper.setFormFlattening(true);
                stamper.close();

                postProcess();
                //FileOutputStream fos = new FileOutputStream(basePath + "/" + tempFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return basePath + "/" + resultFileName;
        }

        /**
         * 填充规整的表单域
         *
         * @param form
         */
        private void transformRegular(AcroFields form) {
            if (dataMap == null || dataMap.size() == 0) {
                return;
            }
            String key = "";
            Iterator ekey = dataMap.keySet().iterator();
            Object obj = null ;
            while (ekey.hasNext()) {
                key = ekey.next().toString();
                try {
                    obj = dataMap.get(key);
                    if(obj instanceof List)    {
                        //map中放的是list，为动态字段
                        dynData = (List)obj;
                        transformDynTable(form);
                    }else{
                        //非空放入
                        if( dataMap.get(key) != null) {
                            form.setField(key, dataMap.get(key).toString());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 动态table的填充
         * @param form
         */
        private void transformDynTable(AcroFields form) {
            if (dynData == null || dynData.size() == 0) {
                return;
            }
            Object obj;
            String name;
            String value = "";
            for (int x = 0; x < dynData.size(); x++) {
                obj = dynData.get(x);
                Field[] fld = obj.getClass().getDeclaredFields();
                for (int i = 0; i < fld.length; i++) {
                    name = fld[i].getName();
                    value = (String) ReflectUtil.getFieldValue(obj, name);
                    try {
                        form.setField(name + x, value);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 对生成的pdf文件进行后处理
         *
         * @throws Exception
         */
        private synchronized void postProcess() throws Exception {
            FileOutputStream fosRslt = null;
            PdfStamper stamper = null;
            try {
                PdfReader reader = new PdfReader(basePath + "/" + tempFileName);

                fosRslt = new FileOutputStream(basePath + "/" + resultFileName);
                stamper = new PdfStamper(reader, fosRslt);

                Rectangle pageSize = reader.getPageSize(1);
                float width = pageSize.getWidth();
                //float height = pageSize.getHeight();
            /*BaseFont bf = BaseFont.createFont("simsun.ttf",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);*/
                BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            /*BaseFont bfComic = BaseFont.createFont("simhei.ttf",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);*/
                PdfContentByte over;
                int total = reader.getNumberOfPages() + 1;
                for (int i = 1; i < total; i++) {
                    over = stamper.getOverContent(i);
                    if (total <= 2) {
                        break;
                    }
                    over.beginText();
                    over.setFontAndSize(bf, 10);
                    over.setTextMatrix(width - 92f, 32);
                    over.showText("第 " + i + " 页");
                    over.endText();
                }
            } catch (Exception ie) {
                ie.printStackTrace();
            } finally {
                if (stamper != null) {
                    try {
                        stamper.close();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fosRslt != null) {
                    try {
                        fosRslt.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                File pdfFile = new File(basePath, "/" + tempFileName);
                pdfFile.delete();
            }
        }

    }
}
