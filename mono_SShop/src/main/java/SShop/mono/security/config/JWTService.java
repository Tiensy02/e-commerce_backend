package SShop.mono.security.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private long expiration;
  @Value("${application.security.jwt.expiration-refresh}")
  private long refreshExpiration;

  /**
   * tạo access token với thông tin thêm
   *
   * @param claims      tổng hợp các phần muốn có trong token
   * @param userDetails thông tin user cần được xác thực
   * @return String
   */
  public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
    return buildToken(claims, userDetails, expiration);
  }

  /**
   * tạo token với thông tin user
   *
   * @param userDetails thông tin user
   * @return String
   */
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  /**
   * tạo refresh token
   *
   * @param userDetails
   * @return
   */
  public String generateRefeshToken(UserDetails userDetails) {
    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
  }

  /**
   * Hàm sinh ra 1 token
   *
   * @param claims      tổng hợp các phần muốn có trong token
   * @param userDetails thông tin user cần được xác thực
   * @param expiration  thời gian sống của token
   * @return String
   */
  public String buildToken(Map<String, Object> claims, UserDetails userDetails, long expiration) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userDetails.getUsername())
        .signWith(getSecretKey(), SignatureAlgorithm.HS256)
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .compact();
  }

  /**
   * chuyển token thành các Claims
   * @param token token
   * @return Claims
   */
  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSecretKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractEmail(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Key getSecretKey() {
    return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
  }
  public long getExpiration() {
    return expiration;
  }
}
