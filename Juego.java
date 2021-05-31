package Ajedrez;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

public class Juego extends Applet{
    public static final String DIRECCION = "Ajedrez/piezas/";
    public static final String[] TIPOS_PIEZAS = {"peon.png","torre.png","alfil.png","caballo.png","rey.png","reina.png","peonBlanco.png","torreBlanca.png","alfilBlanco.png","caballoBlanco.png","reyBlanco.png","reinaBlanca.png"};
    public static final int FILAS_COLUMNAS = 8;
    public static final int MEDIDA_CASILLA = 50;
    
    boolean casillaSeleccionada;
    Graphics hidden;
    Image imagen;
    Image[] images;
    Boolean empezarJuego;
    Button boton;
    Tablero[][] tablero;
    Integer indi1, indi2;
    
    public void init() {
        imagen = this.createImage(800, 900);
        hidden = imagen.getGraphics();

        Panel panel = new Panel();
        boton = new Button("Empezar");
        panel.add(boton);
        this.setLayout(new BorderLayout());
        this.add("East", panel);
        
        indi1 = 0;
        indi2 = 0;
        casillaSeleccionada = false;
        images = new Image[TIPOS_PIEZAS.length];
        for (int i = 0; i < images.length; i++) {
            images[i] = getImage(getCodeBase(), DIRECCION + TIPOS_PIEZAS[i]);
        }
        
        tablero = new Tablero[FILAS_COLUMNAS][FILAS_COLUMNAS];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (j == 1) {
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, images[0], 0,true, 0);
                }else if(j == 6){
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, images[6], 6,true, 1);
                }else if(i == 0 && j == 0 || i == 7 && j == 0){
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, images[1], 1,true, 0);
                }else if(i == 0 && j == 7 || i == 7 && j == 7){
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, images[7], 7,true, 1);
                }else if(i == 1 && j == 0 || i == 6 && j == 0){
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, images[2], 2,true, 0);
                }else if(i == 1 && j == 7 || i == 6 && j == 7){
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, images[8], 8,true, 1);
                }else if(i == 2 && j == 0 || i == 5 && j == 0){
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, images[3], 3,true, 0);
                }else if(i == 2 && j == 7 || i == 5 && j == 7){
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, images[9], 9,true, 1);
                }else if(i == 3 && j == 0){
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, images[5], 5,true, 0);
                }else if(i == 4 && j == 0){
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, images[4], 4,true, 0);
                }else if(i == 4 && j == 7){
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, images[10], 10,true, 1);
                }else if(i == 3 && j == 7){
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, images[11], 11,true, 1);
                }
                else{
                    tablero[i][j] = new Tablero(i * MEDIDA_CASILLA, j * MEDIDA_CASILLA, MEDIDA_CASILLA, MEDIDA_CASILLA, null, null, false, null);
                }
            }
        }
        
        empezarJuego = false;

    }
    
    public void paint(Graphics g) {
        hidden.setColor(Color.white);
        hidden.fillRect(0, 0, 800, 900);
        
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if ((j+i)%2==0) {
                    tablero[i][j].negro_blanco = false;
                    tablero[i][j].paint(hidden, this);
                }else{
                    tablero[i][j].negro_blanco = true;
                    tablero[i][j].paint(hidden, this);
                }
            }
        }
        
        g.drawImage(imagen, 0, 0, this);
        resize(1000, 1000);
    }
    
    public void update(Graphics g) {
        paint(g);
    }
    
    public boolean mouseDown(Event ev, int x, int y) {
        if(casillaSeleccionada) {
            casillaSeleccionada = false;
            tablero[indi1][indi2].activa = false;
            comprobar(tablero, indi1, indi2, x, y);
        }
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (casillaSeleccionada == false && tablero[i][j].contains(x, y) && tablero[i][j].casillaOcupada) {
                    tablero[i][j].activa = true;
                    casillaSeleccionada = true;
                    indi1 = i;
                    indi2 = j;
                }
            }
        }
        
        
        
        repaint();
        return true;
    }
    
    public void comprobar(Tablero[][] tablero, Integer x, Integer y, int posX, int posY){
        switch (tablero[x][y].id) {
            case 6:
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero[i].length; j++) {
                        if (tablero[x][y-1] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                            meterFicha(tablero[i][j], tablero[x][y]);
                        }
                        else if (x >= 1 && x <= 6 && (tablero[x-1][y-1] == tablero[i][j] || tablero[x+1][y-1] == tablero[i][j]) && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0 && tablero[i][j].blanca != null) {
                            meterFicha(tablero[i][j], tablero[x][y]);
                        }
                        else if(x == 0 && tablero[x+1][y-1] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0 && tablero[i][j].blanca != null){
                            meterFicha(tablero[i][j], tablero[x][y]);
                        }
                        else if(x == 7 && tablero[x-1][y-1] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0 && tablero[i][j].blanca != null){
                            meterFicha(tablero[i][j], tablero[x][y]);
                        }
                    }
                }
                break;
            case 0:
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero[i].length; j++) {
                        if (y < 7 && tablero[x][y+1] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                            meterFicha(tablero[i][j], tablero[x][y]);
                        }
                        else if (y < 7 && x >= 1 && x <= 6 && (tablero[x-1][y+1] == tablero[i][j] || tablero[x+1][y+1] == tablero[i][j]) && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1 && tablero[i][j].blanca != null) {
                            meterFicha(tablero[i][j], tablero[x][y]);
                        }
                        else if(y < 7 && x == 0 && tablero[x+1][y+1] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1 && tablero[i][j].blanca != null){
                            meterFicha(tablero[i][j], tablero[x][y]);
                        }
                        else if(y < 7 && x == 7 && tablero[x-1][y+1] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1 && tablero[i][j].blanca != null){
                            meterFicha(tablero[i][j], tablero[x][y]);
                        }
                    }
                }
                break;
            case 7:
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero[i].length; j++) {
                        for (int k = 1; k <= y; k++) {
                            if (tablero[x][y-k].blanca == null) {
                                if (tablero[x][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = y;
                            }
                        }
                        for (int k = 1; k+y <= 7; k++) {
                            if (tablero[x][y+k].blanca == null) {
                                if (tablero[x][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                        for (int k = 1; k <= x; k++) {
                            if (tablero[x-k][y].blanca == null) {
                                if (tablero[x-k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x-k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = x;
                            }
                        }
                        for (int k = 1; k+x <= 7; k++) {
                            if (tablero[x+k][y].blanca == null) {
                                if (tablero[x+k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x+k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                    }
                }
                break;
            case 1:
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero[i].length; j++) {
                        for (int k = 1; k <= y; k++) {
                            if (tablero[x][y-k].blanca == null) {
                                if (tablero[x][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = y;
                            }
                        }
                        for (int k = 1; k+y <= 7; k++) {
                            if (tablero[x][y+k].blanca == null) {
                                if (tablero[x][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                        for (int k = 1; k <= x; k++) {
                            if (tablero[x-k][y].blanca == null) {
                                if (tablero[x-k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x-k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = x;
                            }
                        }
                        for (int k = 1; k+x <= 7; k++) {
                            if (tablero[x+k][y].blanca == null) {
                                if (tablero[x+k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x+k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                    }
                }
                break;
            case 8:
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero[i].length; j++) {
                        for (int k = 1; k <= y && k <=x; k++) {
                            if (tablero[x-k][y-k].blanca == null) {
                                if (tablero[x-k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x-k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = y;
                            }
                        }
                        for (int k = 1; k+y <= 7 && k+x <=7; k++) {
                            if (tablero[x+k][y+k].blanca == null) {
                                if (tablero[x+k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x+k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                        for (int k = 1; k <= x && k+y <= 7; k++) {
                            if (tablero[x-k][y+k].blanca == null) {
                                if (tablero[x-k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x-k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = x;
                            }
                        }
                        for (int k = 1; k+x <= 7 && k <= y; k++) {
                            if (tablero[x+k][y-k].blanca == null) {
                                if (tablero[x+k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x+k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero[i].length; j++) {
                        for (int k = 1; k <= y && k <=x; k++) {
                            if (tablero[x-k][y-k].blanca == null) {
                                if (tablero[x-k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x-k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = y;
                            }
                        }
                        for (int k = 1; k+y <= 7 && k+x <=7; k++) {
                            if (tablero[x+k][y+k].blanca == null) {
                                if (tablero[x+k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x+k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                        for (int k = 1; k <= x && k+y <= 7; k++) {
                            if (tablero[x-k][y+k].blanca == null) {
                                if (tablero[x-k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x-k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = x;
                            }
                        }
                        for (int k = 1; k+x <= 7 && k <= y; k++) {
                            if (tablero[x+k][y-k].blanca == null) {
                                if (tablero[x+k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x+k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                    }
                }
                break;
            case 11:
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero[i].length; j++) {
                        for (int k = 1; k <= y; k++) {
                            if (tablero[x][y-k].blanca == null) {
                                if (tablero[x][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = y;
                            }
                        }
                        for (int k = 1; k+y <= 7; k++) {
                            if (tablero[x][y+k].blanca == null) {
                                if (tablero[x][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                        for (int k = 1; k <= x; k++) {
                            if (tablero[x-k][y].blanca == null) {
                                if (tablero[x-k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x-k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = x;
                            }
                        }
                        for (int k = 1; k+x <= 7; k++) {
                            if (tablero[x+k][y].blanca == null) {
                                if (tablero[x+k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x+k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                        for (int k = 1; k <= y && k <=x; k++) {
                            if (tablero[x-k][y-k].blanca == null) {
                                if (tablero[x-k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x-k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = y;
                            }
                        }
                        for (int k = 1; k+y <= 7 && k+x <=7; k++) {
                            if (tablero[x+k][y+k].blanca == null) {
                                if (tablero[x+k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x+k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                        for (int k = 1; k <= x && k+y <= 7; k++) {
                            if (tablero[x-k][y+k].blanca == null) {
                                if (tablero[x-k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x-k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = x;
                            }
                        }
                        for (int k = 1; k+x <= 7 && k <= y; k++) {
                            if (tablero[x+k][y-k].blanca == null) {
                                if (tablero[x+k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x+k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 0){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                    }
                }
                break;
            case 5:
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero[i].length; j++) {
                        for (int k = 1; k <= y; k++) {
                            if (tablero[x][y-k].blanca == null) {
                                if (tablero[x][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = y;
                            }
                        }
                        for (int k = 1; k+y <= 7; k++) {
                            if (tablero[x][y+k].blanca == null) {
                                if (tablero[x][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                        for (int k = 1; k <= x; k++) {
                            if (tablero[x-k][y].blanca == null) {
                                if (tablero[x-k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x-k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = x;
                            }
                        }
                        for (int k = 1; k+x <= 7; k++) {
                            if (tablero[x+k][y].blanca == null) {
                                if (tablero[x+k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x+k][y] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                        for (int k = 1; k <= y && k <=x; k++) {
                            if (tablero[x-k][y-k].blanca == null) {
                                if (tablero[x-k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x-k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = y;
                            }
                        }
                        for (int k = 1; k+y <= 7 && k+x <=7; k++) {
                            if (tablero[x+k][y+k].blanca == null) {
                                if (tablero[x+k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x+k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                        for (int k = 1; k <= x && k+y <= 7; k++) {
                            if (tablero[x-k][y+k].blanca == null) {
                                if (tablero[x-k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x-k][y+k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = x;
                            }
                        }
                        for (int k = 1; k+x <= 7 && k <= y; k++) {
                            if (tablero[x+k][y-k].blanca == null) {
                                if (tablero[x+k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == null) {
                                    meterFicha(tablero[i][j], tablero[x][y]);
                                }
                            }else if(tablero[x+k][y-k] == tablero[i][j] && tablero[i][j].contains(posX, posY) && tablero[i][j].blanca == 1){
                                meterFicha(tablero[i][j], tablero[x][y]);
                            }else{
                                k = 7;
                            }
                        }
                    }
                }
                break;
            case 10:
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero[i].length; j++) {
                        if (tablero[i][j].contains(posX, posY)) {
                            if (y > 0 && tablero[x][y-1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (y < 7 && tablero[x][y+1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (x > 0 && tablero[x-1][y] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (x < 7 && tablero[x+1][y] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if ((x < 7 && y < 7) && tablero[x+1][y+1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if ((x > 0 && y > 0) && tablero[x-1][y-1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if ((x < 7 && y > 0) && tablero[x+1][y-1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if ((x > 0 && y < 7) && tablero[x-1][y+1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }
                        }
                    }
                }
                break;
            case 4:
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero[i].length; j++) {
                        if (tablero[i][j].contains(posX, posY)) {
                            if (y > 0 && tablero[x][y-1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 1)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (y < 7 && tablero[x][y+1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 1)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (x > 0 && tablero[x-1][y] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 1)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (x < 7 && tablero[x+1][y] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 1)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if ((x < 7 && y < 7) && tablero[x+1][y+1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 1)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if ((x > 0 && y > 0) && tablero[x-1][y-1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 1)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if ((x < 7 && y > 0) && tablero[x+1][y-1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 1)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if ((x > 0 && y < 7) && tablero[x-1][y+1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 1)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }
                        }
                    }
                }
                break;
            case 9:
                for (int i = 0; i < tablero.length; i++) {
                    for (int j = 0; j < tablero[i].length; j++) {
                        if (tablero[i][j].contains(posX, posY)) {
                            if (y > 1 && x < 7 && tablero[x+1][y-2] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (y > 1 && x > 0 && tablero[x-1][y-2] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (y < 6 && x < 7 && tablero[x+1][y+2] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (y < 6 && x > 0 && tablero[x-1][y+2] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (y < 7 && x > 1 && tablero[x-2][y+1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (y > 0 && x > 1 && tablero[x-2][y-1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (y < 7 && x < 6 && tablero[x+2][y+1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }else if (y > 0 && x < 6 && tablero[x+2][y-1] == tablero[i][j] && (tablero[i][j].blanca == null || tablero[i][j].blanca == 0)) {
                                meterFicha(tablero[i][j], tablero[x][y]); 
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
    
    public void meterFicha(Tablero nuevo, Tablero viejo){
        nuevo.imagen = viejo.imagen;
        nuevo.id = viejo.id;
        nuevo.blanca = viejo.blanca;
        viejo.imagen = null;
        viejo.id = null;
        viejo.blanca = null;
        nuevo.casillaOcupada = true;
        viejo.casillaOcupada = false;
    }
}
