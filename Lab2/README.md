Xây dựng chương trình Socket trong java 
Một chương trình Socket bằng java được thực hiện như sau: 
a/ Server: 
- Lắng nghe và chập nhận kết nối từ cổng 9999.
- Cho phép nhiều client kết nối đến cùng một lúc.
- Khi client gửi đến 1 chuỗi thì : 
+ Nếu chuỗi là "quit" thì ngắt kết nối với client. 
+ Tiến hành đảo chuỗi. 
+ Gửi chuỗi đã được đảo cho client. 
b/ Client: 
-  Kết nối tới Server qua cổng 9999. 
-  Nhập chuỗi từ bàn phím. 
-  Gửi chuỗi tới server. 
-  Hiển thị chuỗi từ server gửi tới.