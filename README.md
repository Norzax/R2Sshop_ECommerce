# R2Sshop E-Commerce
Một ứng dụng E-Commerce được xây dựng bằng Java và Spring Boot.

## Mô Tả

R2Sshop E-Commerce là một ứng dụng thương mại điện tử đơn giản được xây dựng trên nền tảng Java và Spring Boot. Ứng dụng cung cấp các tính năng cơ bản để quản lý sản phẩm, đơn hàng và người dùng.

## Tài Liệu Phát Triển
[Group A++ Documentation](https://docs.google.com/spreadsheets/d/16_YGrq3n_Cq-HTUXetFY-VTpHtURI_AUfCZu_otgRf0)

## Hướng Dẫn Cài Đặt

### Yêu Cầu

- Java 17
- Maven
- MySQL

### Cài Đặt và Chạy

1. Clone repository:

    ```bash
    git clone https://github.com/Norzax/R2Sshop_ECommerce.git
    ```

2. Cấu hình cơ sở dữ liệu trong `application.properties`.

3. Chạy ứng dụng:

    ```bash
    mvn spring-boot:run
    ```

## Các Nhánh Quan Trọng

- **main**: Nhánh chính, luôn ổn định và chứa phiên bản đã được phát hành.
- **develop**: Nhánh phát triển, được sử dụng cho việc phát triển mới, các tính năng chưa hoàn thiện.
- **feature/<tên-tính-năng>**: Nhánh cho từng tính năng cụ thể đang được phát triển.
- **hotfix/<tên-hotfix>**: Nhánh sửa lỗi nhanh chóng để fix các vấn đề cấp bách từ production.


## Cấu Trúc Thư Mục

- `/src`: Mã nguồn Java.
- `/src/main/resources`: Cấu hình ứng dụng và tài nguyên.
- `/pom.xml`: Tệp cấu hình Maven.

## Đóng Góp

Chúng tôi rất hoan nghênh sự đóng góp từ cộng đồng. Nếu bạn muốn đóng góp vào dự án, vui lòng tạo pull request.

---
© 2023 R2Sshop E-Commerce.
