/*
 * MorphologyMathematiqueAboutBox.java
 */
package ensiie.morphologieMathematique.vues;

import java.io.File;
import javax.swing.JOptionPane;

public class MorphologyMathematiqueOpenImageBox extends javax.swing.JDialog {

    public MorphologyMathematiqueOpenImageBox(java.awt.Frame parent) {
        super(parent);
        initComponents();
        //Options supplémentaire 
        imageChooser.setCurrentDirectory(new File("."));
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageChooser = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ensiie.morphologieMathematique.vues.MorphologyMathematiqueApp.class).getContext().getResourceMap(MorphologyMathematiqueOpenImageBox.class);
        setTitle(resourceMap.getString("title")); // NOI18N
        setModal(true);
        setName("aboutBox"); // NOI18N
        setResizable(false);

        imageChooser.setCurrentDirectory(new java.io.File("C:\\Program Files\\NetBeans 7.0\\..\\testimges\\jmorph.gif"));
        imageChooser.setName("imageChooser"); // NOI18N
        imageChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(imageChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void imageChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageChooserActionPerformed

        if (evt.getActionCommand().equalsIgnoreCase("ApproveSelection")) {
            File fichierChoisi = imageChooser.getSelectedFile();
            String paramString = fichierChoisi.getName();
            //on test l'extension de fichier
            MorphologyMathematiqueView main = (MorphologyMathematiqueView) MorphologyMathematiqueApp.getApplication().getMainView();
            if ((paramString.indexOf(".jpg") != -1) || (paramString.indexOf(".gif") != -1)) {                
                main.ajoutImageOriginal(fichierChoisi);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une image .gif ou .jpg ", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else if (evt.getActionCommand().equalsIgnoreCase("CancelSelection")) {
            dispose();
        }
    }//GEN-LAST:event_imageChooserActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser imageChooser;
    // End of variables declaration//GEN-END:variables
}
