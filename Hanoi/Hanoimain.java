package Hanoi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Hanoimain {

    public static void main(String[] args) {
        int discos = 0;
        int rMDes = 0;// radio del mayor disco descolocado
        int id=0;
        int heuristica=0;
        int movimentos = 0;
        int maiorDiscoColocadoEmC = -2;
        boolean caminhoEncontrado = false;
        ArrayList<Estado> abertos = new ArrayList<Estado>();
        ArrayList<Estado> fechados = new ArrayList<Estado>();
        ArrayList<Estado> caminhoFinal = new ArrayList<Estado>();
        ArrayList<Integer> colunaA = new ArrayList<Integer>();
        ArrayList<Integer> colunaC = new ArrayList<Integer>();
        Scanner sc = new Scanner(System.in);
        System.out.printf("Coloque o numero de discos que deseja: ");
        discos= sc.nextInt();
        rMDes = discos;
        //System.out.printf("%d discos deseados\n",discos);
        heuristica = (int) Math.pow(2, rMDes-1);

        //Creación del estado inicial
        for (int i=discos;i>0;i--){
            colunaA.add(i);
            colunaC.add(i);
        }

        Estado estadoInicial = new Estado(0, 0, movimentos, heuristica);// De este estado  partimos para hacer cosas
        estadoInicial.setColA(colunaA);
        Estado estadoFinal = new Estado(-1, -1, movimentos, heuristica);// De este estado  partimos para hacer cosas
        estadoFinal.setColC(colunaC);
        abertos.add(estadoInicial);

        while(!caminhoEncontrado){
            Estado atual = new Estado();
            Estado siguienteizder = new Estado();
            Estado siguientederiz = new Estado();
            Estado siguienteizmed = new Estado();
            Estado siguientemediz = new Estado();
            Estado siguientemedder = new Estado();
            Estado siguientedermed = new Estado();

            atual=setValuesEstado(atual,abertos.get(0));
            fechados.add(atual);
            abertos.remove(0);

            if(fechados.get(fechados.size()-1).getColC()!= null &&fechados.get(fechados.size()-1).getColC().equals(estadoFinal.getColC())){
                caminhoEncontrado = true;
            }
            else{
                siguienteizmed = izq_a_medio(atual);
                if(siguienteizmed!=null){
                    if(!no_miembro(abertos, fechados, siguienteizmed)){
                        id+=1;
                        siguienteizmed.setId(id);
                        siguienteizmed.setAnterior(atual.getId());
                        siguienteizmed.setG(atual.getG()+1);
                        abertos.add(siguienteizmed);
                        Collections.sort(abertos, MakeComparator);
                    }
                }

                siguientemediz = medio_a_izq(atual);
                if(siguientemediz!=null){
                    if(!no_miembro(abertos,fechados, siguientemediz)){
                        id+=1;
                        siguientemediz.setId(id);
                        siguientemediz.setAnterior(atual.getId());
                        siguientemediz.setG(atual.getG()+1);
                        abertos.add(siguientemediz);
                        Collections.sort(abertos, MakeComparator);
                    }
                }

                siguienteizder = izq_a_der(atual);
                if(siguienteizder!=null){
                    if(!no_miembro(abertos,fechados, siguienteizder)){
                        id+=1;
                        siguienteizder.setId(id);
                        siguienteizder.setAnterior(atual.getId());
                        siguienteizder.setG(atual.getG()+1);
                        //Control de si disco colocado es rmDes
                        if(!siguienteizder.getColC().isEmpty()){
                            if(rMDes == siguienteizder.getColC().get(siguienteizder.getColC().size()-1)){
                                System.out.println("Coloque el rmdes");
                                maiorDiscoColocadoEmC=rMDes;
                                rMDes = rMDes - 1;
                                heuristica = (int) Math.pow(2, rMDes-1);
                                siguienteizder.setH(heuristica);
                                mostraColunasDoEstado(siguienteizder);
                            }
                        }
                        abertos.add(siguienteizder);
                        Collections.sort(abertos, MakeComparator);
                    }
                }

                siguientemedder = medio_a_der(atual);
                if(siguientemedder!=null){
                    if(!no_miembro(abertos,fechados, siguientemedder)){
                        id+=1;
                        siguientemedder.setId(id);
                        siguientemedder.setAnterior(atual.getId());
                        siguientemedder.setG(atual.getG()+1);
                        if(!siguientemedder.getColC().isEmpty()){
                            if(rMDes == siguientemedder.getColC().get(siguientemedder.getColC().size()-1)){
                                System.out.println("Coloque el rmdes");
                                maiorDiscoColocadoEmC=rMDes;
                                rMDes = rMDes - 1;
                                heuristica = (int) Math.pow(2, rMDes-1);
                                siguientemedder.setH(heuristica);
                                mostraColunasDoEstado(siguientemedder);
                            }
                        }
                        abertos.add(siguientemedder);
                        Collections.sort(abertos, MakeComparator);
                    }
                }

                //Control de no estar haciendo movimiento innecesario moviendo el mayor disco colocado en C
                if(!atual.getColC().isEmpty()){
                    if(maiorDiscoColocadoEmC!=atual.getColC().get(atual.getColC().size()-1)){
                        siguientederiz = der_a_izq(atual);
                        if(siguientederiz!=null){
                            if(!no_miembro(abertos,fechados, siguientederiz)){
                                id+=1;
                                siguientederiz.setId(id);
                                siguientederiz.setAnterior(atual.getId());
                                siguientederiz.setG(atual.getG()+1);
                                abertos.add(siguientederiz);
                                Collections.sort(abertos, MakeComparator);
                            }
                        }
                    }
                }
            }
        }// fin while

        mostrarCaminho(fechados);
    }

    public static Estado setValuesEstado(Estado dondeAñado, Estado dondeSaco){
        dondeAñado.setAnterior(dondeSaco.getAnterior());
        dondeAñado.setG(dondeSaco.getG());
        dondeAñado.setH(dondeSaco.getH());
        dondeAñado.setId(dondeSaco.getId());

        for(int i=0;i<dondeSaco.getColA().size(); i++){
            int valor= dondeSaco.getColA().get(i);
            dondeAñado.getColA().add(valor);
        }
        for(int i=0;i<dondeSaco.getColB().size(); i++){
            int valor= dondeSaco.getColB().get(i);
            dondeAñado.getColB().add(valor);
        }
        for(int i=0;i<dondeSaco.getColC().size(); i++){
            int valor= dondeSaco.getColC().get(i);
            dondeAñado.getColC().add(valor);
        }

        return dondeAñado;
    }

    public static Estado izq_a_medio(Estado actual){
        int discoAMover;
        int discoEnDestino;
        Estado nuevo;

        nuevo = new Estado(-1,-1, actual.getG()+1, actual.getH());

        nuevo = setValuesEstado(nuevo,actual);

        if(nuevo.getColA().isEmpty()){
            return null;
        }
        else{
            discoAMover = nuevo.getColA().get(nuevo.getColA().size()-1);
            if(nuevo.getColB().isEmpty()){
                nuevo.getColB().add(discoAMover);
                nuevo.getColA().remove(nuevo.getColA().size()-1);
                return nuevo;
            }
            else{
                discoEnDestino = nuevo.getColB().get(nuevo.getColB().size()-1);
                if(discoAMover<discoEnDestino){
                    nuevo.getColB().add(discoAMover);
                    nuevo.getColA().remove(actual.getColA().size()-1);
                    return nuevo;
                }
                else{
                    return null;
                }

            }
        }
    }

    public static Estado izq_a_der(Estado actual){
        int discoAMover;
        int discoEnDestino;
        Estado nuevo;

        nuevo = new Estado(-1,-1,actual.getG()+1, actual.getH());
        nuevo = setValuesEstado(nuevo, actual);

        if(actual.getColA().isEmpty()){
            return null;
        }
        else{
            discoAMover = actual.getColA().get(actual.getColA().size()-1);
            if(actual.getColC().isEmpty()){
                nuevo.getColC().add(discoAMover);
                nuevo.getColA().remove(actual.getColA().size()-1);
                return nuevo;
            }
            else{
                discoEnDestino = actual.getColC().get(actual.getColC().size()-1);
                if(discoAMover<discoEnDestino){
                    nuevo.getColC().add(discoAMover);
                    nuevo.getColA().remove(actual.getColA().size()-1);
                    return nuevo;
                }else{
                    return null;
                }

            }
        }
    }

    public static Estado medio_a_izq(Estado actual){
        int discoAMover;
        int discoEnDestino;
        Estado nuevo;

        nuevo = new Estado(-1,-1,actual.getG()+1, actual.getH());
        nuevo = setValuesEstado(nuevo, actual);

        if(actual.getColB().isEmpty()){
            return null;
        }
        else{
            discoAMover = actual.getColB().get(actual.getColB().size()-1);
            if(actual.getColA().isEmpty()){
                nuevo.getColA().add(discoAMover);
                nuevo.getColB().remove(actual.getColB().size()-1);
                return nuevo;
            }//Columna destino tiene un disco ver si movimiento es posible en función de tamaño
            else{
                discoEnDestino = actual.getColA().get(actual.getColA().size()-1);
                if(discoAMover<discoEnDestino){
                    nuevo.getColA().add(discoAMover);
                    nuevo.getColB().remove(actual.getColB().size()-1);
                    return nuevo;
                }else
                    return null;
            }
        }
    }

    public static Estado medio_a_der(Estado actual){
        int discoAMover;
        int discoEnDestino;
        Estado nuevo;

        nuevo = new Estado(-1,-1,actual.getG()+1, actual.getH());
        nuevo = setValuesEstado(nuevo, actual);

        if(actual.getColB().isEmpty()){
            return null;
        }
        else{//Columna origen no vacia cojo el disco de arriba que es el que moveremos
            discoAMover = actual.getColB().get(actual.getColB().size()-1);
            if(actual.getColC().isEmpty()){
                nuevo.getColC().add(discoAMover);
                nuevo.getColB().remove(actual.getColB().size()-1);
                return nuevo;
            }//Columna destino tiene un disco ver si movimiento es posible en función de tamaño
            else{
                discoEnDestino = actual.getColC().get(actual.getColC().size()-1);
                if(discoAMover<discoEnDestino){
                    nuevo.getColC().add(discoAMover);
                    nuevo.getColB().remove(actual.getColB().size()-1);
                    return nuevo;
                }else
                    return null;
            }
        }
    }

    public static Estado der_a_izq(Estado actual){
        int discoAMover;
        int discoEnDestino;
        Estado nuevo;

        nuevo = new Estado(-1,-1,actual.getG()+1, actual.getH());
        nuevo = setValuesEstado(nuevo, actual);
        //nuevo.setG(nuevo.getG()+1);

        if(actual.getColC().isEmpty()){
            return null;
        }
        else{//Columna origen no vacia cojo el disco de arriba que es el que moveremos
            discoAMover = actual.getColC().get(actual.getColC().size()-1);
            if(actual.getColA().isEmpty()){
                //Movimiento posible porque destino no tiene discos
                nuevo.getColA().add(discoAMover);
                nuevo.getColC().remove(actual.getColC().size()-1);
                return nuevo;
            }//Columna destino tiene un disco ver si movimiento es posible en función de tamaño
            else{
                discoEnDestino = actual.getColA().get(actual.getColA().size()-1);
                if(discoAMover<discoEnDestino){
                    nuevo.getColA().add(discoAMover);
                    nuevo.getColC().remove(actual.getColC().size()-1);
                    return nuevo;
                }else
                    return null;
            }
        }
    }

    public static Estado der_a_medio(Estado actual){
        int discoAMover;
        int discoEnDestino;
        Estado nuevo;

        nuevo = new Estado(-1,-1,actual.getG()+1, actual.getH());
        nuevo = setValuesEstado(nuevo, actual);
        //nuevo.setG(nuevo.getG()+1);
        if(actual.getColC().isEmpty()){
            return null;
        }
        else{//Columna origen no vacia cojo el disco de arriba que es el que moveremos
            discoAMover = actual.getColC().get(actual.getColC().size()-1);
            if(actual.getColB().isEmpty()){
                //Movimiento posible porque destino no tiene discos
                nuevo.getColB().add(discoAMover);
                nuevo.getColC().remove(actual.getColC().size()-1);
                return nuevo;
            }//Columna destino tiene un disco ver si movimiento es posible en función de tamaño
            else{
                discoEnDestino = actual.getColB().get(actual.getColB().size()-1);
                if(discoAMover<discoEnDestino){
                    nuevo.getColB().add(discoAMover);
                    nuevo.getColC().remove(actual.getColC().size()-1);
                    return nuevo;
                }else
                    return null;
            }
        }
    }

    public static Boolean no_miembro(ArrayList<Estado> listaAbertos, ArrayList<Estado> listaFechados, Estado siguiente){

        Boolean encontrado = false;


        for(int i =0;i<listaAbertos.size();i++){
            if(siguiente.getColA().equals(listaAbertos.get(i).getColA()) && siguiente.getColB().equals(listaAbertos.get(i).getColB()) && siguiente.getColC().equals(listaAbertos.get(i).getColC())){
                encontrado= true;
                return encontrado;
            }
        }

        for(int i =0;i<listaFechados.size();i++){
            if(siguiente.getColA().equals(listaFechados.get(i).getColA()) && siguiente.getColB().equals(listaFechados.get(i).getColB()) && siguiente.getColC().equals(listaFechados.get(i).getColC())){
                encontrado= true;
                return encontrado;
            }
        }

        return encontrado;
    }

    public static void mostraColunasDoEstado(Estado s){
        System.out.printf("Id: %d, movs: %d, anterior: %d F: %d", s.getId(), s.getG(), s.getAnterior(), s.getF());
        System.out.printf("\nA:");
        for(int x=0;x<s.getColA().size();x++) {
            System.out.printf("|%d|" ,s.getColA().get(x));
        }

        System.out.printf("\nB:");
        for(int x=0;x<s.getColB().size();x++) {
            System.out.printf("|%d|" ,s.getColB().get(x));
        }
        System.out.printf("\nC:");
        for(int x=0;x<s.getColC().size();x++) {
            System.out.printf("|%d|" ,s.getColC().get(x));
        }
        System.out.printf("\n");

    }

    public static void mostraColunasDoEstadoEmUmaLinha(Estado s){
        System.out.printf("Id: %d, movs: %d, anterior: %d ", s.getId(), s.getG(), s.getAnterior());
        System.out.printf("estado([ ");
        for(int x=s.getColA().size()-1;x >=0;x--) {
            System.out.printf("%d " ,s.getColA().get(x));
        }
        System.out.printf("]");
        System.out.printf(", [ ");
        for(int x=s.getColB().size()-1;x >=0;x--) {
            System.out.printf("%d " ,s.getColB().get(x));
        }
        System.out.printf("]");
        System.out.printf(", [ ");
        for(int x=s.getColC().size()-1;x >=0;x--) {
            System.out.printf("%d " ,s.getColC().get(x));
        }
        System.out.printf("])");
        System.out.printf("\n");

    }

    public static void mostrarListaDeAbertos(ArrayList<Estado> listaAbiertos){
        for(int i =0; i< listaAbiertos.size(); i++){
            mostraColunasDoEstadoEmUmaLinha(listaAbiertos.get(i));

        }

    }

    public static void mostrarListaDeCaminhos(ArrayList<Estado> camino){
        for(int i =camino.size()-1; i>=0; i--){
            mostraColunasDoEstadoEmUmaLinha(camino.get(i));

        }
    }

    public static void mostrarCaminho(ArrayList<Estado> lista){
        ArrayList<Estado> camino = new ArrayList<Estado>();
        camino.add(lista.get(lista.size()-1));

        for(int i =lista.size()-1; i>=0; i--){
            if(lista.get(i).getId() == camino.get(camino.size()-1).getAnterior()){
                camino.add(lista.get(i));
            }
        }

        mostrarListaDeCaminhos(camino);
    }

    public static Comparator<Estado> MakeComparator = new Comparator<Estado>() {
        public int compare(Estado a, Estado b){

            Integer ordena =  a.getF();
            Integer ordenb =  b.getF();

            return ordena.compareTo(ordenb);
        }
    };

}