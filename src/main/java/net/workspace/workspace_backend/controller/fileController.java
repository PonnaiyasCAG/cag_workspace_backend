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

  @DeleteMapping("/delete/{userId}")
  public ResponseEntity<StructureResponse> deleteItems(
          @PathVariable String userId,
          @RequestBody Map<String, List<String>> payload
  ) {
    List<String> paths = payload.get("paths");
    StructureResponse response = fileStructureService.deleteItems(userId, paths);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/rename/{userId}")
  public ResponseEntity<StructureResponse> renameItem(
          @PathVariable String userId,
          @RequestBody Map<String, String> payload
  ) {
    String oldPath = payload.get("oldPath");
    String newName = payload.get("newName");
    StructureResponse response = fileStructureService.renameItem(userId, oldPath, newName);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/structure/{userId}")
  public ResponseEntity<StructureResponse> updateFileStructure(
          @PathVariable String userId,
          @RequestBody List<Map<String, Object>> structure
  ) {
    StructureResponse response = fileStructureService.updateStructure(userId, structure);
    return ResponseEntity.ok(response);
  }


}


