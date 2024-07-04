package fr.nozkay;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private File file;

    public FileManager(String fileName) {
        file = new File(Main.getPlugin(Main.class).getDataFolder(), fileName);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToFile(String playerName, String grade) {
        updateGrade(playerName, grade);
    }

    public void updateGrade(String playerName, String grade) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" = ");
                if (parts.length == 2 && parts[0].equalsIgnoreCase(playerName)) {
                    line = playerName + " = " + grade;
                    found = true;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!found) {
            lines.add(playerName + " = " + grade);
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getGrade(String playerName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" = ");
                if (parts.length == 2 && parts[0].equalsIgnoreCase(playerName)) {
                    return parts[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File getFile() {
        return file;
    }
}
