## Ерөнхий танилцуулга

Энэхүү API нь эмнэлгийн системийн хэрэглэгчдийн бүртгэл, мэдээлэл засварлах, хайлт хийх зэрэг үйлдлүүдийг гүйцэтгэх боломжийг олгоно. Мөн картын мэдээллийг gRPC ашиглан унших нэмэлт боломжтой.

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
   java -jar target/userapi-0.0.1-SNAPSHOT.jar 
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
| POST        | /customer/registration/search               | Хэрэглэгчийн мэдээллийг дурын талбарын утгаар хайх боломжтой       | JSON текст (`parameter`)  |
| POST        | /customer/registration/register/{sisi\_id}  | Оюутаны үнэмлэхний мэдээллээр хэрэглэгчийг бүртгэх                 | PathVariable: sisi\_id    |
| POST        | /customer/registration/read/card            | gRPC-ээр карт унших                 | -                         |
| GET         | /customer/registration/fetch                | Бүх хэрэглэгчийг авах               | -                         |
| GET         | /customer/registration/fetch/{id}           | үйлчлүүлэгчийн CustomerID-аар хэрэглэгч авах               | PathVariable: customerId          |
| POST        | /customer/registration/save/customer        | Шинэ хэрэглэгч бүртгэх              | JSON CustomerDto          |
| POST        | /customer/registration/create/customer      | Эмчээс хэрэглэгч бүртгэх            | JSON CustomerDto          |
| POST        | /customer/registration/retrieve             | Регистрийн дугаараас нас, хүйс авах | Raw String (national\_id) |
| DELETE      | /customer/registration/delete/customer/{id} | Хэрэглэгч устгах                    | PathVariable: id          |
| POST        | /customer/registration/edit/customer        | Хэрэглэгчийн мэдээлэл засах         | JSON CustomerDto          |
| POST        | /customer/registration/test                 | тест endpoint       | -                         |

---

## Endpoint’уудын жишээ

### 1. Хэрэглэгч хайх

* **URL:** /customer/registration/search
* **Method:** POST
* **Зорилго:** Текстээр хэрэглэгчийн өгөгдлөөс хайлт хийх
* **Бие (Body):** Хайх текст


  ```json
  "Мөнх"
  ```
