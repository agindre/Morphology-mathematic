package morphologymathematique.vues;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author alex
 */
public class ZoneImage extends JPanel {

    private BufferedImage imageAssociee;

    @Override
    public void paint(Graphics g) {
        g.drawImage(imageAssociee, 0, 0, this);
    }

    public void setImage(BufferedImage currentImage, int width, int height) {
        imageAssociee = currentImage;
        int type = imageAssociee.getType() == 0 ? BufferedImage.TYPE_USHORT_GRAY : imageAssociee.getType();
        imageAssociee = resizeImage(imageAssociee, type, width, height);
    }

    public BufferedImage getImage() {
        return imageAssociee;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }

    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type, int width, int height) {

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;

    }
}