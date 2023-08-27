package servidor;

import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.JOptionPane;
import java.util.List;

/**
 * Clase que representa el hilo del cliente
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Muñoz
 * @version 1.0
 */
public class HiloCliente extends Thread implements Serializable {

  private ObjectInputStream entrada;
  private ObjectOutputStream salida;
  private Socket socketCliente;
  private int cliente;
  private Gestion gestion;
  private GuiServidor server;
  private int numeroPregunta = -1;
  private int tiempoExamen = 0;
  private String tiempo = "";
  private Examen examenACargar = new Examen();
  private String estado = "libre";
  private int mins = 0;
  private int segs = 0;
  private Boolean termino = false;
  private MulticastSocket socketMulticast;
  private DatagramPacket datagrama;
  private String mensajeResultado = "";
  private int cantidad = 0;
  private boolean esperarRespuesta = false;

  /**
   * Constructor con parametros
   * 
   * @param socketCliente
   * @param cliente
   * @param app
   * @param socketMulticast
   * @param datagrama
   * @return void
   */
  public HiloCliente(Socket socketCliente, int cliente, GuiServidor app, MulticastSocket socketMulticast,
      DatagramPacket datagrama) {
    this.socketCliente = socketCliente;
    this.cliente = cliente;
    server = app;
    gestion = app.getGestion();
    this.socketMulticast = socketMulticast;
    this.datagrama = datagrama;
  }

  /**
   * Metodo run del hilo
   * 
   * @param void
   * @return void
   */
  Boolean acaboTiempo = false;

