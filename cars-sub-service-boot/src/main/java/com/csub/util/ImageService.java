package com.csub.util;

import com.csub.exception.ErrorList;
import com.csub.exception.ServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    @Value("${image.upload.api.host}")
    private String imageUploadApiHost;

    @Value("${image.upload.api.key}")
    private String apiKeyHost;

    private final WebClient.Builder webClientBuilder;

    private final ObjectMapper objectMapper;

    public String uploadImage(MultipartFile file) {
        log.debug("Uploading image: {}", file.getOriginalFilename());
        String url = imageUploadApiHost + "/upload";
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("media", file.getResource());
        body.add("key", apiKeyHost);

        return parseUrl(webClientBuilder.build()
                .post()
                .uri(url)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block());
    }

    private String parseUrl(String response) {
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            int status = jsonNode.get("status").asInt();
            if (status != 200) {
                log.error("Error uploading image: {}", response);
                throw new ServerException("Error uploading image", ErrorList.SERVER_ERROR);
            }
            JsonNode data = jsonNode.get("data");
            return data.get("media").asText();
        } catch (JsonProcessingException e) {
            log.error("Error parsing response: {}", response);
            throw new ServerException("Error parsing response", e, ErrorList.SERVER_ERROR);
        }
    }
}
