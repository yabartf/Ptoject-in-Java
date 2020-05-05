package unittests;

import org.junit.jupiter.api.Test;
import renderer.ImageWriter;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {
    @Test
    void createImage(){
        ImageWriter imageTest = new ImageWriter("hello image",1600,1000,800,500);
        //imageTest.writeToImage();
        for (int i = 0; i < 800; i++ ){
            for (int j = 0; j < 500; j++ ){
                    imageTest.writePixel(i, j, (i % 50 == 0 && i > 0 || j % 50 == 0 && j > 0)? new Color(255, 255,255):new Color(0, 0,0));
            }
        imageTest.writeToImage();
        }
    }

    @Test
    void getWidth() {
    }

    @Test
    void getHeight() {
    }

    @Test
    void getNy() {
    }

    @Test
    void getNx() {
    }

    @Test
    void writeToImage() {
    }

    @Test
    void writePixel() {
    }
}