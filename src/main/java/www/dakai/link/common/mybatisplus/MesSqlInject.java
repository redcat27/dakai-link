package www.dakai.link.common.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.*;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * className:IotSqlInject
 * package:com.pi2star.airmon.agent.common.mybatisplus
 * Description:
 *
 * @date: 2021/11/24 11:43 上午
 * @author: tangchengzao
 */
@Component
public class MesSqlInject extends AbstractSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        if (tableInfo.havePK()) {
            return Stream.of(
                    new Insert(),
                    new Delete(),
                    new DeleteByMap(),
                    new DeleteById(),
                    new DeleteBatchByIds(),
                    new Update(),
                    new UpdateById(),
                    new SelectById(),
                    new SelectBatchByIds(),
                    new SelectByMap(),
                    new SelectCount(),
                    new SelectMaps(),
                    new SelectMapsPage(),
                    new SelectObjs(),
                    new SelectList(),
                    new SelectPage()
            ).collect(toList());
        } else {
            logger.warn(String.format("%s ,Not found @TableId annotation, Cannot use Mybatis-Plus 'xxById' Method.",
                    tableInfo.getEntityType()));
            return Stream.of(
                    new Insert(),
                    new Delete(),
                    new DeleteByMap(),
                    new Update(),
                    new SelectByMap(),
                    new SelectCount(),
                    new SelectMaps(),
                    new SelectMapsPage(),
                    new SelectObjs(),
                    new SelectList(),
                    new SelectPage()
            ).collect(toList());
        }
    }
}
