package com.wxmimperio.echarts.controller;

import com.wxmimperio.echarts.entity.TaskInfo;
import com.wxmimperio.echarts.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@Controller
public class EchartsController {

    private DataService dataService;

    @Autowired
    public EchartsController(DataService dataService) {
        this.dataService = dataService;
    }

    @RequestMapping(value = "show", method = RequestMethod.GET)
    public String show(Model model) {
        return "show";
    }

    @ResponseBody
    @RequestMapping(value = "getData", method = RequestMethod.GET)
    public Map<Integer, List<TaskInfo>> getData(@RequestParam("type") String type, @RequestParam("partDate") String partDate) throws ParseException {
        return dataService.getShowData(type, partDate);
    }

    @ResponseBody
    @RequestMapping(value = "prepareData", method = RequestMethod.GET)
    public void prepareData(@RequestParam("partDate") String partDate) throws ParseException {
        dataService.prepareData(partDate);
    }
}
