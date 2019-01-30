package com.wxmimperio.springcloud.controller;

import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ClientController {

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/dc")
    public String dc() {
        String services = "Services: " + discoveryClient.getServices();
        System.out.println(services);
        return services;
    }

    /*@GetMapping("read/{fileName}")
    public ResponseEntity<InputStreamResource> readFile(@PathVariable("fileName") String fileName) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(fileName.getBytes());
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(out.toByteArray().length).body(resource);
    }*/

    @GetMapping("read/{fileName}")
    public ResponseEntity<byte[]> readFile(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {
       /* Map<String, Collection<String>> headers = new HashMap<>();
        Response response1 = feign.Response.builder().body(fileName, Charset.forName("UTF-8")).status(200).headers(headers).build();*/
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(fileName.getBytes());
        response.getOutputStream().write(fileName.getBytes());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(out.toByteArray().length).body(out.toByteArray());
    }
}
