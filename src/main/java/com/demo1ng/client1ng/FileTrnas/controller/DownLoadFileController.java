package com.demo1ng.client1ng.FileTrnas.controller;

import api.getPrivateKey;
import api.MultipartFileToIOFile;
import keyApi.AESUtils;
import keyApi.RSAUtils;
import org.json.JSONObject;
import com.demo1ng.client1ng.FileTrnas.pojo.FileEntity;
import com.demo1ng.client1ng.FileTrnas.service.DownLoadFileService;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/download")
public class DownLoadFileController {
    private static final long FILE_MAX_SIZE = 1024 * 1024 * 200;
    Logger logger = LoggerFactory.getLogger(DownLoadFileController.class);

    @Autowired
    private  DownLoadFileService downLoadFileService;

    @ResponseBody
    @GetMapping("/getFileList")
    public List<FileEntity> getFileList(){
        return downLoadFileService.getFileList();
    }

    @PostMapping("/downLoadThisFile")
    public int downLoadThisFile(String uuid){
        return downLoadFileService.downloadThisFile(uuid);
    }

    @RequestMapping(value = "/getDownloadFile",method = RequestMethod.POST)
    public void getDownloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //强转request
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //获取文件map
        Map<String, MultipartFile> multipartFileMap = multipartRequest.getFileMap();
        MultipartFile resFile = multipartFileMap.get("file");
        //解密前
        File beforeFile = MultipartFileToIOFile.multipartFileToFile(resFile);
        //获取数据源 并解密成对称密钥
        String madaDataJson = multipartRequest.getParameter("data");
        logger.info("传过来json为："+madaDataJson);

        //获取公钥加密后的对称密钥
        JSONObject jb = new JSONObject(madaDataJson);
        String beforeKey = jb.getString("mataData");
        String orgName = jb.getString("orgName");
        System.out.println(jb.getString("mataData"));
        //解密获得对称密钥
        String privateKey = getPrivateKey.getKey();
        String AESKey = RSAUtils.decrypt(beforeKey,privateKey);
        //默认保存在downloadfiles
        String saveFile = "D:\\bisheTest\\client1ng\\downloadfiles\\"+orgName;
        AESUtils.decryptFile(AESKey,beforeFile,saveFile);

        beforeFile.delete();

        return;

    }
}
