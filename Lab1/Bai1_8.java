import java.io.*;

public class Bai1_8 {
    public static void main(String[] args) {
        String src = "file_bai1_8_src.txt";
        String dest = "file_bai1_8_dest.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(src));
             BufferedWriter writer = new BufferedWriter(new FileWriter(dest))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Da copy xong!");
        } catch (IOException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }
}
