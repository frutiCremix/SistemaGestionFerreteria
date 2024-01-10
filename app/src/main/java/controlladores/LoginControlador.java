package controlladores;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;
import modelos.Usuario;
import modelos.UsuarioDao;
import vistas.HomeView;
import vistas.LoginView;

public class LoginControlador implements ActionListener {
    
    LoginView lv;
    
    public LoginControlador(LoginView lv){
        this.lv=lv;
        
       this.lv.getButtonSubmit().addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lv.getButtonSubmit()) {
            String username = lv.getUserName();
            String password = lv.getPassword();
            
            try(UsuarioDao usuarioDao = new UsuarioDao()) {
                List<Usuario> usuarios = usuarioDao.getAllUsuarios();
                boolean login = loginCorrecto(username, password, usuarios);
                
                if (login) {
                    Usuario usuario = getUsuarioByUsername(username, usuarios);
                    
                    if (usuario != null) {
                        // Determina qué vista mostrar según el rol del usuario
                        String rol = usuario.getRol();
                        
                        if ("admin".equals(rol)) {
                            JOptionPane.showMessageDialog(null, "Bienvenido Admin");
                          lv.dispose();
                          HomeView hv=new HomeView();
                          hv.setVisible(true);
                        } else if ("empleado".equals(rol)) {
                            JOptionPane.showMessageDialog(null, "Bienvenido Empleado");
                           
                        } else {
                            // Otro rol no reconocido
                            JOptionPane.showMessageDialog(null, "Rol no reconocido");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    public boolean loginCorrecto(String username,String pass,List<Usuario> user){
    
        for(int i=0;i<user.size();i++){
            Usuario usuario=user.get(i);
            if(usuario.getUsername().equals(username) && usuario.getPassword().equals(pass)){
                return true;
            }
            
        }
        return false;
    }
     private Usuario getUsuarioByUsername(String username, List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }
   

   
    
}
