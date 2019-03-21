package sample.controller;

import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sample.data.Module;
import sample.repository.ModuleRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by buchgeher on 05.09.2016.
 */

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private ModuleRepository moduleRepository;

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello";
    }

    @RequestMapping(path = "/modules", method = RequestMethod.GET)
    public List<Module> modules() {
        return IteratorUtil.asList(moduleRepository.findAll(0));
    }




}
