package ikcoder.services.controller;

import ikcoder.services.services.Services_server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class Controller_server {

    private Services_server _servicesServer;

    @Autowired
    public Controller_server(Services_server servicesServer) {
        this._servicesServer = servicesServer;
    }

    @GetMapping("/server/status")
    @ResponseBody
    public HashMap<String,String> ServerStatus()
    {
        return _servicesServer.GetStatus();
    }


}
