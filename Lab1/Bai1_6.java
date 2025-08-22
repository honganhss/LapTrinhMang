import java.io.*;

public class Bai1_6 {
    public static void main(String[] args) throws IOException {
        Writer writer = new OutputStreamWriter(System.out);
        writer.write("Xin chao, day la luong ky tu co kieu thay doi!\n");
        writer.flush();
    }
}
