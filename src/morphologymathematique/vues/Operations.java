/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package morphologymathematique.vues;

import java.awt.Color;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBufferInt;

public class Operations {

    /**
     * Fonction blur
     * @param t
     * @param a le diamètre du carré constituant le noyau
     * @return
     */
    private static double blur(int t, int a) {
        return Math.min(1, Math.max(0, 1 - (t / a)));
    }

    /**
     * Fonction lamming : calcule la norme de Lamming
     */
    private static int lamming(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    private static double appart(double x) {
        double res = (Math.sin(Math.PI * x) + (Math.sin(2 * Math.PI * x) / 2) + (Math.sin(3 * Math.PI * x) / 3) - 0.4);
        return res;
    }

    /**
     * Fonction convertToGrayscale : convertit un objet BufferedImage en niveau de gris
     */
    public static BufferedImage convertToGrayscale(BufferedImage source) {
        BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        return op.filter(source, null);
    }

    /**
     * Fonction mask_erode : calcule la plus petite valeur de gris parmi les voisins du pixel(x, y)
     * @param x l'abscisse du pixel
     * @param y l'ordonnée du pixel
     * @param img l'image en niveau de gris
     * @param a le rayon de l'élément structurant
     * @return le niveau de gris minimal du pixel et de ses voisins
     */
    private static int mask_erode(int x, int y, BufferedImage img, int a) {
        // Variables temporaires qui stocke les valeurs de gris calculés dans le parcours du noyau
        double tmpMuGL, tmpFGL;
        // Variable temporaire qui stocke la valeur minimal des niveaux de gris de l'ensemble flou des voisins
        double tmpMinGL;
        // Variable stockant le maximum de l'ensemble des minimums des niveaux de gris de l'ensemble flou des voisins
        int minGL = 0;
        Color tempColor;

        for (int i = -a; i < a; i++) {
            for (int j = -a; j < a; j++) {
                boolean imgEdge = false;

                // On vérifie que l'on n'est pas en dehors de l'image
                if (x + j < 0 || x + j > img.getWidth()) {
                    imgEdge = true;
                }
                if (y + i < 0 || y + i > img.getHeight()) {
                    imgEdge = true;
                }

                if (!imgEdge) {
                    // Pour une image en niveau de gris, les valeurs renvoyées par getRBG pour le rouge, le bleu et le vert sont égales
                    // On ne prend donc qu'une seule des valeurs renvoyées
                    // On récupère le niveau de gris du point
                    //tmpMuGL = appart((img.getRGB(x + j, y + i) >> 16) & 0xFF);
                    tempColor = new Color(img.getRGB((x - 1) + j, (y - 1) + i));
                    double test = (double) tempColor.getBlue() / 255;
                    tmpMuGL = appart(test);
                    // Et la valeur via la fonction d'appartenance
                    tmpFGL = blur(lamming(new Point(x + j, y + i), new Point(x, y)), a);
                    // Et on fait le minimum des deux
                    tmpMinGL = Math.min(tmpMuGL, tmpFGL);
                    if (tmpMinGL > minGL) {
                        minGL = (int) (255 * tmpMinGL);
                    }
                }
            }
        }
        return minGL;
    }

    /**
     * Fonction erode : effectue l'érosion sur l'image img
     *                  On copie l'image, l'image d'origine donnant les valeurs permettant d'éroder
     *                  la nouvelle image enregistrant les modifications
     * @param img l'image à éroder
     * @param a le rayon de l'élément structurant
     * @return l'image érodée
     */
    private static BufferedImage erode(BufferedImage img, int a) {
        /*BufferedImage oldImgGray = convertToGrayscale(img);
        BufferedImage newImgGray = convertToGrayscale(img);*/
        BufferedImage newImgGray = img;
        int newGrayLevel = 0;
        int newRGB = 0;
        // On parcourt l'image
        for (int i = img.getHeight(); i >= 0; i--) {
            for (int j = img.getWidth(); j >= 0; j--) {
                // On récupère l'ancienne valeur de gris
                newGrayLevel = mask_erode(j, i, img, a);
                // On la transforme en couleur RGB, afin de pouvoir l'utiliser via setRGB
                newRGB = (0xFF << 24) | ((newGrayLevel & 0xFF) << 16) | ((newGrayLevel & 0xFF) << 8) | ((newGrayLevel & 0xFF));
                System.out.println(newRGB);
                //newImgGray.setRGB(j, i, newRGB);
            }
        }
        return newImgGray;
    }

    /**
     * Fonction mask_dilate : calcule la plus grande valeur de gris parmi les voisins du pixel(x, y)
     * @param x : l'abscisse du pixel
     * @param y : l'ordonnée du pixel
     * @param img : l'image en niveau de gris
     * @param a : le rayon de l'élément structurant
     * @return : le niveau de gris maximal du pixel et de ses voisins
     */
    private static int mask_dilate(int x, int y, BufferedImage img, int a) {
        // Variables temporaires qui stocke les valeurs de gris calculés dans le parcours du noyau
        double tmpMuGL, tmpFGL;
        // Variable temporaire qui stocke la valeur minimal des niveaux de gris de l'ensemble flou des voisins
        double tmpMaxGL;
        // Variable stockant le maximum de l'ensemble des minimums des niveaux de gris de l'ensemble flou des voisins
        int maxGL = 0;
        Color tempColor;

        for (int i = -a; i < a; i++) {
            for (int j = -a; j < a; j++) {
                boolean imgEdge = false;

                // On vérifie que l'on n'est pas en dehors de l'image
                if (x + j < 0 || x + j >= img.getWidth()) {
                    imgEdge = true;
                }
                if (y + i < 0 || y + i >= img.getHeight()) {
                    imgEdge = true;
                }

                if (!imgEdge) {
                    // Pour une image en niveau de gris, les valeurs renvoyées par getRBG pour le rouge, le bleu et le vert sont égales
                    // On ne prend donc qu'une seule des valeurs renvoyées
                    // On récupère le niveau de gris du point
                    tempColor = new Color(img.getRGB(x + j, y + i));
                    tmpMuGL = appart(tempColor.getBlue() / 255);
                    // Et la valeur via la fonction d'appartenance
                    tmpFGL = blur(lamming(new Point(x + j, y + i), new Point(x, y)), a);
                    // Et on fait le minimum des deux
                    tmpMaxGL = Math.max(tmpMuGL, tmpFGL);
                    if (tmpMaxGL < maxGL) {
                        maxGL = (int) (255 * tmpMaxGL);
                    }
                }
            }
        }
        return maxGL;
    }

    /**
     * Fonction dilate : effectue la dilataion sur l'image img
     *                  On copie l'image, l'image d'origine donnant les valeurs permettant d'éroder
     *                  la nouvelle image enregistrant les modifications
     * @param img l'image à éroder
     * @param a le rayon de l'élément structurant
     * @return l'image érodée
     */
    private static BufferedImage dilate(BufferedImage img, int a) {
        // BufferedImage imgGray = convertToGrayscale(img);
        BufferedImage newImgGray = img;

        for (int i = img.getHeight(); i >= 0; i--) {
            for (int j = img.getWidth(); j >= 0; j--) {
                // On récupère la nouvel valeur de gris
                int newGrayLevel = mask_dilate(i, j, img, a);
                // On la transforme en couleur RGB, afin de pouvoir l'utiliser via setRGB
                int newRGB = (0xFF << 24) | ((newGrayLevel & 0xFF) << 16) | ((newGrayLevel & 0xFF) << 8) | ((newGrayLevel & 0xFF));
                newImgGray.setRGB(i, j, newRGB);
            }
        }
        return newImgGray;
    }

    /**
     * Fonction invers : inverse les niveaux de gris sur l'image
     * @param img l'image en niveau de gris à inverser
     * @return l'image transformée
     */
    private static BufferedImage invers(BufferedImage img) {
        BufferedImage tmpImg = img;
        for (int i = tmpImg.getHeight() - 1; i >= 0; i--) {
            for (int j = tmpImg.getWidth() - 1; j >= 0; j--) {
                // On récupère le niveau de gris du pixel
                int tmpGrayLevel = (tmpImg.getRGB(i, j) >> 16) & 0xFF;
                tmpGrayLevel = 255 - tmpGrayLevel;
                // On la transforme en couleur RGB, afin de pouvoir l'utiliser via setRGB
                int newRGB = (0xFF << 24) | ((tmpGrayLevel & 0xFF) << 16) | ((tmpGrayLevel & 0xFF) << 8) | ((tmpGrayLevel & 0xFF));
                tmpImg.setRGB(i, j, newRGB);
            }
        }

        return tmpImg;
    }

    public static BufferedImage open(BufferedImage img, int a, int nbOpen) {
        BufferedImage tmpImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        copySrcIntoDstAt(img, tmpImg, img.getWidth(), img.getHeight());

        for (int i = 0; i < nbOpen; i++) {
            tmpImg = dilate(erode(tmpImg, a), a);
        }
        return tmpImg;
    }

    public static BufferedImage close(BufferedImage img, int a, int nbClose) {
        BufferedImage tmpImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        copySrcIntoDstAt(img, tmpImg, img.getWidth(), img.getHeight());

        for (int i = 0; i < nbClose; i++) {
            tmpImg = erode(dilate(tmpImg, a), a);
        }
        return tmpImg;
    }

    private static void copySrcIntoDstAt(final BufferedImage src, final BufferedImage dst, final int dx, final int dy) {
        int[] srcbuf = ((DataBufferInt) src.getRaster().getDataBuffer()).getData();
        int[] dstbuf = ((DataBufferInt) dst.getRaster().getDataBuffer()).getData();
        int width = src.getWidth();
        int height = src.getHeight();
        int dstoffs = dx + dy * dst.getWidth();
        int srcoffs = 0;
        for (int y = 0; y < height; y++, dstoffs += dst.getWidth(), srcoffs += width) {
            System.arraycopy(srcbuf, srcoffs, dstbuf, dstoffs, width);
        }
    }
    /**
     * Fonction eltStruct : définition non-floue de l'élément structurant (noyau)
     * @param a le diamètre du carré constituant le noyau
     * @return
     */
    /* private static boolean[][] eltStruct(int a) {
    int eltSize = a * 2 + 1;
    boolean[][] elt = new boolean[eltSize][eltSize];
    
    for (int i = 0; i < eltSize; i++) {
    for (int j = 0; j < eltSize; j++) {
    elt[i][j] = true;
    }
    }
    return elt;
    } */
    /**
     * Fonction dilate : effectue la dilatation sur l'image img
     *                  La dilatation n'est que l'opposé de l'érosion, on peut donc utiliser la fonction erode, en inversant les niveaux de gris de l'image
     * @param eltStruct l'élément structurant
     * @param img l'image à éroder
     * @param a le rayon de l'élément structurant
     * @return l'image érodée
     */
    /* private static BufferedImage dilate(boolean[][] eltStruct, BufferedImage img) {
    // On inverse l'image pour changer les niveaux de gris
    BufferedImage tmpImg = invers(img);
    // On applique une érosion, donc chaque pixel a pour couleur le niveau de gris minimal de ces voisins directs
    tmpImg = erode(eltStruct, tmpImg);
    // Et on renvoit l'image inversée à nouveau
    return tmpImg = invers(tmpImg);
    } */
}
