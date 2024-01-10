package modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductoDao implements AutoCloseable {
    private Connection conn;
    
    public ProductoDao(){
        this.conn=ConnectionMySQL.getConnection();
    }
    
    public List<Producto> getAllProducts(){
        List<Producto> productos=new ArrayList<>();
        
        try{
            String selectQuery="SELECT * FROM product";
            try(PreparedStatement pst=conn.prepareStatement(selectQuery);
                    ResultSet rs=pst.executeQuery();){
            
                while(rs.next()){
                    Producto p=new Producto();
                    p.setIdProducto(rs.getInt("idproduct"));
                    p.setNombre(rs.getString("name"));
                    p.setCategoria(rs.getString("category"));
                    p.setMarca(rs.getString("brand"));
                    p.setCantidad(rs.getInt("quantity"));
                    p.setPrecio(rs.getDouble("price"));
                    
                    productos.add(p);
                }
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        
        return productos;
    }
    public Producto getProductByName(String name){
        Producto p=new Producto();
        
        try{
            String query="SELECT * FROM product WHERE name = ?";
            try(PreparedStatement pst=conn.prepareStatement(query);){
                pst.setString(1, name);
                try(ResultSet rs=pst.executeQuery();){
                    while(rs.next()){
                        p.setIdProducto(rs.getInt("idproduct"));
                        p.setNombre(rs.getString("name"));
                        p.setCategoria(rs.getString("category"));
                        p.setMarca(rs.getString("brand"));
                        p.setCantidad(rs.getInt("quantity"));
                        p.setPrecio(rs.getDouble("price"));
                    }
                }
            }
        }catch(SQLException e){
                e.printStackTrace();
        }
        return p;
    }
    public Producto getProductById( int index){
        Producto p=new Producto();
        
        try{
            String query="SELECT * FROM product WHERE idproduct = ?";
            try(PreparedStatement pst=conn.prepareStatement(query);){
                pst.setInt(1, index);
                try(ResultSet rs=pst.executeQuery();){
                    while(rs.next()){
                    
                        p.setIdProducto(rs.getInt("idproduct"));
                        p.setNombre(rs.getString("name"));
                        p.setCategoria(rs.getString("category"));
                        p.setMarca(rs.getString("brand"));
                        p.setCantidad(rs.getInt("quantity"));
                        p.setPrecio(rs.getDouble("price"));
                    
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return p;
        }
    public boolean setProduct(Producto p){
        try{
            String query = "INSERT INTO product (name, category, quantity, brand, price) VALUES (?, ?, ?, ?, ?)";
            try(PreparedStatement st=conn.prepareStatement(query)){
                st.setString(1, p.getNombre());
                st.setString(2,p.getCategoria());
                st.setInt(3,p.getCantidad());
                st.setString(4,p.getMarca());
                st.setDouble(5,p.getPrecio());
                
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
