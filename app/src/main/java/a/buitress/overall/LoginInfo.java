package a.buitress.overall;

public class LoginInfo {
    private String _correo;
    private String _password;

    public LoginInfo(String correo, String password) {
        _correo = correo;
        _password = password;
    }

    public String getCorreo() {
        return _correo;
    }

    public String getPassword() {
        return _password;
    }
}
