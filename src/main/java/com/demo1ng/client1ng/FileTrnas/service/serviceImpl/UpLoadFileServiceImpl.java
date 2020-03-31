package com.demo1ng.client1ng.FileTrnas.service.serviceImpl;

import com.demo1ng.client1ng.FileTrnas.service.UpLoadFileService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class UpLoadFileServiceImpl implements UpLoadFileService{
//    public static void main(String[] args) throws IOException {
//        File file = new File("D:\\bisheTest\\webReception\\gra-design\\index.html");
//        System.out.println(file.getName());
//        UpLoadFileServiceImpl upLoadFileService = new UpLoadFileServiceImpl();
//        System.out.println(upLoadFileService.UpLoadFile(file));
//    }

    /**
    *@Description   上传文件到服务端
    *@Param
    *@Return
    *@Author 1ng
    */
    @Override
    public boolean UpLoadFile(File file) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        String res = null;

        try{
            HttpPost httpPost = new HttpPost("http://localhost:10011/UploadServlet");
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);

            //把用户上传的文件塞进去
            multipartEntityBuilder.addBinaryBody("file",file);

            //发出请求
            httpPost.setEntity(multipartEntityBuilder.build());
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK){
                HttpEntity httpEntity = response.getEntity();
                res = EntityUtils.toString(httpEntity);

                EntityUtils.consume(httpEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            HttpClientUtils.closeQuietly(httpClient);
            HttpClientUtils.closeQuietly(response);
        }

        return false;
    }

    @Override
    public void getInfoFromServer() {

    }
}
