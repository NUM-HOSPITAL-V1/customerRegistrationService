//package mn.edu.userapi.filter;
//
//import io.vertx.core.json.JsonArray;
//import io.vertx.core.json.JsonObject;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.*;
//
//import static io.vertx.core.http.impl.HttpClientConnection.log;
//
///**
// * API Gateway-с дамжуулагдсан хэрэглэгчийн ID болон эрхийн мэдээллийг
// * Spring Security-н SecurityContext руу шилжүүлэх фильтр.
// */
//@Slf4j
//public class GatewayHeadersAuthenticationFilter extends OncePerRequestFilter {
//
//    private static final String USER_ID_HEADER = "X-User-ID";
//    private static final String USER_ROLES_HEADER = "X-User-Roles";
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//
//
//        log.info("==== DEBUG: Request to {} ====", request.getRequestURI());
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            log.info("Header: {} = {}", headerName, request.getHeader(headerName));
//        }
//
//        String token = request.getHeader("authorization");
//        log.info("token = " + token);
//        String[] tokens = token.split(" ");
//        token = tokens[1];
//
//        String[] parts = token.split("\\.");
//
//        String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
//        JsonObject payloadJson = new JsonObject(new String(Base64.getUrlDecoder().decode(parts[1])));
//
//        log.info("Header: " + headerJson);
//        log.info("Payload: " + payloadJson);
//
//        JsonArray rolesArray = payloadJson.getJsonArray("roles");
//        String role = rolesArray.getString(0);
//
//        log.info("Roles = " + role);
//    }
//}
