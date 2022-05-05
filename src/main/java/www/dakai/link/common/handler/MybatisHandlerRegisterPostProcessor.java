package www.dakai.link.common.handler;

import com.pi2star.mes.common.beans.JsonPojo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * className:MybatisHandlerRegisterPostProcessor
 * package:com.pi2star.iot.common.mybatis.handler
 * Description:
 *
 * @date: 2021/12/10 11:49 AM
 * @author: tangchengzao
 */
@Slf4j
@Component
public class MybatisHandlerRegisterPostProcessor implements BeanPostProcessor {

    @Value("${mybatis.json-pojo-scan-package}")
    private String jsonPojoScanPackage;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (SqlSessionFactory.class.isAssignableFrom(bean.getClass())) {
            bean = (SqlSessionFactory) bean;
            try {
                Resource[] resources = new PathMatchingResourcePatternResolver().getResources(jsonPojoScanPackage);
                CachingMetadataReaderFactory readerFactory = new CachingMetadataReaderFactory();
                for (Resource resource : resources) {
                    Class<?> clazz = Class.forName(readerFactory.getMetadataReader(resource).getClassMetadata().getClassName());
                    Class<?>[] innerClasses = clazz.getDeclaredClasses();
                    for (Class<?> innerClass : innerClasses) {
                        if (JsonPojo.class.isAssignableFrom(innerClass)) {
                            ((SqlSessionFactory) bean).getConfiguration().getTypeHandlerRegistry().register(innerClass, JSONPojoTypeHandler.class);
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                log.error("po扫描异常", e);
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
