import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bai1_3 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Nhap cac ky tu (ket thuc bang dau cham '.'): ");
        int c;
        while ((c = reader.read()) != -1) {
            char ch = (char) c;
            if (ch == '.') break;
            System.out.print(ch);
        }
        System.out.println("\nKet thuc!");
    }
}
