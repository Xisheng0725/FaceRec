package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.UUID;

import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format(template, name);
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String saveImage(@RequestBody String imageString) throws IOException, JSONException {
        System.out.println(imageString);
        String base64 = imageString.replace("data:image/png;base64,", "");
        byte[] imageBytes = Base64.getDecoder().decode(imageString);

        String imageName = "photo_" + UUID.randomUUID() + ".png";
        String fileName = "./images/" + imageName;

        File path = new File("./images/");
        if (!path.exists()) {
            path.mkdir();
        }

        Files.write(new File(fileName).toPath(), imageBytes);


        return "Image saved successfully";
    }
}
