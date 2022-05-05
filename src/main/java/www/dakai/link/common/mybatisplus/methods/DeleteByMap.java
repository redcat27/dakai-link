package www.dakai.link.common.mybatisplus.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import www.dakai.link.common.mybatisplus.annotations.UniqueField;
import www.dakai.link.common.util.SqlUtil;

import java.util.Map;

/**
 * className:DeleteByMap
 * package:com.pi2star.airmon.agent.common.mybatisplus.methods
 * Description:
 *
 * @date: 2021/11/24 11:56 上午
 * @author: tangchengzao
 */
public class DeleteByMap extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql;
        SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE_BY_MAP;
        if (tableInfo.isWithLogicDelete()) {

            UniqueField annotation = mapperClass.getAnnotation(UniqueField.class);
            String[] fields = null;
            if (annotation != null) {
                fields = annotation.uniqueFields().split(",");
            }

            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), SqlUtil.appendLogicDeleteSet(sqlLogicSet(tableInfo), fields), sqlWhereByMap(tableInfo));
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Map.class);
            return addUpdateMappedStatement(mapperClass, Map.class, getMethod(sqlMethod), sqlSource);
        } else {
            sqlMethod = SqlMethod.DELETE_BY_MAP;
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), this.sqlWhereByMap(tableInfo));
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Map.class);
            return this.addDeleteMappedStatement(mapperClass, getMethod(sqlMethod), sqlSource);
        }
    }
}
