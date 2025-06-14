package net.workspace.workspace_backend.service;

import net.workspace.workspace_backend.model.FileUploadRequest;
import net.workspace.workspace_backend.model.FileUploadResponse;
import net.workspace.workspace_backend.model.UploadedFile;
import net.workspace.workspace_backend.repository.UploadedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class FileUploadService {

    @Autowired
    private UploadedFileRepository fileRepository;

    public FileUploadResponse saveFiles(List<FileUploadRequest> requests) {
        for (FileUploadRequest fileReq : requests) {
            try {
                String base64Data = fileReq.getBase64().split(",")[1];
                byte[] fileBytes = Base64.getDecoder().decode(base64Data);

                UploadedFile file = new UploadedFile();
                file.setName(fileReq.getName());
                file.setType(fileReq.getType());
                file.setUserId(fileReq.getUserId());
                file.setBase64(fileBytes);

                fileRepository.save(file);

            } catch (Exception e) {
                e.printStackTrace();
                return new FileUploadResponse("error", "Failed to save file: " + fileReq.getName() + " - " + e.getMessage());
            }
        }
        return new FileUploadResponse("success", "Files uploaded successfully!");
    }

}
