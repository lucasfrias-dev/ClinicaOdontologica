package org.lucasf.consultorioodontologico.utils;

public class GeneradorPassword {
    public static void main(String[] args) {
        PasswordEncoder encoder = new PasswordEncoder();
        String password = "admin123";
        String hashed = encoder.encode(password);
        System.out.println("Hashed password: " + hashed);
    }
}

