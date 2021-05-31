package Ajedrez;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Tablero extends Rectangle{
    public static boolean negro_blanco = true;
    public boolean activa = false;
    boolean casillaOcupada;
    Integer blanca;
    Image imagen;
    Integer id;
    
    public Tablero(int x, int y, int width, int height, Image imagen, Integer id, boolean casillaOcupada, Integer blanca){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagen = imagen;
        this.id = id;
        this.casillaOcupada = casillaOcupada;
        this.blanca = blanca;
    }
    
    public void paint(Graphics g, Applet app){
        if (negro_blanco) {
            if (activa) {
                g.setColor(Color.GREEN);
                g.fillRect(x, y, width, height);
            }else{
                g.setColor(Color.BLACK);
                g.fillRect(x, y, width, height);
            }
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            g.drawImage(imagen, x, y,width, height, app); 
        }else{
            if (activa) {
                g.setColor(Color.GREEN);
                g.fillRect(x, y, width, height);
            }else{
                g.setColor(Color.WHITE);
                g.fillRect(x, y, width, height);
            }
            g.setColor(Color.BLACK);
            g.drawRect(x, y, width, height);
            g.drawImage(imagen, x, y,width, height, app);
        }
    }
}
