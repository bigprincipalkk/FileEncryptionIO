package com.demo1ng.client1ng.FileTrnas.service.serviceImpl;

import api.HttpClientUtil;
import com.demo1ng.client1ng.FileTrnas.pojo.FileEntity;
import com.demo1ng.client1ng.FileTrnas.service.DownLoadFileService;
import keyApi.AESUtils;
import keyApi.RSAUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import api.getPrivateKey;
@Service
public class DownLoadFileServiceImpl implements DownLoadFileService {
    Logger logger = LoggerFactory.getLogger(DownLoadFileServiceImpl.class);

    @Resource
    private JdbcTemplate jdbcTemplate;


    @Override
    public boolean DownLoadFile() {
        return false;
    }

    @Override
    public int downloadThisFile(String uuid) {
        Map<String,String> resMap = new HashMap<String,String>();
        String url="http://127.0.0.1:10011/downLoadThisFile";
        resMap.put("uuid",uuid);
        String SID =null;
        String privateKey = null;
        String Signature =null;

        //用对称密钥代替随机生成的X_SID
        try {
            SID = AESUtils.getSecretKey();
            //获取客户端持有的私钥
            privateKey = getPrivateKey.getKey();
            Signature = RSAUtils.sign(SID,privateKey);

        } catch (Exception e) {
            e.printStackTrace();
        }



        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (resMap != null) {
                for (String key : resMap.keySet()) {
                    builder.addParameter(key, resMap.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("SID",SID);
            httpGet.setHeader("Signature",Signature);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }

            logger.info("创建HTTP请求成功");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return 1;
    }

    @Override
    public List<FileEntity> getFileList() {
        List<FileEntity> list = new ArrayList<FileEntity>();
        String sql = "SELECT * FROM files";

        //将返回的结果集放入list
        list = jdbcTemplate.query(sql, new RowMapper<FileEntity>() {
            FileEntity fileEntity = null;
            @Override
            public FileEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                fileEntity = new FileEntity();
                fileEntity.setUuid(resultSet.getString("uuid"));
                fileEntity.setOrgName(resultSet.getNString("orgName"));

                return fileEntity;
            }
        });

        for (FileEntity fileEntity:list){

        }

        return list;
    }
}
