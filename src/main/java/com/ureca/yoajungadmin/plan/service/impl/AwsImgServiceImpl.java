package com.ureca.yoajungadmin.plan.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.ureca.yoajungadmin.plan.service.AwsImgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AwsImgServiceImpl implements AwsImgService {
    @Autowired
    private final AmazonS3 amazonS3;

    private String PLAN_IMG_DIR = "/img/plan/";

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String uploadImg(MultipartFile image) {
        if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())){
            throw new IllegalArgumentException("이미지 파일은 비어있을 수 없습니다.");
        }
        return uploadImage(image);
    }

    @Override
    public boolean deleteImageFromS3(String imageAddress) {
        String key = getKeyFromImageAddress(imageAddress);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));
        } catch (Exception e) {
            throw new IllegalArgumentException("S3 이미지 삭제에 실패.");
        }

        return true;
    }

    private String getKeyFromImageAddress(String imageAddress){
        try{
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        }catch (MalformedURLException | UnsupportedEncodingException e){
            throw new IllegalArgumentException(e);
        }
    }

    private String uploadImage(MultipartFile image) {
        this.validateImageFileExtention(image.getOriginalFilename());
        try {
            return this.uploadImageToS3(image, PLAN_IMG_DIR);
        } catch (IOException e) {
            throw new IllegalArgumentException("S3 이미지 업로드를 실패.");
        }
    }

    private String uploadImageToS3(MultipartFile image, String dirName) throws IOException {
        String originalFilename = image.getOriginalFilename(); //원본 파일 명
        String extention = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명

        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is); //image를 byte[]로 변환

        ObjectMetadata metadata = new ObjectMetadata(); //metadata 생성
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(bytes.length);

        //S3에 요청할 때 사용할 byteInputStream 생성
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try{
            //S3로 putObject 할 때 사용할 요청 객체
            //생성자 : bucket 이름, 파일 명, byteInputStream, metadata
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucket, s3FileName, byteArrayInputStream, metadata);

            //실제로 S3에 이미지 데이터를 넣는 부분이다.
            amazonS3.putObject(putObjectRequest); // put image to S3
        }catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    private void validateImageFileExtention(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new IllegalArgumentException("이미지 확장자가 없습니다.");
        }

        String extention = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extention)) {
            throw new IllegalArgumentException("이미지 확장자 형식이 맞지 않습니다.");
        }
    }
}
