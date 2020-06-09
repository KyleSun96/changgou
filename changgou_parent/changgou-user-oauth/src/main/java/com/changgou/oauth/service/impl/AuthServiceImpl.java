package com.changgou.oauth.service.impl;

import com.changgou.oauth.service.AuthService;
import com.changgou.oauth.util.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Program: ChangGou
 * @ClassName: AuthServiceImpl
 * @Description:
 * @Author: KyleSun
 **/
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;    // 注入redis模板类以操作redis

    @Value("${auth.ttl}")
    private long ttl;

    @Override
    public AuthToken login(String username, String password, String clientId, String clientSecret) {

        // 1.1 构建请求地址：http://localhost:9200/oauth/token。choose【被获取服务的唯一表示名称】
        ServiceInstance serviceInstance = loadBalancerClient.choose("user-auth");
        // 1.2 http://localhost:9200
        URI uri = serviceInstance.getUri();
        // 1.3 http://localhost:9200/oauth/token
        String url = uri + "/oauth/token";

        // 2.2 body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);
        // 2.3 headers
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        // 创建base64(clientId:clientSecret)方法
        headers.add("Authorization", this.getHttpBasic(clientId, clientSecret));
        // 2.1 封装请求参数 【body，headers】
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // 3. 放行401、400异常：表示当前用户没有权限，此时我们设置后端不对着两个异常编码进行处理，而是直接返回给前端
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });

        // A.发送请求，申请令牌(先在配置类中声明restTemplate)【1.被请求的地址，2.被请求的方式，3.封装请求参数，4.数据的返回类型】
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
        Map map = responseEntity.getBody();
        if (map == null || map.get("access_token") == null || map.get("refresh_token") == null || map.get("jti") == null) {
            //申请令牌失败
            throw new RuntimeException("申请令牌失败");
        }

        // B.封装结果数据
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken((String) map.get("access_token"));
        authToken.setRefreshToken((String) map.get("refresh_token"));
        authToken.setJti((String) map.get("jti"));

        // C.将jti作为redis中的key,将jwt作为redis中的value进行数据的存放
        stringRedisTemplate.boundValueOps(authToken.getJti()).set(authToken.getAccessToken(), ttl, TimeUnit.SECONDS);
        return authToken;

    }


    private String getHttpBasic(String clientId, String clientSecret) {
        String value = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode(value.getBytes());
        // Basic Y2hhbmdnb3U6Y2hhbmdnb3U=
        return "Basic " + new String(encode);
    }
}
