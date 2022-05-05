package www.dakai.link.common.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;

public class TableNameSuffixParser implements TableNameHandler {

    private static final String REMOVE_TABLE_SUFFIX = "_d_o";

    @Override
    public String dynamicTableName(String sql, String tableName) {
        return tableName.replace(REMOVE_TABLE_SUFFIX, "");
    }
}
