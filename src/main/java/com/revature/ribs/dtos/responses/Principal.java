package com.revature.ribs.dtos.responses;

import com.revature.ribs.models.Role;

public class Principal {
    private String id;
    private String username;
    private Role role;

    public Principal(){
        super();
    }

    public Principal(String id, String username, Role role){
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