* **Response (200):**

  ```json
     {
     "map": {
       "code": "200",
       "body": {
         "list": [
           {
             "map": {
               "customerId": "X531K749Q551",
               "surname": "Цагаантогоо1",
               "firstName": "Мөнх-Илд1",
               "dateOfBirth": "2008-01-01",
               "age": "17",
               "gender": "FEMALE",
               "phoneNumber": "88119900",
               "email": "example@email.com",
               "province": "Улаанбаатар",
               "district": "Сүхбаатар",
               "subdistrict": "7-р хороо",
               "addressDetail": "Бага тойруу",
               "profession": "DOCTOR",
               "sisiId": "SISI123457675",
               "userId": "USER123456",
               "nationalId": "Pz8xMjM0NTY3OA=="
             },
             "empty": false
           }
         ],
         "empty": false
       }
     },
     "empty": false
   }
``` ```
### 2. ID-аар бүртгэл хийх
* **URL:** `/customer/registration/register/{sisi_id}`
* **Method:** POST
* **Зорилго:** Өгөгдсөн ID-аар хэрэглэгчийг бүртгэх
* **URL параметр:** `sisi_id` - Бүртгэх хэрэглэгчийн ID
* **Амжилттай хариу (200):**
  ```json
  {
    "message": "Бүртгэл амжилттай",
    "code": "200"
  }
  ```
### 3. Бүх хэрэглэгчийг авах
- **URL:** `/customer/registration/fetch`
- **Method:** GET
- **Зорилго:** Системд бүртгэлтэй бүх хэрэглэгчийн жагсаалтыг авах
- **Амжилттай хариу (200):**
   ```json
   {
     "map": {
       "body": {
         "list": [
           {
             "map": {
               "id": "X531K749Q551",
               "surname": "Цагаантогоо1",
               "firstName": "Мөнх-Илд1"
             },
             "empty": false
           },
           {
             "map": {
               "id": "X479J944I154",
               "surname": "Цагаантогоо2",
               "firstName": "Мөнх-Илд2"
             },
             "empty": false
           }
         ],
         "empty": false
       },
       "code": "200"
     },
     "empty": false
   }
  ```
### 4. ID-аар хэрэглэгч авах
- **URL:** `/customer/registration/fetch/{id}`
- **Method:** GET
- **Зорилго:** Өгөгдсөн ID-тай хэрэглэгчийн мэдээллийг авах
- **URL параметр:** `id` - Хэрэглэгчийн ID
- **Амжилттай хариу (200):**
  ```json
     {
     "map": {
       "customerId": "X531K749Q551",
       "surname": "Цагаантогоо1",
       "firstName": "Мөнх-Илд1",
       "dateOfBirth": "2008-01-01",
       "age": "17",
       "gender": "FEMALE",
       "phoneNumber": "88119900",
       "email": "example@email.com",
       "province": "Улаанбаатар",
       "district": "Сүхбаатар",
       "subdistrict": "7-р хороо",
       "addressDetail": "Бага тойруу",
       "profession": "DOCTOR",
       "sisiId": "SISI123457675",
       "userId": "USER123456",
       "nationalId": "??12345678"
     },
     "empty": false
   }
  ```
- **Хэрэглэгч олдоогүй хариу (204):**
  ```json
  {
    "message": "Тийм ID тай хэрэглэгч олдсонгүй",
    "code": "204"
  }
  ```
### 5. Хэрэглэгч бүртгэх
- **URL:** `/customer/registration/save/customer`
- **Method:** POST
- **Зорилго:** Шинэ хэрэглэгчийн мэдээллийг системд бүртгэх
- **Бие (Body):**
  ```json
  {
    "surname": "Бат",
    "firstName": "Болд",
    "dateOfBirth": "1990-01-01",
    "age": "35",
    "gender": "MALE",
    "phoneNumber": "99112233",
    "email": "bold@example.com",
    "province": "Улаанбаатар",
    "district": "Баянгол",
    "subdistrict": "1-р хороо",
    "addressDetail": "23-р байр 5 тоот",
    "profession": "ENGINEER",
    "sisiId": "SI123456",
    "userId": "UI12345",
    "nationalId": "УД92010199"
  }
  ```
- **Амжилттай хариу (200):**
  ```json
  {
    "message": "Customer saved successfully",
    "code": "200",
    "customerId": "AB123CD456"
  }
  ```
- **Бүртгэлтэй хэрэглэгч хариу (400):**
  ```json
  {
    "message": "Тийм регистрийн дугаартай хэрэглэгч бүртгэлтэй байна."
  }
  ```
### 6. Хэрэглэгч эмчээс бүртгэх
- **URL:** `/customer/registration/create/customer`
- **Method:** POST
- **Зорилго:** Шинэ хэрэглэгчийн мэдээллийг эмчээс системд бүртгэх
- **Бие (Body):**
  ```json
  {
    "surname": "Бат",
    "firstName": "Болд",
    "dateOfBirth": "1990-01-01",
    "age": "35",
    "gender": "MALE",
    "phoneNumber": "99112233",
    "email": "bold@example.com",
    "province": "Улаанбаатар",
    "district": "Баянгол",
    "subdistrict": "1-р хороо",
    "addressDetail": "23-р байр 5 тоот",
    "profession": "ENGINEER",
    "nationalId": "УД92010199"
  }
  ```
- **Амжилттай хариу (200):**
  ```json
  {
    "message": "Customer saved successfully",
    "code": "200"
  }
  ```
- **Бүртгэлтэй хэрэглэгч хариу (400):**
  ```json
  {
    "message": "Тийм регистрийн дугаартай хэрэглэгч бүртгэлтэй байна."
  }
  ```
### 7. Хэрэглэгч устгах
- **URL:** `/customer/registration/delete/customer/{id}`
- **Method :** DELETE
- **Зорилго:** Хэрэглэгчийн мэдээллийг системээс устгах
- **URL параметр:** `id` - Хэрэглэгчийн ID
- **Амжилттай хариу (200):** Хоосон хариу
- **Хэрэглэгч олдоогүй хариу (404):** Хоосон хариу

### 8. Хэрэглэгчийн мэдээлэл засах
- **URL:** `/customer/registration/edit/customer`
- **Method:** POST
- **Зорилго:** Систем дэх хэрэглэгчийн мэдээллийг засах
- **Бие (Body):**
  ```json
  {
    "customerId": "AB123CD456",
    "surname": "Бат",
    "firstName": "Болд",
    "dateOfBirth": "1990-01-01",
    "age": "35",
    "gender": "MALE",
    "phoneNumber": "99112233",
    "email": "bold@example.com",
    "province": "Улаанбаатар",
    "district": "Баянгол",
    "subdistrict": "1-р хороо",
    "addressDetail": "23-р байр 5 тоот",
    "profession": "ENGINEER",
    "sisiId": "SI123456",
    "userId": "UI12345",
    "nationalId": "УД92010199"
  }
  ```
- **Амжилттай хариу (200):** Хоосон хариу
## Алдаа болон Статус Кодууд

* `200 OK` – Амжилттай
* `204 NO CONTENT` – Хайсан ID-тэй хэрэглэгч олдсонгүй
* `400 Bad Request` – Буруу хүсэлт (nationalId давхардах)
* `401 Unauthorized` – Төлөвлөгөө эрхгүй
* `500 Internal Server Error` – Серверийн алдаа

---

## Swagger/OpenAPI

Swagger UI руу нэвтрэх: `http://54.251.178.246:8080/swagger-ui/index.html#`

---

## Нэмэлт тайлбар

* `CryptoUtil` класс нь RSA болон Base64 encryption/decryption функцийг агуулна.
* gRPC карт унших үйлдэлд `com.example.grpc.CardReaderServiceGrpc` stub ашиглана.
* Бүх CRUD үйлдэл нь `CustomerService` болон `CustomerRepository` давхаргаар дамжиж өгөгдлийн сантай ажиллана.

---

**Бэлтгэсэн:** vNodes team members М.Мөнх-Оргил Э.Билгүүн Ц.Нинжбадгар Б.Уранцэцэг
**Огноо:** 2025-05-11
