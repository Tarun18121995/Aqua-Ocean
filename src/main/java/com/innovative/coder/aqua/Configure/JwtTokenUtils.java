package com.innovative.coder.aqua.Configure;

import com.innovative.coder.aqua.Model.User;
import com.innovative.coder.aqua.applicationData.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

@Component
public class JwtTokenUtils {
    public String getEmailFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }
    private Object getScopesFromToken(String token){
        return getAllClaimsFromToken(token).get("scopes");
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(ApplicationConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user);
    }

    private String doGenerateToken(User user) {

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority(user.getRole())));

        LocalDateTime localDateTime = LocalDateTime.now();

        return Jwts.builder()
                .setClaims(claims)
                //.setIssuer("http://thrymr.net")
                .setIssuedAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                /*.setExpiration(dateTime.plusMillis(AppConstants.EXPIRATION_TIME).toDate())*/
                .signWith(SignatureAlgorithm.HS256, ApplicationConstants.SECRET)
                .compact();
    }

    public String validateToken(String token) {
        final String email = getEmailFromToken(token);
        Assert.notNull(email,"Email doesn't exist in the token");
        return email;
    }


}
