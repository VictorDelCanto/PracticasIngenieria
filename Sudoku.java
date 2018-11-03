
import java.util.*;
import java.io.*;


public class Sudoku extends OperacionesSudoku{ 

//clase principal tiene el tratamiento de ficheros y las llamadas a metodos para resolver el sudoku


	public static void main (String[] args) {

	boolean traza,test,ayuda;
	traza=false;test=false;ayuda=false;
	String fichero;
	fichero=null;
	String[]argumento;
	try{
		if (args.length==0) {
			fichero=EntradaEstandar();//se introduce el argumento por la entrada estandar
		argumento=args;//se pasa argumento
		}else{
		argumento=args;//se pasa argumento
		if (argumento[0].charAt(0)=='-') {//busqueda de  opciones en sintaxis
			switch (argumento[0].charAt(1)) {
			case 't':
						test=true;
							if (args.length==1){
							fichero=EntradaEstandar();//se introduce el argumento por la entrada estandar							
							}else{fichero=argumento[1]; }
						break;
			case 'a':
						traza=true;
							if (args.length==1){
							fichero=EntradaEstandar();//se introduce el argumento por la entrada estandar							
							}else{fichero=argumento[1]; }
						break;
			case 'h':
						ayuda=true;
						break;
			default :
						System.out.println(" Parametro Incorrecto ");						
						System.out.println();
						return;
			}//fin del switch 
		} else {
		fichero=argumento[0]; //entrada sin opciones
		}
		if (fichero==null) {
			fichero=EntradaEstandar();//se introduce el argumento por la entrada estandar
		} 
		}
	} catch (IOException e) {System.err.println("error de entrada/salida ");return;}
		ResolverSudoku (test,traza,ayuda,fichero);
		return;
	} //fin del matodo principal 
				






	public static String EntradaEstandar ()throws IOException  {
	try {
		BufferedReader entrada = new BufferedReader (new InputStreamReader (System.in));// Lee de la entrada estandar
		PrintWriter salida = new PrintWriter(new BufferedWriter (new FileWriter ("xyz.txt")));// Abre fichero para copiar de la entrada estandar
		int x,aux;
		String s=" ";
		System.out.println("Si no ha redirigido el fichero teclee el sudoku a tratar y ponga fin sin espacios");
		while ((s=entrada.readLine())!=null) {
			salida.println(s);
		}
		salida.close();
	} catch (IOException e) { }
	 
			String argumento;
			argumento="xyz.txt"; 
		return argumento;
	}//fin del metodo EntradaEstandar 
	
	
	
	
	


	public static int[][] LecturaFichero(String fichero) throws FileNotFoundException { 
	int[][]MatrizInicial= new int[9][9];
	int[][]MatrizAuxiliar= new int[10][10];  	
      BufferedReader in = new BufferedReader(new FileReader(fichero));
      String str,celda;
      int i,j,valor,vacio;//i son filas ,j son columnas y valor es el de la casilla, vacio sirve para evitar errores de indice de array cuando leemos las lineas del fichero y alguna esta en blanco
      i=0;
	try {
		while ((str = in.readLine()) != null) {
			vacio=str.length();//numero de caracteres de la linea
			if (vacio==0) continue;//saltamos a la siguiente linea
	  		char cadena[] = str.toCharArray();
			if ((cadena[0]=='#')||(cadena[0]==' ')) {
				continue ;
			}else {
				i++;
   	   			j=0;
      		}
			while (j< cadena.length) {
     				if (cadena[j]=='*') {
     					valor=0;
     				} else { 
				celda=(cadena[j]+" ");
				valor=Integer.parseInt(celda.trim());
     				}
					j++;
					MatrizAuxiliar[i][j]=valor;
			}// fin del while anidado
		}// fin del while principal
		in.close();
		i=0;j=0;
		for (i=1;i<=9;i++) {
			for (j=1;j<=9;j++) {
			MatrizInicial[i-1][j-1]=MatrizAuxiliar[i][j];
			}
		}
	} catch (IOException e) {System.err.println("error de entrada/salida ");}
	return MatrizInicial;
   } // fin del metodo LecturaFichero	
  
  



