import java.io.IOException;

public class Bai1_1 {
    public static void main(String[] args) throws IOException {
        byte[] buffer = new byte[100];
        System.out.println("Nhap du lieu:");
        int bytesRead = System.in.read(buffer);
        System.out.println("So byte da doc: " + bytesRead);
        System.out.print("Du lieu vua nhap: ");
        for (int i = 0; i < bytesRead; i++) {
            System.out.print((char) buffer[i]);
        }
    }
}
