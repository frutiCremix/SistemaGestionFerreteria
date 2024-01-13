package modelos;

import java.text.SimpleDateFormat;

import java.util.Date;

public class Usuario {
    private int id;
    private String name;
    private String email;
    private String username;
    private String password;
    private String rol;
    private double salary;
    private String sector;
    private Date fIngreso;
    
    public String getFormattedIngreso(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(this.fIngreso);
    }
    
    
    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    public void setRol(String rol) {
      this.rol=rol;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Date getFIngreso() {
        return fIngreso;
    }
    
    public void setFIngreso(Date fIngreso) {
        this.fIngreso = fIngreso;
    }
    
}
