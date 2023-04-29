package com.csub.util;

import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class for storing images in the file system and retrieving them
 */

@Service
@Slf4j
public class ImageService {

    private static final String IMAGE_PATH = "src/main/resources/static/images/";

    public String storeImage(MultipartFile file) {
        log.debug("Storing image: {}", file.getOriginalFilename());
        String fileName = file.getOriginalFilename();
        Path filePath = Path.of(IMAGE_PATH + fileName);
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            log.debug("Error while saving image: {}", e.getMessage());
            throw new ServerException("Error while saving image", e, ErrorList.IMAGE_SAVE_ERROR);
        }

        log.debug("Image stored: {}", file.getOriginalFilename());
        return IMAGE_PATH + fileName;
    }

    public byte[] getImage(String fileName) {
        log.debug("Getting image: {}", fileName);
        File file = new File(fileName);
        byte[] image;
        try {
            image = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            log.debug("Error while getting image: {}", e.getMessage());
            throw new ServerException("Error while getting image", e, ErrorList.IMAGE_GET_ERROR);
        }
        log.debug("Image got: {}", fileName);
        return image;
    }

}
