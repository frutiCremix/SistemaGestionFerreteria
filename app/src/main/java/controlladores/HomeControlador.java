package controlladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.List;
import javax.swing.JLabel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelos.Producto;
import modelos.ProductoDao;
import vistas.HomeView;
import vistas.LoginView;
import vistas.ModalAddProduct;

public class HomeControlador implements ActionListener{
    
    private HomeView hv;
    private TableRowSorter<TableModel> sorter;
    
    public HomeControlador(HomeView hv){
        this.hv=hv;
        this.hv.getButtonSalir().addActionListener(this);
        inicializarTabla();
        configurarListeners();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        hv.dispose();
        LoginView lv =new LoginView();
        lv.setVisible(true);
       
        
    }
    
    private void inicializarTabla() {
        buscarTodo();
          
        sorter = new TableRowSorter<>(hv.getjTablaStock().getModel());
        hv.getjTablaStock().setRowSorter(sorter);

        hv.getjTablaStock().getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columnaSeleccionada = hv.getjTablaStock().columnAtPoint(e.getPoint());
                ordenarTablaPorColumna(columnaSeleccionada);
            }
        });
         
    }

    private void configurarListeners() {
        // Agregar listeners para cada JLabel del menú lateral
        agregarListenerJLabel(hv.getjLabelStock(),0);
        
        agregarListenerJLabel(hv.getjLabelVentas(),1);
        agregarListenerJLabel(hv.getjLabelPedidos(),2);
        agregarListenerJLabel(hv.getjLabelProveedores(),3);
        agregarListenerJLabel(hv.getjLabelEmpleados(),4);
        agregarListenerJLabel(hv.getjLabelConfiguracion(),5);
        
        hv.getButtonBuscarStock().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
            
        });
        hv.getjButtonAdd().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ModalAddProduct mp=new ModalAddProduct();
                
                mp.addWindowListener(new WindowAdapter(){
                   @Override
                   public void windowClosed(WindowEvent e){
                       mp.dispose();
                       hv.setEnabled(true);
                       hv.toFront();
                       buscarTodo();
                      
                   }
                });
                
                hv.setEnabled(false);
                mp.setVisible(true);
            }
            
        });
        
    }
    
    private void seleccionarPestana(int index) {
        // Seleccionar la pestaña correspondiente en el JTabbedPane
        hv.getjTabbedPaneStock().setSelectedIndex(index);
    }
    
    private void agregarListenerJLabel(JLabel label,int index){
        label.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                seleccionarPestana(index);
            }
        });
    }
    
    private boolean esNumero(String texto){
        try{
            Integer.parseInt(texto);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    private void buscarTodo(){
        DefaultTableModel tableModel = (DefaultTableModel) hv.getjTablaStock().getModel();
          tableModel.setRowCount(0);//limpiamos la tabla
    try (ProductoDao productoDao = new ProductoDao()) {
            List<Producto> productos = productoDao.getAllProducts();

            for (Producto p : productos) {
                tableModel.addRow(new Object[]{
                        p.getIdProducto(),
                        p.getNombre(),
                        p.getCategoria(),
                        p.getMarca(),
                        p.getCantidad(),
                        p.getPrecio()
                        
                });
            }
            hv.getjLabelProductosEncontrados().setText(productos.size()+" productos encontrados");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void buscarProducto(){
        String textoBusqueda=hv.getTextBuscarStock();
        if(!textoBusqueda.isEmpty()){
            
            if(esNumero(textoBusqueda)){
            int idBusqueda=Integer.parseInt(textoBusqueda);
            Producto p=buscarPorId(idBusqueda);
           
            DefaultTableModel tableModel = (DefaultTableModel) hv.getjTablaStock().getModel();
            tableModel.setRowCount(0);//limpiamos la tabla
            
            if(p.getIdProducto()!= 0){
                tableModel.addRow(new Object[]{
                        p.getIdProducto(),
                        p.getNombre(),
                        p.getCategoria(),
                        p.getMarca(),
                        p.getCantidad(),
                        p.getPrecio()
                        
            });
            
            
                 hv.getjLabelProductosEncontrados().setText("1 productos encontrados");
            }else{
                 hv.getjLabelProductosEncontrados().setText("ese producto no existe");
            }
        }else{
            Producto p=buscarPorNombre(textoBusqueda);
            DefaultTableModel tableModel = (DefaultTableModel) hv.getjTablaStock().getModel();
            tableModel.setRowCount(0);//limpiamos la tabla
                
            if(p.getIdProducto()!= 0){
                tableModel.addRow(new Object[]{
                    p.getIdProducto(),
                    p.getNombre(),
                    p.getCategoria(),
                    p.getMarca(),
                    p.getCantidad(),
                    p.getPrecio()
                        
                });
            
                 hv.getjLabelProductosEncontrados().setText("1 productos encontrados");                
            }else{
                 hv.getjLabelProductosEncontrados().setText("ese producto no existe");
            }
        }
        }else{
            buscarTodo();
        }
        
    }
    
    private Producto buscarPorId(int idBusqueda){
        
        try(ProductoDao productoDao=new ProductoDao();){
                return productoDao.getProductById(idBusqueda);
        }catch(Exception e){
                e.printStackTrace();
                return null;
        }
    }
    
    private Producto buscarPorNombre(String name){
           try(ProductoDao productoDao=new ProductoDao();){
                return productoDao.getProductByName(name);
        }catch(Exception e){
                e.printStackTrace();
                return null;
        }
    }
    
   private void ordenarTablaPorColumna(int columna) {
    sorter.setSortKeys(List.of(new RowSorter.SortKey(columna, SortOrder.ASCENDING)));
    sorter.sort();
}
    private boolean esColumnaNumrica(int columna){
            return true;
    }
}
