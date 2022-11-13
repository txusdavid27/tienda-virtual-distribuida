import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.PrintWriter; // Step 1

public class Servidor {
    private static final int PUERTO = 1100; // El puerto debe ser el mismo que se configura en el cliente
    public static final String SEPARATOR = ";";
    public static final String QUOTE = "\"";
    public static ArrayList<Producto> listaProductos = new ArrayList<>();

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Remote remote = UnicastRemoteObject.exportObject(new Interfaz() {
            /*
             * Sobrescribir opcionalmente los metodos que escribimos en la interfaz
             */
            @Override
            public String consultar() throws RemoteException {
            
                System.out.println("Enviando Listado...");
                return leerRegistros();
            };
            @Override
            public String adquirir(Integer id, Integer cantidad) throws RemoteException {
                // Escribir archivo
                leerRegistros();
                //System.out.println(id);
                //imprimir();
                int resta=0;
                for(int i=0; i<listaProductos.size();i++){
                    //System.out.println( listaProductos.get(i).ID );
                    if(listaProductos.get(i).ID.equals(id)){
                        resta = listaProductos.get(i).cantidad - cantidad;
                        if(resta>=0){
                            listaProductos.get(i).cantidad=resta;
                            System.out.println("Actualizando...");
                            try{
                                actualizarRegistro();
                            }catch(Exception e){};
                            return "Adquisición exitosa!\n";
                        }else{
                            return "\nCantidad ->Producto: "+listaProductos.get(i).nombre+" No disponible."
                            +"\n Disponibles: "+listaProductos.get(i).cantidad
                            +"\n Solicitados: "+cantidad+"\n";
                        }
                    }
                }
                return "Producto No Registrado.\n";
            };
        }, 0);

        Registry registry = LocateRegistry.createRegistry(PUERTO);
        leerRegistros();
        System.out.println("Servidor escuchando en el puerto " + String.valueOf(PUERTO));
        registry.bind("Tienda Virtual", remote); // Registrar calculadora
    }

    private static String leerRegistros(){
             //Leer archivo
             String listado="";
             listaProductos.clear();
             BufferedReader br = null;
              try {
      
                  br =new BufferedReader(new FileReader("producto.csv"));
                  String line = br.readLine();//HEADER
                  line = br.readLine();
                  while (null!=line) {
                     String [] fields = line.split(SEPARATOR);
                     Producto nuevo= new Producto(Integer.parseInt(fields[0]), fields[1], Integer.parseInt(fields[2]));
                     //System.out.println(nuevo.ID+" "+nuevo.nombre+" "+nuevo.cantidad);
                     //System.out.println(Arrays.toString(fields));
                     listado += Arrays.toString(fields);
                     listado +="\n";
                     listaProductos.add(nuevo);
                     line = br.readLine();
                  }
                  if (br!=null){
                      br.close();
                  }
               }catch (Exception e){}
               return listado;
    }
    public static void actualizarRegistro(){
        try{
            PrintWriter out = new PrintWriter("producto.csv");
            out.print("ID;NOMBRE;CANTIDAD");
            for(int i=0; i<listaProductos.size();i++){
                out.print("\n"+listaProductos.get(i).ID+";"+listaProductos.get(i).nombre+";"+listaProductos.get(i).cantidad);
            }
            out.close();
        }catch(Exception e){};
    }

    public static void imprimir(){
        System.out.print("ID;NOMBRE;CANTIDAD");
        for(int i=0; i<listaProductos.size();i++){
            System.out.print("\n"+listaProductos.get(i).ID+";"+listaProductos.get(i).nombre+";"+listaProductos.get(i).cantidad);
        }
    }

}