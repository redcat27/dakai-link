package www.dakai.link.common.util;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @ClassName: ExcelUtil
 * @Description:
 * @Author: jiangdakai
 * @Since: 2022/3/1 6:52 下午
 * @Version
 */
public class ExcelUtil {

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass, IExcelVerifyHandler iExcelVerifyHandler) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setNeedVerify(true);
        params.setVerifyHandler(iExcelVerifyHandler);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    public static <T> void throwExcelImportException(Class<T> pojoClass) {
        if (ObjectUtils.isEmpty(pojoClass)) {
            return;
        }
        Field[] fields = pojoClass.getDeclaredFields();
        if (ArrayUtils.isEmpty(fields)) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            Excel excel = field.getAnnotation(Excel.class);
            if (excel != null && !StringUtils.isBlank(excel.isImportField())) {
                sb.append(excel.isImportField() + ", ");
            }
        }
        if (ObjectUtils.isEmpty(sb)) {
            String msg = String.format("Excel文件为空或Excel文件列名应是【%s】", sb.substring(0, sb.length() - 2));
            throw new ExcelImportException(msg);
        }
    }

    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkFieldAllNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.get(object) != null && StringUtils.isNotBlank(String.valueOf(f.get(object)))) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
