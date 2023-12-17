package rs.raf.demo.model;

import lombok.Data;

@Data
public class UpdateRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String permissions;
}