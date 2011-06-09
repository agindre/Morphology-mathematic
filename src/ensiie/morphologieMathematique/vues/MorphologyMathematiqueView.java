/*
 * MorphologyMathematiqueView.java
 */
package ensiie.morphologieMathematique.vues;

import ensiie.morphologieMathematique.model.Operations;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class MorphologyMathematiqueView extends FrameView {

    public MorphologyMathematiqueView(SingleFrameApplication app) {
        super(app);

        initComponents();
        a = 1;
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = MorphologyMathematiqueApp.getApplication().getMainFrame();
            aboutBox = new MorphologyMathematiqueAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        MorphologyMathematiqueApp.getApplication().show(aboutBox);
    }

    @Action
    public void showOuvirImage() {
        if (ouvrirFichier == null) {
            JFrame mainFrame = MorphologyMathematiqueApp.getApplication().getMainFrame();
            ouvrirFichier = new MorphologyMathematiqueOpenImageBox(mainFrame);
            ouvrirFichier.setLocationRelativeTo(mainFrame);
        }

        MorphologyMathematiqueApp.getApplication().show(ouvrirFichier);
    }

    @Action
    public void showManuel() {
        if (manuel == null) {
            JFrame mainFrame = MorphologyMathematiqueApp.getApplication().getMainFrame();
            manuel = new MorphologyMathematiqueManuel(mainFrame);
            manuel.setLocationRelativeTo(mainFrame);
        }

        MorphologyMathematiqueApp.getApplication().show(manuel);
    }

    @Action
    public void ouverture() {
        if (MorphologyMathematiqueApp.getApplication().imageCourante != null) {
            traitee.setImage(Operations.open(MorphologyMathematiqueApp.getApplication().imageCourante, a), originale.getWidth(), originale.getHeight());
            traitee.repaint();
        }
    }

    @Action
    public void fermeture() {
        if (MorphologyMathematiqueApp.getApplication().imageCourante != null) {
            traitee.setImage(Operations.close(MorphologyMathematiqueApp.getApplication().imageCourante, a), originale.getWidth(), originale.getHeight());
            traitee.repaint();
        }
    }

    @Action
    public void echangerImage() {
        if (traitee.getImage() != null) {
            MorphologyMathematiqueApp.getApplication().imageCourante = Operations.convertToGrayscale(traitee.getImage());
            traitee.removeImage();
            originale.setImage(MorphologyMathematiqueApp.getApplication().imageCourante, originale.getWidth(), originale.getHeight());
            traitee.repaint();
            originale.repaint();
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

        mainPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        originale = new ensiie.morphologieMathematique.model.ZoneImage();
        traitee = new ensiie.morphologieMathematique.model.ZoneImage();
        jLabel2 = new javax.swing.JLabel();
        choixA = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        ouvertureBouton = new javax.swing.JButton();
        fermeureBouton = new javax.swing.JButton();
        changeImage = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        ouvertureFichier = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        choixFonction = new javax.swing.JMenu();
        ouvertureFonction = new javax.swing.JMenuItem();
        fermetureFonction = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        MenuItem1 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setMaximumSize(new java.awt.Dimension(500, 400));
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(1000, 550));

        jSeparator1.setName("jSeparator1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(ensiie.morphologieMathematique.vues.MorphologyMathematiqueApp.class).getContext().getResourceMap(MorphologyMathematiqueView.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        originale.setName("originale"); // NOI18N
        originale.setPreferredSize(new java.awt.Dimension(300, 300));
        originale.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                originaleMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout originaleLayout = new javax.swing.GroupLayout(originale);
        originale.setLayout(originaleLayout);
        originaleLayout.setHorizontalGroup(
            originaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        originaleLayout.setVerticalGroup(
            originaleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        traitee.setName("traitee"); // NOI18N
        traitee.setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout traiteeLayout = new javax.swing.GroupLayout(traitee);
        traitee.setLayout(traiteeLayout);
        traiteeLayout.setHorizontalGroup(
            traiteeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        traiteeLayout.setVerticalGroup(
            traiteeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        choixA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
        choixA.setName("choixA"); // NOI18N
        choixA.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                choixAItemStateChanged(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(ensiie.morphologieMathematique.vues.MorphologyMathematiqueApp.class).getContext().getActionMap(MorphologyMathematiqueView.class, this);
        ouvertureBouton.setAction(actionMap.get("ouverture")); // NOI18N
        ouvertureBouton.setText(resourceMap.getString("ouvertureBouton.text")); // NOI18N
        ouvertureBouton.setName("ouvertureBouton"); // NOI18N

        fermeureBouton.setAction(actionMap.get("fermeture")); // NOI18N
        fermeureBouton.setText(resourceMap.getString("fermeureBouton.text")); // NOI18N
        fermeureBouton.setName("fermeureBouton"); // NOI18N

        changeImage.setAction(actionMap.get("echangerImage")); // NOI18N
        changeImage.setText(resourceMap.getString("changeImage.text")); // NOI18N
        changeImage.setName("changeImage"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(originale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(214, 214, 214)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ouvertureBouton)
                                    .addComponent(fermeureBouton)
                                    .addComponent(jLabel1))))
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(27, 27, 27))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(changeImage)
                                        .addGap(64, 64, 64)))
                                .addComponent(traitee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(908, 908, 908)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(301, 301, 301)
                                .addComponent(jLabel2))))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(651, 651, 651)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(choixA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap(21, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(traitee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(originale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)))
                        .addGap(37, 37, 37)))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(choixA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ouvertureBouton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fermeureBouton)))
                .addGap(532, 532, 532))
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(177, 177, 177)
                .addComponent(changeImage)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(735, Short.MAX_VALUE))
        );

        jLabel3.getAccessibleContext().setAccessibleName(resourceMap.getString("jLabel3.AccessibleContext.accessibleName")); // NOI18N

        menuBar.setName("menuBar"); // NOI18N
        menuBar.setPreferredSize(new java.awt.Dimension(122, 21));
        menuBar.setRequestFocusEnabled(false);

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        ouvertureFichier.setAction(actionMap.get("showOuvirImage")); // NOI18N
        ouvertureFichier.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        ouvertureFichier.setText(resourceMap.getString("ouvertureFichier.text")); // NOI18N
        ouvertureFichier.setName("ouvertureFichier"); // NOI18N
        fileMenu.add(ouvertureFichier);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        choixFonction.setText(resourceMap.getString("choixFonction.text")); // NOI18N
        choixFonction.setName("choixFonction"); // NOI18N

        ouvertureFonction.setAction(actionMap.get("ouverture")); // NOI18N
        ouvertureFonction.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK));
        ouvertureFonction.setText(resourceMap.getString("ouvertureFonction.text")); // NOI18N
        ouvertureFonction.setName("ouvertureFonction"); // NOI18N
        choixFonction.add(ouvertureFonction);

        fermetureFonction.setAction(actionMap.get("fermeture")); // NOI18N
        fermetureFonction.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        fermetureFonction.setText(resourceMap.getString("fermetureFonction.text")); // NOI18N
        fermetureFonction.setName("fermetureFonction"); // NOI18N
        choixFonction.add(fermetureFonction);

        menuBar.add(choixFonction);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        MenuItem1.setAction(actionMap.get("showManuel")); // NOI18N
        MenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        MenuItem1.setText(resourceMap.getString("MenuItem1.text")); // NOI18N
        MenuItem1.setName("MenuItem1"); // NOI18N
        helpMenu.add(MenuItem1);

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1820, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1650, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    //Modifier le a dans l'application quand l'utilisateur 
    private void choixAItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_choixAItemStateChanged
        if (Integer.parseInt(evt.getItem().toString()) != a) {
            a = Integer.parseInt(evt.getItem().toString());
        }
    }//GEN-LAST:event_choixAItemStateChanged

    private void originaleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_originaleMouseClicked
        showOuvirImage();
    }//GEN-LAST:event_originaleMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuItem1;
    private javax.swing.JButton changeImage;
    private javax.swing.JComboBox choixA;
    private javax.swing.JMenu choixFonction;
    private javax.swing.JMenuItem fermetureFonction;
    private javax.swing.JButton fermeureBouton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private ensiie.morphologieMathematique.model.ZoneImage originale;
    private javax.swing.JButton ouvertureBouton;
    private javax.swing.JMenuItem ouvertureFichier;
    private javax.swing.JMenuItem ouvertureFonction;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private ensiie.morphologieMathematique.model.ZoneImage traitee;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private int a;
    private JDialog aboutBox;
    private JDialog ouvrirFichier;
    private JDialog manuel;

    public void ajoutImageOriginal(File currentFile) {
        try {
            MorphologyMathematiqueApp.getApplication().imageCourante = ImageIO.read(currentFile);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        originale.setImage(MorphologyMathematiqueApp.getApplication().imageCourante, originale.getWidth(), originale.getHeight());
        traitee.removeImage();
        //traitee.repaint();
        originale.repaint();
    }
}
