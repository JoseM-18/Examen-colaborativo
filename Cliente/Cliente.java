package Cliente;

import java.util.*;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import java.io.*;

/**
 * Clase que representa un cliente
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Muñoz
 * @version 1.0
 */
public class Cliente implements Serializable {

  private ObjectInputStream entrada;
  private ObjectOutputStream salida;
  private Socket socketCliente;
  private GuiCliente guiCliente;

  /**
   * Funcion que crea una conexion con el servidor e inicia el hilo de escucha
   * 
   * @param guiCliente
   * @return void
   */
  public void iniciarCliente(GuiCliente guiCliente) {

    try {

      this.guiCliente = guiCliente;

      socketCliente = new Socket("localhost", 5000);

      this.guiCliente.editarTAinfo("Conectado al servidor");

      abrirFlujos();

      escucharServidor();

    } catch (UnknownHostException uo) {
      JOptionPane.showMessageDialog(null, "Error al iniciar el cliente ");
    } catch (IOException io) {
      JOptionPane.showMessageDialog(null, "Error al iniciar el cliente");
    }

  }

  /**
   * Funcion que envia los datos al servidor
   * 
   * @param guiCliente
   * @return void
   */
  public void enviarDatos(String mensaje) {
    try {
      String[] datosMens = mensaje.split(" ");

      if (datosMens.length < 3) {
        salida.writeObject(mensaje);
        salida.flush();

      } else {
        guiCliente.editarTAinfo("Pregunta no se puede obtener por que esta " + datosMens[2]);

      }

    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error al enviar datos");
    }
  }

  // creamos un modelo que va a modificar el combobox de las preguntas
  private DefaultComboBoxModel modelo = new DefaultComboBoxModel<>();
  private Boolean unaVez = false;

  /**
   * Funcion que escucha al servidor recibiendo los datos por medio de una lista
   * y haciendo las acciones correspondientes a cada mensaje
   * 
   * @param guiCliente
   * @return void
   */
  public void escucharServidor(GuiCliente guiCliente) {
    try {

      List<String> lista = (List<String>) entrada.readObject();
      /**
       * si el servidor me envia seleccione una pregunta y -1 (que significa que el
       * cliente no esta en una pregunta)
       * y unaVez es falso (para que no se repita la informacion)
       * ocultamos el panel de las opciones y mostramos el combobox con las preguntas
       * y el enunciado
       * 
       */
      if (lista.get(0).equalsIgnoreCase("seleccione una pregunta") && !lista.get(1).equalsIgnoreCase("-1") && !unaVez) {

        guiCliente.ocultarPnlDerecho();
        guiCliente.editarTAinfo(lista.get(0));
        modelo = (DefaultComboBoxModel) guiCliente.cbNoPreguntas.getModel();
        int numPreguntas = Integer.parseInt(lista.get(1));

        for (int x = 0; x < numPreguntas; x++) {

          int num = x + 1;
          String pregunta = "" + num;
          modelo.addElement(pregunta);
        }
        unaVez = true;
      }

      /**
       * si el servidor me envia seleccione una pregunta y -1 (que significa que el
       * cliente no esta en una pregunta)
       * y no a terminado el examen se verifica si la pregunta esta ocupada
       */
      if (lista.get(0).equalsIgnoreCase("seleccione una pregunta") && lista.get(1).equalsIgnoreCase("-1")) {
        guiCliente.ocultarPnlDerecho();
        guiCliente.editarTAinfo(lista.get(0));

      }

      /**
       * si el servidor me envia seleccione una pregunta y el cliente no esta en una
       * pregunta y no a terminado el examen
       * se muestra el panel de las opciones y se edita el enunciado y las opciones
       * 
       */
      if (!lista.get(0).equalsIgnoreCase("seleccione una pregunta") && !lista.get(1).equalsIgnoreCase("-1")
          && !lista.get(1).equalsIgnoreCase("-2")) {
        guiCliente.mostrarPnlDerecho();
        guiCliente.editarTAinfo(lista.get(0));
        guiCliente.editarOpcion1(lista.get(2));
        guiCliente.editarOpcion2(lista.get(3));
        guiCliente.editarOpcion3(lista.get(4));
        guiCliente.editarOpcion4(lista.get(5));

      }

      /**
       * si se envia -2 significa que el cliente ya termino el examen o se acabo el
       * tiempo
       * se edita el enunciado y las opciones
       * se oculta el panel de las opciones
       * se muestra el panel de los resultados
       * se muestra la calificacion
       */
      if (lista.get(1).equalsIgnoreCase("-2")) {
        guiCliente.editarTAinfo(lista.get(0));
        guiCliente.editarTfCalificacion(lista.get(7));
        guiCliente.ocultarPnlDerecho();
        guiCliente.verResultados();
      }

    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error al escuchar al servidor");
    } catch (ClassNotFoundException nfe) {

      JOptionPane.showMessageDialog(null, "Error al escuchar al servidor");
    }

  }

  /**
   * Funcion que escucha al servidor constantemente
   * 
   * @param void
   * @return void
   */
  public void escucharServidor() {

    while (true) {

      escucharServidor(guiCliente);

    }

  }

  /**
   * Funcion que abre los flujos de entrada y salida de la informacion
   * 
   * @param void
   * @return void
   */
  public void abrirFlujos() {
    try {
      salida = new ObjectOutputStream(socketCliente.getOutputStream());
      salida.flush();
      entrada = new ObjectInputStream(socketCliente.getInputStream());
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Error al abrir los flujos");
    }
  }

  /**
   * Funcion que cierra los flujos de entrada y salida de la informacion
   * 
   * @param void
   * @return void
   */
  public void cerrarFlujos() {
    try {
      salida.close();
      entrada.close();
      socketCliente.close();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error al cerrar los flujos");
    }
  }
}
