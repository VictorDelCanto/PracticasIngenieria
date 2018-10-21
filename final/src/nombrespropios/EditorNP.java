/*
 * EditorNP.java
 *
 * Created on 13 de abril de 2006, 18:18
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package nombrespropios;

import java.io.*;
import java.util.*;

/**
 *
 * @author Victor del Canto
 */
public class EditorNP extends Editor{
//Tratamiento de un texto para obtener NOMBRES PROPIOS (segun practica)    
    public static LinkedList ListaExcepTexto = new LinkedList();
    public static LinkedList ListaConocTexto = new LinkedList();
    public static LinkedList ListaDesconocTexto = new LinkedList();
    public static Set ListaDesconocidos = new TreeSet();
    public static Set ListaConocidos = new TreeSet();
    public static Set ListaExcepciones = new TreeSet(); 
    /** Creates a new instance of EditorNP */
    public EditorNP() {
    }
    
    public void CrearLista()throws IOException {
    limpiar();    
    num=   NumeroTokensArchivo(ner.g);
    for (int c=0;c<num;c++){    //Analiza cada token del archivo
        String cadena=tokenizarArchivo();           //Busca
        String inicial=cadena.substring(0,1);       // si es
        String cadenaMAY=cadena.toLowerCase();      //Nombre 
        String INICMAY=cadenaMAY.substring(0,1);    //Propio
        if (inicial.equals(INICMAY)==false) {   //Si lo es
        //buscamos si esta en Excepciones o Conocidos 
        //y se añade su posicion al LinkedList que corresponda
            buscarNP(cadena,c);     //
        }
        // Colocamos cada token en el ArrayList en su posicion
        enlistar(cadena);
    }
    }
    
    public void CrearSet(String fichero,Set setAux) throws IOException {
//Analiza cada fichero y añade cada token al conjunto correspondiente
        num=   NumeroTokensFichero(fichero);
     for (int c=0;c<num;c++){
        String cadena=tokenizarFichero(); 
        añadirSet(setAux,cadena);
     }    
    }
    
    public void buscarNP(String cadena,int indice) {
        // Busca cada cadena en los conjuntos 
        //y añade su posicion al LinkedList que corresponda        
        if (contiene(ListaExcepciones,cadena)==true) {
            añadirLista(ListaExcepTexto,indice);
        }else if (contiene(ListaConocidos,cadena)==true) {
            añadirLista(ListaConocTexto,indice);            
        }else {
            ListaDesconocidos.add(cadena);
            añadirLista(ListaDesconocTexto,indice);
        }
    }
    
    public void limpiar() {
        //Limpia de elementos todas las listas
        ListaExcepTexto.clear();
        ListaConocTexto.clear();
        ListaDesconocTexto.clear();
        lista.clear();
    }
}
