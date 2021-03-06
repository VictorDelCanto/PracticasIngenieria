/*
 * ListarNPconocidos.java
 *
 * Created on 10 de abril de 2006, 10:37
 */

package nombrespropios;
import javax.swing.*;
import java.util.*;


/**
 *
 * @author  Victor del Canto
 */
public class ListarNPconocidos extends javax.swing.JFrame {

    public static DefaultListModel lscon=new DefaultListModel();
    public static EditorNP jlistcon= new EditorNP();
    
    /** Creates new form ListarNPconocidos */
    public ListarNPconocidos() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        Cerrar = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("LISTA DE NOMBRES PROPIOS CONOCIDOS");
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(4450, 2200));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(250, 200));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(375, 280));
        jList1.setModel(lscon);
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jList1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 35;
        gridBagConstraints.gridheight = 12;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.ipady = 60;
        getContentPane().add(jScrollPane1, gridBagConstraints);

        Cerrar.setText("Cerrar");
        Cerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CerrarMouseClicked(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.insets = new java.awt.Insets(10, 100, 1, 4);
        getContentPane().add(Cerrar, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-450)/2, (screenSize.height-350)/2, 450, 350);
    }
    // </editor-fold>//GEN-END:initComponents

    private void CerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CerrarMouseClicked
        lscon.clear();      //borra el contenido de la JList
        this.dispose();     //cierra y
        this.setVisible(false);     //oculta la ventana
    }//GEN-LAST:event_CerrarMouseClicked
   
    public static void creaJLIST() {
//Pone los elementos del conjunto Conocidos que estan en el texto en un JList
        Iterator c=jlistcon.ListaConocidos.iterator();
        while (c.hasNext()==true) {
            Object palabra=c.next();
            if (jlistcon.lista.contains(palabra)==true) {
                lscon.addElement(palabra);
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                creaJLIST();                
                new ListarNPconocidos().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cerrar;
    public static javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}
