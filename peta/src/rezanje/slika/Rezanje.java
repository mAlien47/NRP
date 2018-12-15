package rezanje.slika;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rezanje {

	public static void main(String[] args) {
		
		try {
			File file = new File(".\\imgs\\cetvrtinka\\cetvrtinka.JPG");
			FileInputStream fis = new FileInputStream(file);
			// Pozivanje metode za čitanje slike
			BufferedImage image = ImageIO.read(fis);
			// Definiranje broja redaka i stupaca slike
			// 1 redak i 5 stupaca za pjesmicu od 5 nota
			int rows = 1;
			int cols = 4;
			int chunks = rows * cols;
			// određivanje veličine jednog dijela
			int chunkWidth = image.getWidth() / cols;
			int chunkHeight = image.getHeight() / rows;
			// Definiranje polja koje sadrži dijelove slika
			int count = 0;
			BufferedImage imgs[] = new BufferedImage[chunks];
			for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
			// Inicijalizacija polja s dijelovima slike
			imgs[count] = new BufferedImage(chunkWidth, chunkHeight,
			image.getType());
			// "Crtanje" pojedinih dijelova slike
			Graphics2D gr = imgs[count++].createGraphics();
			gr.drawImage(image, 0, 0, chunkWidth, chunkHeight,
			chunkWidth * y, chunkHeight * x, chunkWidth * y +
			chunkWidth, chunkHeight * x + chunkHeight, null);
			gr.dispose();
			}
			}
			System.out.println("Rezanje slike je završeno.");
			// Zapisivanje dijelova slika u zasebne datoteke
			for (int i = 0; i < imgs.length; i++) {
			ImageIO.write(imgs[i], "jpg",
			new File("./imgs/cetvrtinka/cetvrtinka" + i + ".jpg"));
			}
			System.out.println("Kreiranje slika je završeno.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
