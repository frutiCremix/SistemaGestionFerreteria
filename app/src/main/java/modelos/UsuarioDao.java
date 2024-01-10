package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                    usuario.setId(rs.getString("iduser"));
                    usuario.setName(rs.getString("name"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setRol(rs.getString("rol"));
                    
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
        String selectQuery = "SELECT * FROM user WHERE idusuario = ?";
        
        try (PreparedStatement pst = conn.prepareStatement(selectQuery)) {
            pst.setInt(1, id);  // Asigna el valor del parámetro id

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getString("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRol(rs.getString("rol"));
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return user;
}
    @Override
    public void close() throws Exception {
        ConnectionMySQL.closeConnection(conn);
    }
}
