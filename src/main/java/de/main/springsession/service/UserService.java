package de.main.springsession.service;

import de.main.springsession.model.User;
import de.main.springsession.model.UserSession;
import de.main.springsession.repository.UserRepository;
import de.main.springsession.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserService{

   static Map<ZonedDateTime, String> sessionDeleteMap = new HashMap<>();

    @Autowired
    UserRepository userRepository;

    @Autowired
     UserSessionRepository userSessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public void register(User user){
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    public boolean login(User user){
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            return passwordEncoder.matches(user.getPassword(),userRepository.findByUsername(user.getUsername()).get().getPassword());
        }
        return false;

    }

    @Transactional
    public UserSession createSession(User user) throws InterruptedException {

        //change later
       /* if(userRepository.findByUsername(user.getUsername()).isPresent()){
            Optional<User> dbUser = userRepository.findByUsername(user.getUsername());
            if(dbUser.isPresent()){
                if(userSessionRepository.findByUserId(dbUser.get().getId()).isPresent()){
                    userSessionRepository.de(dbUser.get().getId());
                }
            }
           }*/
        UserSession session = new UserSession();
        ZonedDateTime localDate = ZonedDateTime.now().plusSeconds(30L);
        session.setSessionId(UUID.randomUUID().toString());
        session.setUserId(userRepository.findByUsername(user.getUsername()).get().getId());
        session.setSessionTimeout(localDate);


        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        sessionDeleteMap.put(session.getSessionTimeout(), session.getSessionId());

        executorService.schedule(this::deleteSession, 60, TimeUnit.MINUTES);

        userSessionRepository.save(session);

        System.err.println(ZonedDateTime.now());

        System.err.println(userSessionRepository.findAll().get(0).getSessionId());

        return session;


    }


    @Transactional
    public void deleteSession(){
        AtomicReference<Long> id = new AtomicReference<>(0L);
        userSessionRepository.findAll().stream().forEach(data->{
            if(data.getUserId() ==2L){
                id.set(data.getId());
            }
        });
        userSessionRepository.deleteById(id.get());


    }


}
