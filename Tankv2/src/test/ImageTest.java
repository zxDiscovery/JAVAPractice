package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

class ImageTest {

	@Test
	void test() {
		try {
			BufferedImage image1 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("image/tankL.jpg"));
			assertNotNull(image1);
			BufferedImage image2 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("image/tankR.jpg"));
			assertNotNull(image2);
			BufferedImage image3 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("image/tankU.jpg"));
			assertNotNull(image3);
			BufferedImage image4 = ImageIO.read(ImageTest.class.getClassLoader().getResourceAsStream("image/tankD.jpg"));
			assertNotNull(image4);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
