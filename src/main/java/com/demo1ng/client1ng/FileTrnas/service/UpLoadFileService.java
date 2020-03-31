package com.demo1ng.client1ng.FileTrnas.service;

import java.io.File;
import java.io.IOException;

public interface UpLoadFileService {
    public boolean UpLoadFile(File file) throws IOException;

    public void getInfoFromServer();
}
