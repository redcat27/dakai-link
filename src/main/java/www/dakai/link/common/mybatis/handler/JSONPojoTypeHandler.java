package www.dakai.link.common.mybatis.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.postgresql.util.PGobject;
import www.dakai.link.common.beans.JsonPojo;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * className:JSONPojoTypeHandler
 * package:com.pi2star.iot.common.mybatis
 * Description:
 *
 * @date: 2021/12/3 11:46 AM
 * @author: tangchengzao
 */
@MappedJdbcTypes(JdbcType.JAVA_OBJECT)
public class JSONPojoTypeHandler<T extends JsonPojo> extends BaseTypeHandler<T> {
    private static final PGobject pgObject = new PGobject();
    private static final SerializeConfig serializeConfig = new SerializeConfig();
    private static final ParserConfig parserConfig = new ParserConfig();

    private Class<T> clazz;

    public JSONPojoTypeHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    static {
        serializeConfig.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);
        parserConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        parserConfig.setAsmEnable(true);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        pgObject.setType("json");
        pgObject.setValue(JSON.toJSONString(parameter, serializeConfig));
        ps.setObject(i, pgObject);
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String sqlJson = rs.getString(columnName);
        if (StringUtils.isNoneBlank(sqlJson)) {
            return JSON.parseObject(sqlJson, clazz, parserConfig);
        }
        return null;
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String sqlJson = rs.getString(columnIndex);
        if (StringUtils.isNoneBlank(sqlJson)) {
            return JSON.parseObject(sqlJson, clazz, parserConfig);
        }
        return null;
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String sqlJson = cs.getString(columnIndex);
        if (StringUtils.isNoneBlank(sqlJson)) {
            return JSON.parseObject(sqlJson, clazz, parserConfig);
        }
        return null;
    }

}
