package se.maokei.kanban.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTLoginSucessResponse {
    private boolean success;
    private String token;
}
