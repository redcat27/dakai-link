package www.dakai.link.common.beans;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Data
public class BasePageList<TData> {

    private Long total;
    private List<TData> list;

    public BasePageList(IPage<?> page, Class<TData> clazz) {
        this(page, clazz, record -> {
            TData data = null;
            try {
                data = clazz.getConstructor().newInstance();
                BeanUtils.copyProperties(record, data);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            return data;
        });
    }

    @SuppressWarnings("unchecked")
    public BasePageList(IPage<?> page, Class<TData> clazz, Function<Object, TData> transformFunction) {
        this.total = page.getTotal();
        this.list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(page.getRecords())) {
            if (page.getRecords().get(0).getClass() == clazz) {
                this.list = (List<TData>) page.getRecords();
            } else {
                for (Object record : page.getRecords()) {
                    TData data = transformFunction.apply(record);
                    this.list.add(data);
                }
            }
        }
    }

}
