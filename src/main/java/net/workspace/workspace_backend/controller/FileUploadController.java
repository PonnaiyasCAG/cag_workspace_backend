package net.workspace.workspace_backend.controller;

import net.workspace.workspace_backend.model.FileUploadRequest;
import net.workspace.workspace_backend.model.FileUploadResponse;
import net.workspace.workspace_backend.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file")
@CrossOrigin( origins = {"http://localhost:3000/"})

public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public FileUploadResponse uploadFiles(@RequestBody List<FileUploadRequest> result) {
        return fileUploadService.saveFiles(result);
    }

}
