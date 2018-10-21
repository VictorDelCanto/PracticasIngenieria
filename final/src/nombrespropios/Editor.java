/*
 * Editor.java
 *
 * Created on 10 de abril de 2006, 11:51
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
public class Editor {       //Herramientas generales para el tratamiento del texto
    
    public Set setAux = new TreeSet();
    public List listaAux= new LinkedList();
    static int num;
    static ArrayList lista = new ArrayList();        
    static StringTokenizer stA,stF;

    /** Creates a new instance of Editor */
    public Editor() {
        }
    


protected static final int BLKSIZ=8192;

public static String readerToString (String f)throws IOException {
// Extrae el contenido del archivo en una cadena    
    FileReader is=new FileReader(f);
    StringBuffer sb= new StringBuffer();
    char[]b=new char[BLKSIZ];
    int n;
    
    //Lee un bloque. Si obtiene algun caracter, los añade.
    while ((n=is.read(b))>0) {
        sb.append(b,0,n);
    }
    return sb.toString();
}

public static int NumeroTokensArchivo(String str)throws IOException{
//Numero de tokens del archivo a tratar (incluyendo delimitadores)    
    stA=new StringTokenizer(readerToString(str),"\n"+"\r"+" "+"."+"/"+"*"+"-"+"+"+"("+")"+"{"+"}"+"["+"]"+"_"+":"+";"+","+"?"+"¡"+"¿"+"!"+"#"+"="+"  ",true);    
    num=stA.countTokens();
    return num;
}

public static int NumeroTokensFichero(String str)throws IOException{
//Numero de tokens del fichero(Excepciones.txt o Conocidos.txt) a tratar (excluyendo delimitadores)    
    stF=new StringTokenizer(readerToString(str),"\n"+"\r"+" "+"["+"]"+","+"  ",false);    
    num=stF.countTokens();
    return num;
}

public static String tokenizarArchivo () {
//Devuelve los tokens (incluyendo delimitadores) del archivo    
        while (stA.hasMoreTokens()) {
            return stA.nextToken();
        }
        return (" ");
    } 

public static String tokenizarFichero () {
//Devuelve los tokens (excluyendo delimitadores) del fichero
        while (stF.hasMoreTokens()) {
            return stF.nextToken();
        }
        return (" ");
    } 


public static void enlistar (String cadena) throws IOException{
//Añade la cadena al ArrayList
    lista.add(cadena);
}

public boolean contiene(Set setAux ,String cadena) {
//Devuelve cierto si la cadena esta en el conjunto    
    return setAux.contains(cadena);
    
}

public void añadirLista(LinkedList listaAux, int indice) {
//Añade la posicion entera a la LinkedList y ordena la lista    
    listaAux.addLast(indice);
    Collections.sort(listaAux);
}

public void añadirSet(Set setAux,String cadena) {
//Añade la cadena al conjunto    
    setAux.add(cadena);
}

public void quitarSet(Set setAux,String cadena) {
// Quita la cadena del conjunto    
    setAux.remove(cadena);
}

}