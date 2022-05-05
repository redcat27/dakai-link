package www.dakai.link.common.util;

/**
 * className:SqlUtil
 * package:com.pi2star.airmon.agent.common.util
 * Description:
 *
 * @date: 2021/11/24 11:52 上午
 * @author: tangchengzao
 */
public class SqlUtil {

    /**
     * 拼接唯一索引破坏set sql
     *
     * @param sqlLogicSet
     * @param uniqueFields
     * @return
     */
    public static String appendLogicDeleteSet(String sqlLogicSet, String[] uniqueFields) {
        if (uniqueFields == null || uniqueFields.length == 0) {
            return sqlLogicSet;
        }
        StringBuilder sqlLogicSetBuilder = new StringBuilder(sqlLogicSet);
        for (String uniqueField : uniqueFields) {
            sqlLogicSetBuilder.append(",").append(uniqueField).append("=CONCAT(").append(uniqueField).append(",'_',id)");
        }
        return sqlLogicSetBuilder.toString();
    }

}
