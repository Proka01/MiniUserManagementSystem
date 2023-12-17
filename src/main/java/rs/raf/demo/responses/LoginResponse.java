package rs.raf.demo.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginResponse {
    private String jwt;
    private boolean readUsersPermission;
    private boolean createUsersPermission;
    private boolean updateUsersPermission;
    private boolean deleteUsersPermission;

    public LoginResponse(String jwt, boolean readUsersPermission, boolean createUsersPermission, boolean updateUsersPermission, boolean deleteUsersPermission) {
        this.jwt = jwt;
        this.readUsersPermission = readUsersPermission;
        this.createUsersPermission = createUsersPermission;
        this.updateUsersPermission = updateUsersPermission;
        this.deleteUsersPermission = deleteUsersPermission;
    }
}
