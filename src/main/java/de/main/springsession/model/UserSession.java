package de.main.springsession.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity(name = "sessionStorage")
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    Long userId;
    String sessionId;
    ZonedDateTime sessionTimeout;


    public UserSession() {

    }

    public UserSession(Long userId) {
        this.userId = userId;
    }

    public UserSession(Long userId, String sessionId, ZonedDateTime sessionTimeout) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.sessionTimeout = sessionTimeout;
    }

    public UserSession(Long id, Long userId, String sessionId, ZonedDateTime sessionTimeout) {
        Id = id;
        this.userId = userId;
        this.sessionId = sessionId;
        this.sessionTimeout = sessionTimeout;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ZonedDateTime getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(ZonedDateTime sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }
}
