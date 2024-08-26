package com.teamsix.firstteamproject.travelplan.service;


import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
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

        log.info("[uploadImageList]fileUrlList : {} ", fileUrlList.toString());
        log.info("[uploadImageList]fileUrlList : {} ", fileUrlList.get(0));
        return fileUrlList;
    }

    private String generateBasketItemImageName(MultipartFile multipartFile, Long userId){
        return TRAVEL_PLAN_DIR + userId + "/" + UUID.randomUUID().toString()
                + "-" + multipartFile.getOriginalFilename();
    }

    private String getFileUrl(String fileName){
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void deleteImage(List<String> imageFileNames, Long userId){
        List<String> failedDeletions = new ArrayList<>();
        for(String imageName : imageFileNames) {
            try {
                amazonS3Client.deleteObject(
                        new DeleteObjectRequest(bucket, TRAVEL_PLAN_DIR + userId + "/" + imageName));
            } catch (AmazonClientException e) {
                log.error("Error deleting object {} from bucket {}: {}", imageName, bucket, e.getMessage());
                failedDeletions.add(imageName);
            }
        }
        // 삭제되지 못한 파일들 처리 미구현
    }

    // 폴더안에 이미지 반환
    public List<String> getImageListInFolder(Long userId) {
        List<String> imageNames = new ArrayList<>();
        ListObjectsV2Request req = new ListObjectsV2Request()
                .withBucketName(bucket)
                .withPrefix(TRAVEL_PLAN_DIR + userId + "/");
        ListObjectsV2Result result;

        do {
            result = amazonS3Client.listObjectsV2(req);
            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                String imageName = objectSummary.getKey();
                imageNames.add(imageName);
            }
            req.setContinuationToken(result.getContinuationToken());
        } while (result.isTruncated());
        log.info("getImageListInFolder method : {} ", imageNames);
        return imageNames;
    }



    // 만약 파일변환이 필요하다면 사용
    private File convertMultiPartFileToFile(MultipartFile file) throws IOException{
        File convertedFile = new File(file.getOriginalFilename());
        try(FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }
        return convertedFile;
    }

    // 만약 파일변환이 필요하다면 사용
    private void removeFile(File target){
        if(target.delete()){
            return ;
        }
        log.info("File Delete Fail!");
    }
}
