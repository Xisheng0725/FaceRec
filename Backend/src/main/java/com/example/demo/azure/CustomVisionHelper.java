package com.example.demo.azure;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class CustomVisionHelper {

    final static String trainingEndpoint = "southcentralus.api.cognitive.microsoft.com";
    final static String trainingApiKey = "c479074503a948ccb9c81dc0bf3b9d4f";
    static RestTemplate restTemplate = new RestTemplate();
    final static String projectID = "4c209840-8cfe-42ec-be85-f301c2fd10e6";
    public final static String tagEvan = "7c55e5f5-a60b-427d-ae77-288a102b5713";
    public final static String tagOthers = "2066f641-f441-4103-b694-eaed62ede5ec";

    public static void createproject(String projectName) throws JSONException {
        String url = "https://{endpoint}/customvision/v3.3/Training/projects?name={name}";

        Map<String, String> params = new HashMap<>();
        params.put("endpoint", trainingEndpoint);
        params.put("name", projectName);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        // System.out.println(builder.buildAndExpand(params).toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Training-key", trainingApiKey);

        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(builder.buildAndExpand(params).toUri(), request,
                String.class);
        // System.out.println(response.getBody())

        JSONObject jsonObject = new JSONObject(response.getBody());
        System.out.println(jsonObject.getString("id"));
    }

    public static void main(String[] args) throws JSONException, IOException {
        // createTag("Evan");
        // createTag("Others");
        // String fileName =
        // "/Users/zhangxisheng/Desktop/FaceRec/Backend/images/photo_1bba60f9-2dd7-42db-9800-2d59b9aec578.png";
        // uploadImage(tagEvan, fileName);
        Path path = Paths.get(
                "/Users/zhangxisheng/Desktop/FaceRec/Backend/images/photo_0e63f29f-cb9d-43d0-a101-93f29f31040f.png");
        byte[] data = Files.readAllBytes(path);
        System.out.println(validate(data));
    }

    public void uploadImages(File dir) throws JSONException, IOException {
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File image : directoryListing) {

                System.out.println(image.getAbsolutePath());
                uploadImage(tagOthers, image.getAbsolutePath());
            }
        }
    }

    public static void createTag(String tagName) throws JSONException {
        String url = "https://{endpoint}/customvision/v3.3/Training/projects/{projectId}/tags?name={name}";

        Map<String, String> params = new HashMap<>();
        params.put("endpoint", trainingEndpoint);
        params.put("projectId", projectID);
        params.put("name", tagName);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.buildAndExpand(params).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Training-key", trainingApiKey);

        HttpEntity<String> request = new HttpEntity<String>(headers);

        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        // System.out.println(response.getBody());

        JSONObject jsonObject = new JSONObject(response.getBody());
        System.out.println(jsonObject.getString("id") + ": " + tagName);
    }

    public static void uploadImage(String tagId, String fileName) throws JSONException, IOException {
        String url = "https://{endpoint}/customvision/v3.3/training/projects/{projectId}/images?tagIds={tagIds}";

        Map<String, String> params = new HashMap<>();
        params.put("endpoint", trainingEndpoint);
        params.put("projectId", projectID);
        params.put("tagIds", tagId);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI uri = builder.buildAndExpand(params).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("Training-key", trainingApiKey);

        Path path = Paths.get(fileName);
        byte[] imageFile = Files.readAllBytes(path);

        HttpEntity<byte[]> request = new HttpEntity<>(imageFile, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        System.out.println(response.getBody());
    }

    public static ResponseEntity<String> validate(byte[] data) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");
        headers.add("Prediction-Key", "c479074503a948ccb9c81dc0bf3b9d4f");

        HttpEntity<byte[]> entity = new HttpEntity<>(data, headers);
        String URL = "https://southcentralus.api.cognitive.microsoft.com/customvision/v3.0/Prediction/4c209840-8cfe-42ec-be85-f301c2fd10e6/classify/iterations/Iteration1/image";
        ResponseEntity<String> result = restTemplate.postForEntity(URL, entity, String.class);
        return result;
    }
}