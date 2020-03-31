package com.demo1ng.client1ng.FileTrnas.service;

import com.demo1ng.client1ng.FileTrnas.pojo.FileEntity;

import java.util.List;

public interface DownLoadFileService {
    public boolean DownLoadFile();

    public int downloadThisFile(String uuid);

    public List<FileEntity> getFileList();
}
