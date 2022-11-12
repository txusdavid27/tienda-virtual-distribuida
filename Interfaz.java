import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interfaz extends Remote{
    void consultar() throws RemoteException;
    void adquirir(float id, float cantidad) throws RemoteException;
}