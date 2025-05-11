## Ерөнхий танилцуулга

Энэхүү Customer Registration Service нь хэрэглэгчийн бүртгэл, хайлт, засвар, устгал зэрэг үйлдлийг гүйцэтгэх RESTful API интерфейсээр үйлчилдэг. Spring Boot, Spring Security, Swagger/OpenAPI ашиглан бүтээсэн.

---

## Шаардлага, тохиргоо

* Java 17 болон түүнээс дээш
* Maven 3.6+
* PostgreSQL эсвэл өөр JPA дэмжсэн өгөгдлийн сан
* gRPC сервер (карт унших үйлдэлд ашиглана)

### Орчны хувиргалт

1. `application.yml` эсвэл `application.properties` файлд дараах утгуудыг тохируулна:

   ```yaml
   hospital:
     security:
       publicKey: "<RSA public key base64>"
       privateKey: "<RSA private key base64>"
   auht:
     url: "<Auth service URL>"
   spring:
     datasource:
       url: jdbc:postgresql://<host>:<port>/<db>
       username: <user>
       password: <password>
   ```
2. Maven build:

   ```bash
   mvn clean install
   ```
3. Серверийг эхлүүлэх:

   ```bash
   java -jar target/userapi-1.0.jar
   ```

---

## Аюулгүй байдал

* JWT Bearer Token хэлбэрийн “Authorization: Bearer <token>” header шаардлагатай.
* API Gateway ашиглан X-User-ID, X-User-Roles header’үүдээр дамжуулж хэрэглэгчийн эрхийг шалгана.
* Swagger UI ([http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)) руу нэвтрэх үед `/v3/api-docs/**`, `/swagger-ui/**` endpoint-ууд нээлттэй.

---

## API Endpoint’ууд

| HTTP Method | URL                                         | Тайлбар                             | Шаардлагатай body/headers |
| ----------- | ------------------------------------------- | ----------------------------------- | ------------------------- |
| POST        | /customer/registration/search               | Хэрэглэгч текстээр хайлт хийх       | JSON текст (`parameter`)  |
| POST        | /customer/registration/register/{sisi\_id}  | ID-аар бүртгэл хийх                 | PathVariable: sisi\_id    |
| POST        | /customer/registration/read/card            | gRPC-ээр карт унших                 | -                         |
| GET         | /customer/registration/fetch                | Бүх хэрэглэгчийг авах               | -                         |
| GET         | /customer/registration/fetch/{id}           | ID-аар хэрэглэгч авах               | PathVariable: id          |
| POST        | /customer/registration/save/customer        | Шинэ хэрэглэгч бүртгэх              | JSON CustomerDto          |
| POST        | /customer/registration/create/customer      | Эмчээс хэрэглэгч бүртгэх            | JSON CustomerDto          |
| POST        | /customer/registration/retrieve             | Регистрийн дугаараас нас, хүйс авах | Raw String (national\_id) |
| DELETE      | /customer/registration/delete/customer/{id} | Хэрэглэгч устгах                    | PathVariable: id          |
| POST        | /customer/registration/edit/customer        | Хэрэглэгчийн мэдээлэл засах         | JSON CustomerDto          |
| POST        | /customer/registration/test                 | Нууцлалын түлхүүр үүсгэх тест       | -                         |

---

## Endpoint’уудын жишээ

### 1. Хэрэглэгч хайх

* **URL:** `/customer/registration/search`
* **Method:** POST
* **Request Body:**

  ```json
  "Жишээ хайлтын текст"
  ```
* **Response (200):**

  ```json
  {
    "code": "200",
    "body": [
      { "customerId": "C001", "surname": "Бат", "firstName": "Эрдэнэ", ... }
    ]
  }
  ```

### 2. Хэрэглэгч бүртгэх (save)

* **URL:** `/customer/registration/save/customer`
* **Method:** POST
* **Request Body:**

  ```json
  {
    "surname": "Бат",
    "firstName": "Эрдэнэ",
    "dateOfBirth": "1990-05-12",
    "age": "35",
    "gender": "MALE",
    "phoneNumber": "99001122",
    "email": "bat@example.com",
    "province": "Улаанбаатар",
    "district": "Сүхбаатар",
    "subdistrict": "1-р хороо",
    "addressDetail": "17 тоот",
    "profession": "Doctor",
    "sisiId": "S123",
    "userId": "U456",
    "nationalId": "90051201"
  }
  ```
* **Response (200):**

  ```json
  {
    "message": "Customer saved successfully",
    "code": "200",
    "customerId": "C002"
  }
  ```

---

## Алдаа болон Статус Кодууд

* `200 OK` – Амжилттай
* `204 NO CONTENT` – Хайсан ID-тэй хэрэглэгч олдсонгүй
* `400 Bad Request` – Буруу хүсэлт (например, давтах nationalId)
* `401 Unauthorized` – Төлөвлөгөө эрхгүй
* `500 Internal Server Error` – Серверийн алдаа

---

## Swagger/OpenAPI

Swagger UI руу нэвтрэх: `http://<host>:<port>/swagger-ui.html`

---

## Нэмэлт тайлбар

* `CryptoUtil` класс нь RSA болон Base64 encryption/decryption функцийг агуулна.
* gRPC карт унших үйлдэлд `com.example.grpc.CardReaderServiceGrpc` stub ашиглана.
* Бүх CRUD үйлдэл нь `CustomerService` болон `CustomerRepository` давхаргаар дамжиж өгөгдлийн сантай ажиллана.

---

**Бэлтгэсэн:** \[Таны нэр]
**Огноо:** 2025-05-12
