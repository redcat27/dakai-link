package www.dakai.link.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * @ClassName: HttpClientUtil
 * @Description:
 * @Author: jiangdakai
 * @Since: 2022/3/24 10:44 上午
 * @Version
 */
@Log4j2
public class HttpClientUtil {
    /**
     * post请求传输json数据
     *
     * @param url
     * @param jsonParam
     * @param clazz
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static <T> T sendPostDataByJson(String url, String jsonParam, Class<T> clazz) {
        log.info(String.format("Http Post 请求{url : %s; 入参 : %s;}", url, jsonParam));
        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 设置参数到请求对象中
        StringEntity stringEntity = new StringEntity(jsonParam, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding("utf-8");
        httpPost.setEntity(stringEntity);
        T t = null;
        CloseableHttpResponse response = null;
        try {
            // 执行请求操作，并拿到结果（同步阻塞）
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            // 获取结果实体
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                //组装返回值
                if (!StringUtils.isEmpty(result)) {
                    JSONObject bodyJson = JSONObject.parseObject(result);
                    if (!MapUtils.isEmpty(bodyJson) && !ObjectUtils.isEmpty(bodyJson.get("data"))) {
                        Object data = bodyJson.get("data");
                        t = JSONObject.parseObject(JSONObject.toJSONString(data), clazz);
                        log.info(String.format("Http Post 请求{url : %s; 出参 : %s;}", url, JSONObject.toJSONString(t)));
                    }
                }
            }
            // 释放链接
            response.close();
        } catch (Exception e) {
            try {
                response.close();
            } catch (IOException e2) {
            }
            log.info(String.format("Http Post 请求{url : %s; 异常信息 : %s;}", url, e));
        }
        return t;
    }
}
