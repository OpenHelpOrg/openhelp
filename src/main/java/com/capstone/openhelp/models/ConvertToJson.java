package com.capstone.openhelp.models;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class ConvertToJson {
    private Gson gson;

    public ConvertToJson(){
        gson = new Gson();
    }

    public void writeJSONFile(String filename, Object o){
        String directory = "src/main/resources/static/js/";
        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, filename);

        try{
            if(Files.notExists(dataDirectory)){
                Files.createDirectories(dataDirectory);
            }

            if(!Files.exists(dataFile)){
                Files.createFile(dataFile);
            }

            Path filepath = Paths.get(directory, filename);
            Files.write(filepath, Collections.singleton(gson.toJson(o)));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
