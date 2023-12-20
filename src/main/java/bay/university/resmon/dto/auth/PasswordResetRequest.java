package bay.university.resmon.dto.auth;

public class PasswordResetRequest {

    private String jwt;

    private String password;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