  public static void ResolverSudoku (boolean test,boolean traza,boolean ayuda,String fichero) {
	int[][] Matriz=new int[9][9];	
	int[][][]MatrizAux = new int[9][9][11];
	boolean correcto;
	int fila,columna,hora,minuto,segundo;
	Date Costeinicio=new Date();//sirve para calcular el tiempo que tarda en encontrar la solucion
	long inicio,actual;
		if (ayuda==true) {
		ImprimirAyuda();
		return;
		}

	try{
	Matriz=LecturaFichero(fichero);
  	} catch (FileNotFoundException e) {
  	System.err.println("fichero no existe o mal introducido ");//excepcion el fichero no existe 
  	return;
	}catch (NumberFormatException e)  {
  	System.err.println("datos fichero incorrectos ");
  	return;
	}	
  	correcto=ComprobarSudoku(Matriz);
 		if (correcto==false) {
 		System.out.println(" Los datos del fichero "+fichero+" NO son correctos segun las reglas de SUDOKU ");
 		return;
 		}
 		if (test==true) {
 		System.out.println(" Los datos del fichero "+fichero+" son correctos segun las reglas de SUDOKU ");
 		return;
 		}
	inicio=Costeinicio.getTime();
 	MatrizAux=EstablecerCandidatos(Matriz);
		if (traza==true){System.out.println(" AJUSTE DE CANDIDATOS SOLOS "); 	}
	MatrizAux=AjusteCandidatosSolos(MatrizAux,traza);
 	//MatrizAux=AjusteSolosEscondidos(MatrizAux,traza);// QUEDA AQUI POR SI SE QUIERE PROBAR SOLOS ESCONDIDOS(QUITAR // INICIAL)
		if (traza==true){System.out.println(" AJUSTE POR ALGORITMO VUELTA ATRAS "); 	}
 	MatrizAux=VueltaAtras(MatrizAux,0,0,0,traza);
	SalidaDatos(MatrizAux);
	Date Costefin=new Date();
	actual=Costefin.getTime();
	//System.out.println(" el tiempo tardado en resolver es de  "+(actual-inicio)+"  ms. ");//muestra el tiempo empleado
 	return;
  }//fin del metodo ResolverSudoku		  
	
  public static void ImprimirAyuda () {
 System.out.println();
 System.out.println("La practica se invoca usando la siguiente sintaxis:"); 
 System.out.println();
 System.out.println("sudoku [-t][-a][-h] [fichero] "); 
 System.out.println();
 System.out.println("Opciones: "); 
 System.out.println();
 System.out.println("-t:  Realiza un test de correccion a la matriz de entrada. Si es incompleta o"); 
 System.out.println("     incorrecta segun las reglas del juego devuelve 1 en caso contrario"); 
 System.out.println("     devuelve 0. Sirve para comprobar errores sintacticos en la entrada."); 
 System.out.println();
 System.out.println("-a:  Modo traza. Muestra toda la secuencia de valores que se van incorporando a"); 
 System.out.println("     la matriz de entrada hasta completarla correctamente. Hay que tratar que"); 
 System.out.println("     sea lo mas ilustrativa posible sobre el proceso del problema por parte del"); 
 System.out.println("     algoritmo."); 
 System.out.println( );
 System.out.println("-h:  Modo ayuda. Muestra la sintaxis y los creditos.  "); 
 System.out.println( );
 System.out.println("Si no tiene argumentos, el programa muestra la matriz inicial, y la matriz final"); 
 System.out.println("resuelta."); 
 System.out.println( );
 System.out.println(" Practica resuelta por Victor M. del Canto Godino ");
 System.out.println( );
 System.out.println(" Alumno del Centro Asociado De Palencia. UNED Curso 2005-2006" );
 System.out.println( );

 return;
  }//fin del metodo ImprimirAyuda 
  
    		
}// fin de la clase Sudoku


class OperacionesSudoku extends Util{
//clase que realiza operaciones especificas de sudoku

	static int[][] MatrizOrigen =new int[9][9];


	public static boolean ComprobarSudoku(int[][] MatrizOrigen) { 
		int aux,aux1,aux2,aux3,aux4,cont;
		int[][] RangoCaja = new int[2][3];
		int[] VectorOrigen = new int[11];
		for (aux1=0;aux1<=8;aux1++) {
			for (cont=0;cont<=8;cont++) {
			VectorOrigen[cont+1]=MatrizOrigen[aux1][cont];//COMPRUEBA SUDOKU EN FILAS 
			}
				if (RangoCandidato(VectorOrigen,0,9)==false) {
				return false;
				}
				for (aux=1;aux<=9;aux++) {
					if (ComparaElementos(VectorOrigen,aux)==false) {
					return false;
					}
				}
			
					
			for (cont=0;cont<=8;cont++) {
			VectorOrigen[cont+1]=MatrizOrigen[cont][aux1];//COMPRUEBA SUDOKU EN COLUMNAS 
			}
				if (RangoCandidato(VectorOrigen,0,9)==false) {
				return false;
				}
				for (aux=1;aux<=9;aux++) {
					if (ComparaElementos (VectorOrigen,aux)==false) {
					return false;
					}
				}
		}
		for (aux1=0;aux1<=2;aux1++) { //COMPRUEBA SUDOKU  EN CAJAS 
			for (aux2=0;aux2<=2;aux2++) {		
				RangoCaja=CeldaEstaEnCaja(((3*(aux1+1))-1),((3*(aux2+1))-1));

				for (aux3=0;aux3<=2;aux3++) { //pongo todos los valores de la caja3X3 que corresponda en un array para comprobar
					for (aux4=0;aux4<=2;aux4++) {	
					VectorOrigen[(3*aux3)+aux4+1]=MatrizOrigen[RangoCaja[0][aux3]][(RangoCaja[1][aux4])];
					}
				}	
				if (RangoCandidato(VectorOrigen,0,9)==false) {
				return false;
				}
				for (aux=1;aux<=9;aux++) {
					if (ComparaElementos (VectorOrigen,aux)==false) {
					return false;
					}
				}
			}	
				
		}
		return true;		
	} // fin del metodo ComprobarSudoku
	
	


