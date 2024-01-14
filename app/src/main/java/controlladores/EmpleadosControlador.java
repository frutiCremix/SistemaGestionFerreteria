package controlladores;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import modelos.Usuario;
import modelos.UsuarioDao;
import vistas.HomeView;


public class EmpleadosControlador {
    
    private HomeView hv;
    DefaultTableModel tableModel;
    public EmpleadosControlador(HomeView hv){
     this.hv=hv;
     configurarListener();
     tableModel = (DefaultTableModel) hv.getjTableEmpleados().getModel();
    }
    public void inicializarTabla(){
        //recuperamos la tabla
       
        tableModel.setRowCount(0);//limpiamos la tabla
        
        //nos manejamos con el dao
        try(UsuarioDao ud=new UsuarioDao()){
            List<Usuario> usuarios=ud.getAllUsuarios();
            for(Usuario u : usuarios){
                tableModel.addRow(new Object[]{
                        u.getId(),
                        u.getName(),
                        u.getSector(),
                        u.getSalary(),
                        u.getFormattedIngreso(),
                        
                });
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void configurarListener(){
        hv.getjButtonBuscarEmpleado().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                 String busqueda=hv.getjTextFieldBuscarEmpleado().getText();
                 
                 if(!busqueda.isEmpty()){
                         tableModel.setRowCount(0);//limpiamos la tabla
                    if(esNumero(busqueda)){
                        Usuario user=searchById(Integer.parseInt(busqueda)); 
                        if(user.getId()!= 0){
                            
                            tableModel.addRow(new Object[]{
                                user.getId(),
                                user.getName(),
                                user.getSector(),
                                user.getSalary(),
                                user.getFormattedIngreso()
                            });
                        }
                    }else{
                        //si no es numero
                        Usuario user=searchByName(busqueda);
                        if(user.getId()!=0){
                            tableModel.addRow(new Object[]{
                                user.getId(),
                                user.getName(),
                                user.getSector(),
                                user.getSalary(),
                                user.getFormattedIngreso()
                            });
                        }
                    }
                    
                }
            }
        });
        hv.getjButtonAgregarUsuario().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               agregarUsuario();            
            }
        });
        hv.getjButtonBorrarUsuario().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean usuarioBorrado=borrarUsuario();
                if(usuarioBorrado){
                    mostrarMensaje("usuario borrado con exito");
                }else{
                    mostrarMensaje("no se encontro el usuario");
                }
            }
            
        });
    }
    
    public Usuario searchById(int id){
       
        try(UsuarioDao ud=new UsuarioDao()){
            return ud.getUserID(id);
                   
        }catch(Exception err){
            err.printStackTrace();
            return null;
        }
    }
    
    public Usuario searchByName(String name){
        try(UsuarioDao ud=new UsuarioDao()){
            return ud.getUserByName(name);
                   
        }catch(Exception err){
            err.printStackTrace();
            return null;
        }
    }
    private boolean esNumero(String texto){
        try{
            Integer.parseInt(texto);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    private void agregarUsuario(){
        String name=hv.getjTextFieldAgregarNombre().getText();
        String password=hv.getjTextFieldAgregarPassword().getText();
        String sector=hv.getjTextFieldAgregarSector().getText();
        String username=hv.getjTextFieldAgregarUsername().getText();
        String email=hv.getjTextFieldAgregarEmail().getText();
        String sueldoStr=hv.getjTextFieldAgregarSueldo().getText();
        
        if(!name.isEmpty() && !password.isEmpty() && !sector.isEmpty() && !username.isEmpty() && !email.isEmpty() && !sueldoStr.isEmpty()){
            
            try{
                double sueldo=Double.parseDouble(sueldoStr);
                boolean cargaExitosa=cargarDatos(name,email,username,password,sueldo,sector);
                if(cargaExitosa){
                    mostrarMensaje("se cargo correctamente a la bd");
                    hv.setjTextFieldAgregarNombre("");
                    hv.setjTextFieldAgregarPassword("");
                    hv.setjTextFieldAgregarSector("");
                    hv.setjTextFieldAgregarUsername("");
                    hv.setjTextFieldAgregarEmail("");
                    hv.setjTextFieldAgregarSueldo("");
                    
                }else{
                     mostrarMensaje("Error al cargar en la bd");
                }
            }catch(NumberFormatException e){
               mostrarMensaje("error en al ingresar el sueldo.");
            }
        }else{
            mostrarMensaje("verificar campos vacios");
        }
    }
    private boolean borrarUsuario(){
        String idStr = hv.getjTextFieldBorrarUsuario().getText();

        if (!idStr.isEmpty()) {
            try{
                int id=Integer.parseInt(idStr);
                try(UsuarioDao userDao=new UsuarioDao();){
            
                    return userDao.removeUsuario(id);
                }catch(Exception e){
                    e.printStackTrace();
                    return false;
                }
                 
            }catch(NumberFormatException e){
                e.printStackTrace();
                return false;
            }
        } else {
            mostrarMensaje("Debes ingresar un ID válido.");
            return false;
        }
    }
    public boolean cargarDatos(String name,String email, String username, String password, double salary, String sector){
        try(UsuarioDao usuarioDao =new UsuarioDao();){
            Usuario user=new Usuario();
            user.setName(name);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setSalary(salary);
            user.setSector(sector);
            Date fecha = new Date();

        // Ajustar la fecha para eliminar la información de horas, minutos y segundos
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date fechaAjustada = calendar.getTime();
        user.setFIngreso(fechaAjustada);
            user.setRol("empleado");
            //obviamos el id en la carga. que se encargue la bd
            
           boolean cargaExitosa=usuarioDao.setUsuario(user);
            return cargaExitosa;
        }catch(Exception err){
            err.printStackTrace();
            return false;
        }
    }
    
     private void mostrarMensaje(String text) {
        JDialog dialog = new JDialog(hv, text, true);
        JLabel label = new JLabel(text);
        JButton closeButton = new JButton("Cerrar");

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(closeButton);

        dialog.getContentPane().add(panel);
        dialog.setSize(300, 100);
        dialog.setLocationRelativeTo(hv);
        dialog.setVisible(true);
    }
}
