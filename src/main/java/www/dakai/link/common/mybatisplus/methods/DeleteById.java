package www.dakai.link.common.mybatisplus.methods;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import www.dakai.link.common.mybatisplus.annotations.UniqueField;
import www.dakai.link.common.util.SqlUtil;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * className:DeleteById
 * package:com.pi2star.airmon.agent.common.mybatisplus.methods
 * Description:
 *
 * @date: 2021/11/24 11:57 上午
 * @author: tangchengzao
 */
public class DeleteById extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql;
        SqlMethod sqlMethod = SqlMethod.LOGIC_DELETE_BY_ID;
        if (tableInfo.isWithLogicDelete()) {
            List<TableFieldInfo> fieldInfos = tableInfo.getFieldList().stream()
                    .filter(TableFieldInfo::isWithUpdateFill)
                    .collect(toList());

            UniqueField annotation = mapperClass.getAnnotation(UniqueField.class);
            String[] fields = null;
            if (annotation != null) {
                fields = annotation.uniqueFields().split(",");
            }

            if (CollectionUtils.isNotEmpty(fieldInfos)) {
                String sqlSet = "SET " + SqlScriptUtils.convertIf(fieldInfos.stream()
                        .map(i -> i.getSqlSet(EMPTY)).collect(joining(EMPTY)), "!@org.apache.ibatis.type.SimpleTypeRegistry@isSimpleType(_parameter.getClass())", true)
                        + tableInfo.getLogicDeleteSql(false, false);
                sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), sqlSet, tableInfo.getKeyColumn(),
                        tableInfo.getKeyProperty(), tableInfo.getLogicDeleteSql(true, true));
            } else {
                sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), SqlUtil.appendLogicDeleteSet(sqlLogicSet(tableInfo), fields),
                        tableInfo.getKeyColumn(), tableInfo.getKeyProperty(),
                        tableInfo.getLogicDeleteSql(true, true));
            }
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return addUpdateMappedStatement(mapperClass, modelClass, getMethod(sqlMethod), sqlSource);
        } else {
            sqlMethod = SqlMethod.DELETE_BY_ID;
            sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                    tableInfo.getKeyProperty());
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, getMethod(sqlMethod), sqlSource);
        }
    }
}
