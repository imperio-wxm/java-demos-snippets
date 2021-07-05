package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelController {

    private final ModelService modelService;

    @Autowired
    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/pipeline")
    public void pipelineModel() {
        modelService.buildModelInstance();
    }
}
