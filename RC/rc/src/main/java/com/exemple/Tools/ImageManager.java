package main.java.com.exemple.Tools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Classe qui gère toutes les images du jeu
 * Pour ajouter une nouvelle image :
 *      ajouter dans le constructeur de ImageManager votre image en faisant addToManager(typeImage, pathImage)
 *      avec typeImage le type de l'image (ex: Fantome pour un fantome) et pathImage le chemin vers l'image (on part du module resources)
 * Pour trouver le type d'image à afficher, il suffit d'appeler la fonction getType() sur une case ou un personnage
 */
public class ImageManager {

    /**
     * Instance de ImageManager
     */
    private static ImageManager instance = new ImageManager();
    /**
     * Map regroupant les types de cases/persos avec leur path
     */
    private Map<String, String> imagePaths = new HashMap<>();
    /**
     * Map regroupant le path des cases/persos avec leur image
     */
    private Map<String, BufferedImage> imageCache = new HashMap<>();


    /**
     * Constructeur privé de ImageManager
     */
    private ImageManager() {
        //Pour le menu
        addToManager("MenuBackground", "/sunset_background.jpg");
        addToManager("MenuIcon", "/icon.png");
        addToManager("MenuPlay","/play_s.png");
        addToManager("MenuStop","/stop_s.png");
        addToManager("MenuDebug","/debug_s.png");
    }

    private void addToManager(String type, String path){
        imagePaths.put(type, path);
        loadImage(path);
    }

    /**
     * Recupere l'instance de ImageManager
     * @return l'instance de ImageManager
     */
    public static synchronized ImageManager getInstance() {
        return instance;
    }

    /**
     * Procédure privée qui charge une image avec son path
     * @param imagePath
     */
    private void loadImage(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            imageCache.put(imagePath, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Getter public permettant de recuperer une image en fonction du type
     * @param imageType
     * @return
     */
    public BufferedImage getImage(String imageType) {
        //On recupere d'abord le path puis l'image
        String imagePath = imagePaths.getOrDefault(imageType, imagePaths.get("Default"));
        return imageCache.get(imagePath);
    }
}
