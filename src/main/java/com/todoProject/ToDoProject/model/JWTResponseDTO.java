package com.todoProject.ToDoProject.model;

public class JWTResponseDTO {

    private String status;
    private String jwtToken;

    public JWTResponseDTO(String status, String jwtToken) {
        this.status = status;
        this.jwtToken = jwtToken;
    }

    public JWTResponseDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
