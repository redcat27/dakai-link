package www.dakai.link.common.mybatisplus.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import www.dakai.link.common.mybatisplus.annotations.UniqueField;
import www.dakai.link.common.util.SqlUtil;

/**
 * className:DeleteBatchByIds
 * package:com.pi2star.airmon.agent.common.mybatisplus.methods
 * Description:
 *
 * @date: 2021/11/24 11:57 上午
 * @author: tangchengzao
 */
public class DeleteBatchByIds extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql;
        SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE_BATCH_BY_IDS;
        if (tableInfo.isWithLogicDelete()) {

            UniqueField annotation = mapperClass.getAnnotation(UniqueField.class);
            String[] fields = null;
            if (annotation != null) {
                fields = annotation.uniqueFields().split(",");
            }

            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), SqlUtil.appendLogicDeleteSet(sqlLogicSet(tableInfo), fields),
                    tableInfo.getKeyColumn(),
                    SqlScriptUtils.convertForeach(
                            SqlScriptUtils.convertChoose("@org.apache.ibatis.type.SimpleTypeRegistry@isSimpleType(item.getClass())",
                                    "#{item}", "#{item." + tableInfo.getKeyProperty() + "}"),
                            COLLECTION, null, "item", COMMA),
                    tableInfo.getLogicDeleteSql(true, true));
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return addUpdateMappedStatement(mapperClass, modelClass, getMethod(sqlMethod), sqlSource);
        } else {
            sqlMethod = SqlMethod.DELETE_BATCH_BY_IDS;
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                    SqlScriptUtils.convertForeach(
                            SqlScriptUtils.convertChoose("@org.apache.ibatis.type.SimpleTypeRegistry@isSimpleType(item.getClass())",
                                    "#{item}", "#{item." + tableInfo.getKeyProperty() + "}"),
                            COLLECTION, null, "item", COMMA));
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, getMethod(sqlMethod), sqlSource);
        }
    }
}
