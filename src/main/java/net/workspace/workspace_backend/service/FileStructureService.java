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

            String message = "created successfully ";
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


    /*private Map<String, Object> buildStructure(Path path) {
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
    }*/

    private Map<String, Object> buildStructure(Path path) {
        Map<String, Object> item = new HashMap<>();
        String fileName = path.getFileName().toString();
        item.put("name", fileName);

        // Extract file extension
        String extension = "";
        int lastDot = fileName.lastIndexOf('.');
        if (lastDot != -1 && lastDot < fileName.length() - 1) {
            extension = fileName.substring(lastDot + 1);
        }

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
            item.put("extension", extension); // <-- Add extension here

            try {
                String mimeType = Files.probeContentType(path);
                item.put("originalType", mimeType != null ? mimeType : "unknown");

                byte[] fileBytes = Files.readAllBytes(path);
                String base64 = Base64.getEncoder().encodeToString(fileBytes);

                String base64DataUrl = "data:" + mimeType + ";base64," + base64;
                item.put("url", base64DataUrl);

            } catch (IOException e) {
                item.put("originalType", "unknown");
                item.put("url", null);
            }
        }

        return item;
    }
    public StructureResponse deleteItems(String userId, List<String> relativePaths) {
        Path userPath = Paths.get(BASE_PATH, userId);
        List<String> failed = new ArrayList<>();

        for (String relativePath : relativePaths) {
            Path fullPath = userPath.resolve(relativePath);
            try {
                if (Files.exists(fullPath)) {
                    deleteRecursively(fullPath);
                } else {
                    failed.add(relativePath + " (not found)");
                }
            } catch (IOException e) {
                failed.add(relativePath + " (error: " + e.getMessage() + ")");
            }
        }

        if (failed.isEmpty()) {
            return new StructureResponse("success", "All items deleted successfully.", new ArrayList<>());
        } else {
            List<Map<String, Object>> failedList = new ArrayList<>();
            for (String failMsg : failed) {
                Map<String, Object> failEntry = new HashMap<>();
                failEntry.put("error", failMsg);
                failedList.add(failEntry);
            }
            return new StructureResponse("partial", "Some items couldn't be deleted.", failedList);
        }
    }

    private void deleteRecursively(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (Stream<Path> stream = Files.list(path)) {
                for (Path subPath : (Iterable<Path>) stream::iterator) {
                    deleteRecursively(subPath);
                }
            }
        }
        Files.delete(path);
    }
    public StructureResponse renameItem(String userId, String oldPath, String newName) {
        Path userPath = Paths.get(BASE_PATH, userId);
        Path oldFullPath = Paths.get(userPath.toString(), oldPath);
        Path newFullPath = oldFullPath.resolveSibling(newName);

        if (!Files.exists(oldFullPath)) {
            return new StructureResponse("error", "Item not found", null);
        }

        try {
            Files.move(oldFullPath, newFullPath);
            return new StructureResponse("success", "Item renamed successfully", null);
        } catch (IOException e) {
            return new StructureResponse("error", "Rename failed: " + e.getMessage(), null);
        }
    }

    public StructureResponse updateStructure(String userId, List<Map<String, Object>> structure) {
        // Build the path to the user's root folder—but do NOT create it
        Path userBasePath = Paths.get(BASE_PATH, userId);

        if (!Files.exists(userBasePath) || !Files.isDirectory(userBasePath)) {
            // If the user folder really doesn't exist, return an error
            return new StructureResponse("error", "User folder not found: " + userId, structure);
        }

        try {
            // Only iterate the incoming structure and create under the existing folder
            for (Map<String, Object> item : structure) {
                createRecursive(item, userBasePath.toString(), userBasePath.toString());
            }
            return new StructureResponse("success", "Structure updated successfully", structure);
        } catch (Exception e) {
            e.printStackTrace();
            return new StructureResponse("error", "Error: " + e.getMessage(), structure);
        }
    }

}