package api;

import com.demo1ng.client1ng.FileTrnas.service.DownLoadFileService;
import com.demo1ng.client1ng.FileTrnas.service.serviceImpl.DownLoadFileServiceImpl;

public class test {
    public static void main(String[] args) {
        DownLoadFileServiceImpl downLoadFileService = new DownLoadFileServiceImpl();
        downLoadFileService.getFileList();
    }
}