  public void run() {

    HiloEscucharCliente hiloEscuchar = new HiloEscucharCliente(this);
    hiloEscuchar.start();

    while (true) {

      try {
        if (server.getInicio()) {

          if (!esperarRespuesta) {

            enviarDatos();
          }
          if (!acaboTiempo) {

            enviarPorMulti("tiempo" + tiempo);
          }

        }

        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    }
  }

  private int primeraVez = 0;
  boolean temporizador = false;

  /**
   * Metodo que envia datos al ciente por medio de una lista de strings
   * 
   * @param void
   * @return void
   */
  boolean unaVez = false;

  public void enviarDatos() {
    try {
      ArrayList<Examen> examenes = gestion.getExamenes();
      Pregunta pregunta = new Pregunta();
      // creamos una lista donde vamos a enviar la informacion que nos pida el cliente
      List<String> lista = new ArrayList<String>();

      String enunciado = " ";
      String respuesta = " ";

      if (!temporizador) {
        EnviarTiempo enviarTiempo = new EnviarTiempo();
        enviarTiempo.start();
        temporizador = true;
      }

      int posicionExamen = gestion.getExamenSeleccionado();

      // obtenemos el examen que se va a cargar

      examenACargar = examenes.get(posicionExamen);

      // obtenemos las preguntas del examen
      ArrayList<Pregunta> preguntas = examenACargar.getPreguntas();

      // obtenemos la cantidad de preguntas del examen
      String cantidadPreguntas = Integer.toString(examenACargar.getPreguntas().size());
      cantidad = Integer.parseInt(cantidadPreguntas);
      primeraVez++;
      tiempoExamen = examenACargar.getTiempoExamen();

      /**
       * si el examen no es nulo y el numero de pregunta es -1 (el cliente no esta en
       * ninguna pregunta) y no se termino el examen
       */
      if (examenACargar != null && numeroPregunta == -1 && !termino) {
        if (!acaboTiempo && !verificarTodasLasPreguntasRespondidas()) {

          enunciado = "seleccione una pregunta";
          lista.add(enunciado);
          if (unaVez) {
            cantidadPreguntas = "-1";
          }
          lista.add(cantidadPreguntas);
          lista.add("opcion 1.");
          lista.add("opcion 2.");
          lista.add("opcion 3.");
          lista.add("opcion 4.");
          lista.add(tiempo);

          salida.writeObject(lista);
          salida.flush();
          unaVez = true;

        }

        if (acaboTiempo || verificarTodasLasPreguntasRespondidas()) {
          enunciado = "se acabo el tiempo";
          if (verificarTodasLasPreguntasRespondidas()) {
            enviarPorMulti("tiempo respondidas");
            enunciado = "todas las preguntas se respondieron";
          }
          numeroPregunta = -1;
          cantidadPreguntas = "-2";
          lista.add(enunciado);
          lista.add(cantidadPreguntas);
          lista.add("opcion 1.");
          lista.add("opcion 2.");
          lista.add("opcion 3.");
          lista.add("opcion 4.");
          lista.add(tiempo);
          lista.add(gestion.notaParaClientes(examenACargar));
          salida.writeObject(lista);
          enviarPorMulti(gestion.getResultadoParaClientes());
          gestion.guardarResultado(examenACargar);
          gestion.guardarHistorialExamenes();
          gestion.mostrarHistorial(server);

          mins = 0;
          segs = 1;

          salida.flush();
          termino = true;

        }

      }

      if (examenACargar != null && numeroPregunta > 0 && !termino) {
        if (!acaboTiempo && !verificarTodasLasPreguntasRespondidas()) {
          pregunta = preguntas.get(numeroPregunta - 1);

          String opcion1 = pregunta.getOpciones().get(0);
          String opcion2 = pregunta.getOpciones().get(1);
          String opcion3 = pregunta.getOpciones().get(2);
          String opcion4 = pregunta.getOpciones().get(3);

          enunciado = pregunta.getPregunta();

          respuesta = pregunta.getRespuesta();
          lista.add(enunciado);
          lista.add(cantidadPreguntas);
          lista.add(opcion1);
          lista.add(opcion2);
          lista.add(opcion3);
          lista.add(opcion4);
          lista.add(tiempo);
          salida.writeObject(lista);
          salida.flush();

        }

      }

    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error al enviar datos");
    }

    esperarRespuesta = true;
  }

  /**
   * Metodo que verifica si todas las preguntas fueron respondidas
   * 
   * @param void
   * @return Boolean
   * 
   */
  public Boolean verificarTodasLasPreguntasRespondidas() {

    if (gestion.verCantidadPreguntasRespondidas() == cantidad) {
      return true;
    } else {
      return false;
    }

  }

  /**
   * Metodo que recibe datos del cliente, procesandolos y realizando las acciones
   * deacuerdo a lo que el cliente envie
   * 
   * @param void
   * @return void
   */
  public void recibirDatos() {

    try {

      String accion = "";

      accion = (String) entrada.readObject();
      esperarRespuesta = false;

      // si el cliente envia la palabra respuesta, significa que el cliente respondio
      // una pregunta
      if (accion.contains("respuesta")) {

        String[] respuesta = accion.split(" ");
        int respuestaAlumno = Integer.parseInt(respuesta[1]);
        Pregunta pregunta = examenACargar.getPreguntas().get(numeroPregunta - 1);
        String enunciado = pregunta.getPregunta();
        String resAlumno = pregunta.getOpciones().get(respuestaAlumno - 1);
        Boolean resul = pregunta.getRespuesta().equalsIgnoreCase(resAlumno);
        String conversionBool = conversionRespuesta(resul);
        mensajeResultado = "-Pregunta: " + numeroPregunta + " \nEnunciado: " + enunciado
            + "\nRespondida por estudiante: " + cliente + "\nRespuesta: " + resAlumno + "\nEs correcta?: "
            + conversionBool + "\n";
        gestion.agregarResultadoParaCliente(mensajeResultado);
        gestion.guardarRespuesta(numeroPregunta, enunciado, "" + cliente, resul);
        estado = "respondida";
        enviarPorMulti(numeroPregunta + " " + estado + " ");
        numeroPregunta = -1;

      }

      if (accion.contains("seleccionar")) {

        try {

          String[] seleccion = accion.split(" ");

          numeroPregunta = Integer.parseInt(seleccion[1]);

          estado = "ocupada";
          enviarPorMulti(numeroPregunta + " " + estado + " ");

        } catch (NullPointerException e) {
          JOptionPane.showMessageDialog(null, "Error al enviar datos");
        }

      }

      if (accion.contains("cancelar")) {

        estado = "libre";
        enviarPorMulti(numeroPregunta + " " + estado + " ");
        numeroPregunta = -1;

      }

      if (accion.contains("finalizar")) {
        acaboTiempo = true;
      }

    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error al recibir datos");

    } catch (ClassNotFoundException e) {
      JOptionPane.showMessageDialog(null, "Error al recibir datos");
    }

  }


  /**
   * Metodo que envia datos al cliente por medio de MultiCast
   * 
   * @param info
   * @return void
   */
  public void enviarPorMulti(String info) {

    try {

      byte[] inf = info.getBytes();
      datagrama.setData(inf);
      datagrama.setLength(inf.length);
      socketMulticast.send(datagrama);

    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error al enviar por multicast");
    }

  }

  /**
   * Metodo que conversiona un boolean a un string
   * 
   * @param respuesta
   * @return String
   */
  public String conversionRespuesta(Boolean respuesta) {
    String resBoolean = "";
    if (respuesta) {
      resBoolean = "correcta";
    } else {
      resBoolean = "incorrecta";
    }
    return resBoolean;

  }

  /**
   * Metodo que funciona como tiempo determinado para el examen
   * 
   * @param void
   * @return void
   */
  public void cuentaRegresiva() {

    mins = tiempoExamen;
    segs = 0;

    while (mins >= 0) {
      while (segs >= 0) {
        try {
          tiempo = " " + mins + ":" + segs + "";
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          JOptionPane.showMessageDialog(null, "Error al hacer la cuenta regresiva");
        }
        segs--;
      }
      segs = 59;
      mins--;

    }

    tiempoExamen = 0;
  }

  /**
   * Metodo que envia el tiempo al cliente como un hilo
   */
  class EnviarTiempo extends Thread {

    /**
     * Metodo run del hilo usado para la cuenta regresiva
     * 
     * @param void
     * @return void
     */
    public void run() {
      while (true) {

        cuentaRegresiva();
      }
    }

  }

  /**
   * Metodo que abre la salida y entrada de la informacion
   * 
   * @param void
   * @return void
   */
  public void abrirFlujos() {

    try {
      salida = new ObjectOutputStream(socketCliente.getOutputStream());
      salida.flush();
      entrada = new ObjectInputStream(socketCliente.getInputStream());
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error al abrir los flujos");
    }

  }

  /**
   * Metodo que cierra la salida y entrada de la informacion
   * 
   * @param void
   * @return void
   */
  public void cerrarFlujos() {
    try {
      salida.close();
      entrada.close();
      socketCliente.close();
      socketMulticast.close();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error al cerrar los flujos");
    }
  }

  /**
   * clase que funciona como hilo para escuchar al cliente y asi no se quede
   * esperando
   */
  public class HiloEscucharCliente extends Thread {
    private HiloCliente hiloCliente;

    public HiloEscucharCliente(HiloCliente hiloCliente) {
      this.hiloCliente = hiloCliente;
    }

    public void run() {
      while (true) {
        hiloCliente.recibirDatos();
      }
    }
  }

}
