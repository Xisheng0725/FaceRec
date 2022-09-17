package com.example.demo.azure;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

public class BlobStorageHelper {

    static String connectionStr = "DefaultEndpointsProtocol=https;AccountName=xishengstore;AccountKey=FqTwskj49pwAoCs0JltWv0qtdpJdeAfApn7ZtvYW+sMbbPJlaBNTF9PGEpXGfXH8q+sCZNeIsH6I+AStWeNxTw==;EndpointSuffix=core.windows.net";

    static BlobServiceClient blobServiceClient;

    private static BlobStorageHelper instance;

    private BlobStorageHelper() {
    }

    public static BlobStorageHelper getInstance() {
        if (instance == null) {
            instance = new BlobStorageHelper();
            blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionStr).buildClient();
        }
        return instance;
    }

    public BlobContainerClient getBlobContainer(String containerName) {
        // Create a BlobServiceClient object which will be used to create a container
        // client

        // Create the container and return a container client object
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        if (!containerClient.exists()) {
            containerClient.create();
        }

        return containerClient;
    }

    public void uploadFile(String fileName) {

        // Get a reference to a blob
        BlobClient blobClient = getBlobContainer("images").getBlobClient(fileName);

        // Upload the blo
        blobClient.uploadFromFile("./images/" + fileName);
    }

}