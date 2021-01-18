package de.main.springsession.repository;

import de.main.springsession.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByUserId(Long userId);
    List<UserSession> findAll();
    Optional<UserSession>findBySessionId(String sessionId);
    Optional<UserSession>findBySessionTimeout(ZonedDateTime sessionTimeOut);
    void deleteById(Long id);
    void deleteAll();



}
