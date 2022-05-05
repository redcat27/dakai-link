package www.dakai.link.common.handler.common;

import org.apache.ibatis.type.*;
import org.springframework.util.CollectionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * className:ListTypeHandler
 * package:com.pi2star.airmon.agent.common.mybatis
 * Description:
 *
 * @date: 2021/11/23 5:56 下午
 * @author: tangchengzao
 */
@MappedJdbcTypes(JdbcType.ARRAY)
@MappedTypes({List.class, ArrayList.class})
public class ListTypeHandler extends BaseTypeHandler<List<Object>> {

    protected static final String TYPE_NAME_VARCHAR = "varchar";
    protected static final String TYPE_NAME_INTEGER = "integer";
    protected static final String TYPE_NAME_BOOLEAN = "boolean";
    protected static final String TYPE_NAME_NUMERIC = "numeric";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Object> parameter, JdbcType jdbcType) throws SQLException {
        if (CollectionUtils.isEmpty(parameter)) {
            ps.setNull(i, JdbcType.ARRAY.TYPE_CODE);
            return;
        }
        String typeName = null;
        Class<?> clazz = parameter.get(0).getClass();
        if (clazz == Integer.class) {
            typeName = TYPE_NAME_INTEGER;
        } else if (clazz == String.class) {
            typeName = TYPE_NAME_VARCHAR;
        } else if (clazz == Boolean.class) {
            typeName = TYPE_NAME_BOOLEAN;
        } else if (clazz == Double.class) {
            typeName = TYPE_NAME_NUMERIC;
        }

        if (typeName == null) {
            throw new TypeException("ListTypeHandler parameter typeName error, your type is " + parameter.getClass().getName());
        }

        Connection connection = ps.getConnection();
        Array array = connection.createArrayOf(typeName, parameter.toArray(new Object[0]));
        ps.setArray(i, array);
    }

    @Override
    public List<Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getList(rs.getArray(columnName));
    }

    @Override
    public List<Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getList(rs.getArray(columnIndex));
    }

    @Override
    public List<Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getList(cs.getArray(columnIndex));
    }

    private List<Object> getList(Array array) {
        if (array == null) {
            return null;
        }
        try {
            return Arrays.stream((Object[]) array.getArray()).collect(Collectors.toList());
        } catch (Exception e) {
        }
        return null;
    }
}