	public static int [][][] EstablecerCandidatos(int [][] MatrizOrigen) {
		int[][][] MatrizCandidatos = new int [9][9][11];
		int fila,columna,profund;
		for (fila=0;fila<=8;fila++) {
			for (columna=0;columna<=8;columna++) {
				Iniciar (MatrizCandidatos[fila][columna],1); //Poner candidatos a 1
				MatrizCandidatos[fila][columna]= Suma (MatrizCandidatos[fila][columna]);// Total de candidatos				
			}
		}
		for (fila=0;fila<=8;fila++) {
			for (columna=0;columna<=8;columna++) {
				if (MatrizOrigen[fila][columna]==0) {//No tiene solucion original
				MatrizCandidatos[fila][columna]= Suma (MatrizCandidatos[fila][columna]);				
				} else {
				Iniciar (MatrizCandidatos[fila][columna],0); //Poner candidatos a 0
				MatrizCandidatos= AjusteCandidatosGeneral (MatrizCandidatos,fila,columna,(MatrizOrigen[fila][columna]));
				}
			}
		}
		return MatrizCandidatos;
	}//fin del metodo EstablecerCandidatos
				



	public static int[][][] AjusteCandidatosSolos(int [][][] MatrizCandidatosOrigen,boolean traza) {//encuentra y trata los candidatos solos
		int[][][] MatrizCandidatosFin = new int [9][9][11];	
		int[][][] MatrizCandidatosAux = new int [9][9][11];	
		int fila,columna,posicion,aux1,aux2;					  
		boolean encontrado,unico;
		aux1=0;aux2=0;
		for (fila=0;fila<=8;fila++) {		
			for (columna=0;columna<=8;columna++) {
				unico=MirarCandidatos(MatrizCandidatosOrigen[fila][columna],1) ;
				if (unico==false) { //No tiene solucion hallada  
				continue;
				}else {
					encontrado=CandidatoEncontrado (MatrizCandidatosOrigen[fila][columna]);				
					if (encontrado==true) { //ya se habia tratado esta celda 
					continue; 
					} else {
					posicion=BuscarUnico(MatrizCandidatosOrigen[fila][columna]) ;
					MatrizCandidatosFin=AjusteCandidatosGeneral(MatrizCandidatosOrigen,fila,columna,posicion);
						if (traza==true) { //mostrar la secuencia de valores que se añaden
							System.out.println("El candidato "+posicion+" era unico en la fila "+(fila+1)+" columna "+(columna+1));
							System.out.println();
						}	
					MatrizCandidatosFin= AjusteCandidatosSolos(MatrizCandidatosFin,traza);//llamada a si misma para que comienze el proceso desde el principio otra vez
					}
					}//fin del if-else
			}
		}
						
		return MatrizCandidatosOrigen;//en la ultima llamada no hay candidatos solos y devuelve el resultado de la anterior
	}//fin del metodo	AjusteCandidatosSolos		
			
	



