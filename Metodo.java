package Archivos;

import java.io.*;
import javax.swing.JOptionPane;

public class Metodo {
	String nombre;
	int clave;
	double sueldo;

	String ruta = "C:\\Users\\navar\\Documents\\hola.txt";
	File Registros = new File(ruta, "Registros.dat");
	File Registros2 = null;
	File Registros3 = null;
	static int num = 0;
	//metodo para crear el archivo
	public void crearArchivo() {
		try {
			if(!Registros.exists()) {
				Registros.createNewFile();
				JOptionPane.showMessageDialog(null, Registros.getName() + " El archivo ha sido creado sastifactoriamente");

			}

		}catch(IOException e) {
			e.printStackTrace();

		}

	}
	//metodo para ingresar informacion al archivo
	public void ingresarDatos() throws FileNotFoundException, IOException  {
		String resp  = "si";
		while(resp.equals("si")) {
			try (DataOutputStream re = new DataOutputStream(new FileOutputStream(Registros, true))) {
				clave = Integer.parseInt(JOptionPane.showInputDialog("Porfavor Ingrese una clave al empleado "));
				nombre = JOptionPane.showInputDialog("Porfavor Ingrese nombre del empelado ");
				sueldo = Double.parseDouble(JOptionPane.showInputDialog("Porfavor Ingrese sueldo del empleado "));
				re.writeUTF(nombre);
				re.writeInt(clave);
				re.writeDouble(sueldo);
				resp = JOptionPane.showInputDialog(null, "¿Desea agregar más Registros al archivo? conteste (si) o (no)");
				re.close();

			}

		}

	}

	public void leerArchivo() throws IOException{
		DataInputStream ob;
		FileInputStream  obaux = new FileInputStream(Registros);
		ob = new DataInputStream(obaux);
	        try {
	        	System.out.println("Nombre" + "\t\t" + "Clave" + "\t\t" + "Sueldo" + "\t\t");
	        	while (obaux.available() != 0) {
	                System.out.print(ob.readUTF() + "\t\t");
	                System.out.print(ob.readInt()+ "\t\t");
	                System.out.print(ob.readDouble()+ "\t\t");
	                System.out.println();
	                num++;
	        	}

	        } catch (EOFException ex) {
	        	JOptionPane.showMessageDialog(null,"Archivo leido");
	        	} finally {
	        		try {
	        			ob.close();

	        		} catch (Exception e){
	        			JOptionPane.showMessageDialog(null, "cerrar");
	        		}

	        	}
	}

	public void metodoOrdenar() throws IOException, FileNotFoundException, ClassNotFoundException {
        int ar = (int) Registros.length();
        meclaDiecta(ar);
        System.out.println("Metodo de ordenacion Por Mezcla Directa ");
        leerArchivo();
    }
    private void meclaDiecta(int lo) throws FileNotFoundException, IOException, ClassNotFoundException {
    	Registros2 = new File(ruta, "Registros2.dat");
    	Registros3 = new File(ruta, "Registros3 2.dat");
        int divide = 1;
        while (divide < ((lo + lo) / 2)) {
            particiona(divide, Registros2, Registros3);
            fusionaArchivo(divide, Registros2, Registros3);
            divide = divide * 2;
        }
    }


	    private void particiona(int part, File archivo2, File archivo3) throws FileNotFoundException, IOException, ClassNotFoundException {
	        int p;
	        FileInputStream flu = new FileInputStream(Registros);
	        DataInputStream flu2 = new DataInputStream(flu);
	        DataOutputStream arch2 = new DataOutputStream(new FileOutputStream(archivo2));
	        DataOutputStream arch3 = new DataOutputStream(new FileOutputStream(archivo3));
	        //Registro r = null;
	        String aux1;
	        int aux2;
	        double aux3;

	        while (flu.available() > 0) {
	            p = 0;
	            while ((p < part) && flu.available() > 0) {
	                aux1 = flu2.readUTF();
	                aux2 = flu2.readInt();
	                aux3 = flu2.readDouble();
	                arch2.writeUTF(aux1);
	                arch2.writeInt(aux2);
	                arch2.writeDouble(aux3);
	                p++;
	            }
	            int l = 0;
	            while ((l < part) && flu.available() > 0) {
	                aux1 = flu2.readUTF();
	                aux2 = flu2.readInt();
	                aux3 = flu2.readDouble();
	                arch3.writeUTF(aux1);
	                arch3.writeInt(aux2);
	                arch3.writeDouble(aux3);
	                l++;
	            }
	        }
	    }

