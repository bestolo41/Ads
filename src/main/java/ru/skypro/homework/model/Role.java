package ru.skypro.homework.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
@Getter
public enum Role implements GrantedAuthority{
    USER("USER"), ADMIN("ADMIN");
    private final String name;

    Role(String name) {
        this.name = name;
    }


    @Override
    public String getAuthority() {
        return getName();
    }
}
