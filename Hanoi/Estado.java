package Hanoi;

import java.util.ArrayList;

public class Estado {
    private ArrayList<Integer> colA = new ArrayList();
    private ArrayList<Integer> colB = new ArrayList();
    private ArrayList<Integer> colC = new ArrayList();
    private int id;
    private int anterior;
    private int f;
    private int g;
    private int h;

    public Estado(int i, int ant, int g, int h) {
        this.id = i;
        this.anterior = ant;
        this.g = g;
        this.h = h;
        this.f = soma(this.g, this.h);
    }

    public Estado(){
        this.colA = new ArrayList();
        this.colB = new ArrayList();
        this.colC = new ArrayList();
        this.id = -1;
        this.anterior = -1;
    }

    public ArrayList<Integer> getColA() {
        return colA;
    }

    public void setColA(ArrayList<Integer> colA) {
        this.colA = colA;
    }

    public ArrayList<Integer> getColB() {
        return colB;
    }

    public void setColB(ArrayList<Integer> colB) {
        this.colB = colB;
    }

    public ArrayList<Integer> getColC() {
        return colC;
    }

    public void setColC(ArrayList<Integer> colC) {
        this.colC = colC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnterior() {
        return anterior;
    }

    public void setAnterior(int anterior) {
        this.anterior = anterior;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
        this.f = soma(this.g, this.h);
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
        this.f = soma(this.g, this.h);
    }

    public int soma(int g, int h){
        int soma = 0;
        soma = g + h;
        return soma;
    }
}