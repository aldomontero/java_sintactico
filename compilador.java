/**
 * @(#)Ronny
 *
 *
 * @author Montero
 * @version 1.00 2009/6/10
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.util.regex.*;

import java.io.*;

public class compilador extends JFrame{

	private static final long serialVersionUID = 1L;
	
	JTextArea codigo = new JTextArea();
	JTextArea result = new JTextArea();
	JToolBar barra = new JToolBar();
	JSplitPane areaTrabajo;
	
    private JMenuItem abrir;
    private JMenuItem acercade;
    private JMenu archivo;
    private JMenu ayuda;
    private JMenuItem compilar;
    private JMenuBar jMenuBar1;
    private JSeparator jSeparator1;
    private JMenu proyecto;
    private JMenuItem salir;
    
	JLabel tCodigo;
	
	String[][] reserveWords = {{"INCLUYE","'Incluye libreria'"},
							{"SALTA","'Instruccion de salto'"},
							{"RONNY_INICIO","'Incio del programa'"},
							{"RONNY_FIN","'Fin del programa'"},
							{"IR","'Instruccion de direccion'"},
							{"COMIENZA","'Inicio de metodo'"},
							{"TERMINA","'Termina un metodo'"},
							{"REPITE","'Ciclo repititivo'"},
							{"LIMPIA","'Instruccion de limpieza de pantalla'"},
							{"LEE","'Lectura de dato'"},
							{"CARGA","'Carga de un dato'"},
							{"IMPRIME","'Impresion pantalla'"},
							{"CONSTANTE","'Variable de tipo constante'"},
							{"GLOBAL","'Objeto publico'"},
							{"OCULTO","'Objeto privado'"},
							{"RETORNA","'Instruccion de retorno'"},
							{"CAJA_DATOS","'Arreglo de datos'"},
							{"INICIO","'Metodo de inicio'"},
							{"PALABRA","'Variable de tipo palabra'"},
							{"ENTERO","'Variable de tipo numero'"},
							{"FLOTANTE","'Variable de tipo flotante'"},
							{"DECIDE","'Variable de tipo booleana'"},
							{"FIN","'Instruccion de finalizacion'"},
							{"FIN_CICLO","'Final de ciclo'"},
							{"MIENTRAS","'Ciclo repetitivo Mientras'"},
							{"MANDA","'Envio de informacion'"},
							{"INC1","'Incremento de 1 unidad'"},
							{"SI","'Condicion Si'"},
							{"ADEMAS","'Condicion adicional al SI'"},
							{"NULO","'Valor Indefinido'"},
							{"PARA","'Ciclo repetitivo Para'"},
							{"LLAMAR","'Ejecuta un metodo'"},
							{"COMPARA","'Funcion Comparacion'"},
							{"SELECCION_REGION","'Funcion de seleccion de Cadena'"},
							{"VERDAD","'Valor tipo Verdadero'"},
							{"FALSO","'Valor tipo Falso'"}};
							
	String[][] opRacional = {{"</","'Comentario'"},
							{"/>","'Fin comentario'"},
							{">=","'Mayor o igual que'"},
							{"==","'Igual que'"},
							{"=!","'Diferente de'"},
							{"=&","'Asignar sumando'"},
							{"<=","'Menor o igual que'"},
							{"&&","'Concatena cadenas'"},
							{"--","'Decremento'"},
							{"=#","'Multiplicación mas asignación'"},
							{"=/","'División mas Asignación'"},
							{"#%","'Modulo más asignación'"},
							{"=<","'Desplazamiento a la izquierda y Asignación'"},
							{"=>","'Desplazamiento a la derecha y Asignación'"},
							{"=-","'Asignar restando'"}};
	
	Object[][] opAritmetico = {{'<',"'Menor que'"},
							  {'+',"'Mas'"},
							  {'>',"'Mayor que'"},
							  {'=',"'Asignacion'"},
							  {'|',"'Operador logico o'"},
							  {'&',"'Operador logico y'"},
							  {'@',"'Metodo'"},
							  {'-',"'Menos'"},
							  {'*',"'Por'"},
							  {'/',"'Entre'"},
							  {'(',"'Abre agrupa sentencia o expresion'"},
							  {')',"'Cierra agrupa sentencia o expresion'"},
							  {'[',"'Abre longitud de arreglo'"},
							  {']',"'Cierra longitud de arreglo'"},
							  {'^',"'Potencia'"}};

	String var = "Es una variable";
	
    public compilador() {
    	
    	super("Ronny");

		result.setEditable(false);
		
		ImageIcon imgCompilar = crearIcono("compilar.gif");
		ImageIcon imgNuevo = crearIcono("nuevo.gif");
		ImageIcon imgAbrir = crearIcono("abrir.gif");
		ImageIcon imgCerrar = crearIcono("cerrar.gif");
		ImageIcon imgLimpiar = crearIcono("limpiar.gif");
		
		JButton btnNuevo = new JButton(imgNuevo);
		btnNuevo.setToolTipText("Nuevo código");
		JButton btnAbrir = new JButton(imgAbrir);
		btnAbrir.setToolTipText("Abrir código");
		JButton btnCompilar = new JButton(imgCompilar);
		btnCompilar.setToolTipText("Compilar proyecto");
		JButton btnSalir = new JButton(imgCerrar);
		btnSalir.setToolTipText("Cerrar");
		JButton btnLimpiar = new JButton(imgLimpiar);
		btnLimpiar.setToolTipText("Limpiar consola de salida");
		
		barra.add(btnNuevo);
		barra.add(btnAbrir);
		barra.add(btnLimpiar);
		barra.addSeparator();
		barra.add(btnCompilar);
		barra.addSeparator();
		barra.add(btnSalir);
		
		jMenuBar1 = new javax.swing.JMenuBar();
        archivo = new javax.swing.JMenu();
        abrir = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        salir = new javax.swing.JMenuItem();
        proyecto = new javax.swing.JMenu();
        compilar = new javax.swing.JMenuItem();
        ayuda = new javax.swing.JMenu();
        acercade = new javax.swing.JMenuItem();

        archivo.setText("Archivo"); // NOI18N
        abrir.setText("Abrir código..."); // NOI18N
        archivo.add(abrir);
        archivo.add(jSeparator1);
        salir.setText("Salir"); // NOI18N
        archivo.add(salir);
        jMenuBar1.add(archivo);
        proyecto.setText("Proyecto"); // NOI18N
        compilar.setText("Compilar"); // NOI18N
        proyecto.add(compilar);
        jMenuBar1.add(proyecto);
        ayuda.setText("Ayuda"); // NOI18N
        acercade.setText("Acerca de..."); // NOI18N
        ayuda.add(acercade);

        jMenuBar1.add(ayuda);

        setJMenuBar(jMenuBar1);
        
		tCodigo = new JLabel("Código: <nuevo.txt>");
		JLabel tResultado = new JLabel("Salida general <Compilación>:");
		
		tCodigo.setBorder((BorderFactory.createEmptyBorder(10,10,10,10)));
		tResultado.setBorder((BorderFactory.createEmptyBorder(10,10,10,10)));
		tCodigo.setFont(new Font("Arial",Font.PLAIN,12));
		tResultado.setFont(new Font("Arial",Font.PLAIN,12));
		
		JScrollPane areaCodigo = new JScrollPane(codigo);
		JScrollPane areaResultado = new JScrollPane(result);

		JPanel divisionCodigo = new JPanel(new BorderLayout());
		JPanel divisionResultado = new JPanel(new BorderLayout());
		
		areaTrabajo = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		divisionCodigo.add(tCodigo,BorderLayout.NORTH);
		divisionCodigo.add(areaCodigo,BorderLayout.CENTER);
		
		divisionResultado.add(tResultado,BorderLayout.NORTH);
		divisionResultado.add(areaResultado,BorderLayout.CENTER);

		areaTrabajo.add(divisionCodigo);
		areaTrabajo.setDividerSize(3);
		areaTrabajo.setDividerLocation(270);
		areaTrabajo.add(divisionResultado);
		
		compilar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				analizar();
			}
		});
		
		abrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirArchivo();
			}
		});
		
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeText();
			}
		});
		
		salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnNuevo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				tCodigo.setText("Codigo: <nuevo.txt>");
				removeCodig();
				removeText();
			}
		});
		
		btnAbrir.addActionListener(abrir.getActionListeners()[0]);
		btnCompilar.addActionListener(compilar.getActionListeners()[0]);
		btnSalir.addActionListener(salir.getActionListeners()[0]);
		
		acercade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String about = "<html><font style='font-weight:normal'><h3>Acerca de analizador<br>Ronny 1.4<br><br></h3>"+
							   "Programador y diseñador:<br>Aldo de Jesus Emmanuel Montero Murillo<br> <br> <br> <br><br>(c) 2009 Montero Software. Marcas registradas"+
							   "";
				JOptionPane.showMessageDialog(compilador.this,about,"Acerca del programa",JOptionPane.PLAIN_MESSAGE,null);
			}
		});
		
		getContentPane().add(barra,BorderLayout.NORTH);
		getContentPane().add(areaTrabajo,BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(500, 550));
		setLocationRelativeTo(null);
		setResizable(true);
    }
    
    private void analizar(){
    	
   		removeText();
		String codigoLinea[] = cortadorLineas(codigo.getText());

		for(int n = 0; n < codigoLinea.length; n++){
			analizarLinea(reformato(codigoLinea[n]));
		}
			
    }
    
    private String[] cortadorLineas(String cadena){
		
		String REGEX = "\n";

    	Pattern p = Pattern.compile(REGEX);
    	String[] lineas = p.split(cadena);
    	
    	return lineas;
    }
    
    private String reformato(String cadena){

    	char[] temp = cadena.toCharArray();
    	String s = "";
    	boolean noEncontro;
    	
    	for(int n = 0; n < temp.length; n++){
    		noEncontro = true;
    		for(int m = 0; m < opAritmetico.length;m++)
    			if(temp[n] == ((Character)opAritmetico[m][0]).charValue()){
    				s += " " + temp[n] + " ";
    				noEncontro = false;
    			}
    		if(noEncontro)
    			s += temp[n];
    	}

    	for(int n = 0; n < opRacional.length; n++){
    		if(s.indexOf(opRacional[n][0].charAt(0)+"  "+opRacional[n][0].charAt(1)) > 0)
    			s = s.replaceAll(opRacional[n][0].charAt(0)+"  "+opRacional[n][0].charAt(1)," "+opRacional[n][0]+" ");
    		else if(s.indexOf(opRacional[n][0]) > 0)
    			s = s.replaceAll(opRacional[n][0]," "+opRacional[n][0]+" ");
    		else if(s.indexOf(opRacional[n][0].charAt(0)+" "+opRacional[n][0].charAt(1)) > 0)
    			s = s.replaceAll(opRacional[n][0].charAt(0)+" "+opRacional[n][0].charAt(1)," "+opRacional[n][0]+" ");
    	}

    	return s;
    }
    
    @SuppressWarnings("deprecation")
	private void abrirArchivo(){

    	JFileChooser open = new JFileChooser();
    	
    	open.setMultiSelectionEnabled(false);

    	if(open.showOpenDialog(compilador.this) == JFileChooser.APPROVE_OPTION){
			File fil = open.getSelectedFile();
			FileInputStream f = null;
			
			try{
				f = new FileInputStream(fil);
				tCodigo.setText("Código: <"+fil.getAbsoluteFile()+">");
			} catch (Exception e){
				System.out.println("Error al encontrar o leer archivo");
			}
			
			DataInputStream d = new DataInputStream(f);
			String linea;
			
			try{
				removeCodig();
				removeText();
				while ((linea = d.readLine())!= null){
					codigo.append(linea+"\n");;
				}
			} catch (FileNotFoundException e) {
		        System.err.println("Error: " + e);
		        //System.exit(-1);
		    } catch (EOFException e){
		    } catch (IOException e) {
		        System.err.println("Error: " + e);
		        //System.exit(-1);
		    }
			
			try{
				d.close();
			} catch(Exception e){
				System.out.println("No se puede cerrar el archivo");
			}
    	}
    }
    
    private void analizarLinea(String cadena){
		
		String REGEX = " ";
   		boolean hayAnteriores;
   		
    	Pattern p = Pattern.compile(REGEX);
    	String[] sentencias = p.split(cadena);
    	
    	addLine("");
    	
    	for(int n = 0; n < sentencias.length; n++){
    		
    		//Saber si es palabra reservada
    		
    		hayAnteriores = false;
    		String f = "xx";
    		
    		if(sentencias[n].length() > 1)
    			f = sentencias[n].substring(0,2);
    			
    		if(sentencias[n].length() > 0){

    			if(f.matches(opRacional[0][0])){
    				addText("Comentario:"+sentencias[n]);
    				n = sentencias.length-1;
    			} else {
    		
		    		if(sentencias[n].charAt(0) == ((Character)opAritmetico[11][0]).charValue()){
		    			
		    			addText((String)opAritmetico[11][1]+":"+opAritmetico[11][0]);
		    			n = sentencias.length;
		    			
		    		} else {
	
			    		if(sentencias[n].length() == 1)
				    		for(int m = 0; m < opAritmetico.length; m++){
								if(sentencias[n].charAt(0) == ((Character)opAritmetico[m][0]).charValue()){
									addText((String)opAritmetico[m][1]+":"+opAritmetico[m][0]);
									hayAnteriores = true;
								}
				    		}
			    		
			    		if(sentencias[n].length() == 2)
				    		for(int m = 0; m < opRacional.length; m++){
								if(sentencias[n].matches(opRacional[m][0])){
									addText(opRacional[m][1]+":"+(String)opRacional[m][0]);
									hayAnteriores = true;
								}
				    		}
			    		
			    		if(sentencias[n].length() > 2)
				    		for(int m = 0; m < reserveWords.length; m++){
								if(sentencias[n].matches(reserveWords[m][0])){
									addText(reserveWords[m][1]+":"+(String)reserveWords[m][0]);
									hayAnteriores = true;
								}
				    		}
				    	
				    	if(!hayAnteriores)
				    		addText(evaluarCadena(sentencias[n])+":"+sentencias[n]);
			    	}
    			}
    		}
    	}
    }
    
    private String evaluarCaracter(char caracter){
    	String result = "'Caracter'";
    	
		char[] numeros = {'0','1','2','3','4','5','6','7','8','9'};
		
		for(int n = 0; n < numeros.length; n++)
			if(numeros[n] == caracter)
				return "'Numero entero'";
				
    	return result;
    	
    }
    
    private String evaluarCadena(String cadena){
    	String result = "'Nombre (Metodo, variable o parametro)'";
    	
    	if(cadena.length() == 1){
    		return evaluarCaracter(cadena.charAt(0));
    	}
    	
    	if(cadena.charAt(0) == '"' && cadena.charAt(cadena.length()-1) == '"'){
    		return "'Cadena de texto'";
    	}
    	
    	@SuppressWarnings("unused")
		Object s;
    	
    	if(cadena.charAt(0) == '-'){
    		try{
    			s = Integer.parseInt(cadena);
    			return "Numero entero negativo";
    		} catch (Exception f){
	    		try{
	    			s = Float.parseFloat(cadena);
	    			return "Numero real negativo";
	    		} catch (Exception e){
	    		}
    		}

    	} else {
			try{
				s = Integer.parseInt(cadena);
				return "Numero entero";
			} catch (Exception f){
				try{
					s = Float.parseFloat(cadena);
	    			return "Numero real";
	    		} catch (Exception e){
	    		}
			}
    	}
    	
    	return result;
    }
    
    private void addText(String texto){
    	result.append(" "+texto);
    }
    
    private void addLine(String texto){
    	result.append(texto+"\n");
    }
    
    private void removeText(){
    	result.setText("");
    }
    
    private void removeCodig(){
    	codigo.setText("");
    }
    
    public static void main(String args[]){
    	new principal();
    }
    
    public static ImageIcon crearIcono(String path) {
		java.net.URL imgURL = principal.class.getResource(path);
		if (imgURL != null)
		    return new ImageIcon(imgURL);
		else
		    return null;
    }
}

