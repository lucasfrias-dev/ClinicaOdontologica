package org.lucasf.consultorioodontologico.utils;


import com.password4j.Password;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordEncoder {

    public String encode(String rawPassword) {
        return Password.hash(rawPassword).withBcrypt().getResult();
    }

    public boolean verify(String rawPassword, String encodedPassword) {
        return Password.check(rawPassword, encodedPassword).withBcrypt();
    }
}