	public static int[][][] AjusteSolosEscondidos(int [][][] MatrizCandidatosOrigen,boolean traza) {//encuentra y trata los candidatos solos escondidos 
		int[][][] MatrizCandidatosFin = new int [9][9][11];	
		int[][] RangoCaja = new int[2][3];		  
		int[] VectorAux = new int[11];
		int fila,columna,posicion,aux,aux1,aux2,aux3,aux4,aux5,cont;
		boolean unico,encontrado;
		MatrizCandidatosFin=MatrizCandidatosOrigen;
		VectorAux=Iniciar(VectorAux,0);//inicializo el vector auxiliar de datos a 0 hasta la posicion 10
		//COMPROBACION DE SOLOS ESCONDIDOS EN FILAS 
		aux5=0;
		for (fila=aux5;fila<=8;fila++){		
			for (posicion=1;posicion<=9;posicion++) {
				for (cont=1;cont<=9;cont++) {
				VectorAux[cont]=MatrizCandidatosOrigen[fila][cont-1][posicion];
				}
			VectorAux=Suma(VectorAux);
			unico=MirarCandidatos(VectorAux,1) ;			
			aux=BuscarUnico(VectorAux);
					if (unico==false) {//No tiene solucion hallada
					continue; 
					} else {
					encontrado=CandidatoEncontrado (MatrizCandidatosOrigen[fila][aux-1]);				
					if (encontrado==true) { //ya se habia tratado esta celda 
					continue; 
					} else {

						MatrizCandidatosFin= AjusteCandidatosGeneral(MatrizCandidatosOrigen,fila,aux-1,posicion);
						if (traza==true) { //mostrar la secuencia de valores que se añaden
							System.out.println("El candidato "+posicion+" era unico 'escondido en fila' en la fila "+(fila+1)+" columna "+(aux)+" marca : "+(MatrizCandidatosOrigen[fila][aux-1][10]));
							System.out.println();

						}
						MatrizCandidatosFin=AjusteCandidatosSolos(MatrizCandidatosFin,traza);// busco si hay candidatos solos
						MatrizCandidatosFin= AjusteSolosEscondidos(MatrizCandidatosFin,traza);//llamada a si misma para que comienze el proceso desde el principio otra vez
					}
					}
			}
		}	

		//COMPROBACION DE SOLOS ESCONDIDOS EN COLUMNAS 	
		for (columna=0;columna<=8;columna++){		
			for (posicion=1;posicion<=9;posicion++) {
				for (cont=1;cont<=9;cont++) {
				VectorAux[cont]=MatrizCandidatosOrigen[cont-1][columna][posicion];
				}
			VectorAux=Suma(VectorAux);
			unico=MirarCandidatos(VectorAux,1) ;			
			aux=BuscarUnico(VectorAux);
					if (unico==false) {//No tiene solucion hallada
					continue; 
					} else {
					encontrado=CandidatoEncontrado (MatrizCandidatosOrigen[aux-1][columna]);				
					if (encontrado==true) { //ya se habia tratado esta celda 
					continue; 
					} else {

						MatrizCandidatosFin= AjusteCandidatosGeneral(MatrizCandidatosOrigen,aux-1,columna,posicion);	
						if (traza==true) { //mostrar la secuencia de valores que se añaden
							System.out.println("El candidato "+posicion+" era unico 'escondido en columna' en la fila "+(aux)+" columna "+(columna+1)+" marca : "+(MatrizCandidatosOrigen[aux-1][columna][10]));
							System.out.println();

						}
						MatrizCandidatosFin=AjusteCandidatosSolos(MatrizCandidatosFin,traza);// busco si hay candidatos solos
						MatrizCandidatosFin= AjusteSolosEscondidos(MatrizCandidatosFin,traza);//llamada a si misma para que comienze el proceso desde el principio otra vez
					}	
					}
			}
		}	

		//COMPROBACION DE SOLOS ESCONDIDOS EN CAJAS 3X3 
		for (aux1=0;aux1<=2;aux1++) { 
			for (aux2=0;aux2<=2;aux2++) {		
				for (posicion=1;posicion<=9;posicion++) {
				RangoCaja=CeldaEstaEnCaja(3*(aux1+1),3*(aux2+1));
					for (aux3=0;aux3<=2;aux3++) {	//pongo todos los valores de la caja3X3 que corresponda en un array para comprobar
						for (aux4=0;aux4<=2;aux4++) {	
						VectorAux[((3*aux3)+(aux4+1))]=MatrizCandidatosOrigen[(RangoCaja[0][aux3])][(RangoCaja[1][aux4])][posicion];
						}
					}	
				VectorAux=Suma(VectorAux);
				unico=MirarCandidatos(VectorAux,1) ;			
				aux=BuscarUnico(VectorAux);
					if (unico==false) {//No tiene solucion hallada
					continue; 
					} else {
					encontrado=CandidatoEncontrado (MatrizCandidatosOrigen[(RangoCaja[0][(aux-1)/3])][(RangoCaja[1][(aux-1)%3])]);				
					if (encontrado==true) { //ya se habia tratado esta celda 
					continue; 
					} else {
			
						MatrizCandidatosFin= AjusteCandidatosGeneral(MatrizCandidatosOrigen,(RangoCaja[0][(aux-1)/3]),(RangoCaja[1][(aux-1)%3]),posicion);			
						if (traza==true) { //mostrar la secuencia de valores que se añaden
							System.out.println("El candidato "+posicion+" era unico 'escondido en caja' en la fila "+((RangoCaja[0][(aux-1)/3])+1)+" columna "+((RangoCaja[1][(aux-1)%3])+1)+" marca : "+(MatrizCandidatosOrigen[(RangoCaja[0][(aux-1)/3])][(RangoCaja[1][(aux-1)%3])][10]));
							System.out.println();

						}
						MatrizCandidatosFin=AjusteCandidatosSolos(MatrizCandidatosFin,traza);// busco si hay candidatos solos
						MatrizCandidatosFin= AjusteSolosEscondidos(MatrizCandidatosFin,traza);//llamada a si misma para que comienze el proceso desde el principio otra vez
					}
					}
				}
			}
		}			

		return MatrizCandidatosOrigen;//en la ultima llamada no hay candidatos escondidos y devuelve el resultado de la anterior

	}//fin del metodo	AjusteSolosEscondidos		
	
			
	


