package com.demo1ng.client1ng.FileTrnas.controller;

import api.MultipartFileToIOFile;
import com.demo1ng.client1ng.FileTrnas.service.*;
import com.demo1ng.client1ng.FileTrnas.service.UpLoadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;


@Controller
public class UpLoadFileController {
    Logger logger = LoggerFactory.getLogger(UpLoadFileService.class);
    @Autowired
    private UpLoadFileService upLoadFileService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/fileTrans")
    public String fileTrans(){
        logger.info("获取文件列表");
        return "fileTrans";
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        System.out.println(file.getOriginalFilename());
        upLoadFileService.UpLoadFile(MultipartFileToIOFile.multipartFileToFile(file));
        return "file is upload successfully";
    }

    @ResponseBody
    @RequestMapping(value = "/getInfoFromServer",method = RequestMethod.GET)
    public void getInfoFromServer(HttpServletRequest request, HttpServletResponse response){
        Map resMap = request.getParameterMap();
        System.out.println(request.getParameter("uuid"));
        System.out.println(request.getParameter("orgName"));

        String sql = "INSERT INTO files(uuid,orgName) VALUES('"+request.getParameter("uuid")+"','"+request.getParameter("orgName")+"')";
        jdbcTemplate.update(sql);
    }

}
