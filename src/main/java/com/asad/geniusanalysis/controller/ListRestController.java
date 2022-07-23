package com.asad.geniusanalysis.controller;

import com.asad.geniusanalysis.Scraper;
import com.asad.geniusanalysis.service.DatabaseManager;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:4200")
public class ListRestController {
    @Autowired
    public DatabaseManager databaseManager;

    // localhost:8080/persistLink[...]
    @RequestMapping(path = "/persistLink/{link}", method = RequestMethod.GET)
    public ResponseEntity<String> test(@PathVariable String link) {
        String baseUrl = "https://genius.com/";

        String geniusUrl = baseUrl + link;

        String title;

        try {
            title = Scraper.createSongJSON(geniusUrl, "temp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        File file = new File("temp/" + title + ".json");

        // This is the source of the unexpected JSON error. Weird thing with the strings.
        return new ResponseEntity<String>(file.getPath(), HttpStatus.OK);
    }

}