	public static int[][][] AjusteCandidatosGeneral(int [][][] MatrizCandidatosOrigen,int fila,int columna,int posicion) {
	//pone las filas columnas y cajas3X3 a 0 en la posicion donde se ha hallado el unico candidato 
		int[][][] MatrizCandidatosFin = new int [9][9][11];	
		int[][][] MatrizCandidatosAux = new int [9][9][11];			
		int[][] RangoCaja = new int[2][3];
		int aux1,aux2;
				for (aux1=0;aux1<=8;aux1++) {		
					if(CandidatoEncontrado(MatrizCandidatosOrigen[fila][aux1])==true) {
						continue;
					} else {
						MatrizCandidatosOrigen[fila][aux1][posicion]=0 ;//pongo a 0 las casillas de filas segun SUDOKU 
					}
				}
				for (aux1=0;aux1<=8;aux1++) {		
					if(CandidatoEncontrado(MatrizCandidatosOrigen[aux1][columna])==true) {
						continue;
					} else {
						MatrizCandidatosOrigen[aux1][columna][posicion]=0 ;//pongo a 0 las columnas adjuntas segun SUDOKU 
					}
				}

				RangoCaja=CeldaEstaEnCaja (fila,columna) ;
				for (aux1=0;aux1<=2;aux1++) {		
					for (aux2=0;aux2<=2;aux2++) {		
						if(CandidatoEncontrado(MatrizCandidatosOrigen[RangoCaja[0][aux1]][RangoCaja[1][aux2]])==true) {
							continue;
						} else {
							MatrizCandidatosOrigen[RangoCaja[0][aux1]][RangoCaja[1][aux2]][posicion]=0 ;//pongo a
							// 0 las casillas de la caja 3X3 segun SUDOKU 
						}	
					} 
				}
		MatrizCandidatosOrigen[fila][columna]=Iniciar(MatrizCandidatosOrigen[fila][columna],0);//pongo a 0 los candidatos de la celda (para solos escondidos)
		MatrizCandidatosOrigen[fila][columna][posicion]=1 ;//dejo a 1 el encontrado 
		MatrizCandidatosFin[fila][columna]=AjusteFinalCandidato(MatrizCandidatosOrigen[fila][columna]) ;
		MatrizCandidatosFin=ActualizaCandidatos(MatrizCandidatosOrigen);

		//Ajuste final de cuantos candidatos quedan en cada celda del SUDOKU 		
		return MatrizCandidatosFin;
		 
	}// fin del metodo  AjusteCandidatosGeneral				
		
		
		




	public static int [][][] ActualizaCandidatos (int [][][] MatrizCandidatosOrigen) {
	// Despues de cada ajuste actualiza la matriz de candidatos 
		int fila,columna;
		for (fila=0;fila<=8;fila++) {		
			for (columna=0;columna<=8;columna++) {
			MatrizCandidatosOrigen[fila][columna]=Suma (MatrizCandidatosOrigen[fila][columna]);
			}
		}
		return MatrizCandidatosOrigen;
	} //fin del metodo ActualizaCandidatos			
	





	public static int[][][] VueltaAtras (int[][][]MatrizCandidatosOrigen,int fila,int columna,int posicion,boolean traza) {
	//Escoge un candidato y avanza en la resolucion, si es erroneo retrocede. Hasta que encuentre la solucion 
		int[][][] MatrizAux1= new int[9][9][11];//hace los calculos de sudoku
		int[][][] MatrizAux2= new int[9][9][11];// se usa para la vuelta atras de la matriz
		int[][][] MatrizFin= new int[9][9][11];//sudoku final
		int[][][] MatrizVacia= new int[9][9][11];//para devolver en caso de error
		int[][][] MatrizAnt= new int[9][9][11];//usada para la traza	
		int[]Cero={0,0,0,0,0,0,0,0,0,0,0};//vector de ceros para crear la matriz vacia
		int aux1,aux2,aux3;
		MatrizAux1=CopiaMatriz(MatrizCandidatosOrigen);
		int[] eleccion=EleccionVueltaAtras(MatrizAux1,fila,columna,posicion);
		MatrizVacia=CopiaVector(Cero);
		
		// CASOS TRIVIALES 
		if ((eleccion[0]==0)&&(eleccion[1]==0)&&(eleccion[2]==0)) {
								if (traza==true) {
							  	System.out.println("SUDOKU RESUELTO ");
							  	System.out.println("CANDIDATOS ELEGIDOS (EN ORDEN INVERSO A SU ELECCION)");
								System.out.println();
								}			
		 	MatrizFin=CopiaMatriz(MatrizCandidatosOrigen);	// HEMOS ENCONTRADO LA SOLUCION DEL SUDOKU
		} else {
			
			if ((eleccion[0]==0)&&(eleccion[1]==0)&&(eleccion[2]==10)) {
								if (traza==true) {
								System.out.println ("Error en los calculos o el SUDOKU. Vuelta atras a la situacion anterior resuelta");
								}			
				MatrizFin=CopiaMatriz(MatrizVacia);	// SE HA HECHO UN RECORRIDO ERRONEO
			} else {	

			// CASOS NO TRIVIALES 
			aux1=eleccion[0];//fila escogida
			aux2=eleccion[1];//columna escogida
			aux3=eleccion[2];//posicion escogida 
			Iniciar(MatrizAux1[aux1][aux2],0);//					 me preparo para
			MatrizAux1[aux1][aux2][aux3]=1;//					 comprobar si el candidato elegido
			MatrizAux1[aux1][aux2]=Suma(MatrizAux1[aux1][aux2]);//		 es valido
			MatrizAnt=CopiaMatriz(MatrizAux1);//guardo una copia por si es correcto y se pide hacer la traza
			MatrizAux1=AjusteCandidatosSolos(MatrizAux1,false);// resuelvo el candidato solo que acabo de establecer
			//MatrizAux1=AjusteSolosEscondidos(MatrizAux1,false);//hago el resto de ajustes solos escondidos,etc..	// QUEDA AQUI POR SI SE QUIERE PROBAR SOLOS ESCONDIDOS(QUITAR // INICIAL)

				// SITUACION DE PODA EL CANDIDATO ELEGIDO NO ES VALIDO, DEJA CELDAS SIN CANDIDATOS
				if (ComprobarCandidatos(MatrizAux1)==false) {
								if (traza==true) {
								System.out.println("Candidato "+aux3+" seleccionado en fila "+(aux1+1)+" columna "+(aux2+1)+" con algoritmo de vuelta atras NO ES VALIDO. Buscamos otro. ");
								System.out.println();
								}
				MatrizFin=VueltaAtras(MatrizCandidatosOrigen,aux1,aux2,aux3,traza);

				//CASOS NO TRIVIALES			
				} else {		
								if (traza==true) {
								System.out.println("Candidato "+aux3+" seleccionado en fila "+(aux1+1)+" columna "+(aux2+1)+" con algoritmo de vuelta atras. ");
								System.out.println();
								MatrizAnt=AjusteCandidatosSolos(MatrizAnt,true);
								//MatrizAnt=AjusteSolosEscondidos(MatrizAnt,true);// QUEDA AQUI POR SI SE QUIERE PROBAR SOLOS ESCONDIDOS(QUITAR // INICIAL)
								SalidaDatos(MatrizAnt);
								System.out.println();
								System.out.println("Ahora se procede a recalcular los candidatos .");
								}

				MatrizAux2=VueltaAtras(MatrizAux1,0,0,0,traza);// busco hacia adelante la siguiente solucion posible
				
					if (ComprobarCeros(MatrizAux2)==true) {
					//el Sudoku no acaba correctamente y hay que volver atras
					MatrizFin=VueltaAtras(MatrizCandidatosOrigen,aux1,aux2,aux3,traza);
					} else {
					//el Sudoku acaba correctamente y devuelve el resultado						
				 	MatrizFin=CopiaMatriz(MatrizAux2);
					}
				}
			}
		}		
	return MatrizFin;
	}//fin del metodo VueltaAtras			
		
		
		




