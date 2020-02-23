package com.example.job.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.util.StringUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: ymfa
 * @Date: 2020/2/14 14:40
 * @Description:
 */
public class SpiderUtil {
    List<String> list =new ArrayList(){{
        add("xxy");
        add("xld");
    }};
    public int pageNum =1;
    @Test
    public void getWaterData(){
        for (String name: list){
            System.out.println("xld");
            pageNum = 1;
            for (;pageNum<= 334;pageNum++){
                //try {
                //    Thread.sleep(1000);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
                // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
                // 创建Post请求
                HttpPost httpPost = new HttpPost("http://oa.xld:8081/xldCMS/sites/main/"+name+"_sq.jsp?p="+pageNum+"&datefrom=null&dateto=null");

                httpPost.setHeader("Content-Type", "application/json;charset=utf8");
                httpPost.setHeader("Cookie","JSESSIONID=5C630E392320EE8813F32F38535B440E; JSESSIONID=zDxCGnUFBmUFQz5vm1_RQQ1kxO-IHJuD9CtKPkrTsLgmww8Dsp6b!-239474541");
                String  res = post(httpPost);
                 if (StringUtils.hasText(res)){
                     FileUtil.saveFileWriter("F:\\work\\water\\"+name+pageNum+".html",res);
                }
            }
        }
    }

    public static String post(HttpPost httpPost){
        // 响应模型
        CloseableHttpResponse response = null;
        HttpEntity responseEntity = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String str = "";
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            responseEntity = response.getEntity();
            str = EntityUtils.toString(responseEntity);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static void main(String[] args) {
        for (int i = 1; i<=174; i++){
            HttpPost httpPost = new HttpPost("http://oa.xld:8081/xldCMS/sites/main/tzgs/kgdl.jsp?p="+i+"&datefrom=null&dateto=null");
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            httpPost.setHeader("Cookie","JSESSIONID=D095347346E1BC8658AA82228B3ABBDE; JSESSIONID=MztmvAx4ny3T4ibmGLj_K8OQhg64u0ft_IDGfk01sMFmbP1zeLDQ!-239474541");
            String  res = post(httpPost);
            if (StringUtils.hasText(res)){
                FileUtil.saveFileWriter("F:\\work\\water\\电站"+i+".html",res);
            }
        }

    }
}