	    private void fusionaArchivo(int part, File archivo2, File archivo3) throws FileNotFoundException, IOException, ClassNotFoundException {
	        DataOutputStream RE = new DataOutputStream(new FileOutputStream(Registros));
	        FileInputStream arch2 = new FileInputStream(archivo2);
	        DataInputStream ar3 = new DataInputStream(arch2);
	        FileInputStream arch4 = new FileInputStream(archivo3);
	        DataInputStream sa = new DataInputStream(arch4);
	        int k, l;
	        String nombre1 = "";
	        String nombre2 = "";
	        int clave1 = 0;
	        int clave2 = 0;
	        double sueldo1 = 0.0;
	        double sueldo2 = 0.0;

	        boolean b1 = true;
	        boolean b2 = true;
	        if (arch2.available() > 0) {
	        	nombre1 = ar3.readUTF();
	        	clave1 = ar3.readInt();
	        	sueldo1 = ar3.readDouble();
	            b1 = false;
	        }
	        if (arch4.available() > 0) {
	        	nombre2 = sa.readUTF();
	        	clave2 = sa.readInt();
	        	sueldo2 = sa.readDouble();
	            b2 = false;
	        }
	        while (((arch2.available() > 0) || (!b1)) && ((arch4.available() > 0) || (!b2))) {
	            k = 0;
	            l = 0;
	            while ((k < part) && (!b1) && ((l < part) && (!b2))) {
	                if (clave1 <= clave2) {
	                	RE.writeUTF(nombre1);
	                	RE.writeInt(clave1);
	                	RE.writeDouble(sueldo1);
	                    b1 = true;
	                    k++;
	                    if (arch2.available() > 0) {
	                    	nombre1 = ar3.readUTF();
	                    	clave1 = ar3.readInt();
	                    	sueldo1 = ar3.readDouble();
	                        b1 = false;
	                    }
	                } else {
	                	RE.writeUTF(nombre2);
	                	RE.writeInt(clave2);
	                	RE.writeDouble(sueldo2);
	                    b2 = true;
	                    l++;
	                    if (arch4.available() > 0) {
	                    	nombre2 = sa.readUTF();
	                    	clave2 = sa.readInt();
	                    	sueldo2 = sa.readDouble();
	                        b2 = false;
	                    }
	                }
	            }
	            while ((k < part) && (!b1)) {
	            	RE.writeUTF(nombre1);
	            	RE.writeInt(clave1);
	            	RE.writeDouble(sueldo1);
	                b1 = true;
	                k++;
	                if (arch2.available() > 0) {
	                	nombre1 = ar3.readUTF();
	                	clave1 = ar3.readInt();
	                	sueldo1 = ar3.readDouble();
	                    b1 = false;
	                }
	            }
	            while ((l < part) && (!b2)) {
	            	RE.writeUTF(nombre2);
	            	RE.writeInt(clave2);
	            	RE.writeDouble(sueldo2);
	                b2 = true;
	                l++;
	                if (arch4.available() > 0) {
	                	nombre2 = sa.readUTF();
	                	clave2 = sa.readInt();
	                	sueldo2 = sa.readDouble();
	                    b2 = false;
	                }
	            }
	        }
	        if (!b1) {
	        	RE.writeUTF(nombre1);
	        	RE.writeInt(clave1);
	        	RE.writeDouble(sueldo1);
	        }
	        if (!b2) {
	        	RE.writeUTF(nombre2);
	        	RE.writeInt(clave2);
	        	RE.writeDouble(sueldo2);
	        }
	        while (arch2.available() > 0) {
	        	nombre1 = ar3.readUTF();
	        	clave1 = ar3.readInt();
	        	sueldo1 = ar3.readDouble();
	            RE.writeUTF(nombre1);
	            RE.writeInt(clave1);
	            RE.writeDouble(sueldo1);
	        }
	        while (arch4.available() > 0) {
	        	nombre2 = sa.readUTF();
	        	clave2 = sa.readInt();
	        	sueldo2 = sa.readDouble();
	            RE.writeUTF(nombre2);
	            RE.writeInt(clave2);
	            RE.writeDouble(sueldo2);
	        }
	        RE.close();
	        ar3.close();
	        sa.close();
	    }

}
