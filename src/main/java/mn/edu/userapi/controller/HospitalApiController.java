package mn.edu.userapi.controller;

import com.example.grpc.CardReaderServiceGrpc;
import com.example.grpc.CardRequest;
import com.example.grpc.CardResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vertx.core.cli.annotations.Summary;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import mn.edu.userapi.dto.CustomerDto;
import mn.edu.userapi.model.Customer;
import mn.edu.userapi.model.enums.Gender;
import mn.edu.userapi.model.enums.Profession;
import mn.edu.userapi.service.CustomerService;
import mn.edu.userapi.util.CryptoUtil;
import mn.edu.userapi.util.HttpClientUtil;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/hospital")
@RequiredArgsConstructor
@Tag(name = "Hospital API", description = "Эмнэлгийн системийн API интерфейс")
public class HospitalApiController {
    private final Logger logger = LoggerFactory.getLogger(HospitalApiController.class);

    private final CustomerService customerService;
    private final CryptoUtil cryptoUtil;
    private final HttpClientUtil httpClientUtil;

    @Operation(summary = "Хайлт хийх", description = "Өгөгдсөн хайлтын параметрээр хэрэглэгчийн мэдээллийг хайх")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Хайлт амжилттай",
                    content = @Content(schema = @Schema(implementation = JsonObject.class))),
            @ApiResponse(responseCode = "500", description = "Серверийн алдаа")
    })
    @PostMapping("/search")
    public ResponseEntity<JsonObject> onSearch(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Хайлтын параметрүүд",
                    required = true,
                    content = @Content(schema = @Schema(implementation = JsonObject.class))
            )
            @RequestBody String parameter) throws Exception {
        try {
            JsonArray ja = customerService.searchCustomers(parameter);

            JsonObject resp = new JsonObject();
            resp.put("code", "200");
            resp.put("body", ja);

            return ResponseEntity.ok(resp);
        } catch (Exception e){
            logger.error("error in search[error={}]", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "ID-аар бүртгэл хийх", description = "Өгөгдсөн ID-аар хэрэглэгчийг бүртгэх")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Бүртгэл амжилттай",
                    content = @Content(schema = @Schema(implementation = JsonObject.class))),
            @ApiResponse(responseCode = "500", description = "Серверийн алдаа")
    })
    @PostMapping("/register/{sisi_id}")
    public ResponseEntity<JsonObject> onRegisterId(
            @Parameter(description = "Бүртгэх хэрэглэгчийн ID", required = true)
            @PathVariable String sisi_id) throws Exception {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            JsonObject jwt = new JsonObject(httpClientUtil.httpRequest("", sisi_id, "POST", headers));

            JsonObject jres = new JsonObject();
            // Process jwt if needed

            return ResponseEntity.ok(jres);
        } catch (Exception e) {
            logger.error("Error in register by id: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Карт уншиж авах", description = "gRPC сервисээр картын мэдээллийг уншиж авах")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Карт амжилттай уншигдлаа",
                    content = @Content(schema = @Schema(implementation = JsonObject.class))),
            @ApiResponse(responseCode = "500", description = "Серверийн алдаа эсвэл gRPC дуудалтын алдаа")
    })
    @PostMapping("/read/card")
    public ResponseEntity<JsonObject> onReadCardFromGrpc() {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress("10.3.132.166", 5000)
                    .usePlaintext()
                    .build();
            CardReaderServiceGrpc.CardReaderServiceBlockingStub stub = CardReaderServiceGrpc.newBlockingStub(channel);

            CardRequest request = CardRequest.newBuilder()
                    .setIncludeImage(false)
                    .build();
            CardResponse response = stub.readCard(request);

            JsonObject result = new JsonObject();
            response.getFieldsMap().forEach(result::put);

            channel.shutdown();

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error calling gRPC server: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Бүх хэрэглэгчийг авах", description = "Системд бүртгэлтэй бүх хэрэглэгчийн жагсаалтыг авах")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Хэрэглэгчдийн жагсаалт амжилттай авагдлаа",
                    content = @Content(schema = @Schema(implementation = JsonObject.class))),
            @ApiResponse(responseCode = "500", description = "Серверийн алдаа")
    })
    @GetMapping("/fetch")
    public ResponseEntity<JsonObject> onFetch() {
        try {
            JsonArray ja = customerService.getAllCustomers();
            JsonObject response = new JsonObject();
            response.put("body", ja);
            response.put("code", "200");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error in fetch all customers: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "ID-аар хэрэглэгч авах", description = "Өгөгдсөн ID-тай хэрэглэгчийн мэдээллийг авах")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Хэрэглэгчийн мэдээлэл амжилттай авагдлаа",
                    content = @Content(schema = @Schema(implementation = JsonObject.class))),
            @ApiResponse(responseCode = "403", description = "Хэрэглэгч олдсонгүй"),
            @ApiResponse(responseCode = "500", description = "Серверийн алдаа")
    })
    @GetMapping("/fetch/{id}")
    public ResponseEntity<JsonObject> onFetchCustomer(
            @Parameter(description = "Хэрэглэгчийн ID", required = true)
            @PathVariable String id) {
        try {
            Customer customer = customerService.getCustomerById(id);

            if (customer == null) {
                return ResponseEntity.ok(new JsonObject().put("message", "No customer found").put("code", "403"));
            }

            CustomerDto customerDto = customerService.mapEntityToDto(customer);

            JsonObject response = new JsonObject();
            response.put("customerId", customerDto.getCustomerId());
            response.put("surname", customerDto.getSurname());
            response.put("firstName", customerDto.getFirstName());
            response.put("dateOfBirth", customerDto.getDateOfBirth().toString());
            response.put("age", customerDto.getAge());
            response.put("gender", customerDto.getGender().toString());
            response.put("phoneNumber", customerDto.getPhoneNumber());
            response.put("email", customerDto.getEmail());
            response.put("province", customerDto.getProvince());
            response.put("district", customerDto.getDistrict());
            response.put("subdistrict", customerDto.getSubdistrict());
            response.put("addressDetail", customerDto.getAddressDetail());
            response.put("profession", customerDto.getProfession().toString());
            response.put("sisiId", customerDto.getSisiId());
            response.put("userId", customerDto.getUserId());
            response.put("nationalId", customerDto.getNationalId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error in fetch customer: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Хэрэглэгч бүртгэх", description = "Шинэ хэрэглэгчийн мэдээллийг системд бүртгэх")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Хэрэглэгч амжилттай бүртгэгдлээ",
                    content = @Content(schema = @Schema(implementation = JsonObject.class))),
            @ApiResponse(responseCode = "500", description = "Серверийн алдаа")
    })
    @PostMapping("/save/customer")
    public ResponseEntity<JsonObject> onSave(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Хэрэглэгчийн мэдээлэл",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CustomerDto.class))
            )
            @RequestBody CustomerDto request) {
        try {
            String customerId = customerService.createCustomerId();
            logger.info("id={}", customerId);

            CustomerDto customerDto = new CustomerDto();
            customerDto.setCustomerId(customerId);
            customerDto.setSurname(request.getSurname());
            customerDto.setFirstName(request.getFirstName());
            customerDto.setDateOfBirth(request.getDateOfBirth());
            customerDto.setAge(request.getAge());
            customerDto.setGender(request.getGender());
            customerDto.setPhoneNumber(request.getPhoneNumber());
            customerDto.setEmail(request.getEmail());
            customerDto.setProvince(request.getProvince());
            customerDto.setDistrict(request.getDistrict());
            customerDto.setSubdistrict(request.getSubdistrict());
            customerDto.setAddressDetail(request.getAddressDetail());
            customerDto.setProfession(request.getProfession());
            customerDto.setSisiId(request.getSisiId());
            customerDto.setUserId(request.getUserId());
            customerDto.setNationalId(request.getNationalId());

            logger.info("customerDto={}", customerDto);

            if(customerService.checkCustomer(request.getNationalId())) {
                return ResponseEntity.badRequest().build();
            }

            if (customerService.saveCustomer(customerDto)) {
                return ResponseEntity.ok(new JsonObject().put("message", "Customer saved successfully").put("code", "200"));
            } else {
                return ResponseEntity.internalServerError().build();
            }

        } catch (Exception e) {
            logger.error("Error in saving customer: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Хэрэглэгч устгах", description = "Хэрэглэгчийн мэдээллийг системээс устгах")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Хэрэглэгчийн мэдээллийг амжилттай устгалаа",
                    content = @Content(schema = @Schema(implementation = JsonObject.class))),
            @ApiResponse(responseCode = "500", description = "Серверийн алдаа")
    })
    @DeleteMapping("/delete/customer/{id}")
    public ResponseEntity<JsonObject> onDelete(
            @Parameter(description = "Хэрэглэгчийн ID", required = true)
            @PathVariable(name = "id") String customerId) throws Exception {
        try{
            customerService.deleteCustomer(customerId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Хэрэглэгчийн мэдээлэл засах", description = "Систем дэх хэрэглэгчийн мэдээллийг засах")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Хэрэглэгчийн мэдээллийг амжилттай засварлалаа",
                    content = @Content(schema = @Schema(implementation = JsonObject.class))),
            @ApiResponse(responseCode = "500", description = "Серверийн алдаа")
    })
    @PostMapping("/edit/customer")
    public ResponseEntity<JsonObject> obEdit(
            @RequestBody CustomerDto request) throws Exception {
        try {
            String customerId = request.getCustomerId();
            customerService.updateCustomer(customerId, request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Нууцлалын тест", description = "Нууцлалын түлхүүр үүсгэх тест")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Нууцлалын түлхүүр амжилттай үүсгэгдлээ",
                    content = @Content(schema = @Schema(implementation = JsonObject.class))),
            @ApiResponse(responseCode = "500", description = "Серверийн алдаа")
    })
    @PostMapping("/test")
    public ResponseEntity<JsonObject> onTest() throws Exception {
        KeyPair keyPair = cryptoUtil.generateKeys();

        String publicKeyStr = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKeyStr = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        JsonObject jwt = new JsonObject()
                .put("public", publicKeyStr)
                .put("private", privateKeyStr);

        return ResponseEntity.ok(jwt);
    }
}