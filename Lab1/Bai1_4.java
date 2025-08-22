import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bai1_4 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.println("Nhap cac chuoi (ket thuc bang chu 'stop'): ");
        while ((line = reader.readLine()) != null) {
            if (line.equalsIgnoreCase("stop")) break;
            System.out.println(line);
        }
        System.out.println("Ket thuc!");
    }
}
