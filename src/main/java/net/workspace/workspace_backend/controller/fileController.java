package net.workspace.workspace_backend.controller;

import net.workspace.workspace_backend.model.StructureResponse;
import net.workspace.workspace_backend.service.FileStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin( origins = {"http://localhost:3000/"})
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
  public ResponseEntity<StructureResponse> createFileStructure(
          @PathVariable String userId,
          @RequestBody List<Map<String, Object>> structure
  ) {
    StructureResponse response = fileStructureService.createStructure(userId, structure);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/structure/{userId}")
  public ResponseEntity<StructureResponse> getStructure(@PathVariable String userId) {
    StructureResponse response = fileStructureService.getStructure(userId);
    return ResponseEntity.ok(response);
  }



}


