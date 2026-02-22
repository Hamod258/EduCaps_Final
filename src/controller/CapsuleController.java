package controller;

import model.LearningCapsule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CapsuleController {

    private final String DATA_FILE = "capsules.dat";
    private List<LearningCapsule> capsules = new ArrayList<>();
    private int nextId = 1;

    public CapsuleController() {
        loadFromFile();
        int maxId = 0;
        for (LearningCapsule c : capsules) {
            if (c.getId() > maxId) maxId = c.getId();
        }
        nextId = maxId + 1;
    }

    public boolean addCapsule(String title, String description, String mediaPath, String category) {

        if (title == null || title.trim().isEmpty()) {
            System.out.println("Error: Title cannot be empty!");
            return false;
        }

        if (description == null || description.trim().isEmpty()) {
            System.out.println("Error: Description cannot be empty!");
            return false;
        }

        if (category == null || category.trim().isEmpty()) {
            System.out.println("Error: Category cannot be empty!");
            return false;
        }

        for (LearningCapsule c : capsules) {
            if (c.getTitle() != null && c.getTitle().equalsIgnoreCase(title.trim())) {
                System.out.println("Error: Capsule with same title already exists!");
                return false;
            }
        }

        if (mediaPath == null) mediaPath = "";
        if (category == null) category = "";

        LearningCapsule capsule = new LearningCapsule(
                nextId++,
                title.trim(),
                description.trim(),
                mediaPath.trim(),
                category.trim()
        );

        capsules.add(capsule);
        saveToFile();

        System.out.println("System: Capsule added successfully with ID = " + capsule.getId());
        return true;
    }

    public List<LearningCapsule> getCapsules() {
        return capsules;
    }

    public LearningCapsule getCapsuleById(int id) {
        for (LearningCapsule c : capsules) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public boolean deleteCapsuleById(int id) {
        boolean removed = capsules.removeIf(c -> c.getId() == id);
        if (removed) {
            saveToFile();
            System.out.println("System: Capsule deleted.");
            return true;
        } else {
            System.out.println("System: Capsule not found.");
            return false;
        }
    }

    public boolean editCapsuleById(int id, String newTitle, String newDescription, String newMediaPath, String newCategory) {
        LearningCapsule c = getCapsuleById(id);
        if (c == null) {
            System.out.println("System: Capsule not found.");
            return false;
        }

        if (newTitle != null && !newTitle.trim().isEmpty()) {
            c.setTitle(newTitle.trim());
        }

        if (newDescription != null && !newDescription.trim().isEmpty()) {
            c.setDescription(newDescription.trim());
        }

        if (newMediaPath != null && !newMediaPath.trim().isEmpty()) {
            c.setMediaPath(newMediaPath.trim());
        }

        if (newCategory != null && !newCategory.trim().isEmpty()) {
            c.setCategory(newCategory.trim());
        }

        if (c.getDescription() == null || c.getDescription().trim().isEmpty()) {
            System.out.println("Error: Description cannot be empty!");
            return false;
        }

        if (c.getCategory() == null || c.getCategory().trim().isEmpty()) {
            System.out.println("Error: Category cannot be empty!");
            return false;
        }

        saveToFile();
        System.out.println("System: Capsule updated successfully.");
        return true;
    }

    public List<LearningCapsule> getCapsulesByCategory(String category) {
        List<LearningCapsule> result = new ArrayList<>();
        if (category == null) return result;

        for (LearningCapsule c : capsules) {
            if (c.getCategory() != null && c.getCategory().equalsIgnoreCase(category.trim())) {
                result.add(c);
            }
        }
        return result;
    }

    public boolean mediaFileExists(String mediaPath) {
        if (mediaPath == null || mediaPath.trim().isEmpty()) return false;
        File f = new File(mediaPath.trim());
        return f.exists() && f.isFile();
    }

    // ============== File Persistence ==============

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            capsules = new ArrayList<>();
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = in.readObject();
            if (obj instanceof List) {
                capsules = (List<LearningCapsule>) obj;
            } else {
                capsules = new ArrayList<>();
            }
        } catch (Exception e) {
            capsules = new ArrayList<>();
            System.out.println("Warning: Could not load capsules from file.");
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(capsules);
        } catch (IOException e) {
            System.out.println("Warning: Could not save capsules to file.");
        }
    }
}