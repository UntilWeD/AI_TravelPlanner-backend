package com.teamsix.firstteamproject.travelplan.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;

    public AwsS3Service(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String TRAVEL_PLAN_DIR = "TravelPlan/";

    public String uploadImage(MultipartFile multipartFile){
        return "yet";
    }

    public List<String> uploadImageList(List<MultipartFile> multipartFile, Long userId){
        List<String> fileUrlList = new ArrayList<>();

        multipartFile.forEach(file -> {
            String fileName = generateBasketItemImageName(file,userId);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()){
                amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException ex){
                log.info("[AwsS3Service] uploadImageList Method Error!! {}", ex);
            }

            fileUrlList.add(getFileUrl(fileName));
        });

        return fileUrlList;
    }

    private String generateBasketItemImageName(MultipartFile multipartFile, Long userId){
        return TRAVEL_PLAN_DIR + userId + "/" + UUID.randomUUID().toString()
                + "-" + multipartFile.getOriginalFilename().replace(" ", "_");
    }

    private String getFileUrl(String fileName){
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


    private File convertMultiPartFileToFile(MultipartFile file) throws IOException{
        File convertedFile = new File(file.getOriginalFilename());
        try(FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

    private void removeFile(File target){
        if(target.delete()){
            return ;
        }
        log.info("File Delete Fail!");
    }
}