	public static int[] EleccionVueltaAtras (int[][][]MatrizCandidatosOrigen,int fila,int columna,int posicion) {
	//SITUACION DE PODA tiene que escoger un candidato (mayor de 1); de entre todas las celdas la que tenga menor,para evitar el mayor numero de busquedas
	int[] eleccion= new int [3];
	int aux1,aux2,aux3,aux4,aux5,cand;
	cand=2;
	aux3=2;
	if ((fila==0)&&(columna==0)&&(posicion==0)) {
		while ((cand+1)>aux3) {// Busco celdas no resueltas con el minimo de candidatos,o sea empezando por 2 
			for (aux1=0;aux1<=8;aux1++) {
				for (aux2=0;aux2<=8;aux2++) {
				 if (CandidatoEncontrado(MatrizCandidatosOrigen[aux1][aux2])==true) {// ya esta resuelta 
				 continue;
				 } else {
				 	cand=MatrizCandidatosOrigen[aux1][aux2][0];//tiene estos candidatos. de esta forma siempre escojera una celda
				 	if (cand>aux3) {//busco la celda con menos candidatos del sudoku 
				 	continue;
				 	} else {
				 		for(aux4=posicion+1;aux4<=9;aux4++) {//escojo el menor candidato que sea mayor que posicion 
				 			if (ComprobarPosicion(MatrizCandidatosOrigen[aux1][aux2],aux4)==true) {
				 			eleccion[0]=aux1;// fila , 
				 			eleccion[1]=aux2;//  columna y 
				 			eleccion[2]=aux4;//  posicion a devolver con el candidato elegido 
				 			return eleccion;
				 			}
				 		}//fin de la iteracion en las posiciones de los candidatos	
				 	}
				 }	
				}// fin de la iteracion en las columnas 
			}// fin de la iteracion en las filas
			aux3++;
		}//fin del while	
		eleccion[0]=0;// fila , 
		eleccion[1]=0;//  columna y 
		eleccion[2]=0;//  posicion a devolver con 0 porque ya esta resuelto, no hay candidatos 
		return eleccion;
	}else {
		for (aux5=(posicion+1);aux5<=9;aux5++) {
			if (ComprobarPosicion(MatrizCandidatosOrigen[fila][columna],aux5)==true) {
			eleccion[0]=fila; //fila ,
			eleccion[1]=columna; //  columna y 
			eleccion[2]=aux5; //posicion a devolver con el candidato elegido
			return eleccion;	
			}
		}
		eleccion[0]=0;// fila , 
		eleccion[1]=0;//  columna y 
		eleccion[2]=10;//  posicion a devolver con 10 porque existe un error, no hay candidatos hay que volver atras 
		return eleccion;
	}
	}//fin del metodo EleccionVueltaAtras 
		
		
	
} // Fin de la clase OperacionesSudoku








 
				
				



