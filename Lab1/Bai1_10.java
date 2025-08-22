import java.io.*;

public class Bai1_10 {
    public static void main(String[] args) throws IOException {
        String file = "file_bai1_10.dat";
        double[] numbers = {1.1, 2.2, 3.3, 4.4, 5.5, 6.6};
        // Ghi 6 số double xuống file
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
        for (double num : numbers) {
            dos.writeDouble(num);
        }
        dos.close();

    // Tự động chọn vị trí ngẫu nhiên từ 0 đến 5
    int index = (int) (Math.random() * 6);
    System.out.println("Vi tri ngau nhien duoc chon: " + index);

    // Đọc số double ở vị trí ngẫu nhiên
    RandomAccessFile raf = new RandomAccessFile(file, "r");
    raf.seek(index * 8); // Mỗi double chiếm 8 byte
    double value = raf.readDouble();
    raf.close();
    System.out.println("So o vi tri " + index + " la: " + value);
    }
}
