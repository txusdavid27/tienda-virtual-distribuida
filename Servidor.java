import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class Servidor {
    private static final int PUERTO = 1100; // El puerto debe ser el mismo que se configura en el cliente
    public static final String SEPARATOR = ";";
    public static final String QUOTE = "\"";

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Remote remote = UnicastRemoteObject.exportObject(new Interfaz() {
            ArrayList<Producto> listaProductos = new ArrayList<>();
            /*
             * Sobrescribir opcionalmente los metodos que escribimos en la interfaz
             */
            @Override
            public String consultar() throws RemoteException {
                //Leer archivo
                String listado="";
                BufferedReader br = null;
                 try {
         
                     br =new BufferedReader(new FileReader("producto.csv"));
                     String line = br.readLine();
                     while (null!=line) {
                        String [] fields = line.split(SEPARATOR);
                        System.out.println(Arrays.toString(fields));
                        listado += Arrays.toString(fields);
                        listado +="\n";
                        listaProductos.add(null);
                        line = br.readLine();
                     }
                     if (br!=null){
                         br.close();
                     }
                  }catch (Exception e){

                  }
                System.out.println("Funcionaaa");
                return listado;
            };
            @Override
            public void adquirir(float id, float cantidad) throws RemoteException {
                // Escribir archivo

                System.out.println("Funciona");
            };

        }, 0);
        Registry registry = LocateRegistry.createRegistry(PUERTO);
        System.out.println("Servidor escuchando en el puerto " + String.valueOf(PUERTO));
        registry.bind("Tienda Virtual", remote); // Registrar calculadora
    }
}