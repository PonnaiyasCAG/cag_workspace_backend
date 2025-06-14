package net.workspace.workspace_backend.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import net.workspace.workspace_backend.model.FileUploadRequest;
import net.workspace.workspace_backend.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public String uploadFiles(@RequestBody List<FileUploadRequest> result) {
        System.out.println("varuthu >>>>1");
        return fileUploadService.saveFiles(result);
    }
}
