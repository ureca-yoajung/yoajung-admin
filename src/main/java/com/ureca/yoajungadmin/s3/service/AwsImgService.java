package com.ureca.yoajungadmin.s3.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsImgService {
    String uploadImg(MultipartFile file);
    String uploadWithOptionalDelete(MultipartFile newFile, String oldUrl);
    void deleteImg(String imageAddr);
}
