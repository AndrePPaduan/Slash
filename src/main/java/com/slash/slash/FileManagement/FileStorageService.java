package com.slash.slash.FileManagement;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileStorageService {

    public String storeFile(MultipartFile file, int id, String columnName);

    public Resource loadFileAsResource(int id, String columnName, String filename);
}
