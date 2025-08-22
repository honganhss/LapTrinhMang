import java.io.*;

public class Bai1_12 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"));
        System.out.println("Nhap cac dong van ban (ket thuc bang chu 'stop'): ");
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equalsIgnoreCase("stop")) break;
            writer.write(line);
            writer.newLine();
        }
        writer.close();
        System.out.println("Da ghi file test.txt xong!");
    }
}
