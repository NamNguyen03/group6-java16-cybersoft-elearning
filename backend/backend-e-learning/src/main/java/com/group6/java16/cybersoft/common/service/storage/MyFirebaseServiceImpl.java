package com.group6.java16.cybersoft.common.service.storage;

import com.google.auth.oauth2.GoogleCredentials;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import com.google.cloud.storage.StorageOptions;
import com.group6.java16.cybersoft.common.exception.BusinessException;

import java.util.HashMap;
import java.util.Map;

@Service
@PropertySources({ @PropertySource("classpath:application-config.properties") })
public class MyFirebaseServiceImpl implements MyFirebaseService {

    private Storage storage;

    @Value("${storage.google.firbase.project-id}")
    private String projectId;
    @Value("${storage.google.firbase.bucket-name}")
    private String bucketName;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        try {
            ClassPathResource serviceAccount = new ClassPathResource("firebase.json");
            storage = StorageOptions.newBuilder().
                    setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream())).
                    setProjectId(projectId).build().getService();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String saveFile(MultipartFile file) {
        
        String imageName, url ="";
        try {
            imageName = generateFileName(file.getOriginalFilename());
            Map<String, String> map = new HashMap<>();
            map.put("firebaseStorageDownloadTokens", imageName);
            BlobId blobId = BlobId.of(bucketName, imageName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setMetadata(map)
                    .setContentType(file.getContentType())
                    .build();
            storage.create(blobInfo, file.getInputStream());
    
            url = "https://firebasestorage.googleapis.com/v0/b/e-learning-5efea.appspot.com/o/" +
                imageName +
                "?alt=media&token=" + imageName;
        }catch(Exception e){
            throw new BusinessException("upload file fails");
        }
       
        return url;
    }
    
    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }
}
