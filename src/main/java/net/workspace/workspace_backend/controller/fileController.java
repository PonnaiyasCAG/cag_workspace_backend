package net.workspace.workspace_backend.controller;

import net.workspace.workspace_backend.service.FileStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin

public class fileController {

  /*  @Autowired
    private FileStructureService fileStructureService;

    @PostMapping("/create")
    public String createFileStructure(@RequestBody List<Map<String, Object>> structure) {
        return fileStructureService.createStructure(structure);
    }
}*/
  @Autowired
  private FileStructureService fileStructureService;

    @PostMapping("/create/{userId}")
    public String createFileStructure(
            @PathVariable String userId,
            @RequestBody List<Map<String, Object>> structure
    ) {
        return fileStructureService.createStructure(userId, structure);
    }
}


