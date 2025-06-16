package com.ureca.yoajungadmin.plan.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsImgService {
    String uploadImg(MultipartFile file);
    boolean deleteImageFromS3(String imageAddress);
}
