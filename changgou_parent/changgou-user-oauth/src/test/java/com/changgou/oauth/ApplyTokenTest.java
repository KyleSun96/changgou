package com.changgou.oauth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * @Program: ChangGou
 * @ClassName: ApplyTokenTest
 * @Description: 申请令牌测试
 * @Author: KyleSun
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class ApplyTokenTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Test
    public void applyToken() {

        // 1.1 构建请求地址：http://localhost:9200/oauth/token。choose【被获取服务的唯一表示名称】
        ServiceInstance serviceInstance = loadBalancerClient.choose("user-auth");
        // 1.2 http://localhost:9200
        URI uri = serviceInstance.getUri();
        // 1.3 http://localhost:9200/oauth/token
        String url = uri + "/oauth/token";

        // 2.2 body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", "itheima");
        body.add("password", "itheima");
        // 2.3 headers
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        // 创建base64(clientId:clientSecret)方法
        headers.add("Authorization", this.getHttpBasic("changgou", "changgou"));
        // 2.1 封装请求参数 【body，headers】
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // 3. 当后端出现了401、400时，表示当前用户没有权限，此时我们设置后端不对着两个异常编码进行处理，而是直接返回给前端
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });

        // 发送请求(先在配置类中声明restTemplate)【1.被请求的地址，2.被请求的方式，3.封装请求参数，4.数据的返回类型】
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
        // 得到数据
        Map map = responseEntity.getBody();
        System.out.println(map);

    }


    private String getHttpBasic(String clientId, String clientSecret) {
        String value = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode(value.getBytes());
        // Basic Y2hhbmdnb3U6Y2hhbmdnb3U=
        return "Basic " + new String(encode);
    }

}
