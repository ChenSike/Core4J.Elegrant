package ikcoder.services.controller;

import ikcoder.services.docservices.DocServices_userpf;
import ikcoder.services.entities.DTO.DTO_CSVHeader;
import ikcoder.services.entities.DTO.DTO_common;
import ikcoder.services.entities.DTO.DTO_users;
import ikcoder.services.services.Services_common;
import ikcoder.services.services.Services_messages;
import ikcoder.services.services.Services_server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;

@RestController
public class Controller_server {

    Services_server services_server;
    DocServices_userpf docServices_userpf;
    Services_messages services_messages;

    @Autowired
    public Controller_server(Services_server _servicesServer, DocServices_userpf docServices_userpf) {
        this.services_server = _servicesServer;
        this.docServices_userpf = docServices_userpf;
    }

    @GetMapping("/server/status")
    @ResponseBody
    public HashMap<String,String> ServerStatus()
    {
        return services_server.GetStatus();
    }

    @PostMapping("/server/upload/single")
    @ResponseBody
    public DTO_common uploadSingle(MultipartFile multipartFile)
    {
        String fileName = multipartFile.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        DTO_users dto_users = Services_common.getUserFromRedis();
        docServices_userpf.SetCurrentUploadFile(dto_users.getUid(),"");
        String filename = docServices_userpf.GetCurrentUploadFileName(dto_users.getUid());
        fileName=filename+"."+suffixName;
        String filePath = Services_common.getTmpPoolPath();
        try {
            multipartFile.transferTo(new File(filePath+"\\"+fileName));
            return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false);
        } catch (Exception e) {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true);
        }
    }

    @GetMapping("/server/upload/currentfile")
    @ResponseBody
    public DTO_common getCurrentFile()
    {
        DTO_users dto_users = Services_common.getUserFromRedis();
        String currentFile = docServices_userpf.GetCurrentUploadFileName(dto_users.getUid());
        if(!currentFile.isEmpty())
            return Services_common.newCommonResStringItem(currentFile, "8001", false);
        else
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true);
    }

    @GetMapping("/server/upload/clear")
    @ResponseBody
    public DTO_common setClearUploadFiles()
    {
        try {
            DTO_users dto_users = Services_common.getUserFromRedis();
            docServices_userpf.SetClearUploadFile(dto_users.getUid());
            return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false);
        }
        catch (Exception err)
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true);
        }
    }

    @GetMapping("/server/upload/getCSVHeader")
    @ResponseBody
    public DTO_common getCSVHeader() {
        try {

            DTO_users dto_users = Services_common.getUserFromRedis();
            DTO_CSVHeader dto_csvHeader = new DTO_CSVHeader();
            String filename = docServices_userpf.GetCurrentUploadFileName(dto_users.getUid());
            dto_csvHeader.setMapCSVHeader(Services_common.GetCVSHeaderMap(Services_common.getTmpPoolPath() + "\\" + filename));
            return Services_common.newCommonResItem(dto_csvHeader, "8001", false);
        }
        catch (Exception err)
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true);
        }
    }



}
