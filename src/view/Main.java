package view;

import controller.CapsuleController;
import model.LearningCapsule;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String ADMIN_PASSWORD = "1234";

    public static void main(String[] args) {
        CapsuleController controller = new CapsuleController();
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== EduCaps Login ====");
            System.out.println("1) User");
            System.out.println("2) Admin");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            String roleChoice = in.nextLine().trim();

            if (roleChoice.equals("0")) {
                System.out.println("Bye!");
                in.close();
                return;
            }

            if (roleChoice.equals("1")) {
                runUserMenu(controller, in);
            } else if (roleChoice.equals("2")) {
                System.out.print("Enter admin password: ");
                String pass = in.nextLine().trim();
                if (pass.equals(ADMIN_PASSWORD)) {
                    runAdminMenu(controller, in);
                } else {
                    System.out.println("Access denied.");
                }
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void runUserMenu(CapsuleController controller, Scanner in) {
        while (true) {
            System.out.println("\n==== User Menu ====");
            System.out.println("1) View all capsules");
            System.out.println("2) View capsules by category");
            System.out.println("3) View capsule details (by ID)");
            System.out.println("0) Logout");
            System.out.print("Choose: ");

            String choice = in.nextLine().trim();

            switch (choice) {
                case "1":
                    printCapsules(controller.getCapsules());
                    break;

                case "2":
                    System.out.print("Enter category: ");
                    String cat = in.nextLine();
                    List<LearningCapsule> filtered = controller.getCapsulesByCategory(cat);
                    printCapsules(filtered);
                    break;

                case "3":
                    System.out.print("Enter capsule ID: ");
                    int id = readInt(in);
                    if (id == -1) break;

                    LearningCapsule c = controller.getCapsuleById(id);
                    if (c == null) {
                        System.out.println("System: Capsule not found.");
                    } else {
                        System.out.println("\n--- Capsule Details ---");
                        System.out.println(c);
                        if (c.getMediaPath() != null && !c.getMediaPath().trim().isEmpty()) {
                            System.out.println("\nMedia path saved: " + c.getMediaPath());
                        } else {
                            System.out.println("\nNo media attached.");
                        }
                    }
                    break;

                case "0":
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void runAdminMenu(CapsuleController controller, Scanner in) {
        while (true) {
            System.out.println("\n==== Admin Menu ====");
            System.out.println("1) Add capsule");
            System.out.println("2) View all capsules");
            System.out.println("3) Edit capsule (by ID)");
            System.out.println("4) Delete capsule (by ID)");
            System.out.println("0) Logout");
            System.out.print("Choose: ");

            String choice = in.nextLine().trim();

            switch (choice) {
                case "1": 
                    System.out.print("Title: ");
                    String title = in.nextLine();

                    System.out.print("Category: ");
                    String category = in.nextLine();

                    System.out.print("Description: ");
                    String desc = in.nextLine();

                    System.out.print("Media path (optional): ");
                    String media = in.nextLine();

                    controller.addCapsule(title, desc, media, category);

                    if (media != null && !media.trim().isEmpty() && !controller.mediaFileExists(media)) {
                        System.out.println("Note: Media file path was saved, but the file was not found on this device.");
                    }
                    break;

                case "2": 
                    printCapsules(controller.getCapsules());
                    break;

                case "3": 
                    System.out.print("Enter capsule ID to edit: ");
                    int editId = readInt(in);
                    if (editId == -1) break;

                    LearningCapsule editTarget = controller.getCapsuleById(editId);
                    if (editTarget == null) {
                        System.out.println("System: Capsule not found.");
                        break;
                    }

                    System.out.println("\nCurrent capsule data:");
                    System.out.println(editTarget);

                    System.out.println("\nEnter new values (leave blank to keep current):");

                    System.out.print("New Title: ");
                    String newTitle = in.nextLine();

                    System.out.print("New Category: ");
                    String newCategory = in.nextLine();

                    System.out.print("New Description: ");
                    String newDesc = in.nextLine();

                    System.out.print("New Media path: ");
                    String newMedia = in.nextLine();

                    controller.editCapsuleById(editId, newTitle, newDesc, newMedia, newCategory);

                    if (newMedia != null && !newMedia.trim().isEmpty() && !controller.mediaFileExists(newMedia)) {
                        System.out.println("Note: Media file path was saved, but the file was not found on this device.");
                    }
                    break;

                case "4": // Delete
                    System.out.print("Enter capsule ID to delete: ");
                    int delId = readInt(in);
                    if (delId == -1) break;

                    LearningCapsule target = controller.getCapsuleById(delId);
                    if (target == null) {
                        System.out.println("System: Capsule not found.");
                        break;
                    }

                    System.out.println("\nYou are about to delete this capsule:");
                    System.out.println(target);
                    System.out.print("\nConfirm delete? (y/n): ");
                    String confirm = in.nextLine().trim().toLowerCase();

                    if (confirm.equals("y") || confirm.equals("yes")) {
                        controller.deleteCapsuleById(delId);
                    } else {
                        System.out.println("System: Delete canceled.");
                    }
                    break;

                case "0":
                    System.out.println("Logged out.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ================= Helpers =================
    private static void printCapsules(List<LearningCapsule> capsules) {
        if (capsules == null || capsules.isEmpty()) {
            System.out.println("No capsules found.");
            return;
        }

        System.out.println("\n--- Capsules ---");
        for (LearningCapsule c : capsules) {
            System.out.println("\n" + c);
        }
    }

    private static int readInt(Scanner in) {
        String s = in.nextLine().trim();
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number.");
            return -1;
        }
    }
}