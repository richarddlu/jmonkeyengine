/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SelectExistingAsset.java
 *
 * Created on 27.10.2010, 14:58:54
 */
package com.jme3.gde.assetpack.project.wizards;

import com.jme3.gde.assetpack.AssetPackLoader;
import com.jme3.gde.assetpack.project.AssetPackProject;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author normenhansen
 */
public class SelectExistingAsset extends javax.swing.JDialog implements ExplorerManager.Provider {

    private transient ExplorerManager explorerManager = new ExplorerManager();
    private AssetPackProject project;
    private List<FileDescription> list;

    /** Creates new form SelectExistingAsset */
    public SelectExistingAsset(java.awt.Frame parent, boolean modal, FileObject folder, AssetPackProject project, List<FileDescription> list) {
        super(parent, modal);
        this.project = project;
        this.list = list;
        Node node = null;
        initComponents();
        setLocationRelativeTo(null);
        try {
            node = DataObject.find(folder).getNodeDelegate();
        } catch (DataObjectNotFoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Error loading asset folder!");
            Exceptions.printStackTrace(ex);
        }
        if (node != null) {
            explorerManager.setRootContext(node);
            explorerManager.getRootContext().setDisplayName(node.getName());
        }
    }

    public ExplorerManager getExplorerManager() {
        return explorerManager;
    }

    public void applyInfo() {
        Node[] nodes = explorerManager.getSelectedNodes();
        for (int i = 0; i < nodes.length; i++) {
            Node node = nodes[i];
            String path = node.getLookup().lookup(DataObject.class).getPrimaryFile().getPath();
            FileObject file1 = FileUtil.toFileObject(new File(path));
            FileDescription description = AssetPackLoader.getFileDescription(file1);
            description.setExisting(true);
            String pathName = project.getProjectAssetManager().getRelativeAssetPath(path);
            pathName=pathName.replaceAll("\\\\", "/");
            pathName = pathName.substring(0, pathName.lastIndexOf("/")+1);
            description.setPath(pathName);
            list.add(description);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new BeanTreeView();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton1.setText(org.openide.util.NbBundle.getMessage(SelectExistingAsset.class, "SelectExistingAsset.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(org.openide.util.NbBundle.getMessage(SelectExistingAsset.class, "SelectExistingAsset.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jButton2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton1)
                    .add(jButton2)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        applyInfo();
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                SelectExistingAsset dialog = new SelectExistingAsset(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}