class Util {
// clase con funciones sencillas y datos utiles

	static int xaux; //variable auxiliar

	
	public static int[]  Iniciar (int[] Matriz,int inicio) { //inicializa el vector base al valor de inicio
		Arrays.fill(Matriz,1,10,inicio);
		Matriz[10]=0;
		return Matriz;
	} //fin del metodo Iniciar 


	
	public static int[]  Suma (int[] Matriz) {// pone en la celda 0 el valor de la suma de las celdas 1-9
		int sumaux; //variable auxiliar 
		sumaux=0;
		for (xaux=1;xaux<10;xaux++) {
		sumaux=(sumaux+(int)Matriz[xaux]);
		}
		Matriz[0]=sumaux;
		return Matriz;
	} //fin del metodo Suma
 

	
	public static int[]  AjusteFinalCandidato (int[] Matriz) { //despues de una seleccion hace la suma y pone un 1 en la celda 10
		int[] Matrizaux=new int[11];									//para identificarlo como hallado ya
		Matrizaux = Suma (Matriz);
		if (Matrizaux[0]==1) {
			Matrizaux[10]=1;
		}
		return Matrizaux;
	} //fin del metodo AjusteFinalCandidato
	

	public static boolean MirarCandidatos (int[] Matriz,int candidatos) { //devuelve cierto si tiene los candidatos que se pasan 
		if (Matriz[0]==candidatos) {
			return true;
		}
		return false ;
	} //fin del metodo
	

	public static boolean ComprobarPosicion (int[] Matriz,int posicion) { //devuelve cierto si la posicion tiene un candidato
		if (Matriz[posicion]==1) { 	
			return true;
		}
		return false ;
	} //fin del metodo ComprobarPosicion


	public static boolean CandidatoEncontrado (int[] Matriz) { //devuelve cierto si el candidato esta 
		// y se habia hallado con anterioridad es decir la celda 10 vale 1 
		if ((Matriz[0]==1)&&(Matriz[10]==1)) { 	
			return true;
		}
		return false ;
	} //fin del metodo CandidatoEncontrado
	

	public static int BuscarUnico (int[] Matriz) { // busca donde esta el unico candidato
		if (MirarCandidatos(Matriz,1)==true) {
			for (xaux=1;xaux<10;xaux++) {
				if (ComprobarPosicion(Matriz,xaux)==true) {
				return xaux;
				}
			}	
		} 
		return 0;// No tiene candidato unico
	} //fin del metodo BuscarUnico						
			

	public static boolean ComparaElementos (int[] Matriz,int elemento) { //busca si existe mas de un candidato igual a elemento
		int igual=0;
		for (xaux=1;xaux<10;xaux++) {
			if (Matriz[xaux]==elemento) {
			igual++;
			}
		}	
		if (igual>1) {
			return false; //hay mas de un candidato igual y devuelve falso
			}
		return true; //hay uno o ningun candidato igual y devuelve cierto
	} //fin del metodo ComparaElementos
	

	public static boolean RangoCandidato (int[] Matriz,int minimo,int maximo) { //calcula si los candidatos estan en un rango 
		int maux;
		for (xaux=1;xaux<10;xaux++) {
		maux=(int)Matriz[xaux];
			if ((maux<minimo)||(maux>maximo)) {
			return false; // Estan fuera del rango y devuelve falso 
			}
		}
		return true;// Estan dentro del rango y devuelve cierto
	} //fin del metodo  RangoCandidato 
	


