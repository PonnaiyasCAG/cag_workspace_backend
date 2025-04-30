package net.workspace.workspace_backend.service;

import net.workspace.workspace_backend.model.StructureResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
/*public class FileStructureService {


    private static final String BASE_PATH = "D:\\Workspace";

    public String createStructure(List<Map<String, Object>> structure) {
        try {
            for (Map<String, Object> item : structure) {
                createRecursive(item, BASE_PATH);
            }
            return "Folder structure created successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @SuppressWarnings("unchecked")
    private void createRecursive(Map<String, Object> node, String currentPath) throws IOException {
        String name = (String) node.get("name");
        String type = (String) node.get("type");
        String newPath = Paths.get(currentPath, name).toString();

        if ("folder".equals(type)) {
            Files.createDirectories(Paths.get(newPath));
            List<Map<String, Object>> children = (List<Map<String, Object>>) node.get("children");
            if (children != null) {
                for (Map<String, Object> child : children) {
                    createRecursive(child, newPath);
                }
            }
        } else if ("file".equals(type)) {
            Files.createDirectories(Paths.get(currentPath)); // Ensure directory exists
            String url = (String) node.get("url");
            String originalType = (String) node.get("originalType");
            Files.write(Paths.get(newPath), ("Dummy content for: " + name).getBytes());
        }
    }
}*/
/*public class FileStructureService {

    private static final String BASE_PATH = "D:\\Workspace";

    public String createStructure(String userId, List<Map<String, Object>> structure) {
        String userBasePath = Paths.get(BASE_PATH, userId).toString();
        try {
            for (Map<String, Object> item : structure) {
                createRecursive(item, userBasePath);
            }
            return "Folder structure created successfully for userId: " + userId;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @SuppressWarnings("unchecked")
    private void createRecursive(Map<String, Object> node, String currentPath) throws IOException {
        String name = (String) node.get("name");
        String type = (String) node.get("type");
        String newPath = Paths.get(currentPath, name).toString();

        if ("folder".equals(type)) {
            Files.createDirectories(Paths.get(newPath));
            List<Map<String, Object>> children = (List<Map<String, Object>>) node.get("children");
            if (children != null) {
                for (Map<String, Object> child : children) {
                    createRecursive(child, newPath);
                }
            }
        } else if ("file".equals(type)) {
            Files.createDirectories(Paths.get(currentPath)); // Ensure the directory exists
            String originalType = (String) node.get("originalType");

            // Just writing dummy content as explained before
            Files.write(Paths.get(newPath), ("Dummy file content: " + name + "\nType: " + originalType).getBytes());
        }
    }
}*/

public class FileStructureService {

    private static final String BASE_PATH = "D:\\Workspace";
    private static final long MAX_USER_FOLDER_SIZE = 50 * 1024 * 1024; // 50 MB

    public StructureResponse createStructure(String userId, List<Map<String, Object>> structure) {
        String userBasePath = Paths.get(BASE_PATH, userId).toString();

        try {
            Files.createDirectories(Paths.get(userBasePath));

            for (Map<String, Object> item : structure) {
                createRecursive(item, userBasePath, userBasePath);
            }

            String message = "Folder structure created successfully ";
            return new StructureResponse("success", message, structure);

        } catch (Exception e) {
            e.printStackTrace();
            return new StructureResponse("error", "Error: " + e.getMessage(), structure);
        }
    }


    @SuppressWarnings("unchecked")
    private void createRecursive(Map<String, Object> node, String currentPath, String userFolderPath) throws IOException {
        String name = (String) node.get("name");
        String type = (String) node.get("type");
        String newPath = Paths.get(currentPath, name).toString();

        if ("folder".equals(type)) {
            Files.createDirectories(Paths.get(newPath));
            List<Map<String, Object>> children = (List<Map<String, Object>>) node.get("children");
            if (children != null) {
                for (Map<String, Object> child : children) {
                    createRecursive(child, newPath, userFolderPath);
                }
            }
        } else if ("file".equals(type)) {
            Files.createDirectories(Paths.get(currentPath));

            // Generate dummy content
            String content = "Dummy content for file: " + name;
            byte[] fileBytes = content.getBytes();
            long fileSize = fileBytes.length;

            long currentSize = calculateDirectorySize(Paths.get(userFolderPath));

            if ((currentSize + fileSize) > MAX_USER_FOLDER_SIZE) {
                System.out.println(" Skipping file (limit exceeded): " + name);
                return;
            }

            Files.write(Paths.get(newPath), fileBytes);
            System.out.println("File created: " + newPath + " (Size: " + fileSize + " bytes)");
        }
    }

    private long calculateDirectorySize(Path folderPath) throws IOException {
        final long[] size = {0};

        Files.walk(folderPath)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        size[0] += Files.size(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return size[0];
    }
    public StructureResponse getStructure(String userId) {
        String userBasePath = Paths.get(BASE_PATH, userId).toString();
        Path basePath = Paths.get(userBasePath);

        List<Map<String, Object>> structure = new ArrayList<>();

        if (!Files.exists(basePath) || !Files.isDirectory(basePath)) {
            return new StructureResponse("error", "Folder not found for userId: " + userId, structure);
        }

        try (Stream<Path> stream = Files.list(basePath)) {
            stream.forEach(path -> {
                structure.add(buildStructure(path));
            });
        } catch (IOException e) {
            e.printStackTrace();
            return new StructureResponse("error", "Error reading folder structure: " + e.getMessage(), structure);
        }

        return new StructureResponse("success", "Folder structure retrieved for userId: " + userId, structure);
    }


    private Map<String, Object> buildStructure(Path path) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", path.getFileName().toString());

        if (Files.isDirectory(path)) {
            item.put("type", "folder");
            List<Map<String, Object>> children = new ArrayList<>();
            try (Stream<Path> stream = Files.list(path)) {
                stream.forEach(child -> children.add(buildStructure(child)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            item.put("children", children);
        } else {
            item.put("type", "file");
            try {
                String mimeType = Files.probeContentType(path);
                item.put("originalType", mimeType != null ? mimeType : "unknown");

                // Read file bytes and convert to Base64
                byte[] fileBytes = Files.readAllBytes(path);
                String base64 = Base64.getEncoder().encodeToString(fileBytes);

                // Convert to data URI format
                String base64DataUrl = "data:" + mimeType + ";base64," + base64;
                item.put("url", base64DataUrl);

            } catch (IOException e) {
                item.put("originalType", "unknown");
                item.put("url", null);
            }
        }

        return item;
    }



}