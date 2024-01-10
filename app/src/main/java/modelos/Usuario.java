package modelos;

public class Usuario {
    private String id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String rol;
    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol(){
        return rol;
    }
    void setRol(String rol) {
      this.rol=rol;
    }
    
}
