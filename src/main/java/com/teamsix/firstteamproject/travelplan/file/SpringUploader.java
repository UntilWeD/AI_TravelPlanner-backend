package com.teamsix.firstteamproject.travelplan.file;

import com.teamsix.firstteamproject.travelplan.dto.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SpringUploader {

    //@Value("${file.dir}")
    private String fileDir;

    public String getFullpath(String filename){
        return fileDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }

        return storeFileResult;
    }


    public UploadFile storeFile(MultipartFile multipartFile) throws IOException{
        if ((multipartFile.isEmpty())){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullpath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName);
    }


    /**
     * 유일한 이름을 생성하는 UUID를 이용해 서버 내부에서 관리할 수 있도록 하여
     * UUID를 사용해서 충돌하지 않도록 한다.
     * @param originalFilename : 원래 파일 이름명
     * @return 저장될 파일이름 반환
     */
    private String createStoreFileName(String originalFilename){
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }


    /*
    // 확장자를 별도로 추출해서 서버 내부에서 관리하는 파일명에도 붙여준다.
     */
    private String extractExt(String originalFilename){
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos+1);
    }




}
