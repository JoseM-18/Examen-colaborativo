package servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

/**
 * Clase que maneja la conexion entre el servidor y los clientes
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Muñoz
 * @version 1.0
 */
public class ManejadorConexion {

  private ServerSocket servidor;
  private Semaphore semaphore;
  private HiloCliente hiloCliente;
  private MulticastSocket socketMulticast;
  private DatagramPacket datagrama;
  private byte[] datos = new byte[0];
  private int contador = 0;

  public void iniciarServidor(GuiServidor app) {

    try {
      socketMulticast = new MulticastSocket();
      InetAddress grupo = InetAddress.getByName("232.0.0.0");
      datagrama = new DatagramPacket(datos, 0, grupo, 4000);
      servidor = new ServerSocket(5000);
      semaphore = new Semaphore(3);

      while (true) {

        if (semaphore.tryAcquire()) {
          Socket socketCliente = servidor.accept();
          contador++;
          app.editarTAinfo("\n Cliente " + contador + " conectado");
          hiloCliente = new HiloCliente(socketCliente, contador, app, socketMulticast, datagrama);
          hiloCliente.abrirFlujos();
          hiloCliente.start();

        }
      }

    } catch (IOException ioe) {
      JOptionPane.showMessageDialog(null, "Error al iniciar el servidor");
    }

  }
}
