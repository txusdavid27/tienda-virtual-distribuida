import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interfaz extends Remote{
    String consultar() throws RemoteException;
    String adquirir(Integer id, Integer cantidad) throws RemoteException;
}
