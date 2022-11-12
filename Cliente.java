import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {
    private static final String IP = "127.0.0.1"; // Puede ser cambiado a localhost
    private static final int PUERTO = 1100; // El puerto debe ser el mismo que se configura en el servidor

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(IP, PUERTO);
        Interfaz interfaz = (Interfaz) registry.lookup("Tienda Virtual"); // Buscar en el registro...
        Scanner sc = new Scanner(System.in);
        int eleccion;
        float id, cantidad;
        String menu = "\n\n------------------\n\n[-1] => Salir\n[0] => Consultar\n[1] => Adquirir\nElige: ";
        do {
            System.out.println(menu);

            try {
                eleccion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                eleccion = -1;
            }

            if (eleccion != -1) {

                switch (eleccion) {
                    case 0:
                        String resultado =interfaz.consultar();
                        System.out.println("Listado: "+"\n"+ resultado);
                        break;
                    case 1:
                        System.out.println("Ingresa el id del producto: ");
                        try {
                            id = Float.parseFloat(sc.nextLine());
                        } catch (NumberFormatException e) {
                            id = 0;
                        }

                        System.out.println("Ingresa la cantidad: ");
                        try {
                            cantidad = Float.parseFloat(sc.nextLine());
                        } catch (NumberFormatException e) {
                            cantidad = 0;
                        }
                        interfaz.adquirir(id, cantidad);
                        break;
                }

                // System.out.println("Resultado => " + String.valueOf(resultado));
                System.out.println("Presiona ENTER para continuar");
                sc.nextLine();
            }
        } while (eleccion != -1);
    }
}