	public static int[][] CeldaEstaEnCaja (int Fila,int Columna) { //Dada una celda establece cuales son las de su caja 3X3
		int[][] RangoCelda = new int[2][3];
		int xaux;
		xaux = (((Fila+3)/3)*((Columna+9)/3)); // Formula para identificar la caja de cada celda
		switch (xaux) {  //Se asignan los valores de fila y columna de cada caja 
			case 3: 	RangoCelda[0][0]=0 ;
					RangoCelda[0][1]=1 ;
					RangoCelda[0][2]=2 ;
					RangoCelda[1][0]=0 ;
					RangoCelda[1][1]=1 ;
					RangoCelda[1][2]=2 ;
					break; 
			case 4: 	RangoCelda[0][0]= 0;
					RangoCelda[0][1]= 1;
					RangoCelda[0][2]= 2;
					RangoCelda[1][0]= 3;
					RangoCelda[1][1]= 4;
					RangoCelda[1][2]= 5;
					break; 
			case 5: 	RangoCelda[0][0]=0 ;
					RangoCelda[0][1]=1 ;
					RangoCelda[0][2]=2 ;
					RangoCelda[1][0]=6 ;
					RangoCelda[1][1]=7 ;
					RangoCelda[1][2]=8 ;
					break; 
			case 6: 	RangoCelda[0][0]= 3;
					RangoCelda[0][1]= 4;
					RangoCelda[0][2]= 5;
					RangoCelda[1][0]= 0;
					RangoCelda[1][1]= 1;
					RangoCelda[1][2]= 2;
					break; 
			case 8: 	RangoCelda[0][0]= 3;
					RangoCelda[0][1]= 4;
					RangoCelda[0][2]= 5;
					RangoCelda[1][0]= 3;
					RangoCelda[1][1]= 4;
					RangoCelda[1][2]= 5;
					break; 
			case 9: 	RangoCelda[0][0]=6 ;
					RangoCelda[0][1]=7 ;
					RangoCelda[0][2]=8 ;
					RangoCelda[1][0]=0 ;
					RangoCelda[1][1]=1 ;
					RangoCelda[1][2]=2 ;
					break; 
			case 10:	RangoCelda[0][0]= 3;
					RangoCelda[0][1]= 4;
					RangoCelda[0][2]= 5;
					RangoCelda[1][0]= 6;
					RangoCelda[1][1]= 7;
					RangoCelda[1][2]= 8;
					break; 
			case 12:	RangoCelda[0][0]=6 ;		
					RangoCelda[0][1]=7 ;
					RangoCelda[0][2]=8 ;
					RangoCelda[1][0]=3 ;
					RangoCelda[1][1]=4 ;
					RangoCelda[1][2]=5 ;
					break; 
			case 15:	RangoCelda[0][0]= 6;
					RangoCelda[0][1]= 7;
					RangoCelda[0][2]= 8;
					RangoCelda[1][0]= 6;
					RangoCelda[1][1]= 7;
					RangoCelda[1][2]= 8;
					break; 
			default:		break;
		}
		return RangoCelda;
	} // Fin del metodo	 CeldaEstaEnCaja




	public static boolean ComprobarCandidatos(int[][][]Matriz) {
	//comprueba que existen candidatos, 1 o mas, en todas las celdas 
	int fila,columna;
		for (fila=0;fila<=8;fila++) {
			for (columna=0;columna<=8;columna++) {
				if (Matriz[fila][columna][0]==0) {
				return false;
				}
			}
		}		
		return true;
	}//fin del metodo ComprobarCandidatos	
	

	public static boolean ComprobarCeros(int[][][]Matriz) { 
	//comprueba en todas las celdas que no existan candidatos
	int fila,columna,posicion;
		for (fila=0;fila<=8;fila++) {
			for (columna=0;columna<=8;columna++) {
				for (posicion=0;posicion<=10;posicion++) {
					if (Matriz[fila][columna][posicion]!=0) {
					return false;
					}
				}
			}		
		}
		return true;
	}//fin del metodo ComprobarCeros	
	
	
		
	public static void SalidaDatos(int[][][]Matriz) {
	int fila,columna;
	int[][]MatrizResultado=new int[9][9];
	System.out.println();
	System.out.println("F");
	System.out.println("I");
	System.out.println("L      COLUMNAS       ");
	System.out.println("A");
	System.out.println("S  1 2 3 4 5 6 7 8 9  ");
	System.out.println();
	MatrizResultado=ConvierteResultado(Matriz);
		for (fila=0;fila<=8;fila++) {
			System.out.print((fila+1)+"  ");
			for (columna=0;columna<=8;columna++) {
			System.out.print(MatrizResultado[fila][columna]+" ");
		 	}
		 	System.out.println(" ");
		 }
		 System.out.println();

		 return; 
	}//fin del metodo SalidaDatos	 	
	
	
	


	public static int[][] ConvierteResultado(int[][][]Matriz) {
	//convierte una matriz de candidatos este como este en una matriz de resultados
	int fila,columna;
	int[][]MatrizResultado=new int[9][9];
		for(fila=0;fila<=8;fila++) {
			for (columna=0;columna<=8;columna++) {
				if (CandidatoEncontrado(Matriz[fila][columna])==true) {
				MatrizResultado[fila][columna]=BuscarUnico(Matriz[fila][columna]);//pone el resultado en cada celda
				}else {  									
				MatrizResultado[fila][columna]=0;	 //y si no se ha obtenido aun pone un 0 
				}
			}	
		}
		return MatrizResultado;
	}//fin del metodo ConvierteResultado			

	public static int[][][] CopiaMatriz(int[][][]Matriz) {
	int[][][] MatrizAux=new int [9][9][11];//hace una copia de una matriz
	int fila,columna;
		for(fila=0;fila<=8;fila++) {
			for (columna=0;columna<=8;columna++) {
				System.arraycopy(Matriz[fila][columna],0,MatrizAux[fila][columna],0,11);
		}	
	}
	return MatrizAux;
	}//fin del metodo CopiaMatriz			

	public static int[][][] CopiaVector(int[]Matriz) {
	int[][][] MatrizAux=new int [9][9][11];//hace una copia de un vector en toda la matriz
	int fila,columna;
		for(fila=0;fila<=8;fila++) {
			for (columna=0;columna<=8;columna++) {
				System.arraycopy(Matriz,0,MatrizAux[fila][columna],0,11);
		}	
	}
	return MatrizAux;
	}//fin del metodo CopiaVector			

} //fin de la clase Util
