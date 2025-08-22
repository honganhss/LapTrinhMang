import java.io.*;

public class Bai1_9 {
    public static void main(String[] args) throws IOException {
        String file = "file_bai1_9.dat";
        // Ghi dữ liệu
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
        dos.writeInt(123);
        dos.writeDouble(3.14);
        dos.writeUTF("Hello");
        dos.close();
        // Đọc dữ liệu
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        int i = dis.readInt();
        double d = dis.readDouble();
        String s = dis.readUTF();
        dis.close();
        System.out.println("Int: " + i);
        System.out.println("Double: " + d);
        System.out.println("String: " + s);
    }
}
