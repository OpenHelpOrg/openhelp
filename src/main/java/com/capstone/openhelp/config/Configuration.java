package com.capstone.openhelp.config;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import static com.capstone.openhelp.config.Domain.YOUR_DOMAIN_NAME;

public class Configuration {

//    Configuration configuration = new Configuration()
//            .domain("myopenhelp.com")
//            .apiKey("key-52b6835e-c057b1e0")

//            .from("Test account", "test@myopenhelp.com");
    public static JsonNode sendSimpleMessage() throws UnirestException {

        HttpResponse<com.mashape.unirest.http.JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + YOUR_DOMAIN_NAME + "/messages")
                .basicAuth("api", APIK.API_KEY)
                .field("from", "Excited User <test@openhelp.com>")
                .field("to", "walsh.c.bryan@gmail.com")
                .field("subject", "hello, and have a nice day!")
                .field("text", "testing")
                .asJson();

        return request.getBody();
    }

    public static void main(String[] args) {
        try {
            JsonNode jsonNode = sendSimpleMessage();
            System.out.println(jsonNode.toString());
        } catch(UnirestException e) {
            e.printStackTrace();
    }
}
}



