package net.workspace.workspace_backend.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

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

    public String createStructure(String userId, List<Map<String, Object>> structure) {
        String userBasePath = Paths.get(BASE_PATH, userId).toString();

        try {
            Files.createDirectories(Paths.get(userBasePath));

            for (Map<String, Object> item : structure) {
                createRecursive(item, userBasePath, userBasePath);
            }

            return "Folder structure created successfully for userId: " + userId;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
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
}