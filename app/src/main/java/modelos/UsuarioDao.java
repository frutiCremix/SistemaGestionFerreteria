package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioDao implements AutoCloseable {
    private Connection conn;
    
    public UsuarioDao(){
        this.conn=ConnectionMySQL.getConnection();
    }
    
    public List<Usuario> getAllUsuarios(){
        List<Usuario> usuarios=new ArrayList<>();
        
        try{
            String selectQuery="SELECT * FROM user";
            
            try(PreparedStatement pst=conn.prepareStatement(selectQuery);
                ResultSet rs=pst.executeQuery();){
                
                while(rs.next()){
                    Usuario usuario=new Usuario();
                    usuario.setId(rs.getInt("iduser"));
                    usuario.setName(rs.getString("name"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setSector(rs.getString("sector"));
                    usuario.setSalary(rs.getDouble("salary"));
                    usuario.setFIngreso(rs.getDate("fIngreso"));
                    
                    usuarios.add(usuario);
                }
            
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return usuarios;
    }
    
    public Usuario getUserID(int id) {
        Usuario user = new Usuario();
        try {
            String selectQuery = "SELECT * FROM user WHERE iduser = ?";
        
            try (PreparedStatement pst = conn.prepareStatement(selectQuery)){
                pst.setInt(1, id);  // Asigna el valor del parámetro id

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        user.setId(rs.getInt("iduser"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setUsername(rs.getString("username"));
                        user.setPassword(rs.getString("password"));
                        user.setRol(rs.getString("rol"));
                        user.setSalary(rs.getDouble("salary"));
                        user.setSector(rs.getString("sector"));
                        user.setFIngreso(rs.getDate("fIngreso"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public Usuario getUserByName(String name){
        Usuario user = new Usuario();
        try{
            String selectQuery = "SELECT * FROM user WHERE name = ?";
            try(PreparedStatement ps=conn.prepareStatement(selectQuery)){
                ps.setString(1,name);
                
                try(ResultSet rs=ps.executeQuery();){
                    if (rs.next()) {
                        user.setId(rs.getInt("iduser"));
                        user.setName(rs.getString("name"));
                        user.setEmail(rs.getString("email"));
                        user.setUsername(rs.getString("username"));
                        user.setPassword(rs.getString("password"));
                        user.setRol(rs.getString("rol"));
                        user.setSalary(rs.getDouble("salary"));
                        user.setSector(rs.getString("sector"));
                        user.setFIngreso(rs.getDate("fIngreso"));
                    }
                }      
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return user;
    }
    
    
    public boolean setUsuario(Usuario user){
        
       try{
           String query="INSERT INTO user (rol, name, email, username, password, salary, sector, fIngreso) VALUES (?,?,?,?,?,?,?,?)";
           try(PreparedStatement st=conn.prepareStatement(query)){
               st.setString(1, user.getRol());
               st.setString(2, user.getName());
               st.setString(3, user.getEmail());
               st.setString(4, user.getUsername());
               st.setString(5, user.getPassword());
               st.setDouble(6, user.getSalary());
               st.setString(7, user.getSector());
               
               Date fecha=user.getFIngreso();
               java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
               st.setDate(8, fechaSQL);
                int filasAfectadas=st.executeUpdate();

                return filasAfectadas > 0; 
                
           }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public void close() throws Exception {
        ConnectionMySQL.closeConnection(conn);
    }
   
}
