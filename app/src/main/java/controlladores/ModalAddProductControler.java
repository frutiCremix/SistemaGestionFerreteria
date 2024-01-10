package controlladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modelos.Producto;
import modelos.ProductoDao;
import vistas.ModalAddProduct;


public class ModalAddProductControler {
    
    ModalAddProduct mp;
    
    public ModalAddProductControler(ModalAddProduct mp){
        this.mp=mp;
        configurarListeners();
    }
    public void configurarListeners(){
       
        
        mp.getjButtonCargar().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre=mp.getjTextFieldName().getText();
                String categoria=mp.getjTextFieldCategory().getText();
                String marca=mp.getjTextFieldBrand().getText();
                String precioStr=mp.getjTextFieldPrice().getText();
                String cantidadStr=mp.getjTextFieldQuantity().getText();
               
                    if (!nombre.isEmpty() && !categoria.isEmpty() && !marca.isEmpty() &&
                    !precioStr.isEmpty() && !cantidadStr.isEmpty()) {
                    try {
                        double precio = Double.parseDouble(precioStr);
                        int cantidad = Integer.parseInt(cantidadStr);
                        
                        //cargar en la bd
                        boolean cargaExitosa = cargarDatos(nombre,categoria,marca,precio,cantidad);
                        if(cargaExitosa){
                            
                            mostrarMensaje("Carga Exitosa");
                            mp.setjTextFieldName("");
                            mp.setjTextFieldCategory("");
                            mp.setjTextFieldBrand("");
                            mp.setjTextFieldPrice("");
                            mp.setjTextFieldQuantity("");
                     
                        }else{
                            mostrarMensaje("Error al cargar en la BD");
                        }
                    }catch (NumberFormatException err) {
                        mostrarMensaje("Error campos numeros incorrectos");
                    }
                } else {
                    mostrarMensaje("Error Verifica por campos vacios");
                }
            }
        });
    }
    
     private void mostrarMensaje(String text) {
        JDialog dialog = new JDialog(mp, text, true);
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
        dialog.setLocationRelativeTo(mp);
        dialog.setVisible(true);
    }
     
    private boolean cargarDatos(String nombre,String categoria,String marca,double precio,int cantidad) {
       
        try (ProductoDao productoDao = new ProductoDao()) {
             Producto p=new Producto();
             p.setNombre(nombre);
             p.setCategoria(categoria);
             p.setMarca(marca);
             p.setPrecio(precio);
             p.setCantidad(cantidad);
             boolean cargaExitosa = productoDao.setProduct(p);
            return cargaExitosa;
        } catch (Exception err) {
            err.printStackTrace();
            return false;
        }
    }
}
