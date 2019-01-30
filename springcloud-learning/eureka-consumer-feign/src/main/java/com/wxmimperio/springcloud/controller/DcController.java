package com.wxmimperio.springcloud.controller;

import com.wxmimperio.springcloud.feign.client.DcClient;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class DcController {

    private DcClient dcClient;

    @Autowired
    public DcController(DcClient dcClient) {
        this.dcClient = dcClient;
    }

    @GetMapping("/consumer")
    public String dc() {
        return dcClient.consumer();
    }

    @GetMapping("/read/{fileName}")
    public void readFile(@PathVariable("fileName") String fileName) throws IOException {
        Response response = dcClient.readFile(fileName);
        System.out.println(response.body().asInputStream().toString());
        /*ResponseEntity<InputStreamResource> responseEntity = dcClient.readFile(fileName);
        byte[] b = new byte[1024];
        System.out.println(responseEntity.getBody().getInputStream().read(b, 0, 2));*/
        /*InputStream is = responseEntity.getBody().getInputStream();
        //InputStream is = responseEntity.getBody().getURL().openStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1) {
            baos.write(i);
            System.out.println(baos.toString());
        }*/

    }
}
