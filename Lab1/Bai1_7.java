import java.io.*;

public class Bai1_7 {
    public static void main(String[] args) {
        String filePath = "test.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Khong the doc file: " + e.getMessage());
        }
    }
}
