package com.qorvia.accountservice.service.jwt;

import com.qorvia.accountservice.model.organizer.Organizer;
import com.qorvia.accountservice.model.user.UserInfo;
import com.qorvia.accountservice.repository.OrganizerRepository;
import com.qorvia.accountservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.Date;

@Service
public class JwtService {
    private final String jwtSecret = "ZmFrbKapka2Y7YWprZGZqYTtsZGZrYW";

    private final UserRepository userRepository;
    private final OrganizerRepository organizerRepository;

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);


    @Autowired
    public JwtService(UserRepository userRepository, OrganizerRepository organizerRepository) {
        this.userRepository = userRepository;
        this.organizerRepository = organizerRepository;
    }

    public String generateTokenForUser(String email) {
        Date currentDate = new Date();
        Date expirationTime = new Date(currentDate.getTime() + 30 * 24 * 60 * 60 * 1000L);
        UserInfo user = userRepository.findByEmail(email).get();
        return Jwts.builder()
                .setSubject(email)
                .claim("userId",user.getId())
                .claim("userEmail", user.getEmail())
                .claim("role", "ROLE_" + user.getRoles().toString())
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String generateTokenForOrganizer(String email) {
        Date currentDate = new Date();
        Date expirationTime = new Date(currentDate.getTime() + 30 * 24 * 60 * 60 * 1000L);
        Organizer organizer = organizerRepository.findByEmail(email).get();
        return Jwts.builder()
                .setSubject(email)
                .claim("userId",organizer.getId())
                .claim("userEmail", organizer.getEmail())
                .claim("role", "ROLE_" + organizer.getRoles().toString())
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Jwt token is expired or is invalid");
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


    public String getJWTFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String jwtToken = cookie.getValue();
                    logger.info("Jwt key : {}", jwtToken);
                    if (StringUtils.hasText(jwtToken)) {
                        return jwtToken;
                    }
                }
            }
        }
        return null;
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class);
    }

}
