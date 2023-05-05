package com.csub.util;

import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.uploadcare.api.Client;
import com.uploadcare.api.File;
import com.uploadcare.upload.FileUploader;
import com.uploadcare.upload.UploadFailureException;
import com.uploadcare.upload.Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final Client client;

    public String uploadImage(MultipartFile file) {
        try {
            Uploader uploader = new FileUploader(client, file.getBytes(), file.getOriginalFilename());
            File uploadedFile = uploader.upload();
            URI uri = uploadedFile.getOriginalFileUrl();
            log.debug("Uploaded file: {}", uri);
            return uri.toString();
        } catch (UploadFailureException | IOException e) {
            log.error("Error uploading file: {}", e.getMessage());
            throw new ServerException("Error uploading file", e, ErrorList.SERVER_ERROR);
        }
    }
}