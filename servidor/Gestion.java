package servidor;

import java.io.*;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 * Clase que contiene los metodos para la gestion de los examenes.
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Munoz
 * @version 1.0
 */
public class Gestion implements Serializable {

  ArrayList<Examen> examenes;
  ArrayList<HistorialExamen> historialExamenes;
  ArrayList<RespuestaAlumno> respuestasAlumnos = new ArrayList<RespuestaAlumno>();
  ArrayList<String> resultadosParaAlumnos = new ArrayList<String>();
  int examenAEscoger;
  Boolean existe = false;
  boolean todasLasPreguntasRespondidas = false;
  int examenSeleccionado = 0;
  Boolean examenPreparado = false;

  /**
   * Metodo que lee la direccion de un archivo que va a recibir la funcion y lo
   * guarda en un arraylist de examenes
   * 
   * @param nombreExamen
   * @param direccionArchivo
   * @param tiempoExamen
   * @return void
   */
  public void leerArchivo(String nombreExamen, String direccionArchivo, int tiempoExamen) {

    try {

      // creamos un fileReader donde le pasamos la direccion del archivo a leer
      if (examenes == null) {
        examenes = new ArrayList<Examen>();
      }

      FileReader fr = new FileReader(direccionArchivo);

      // un bufferReader para que lea linea por linea el archivo
      BufferedReader br = new BufferedReader(fr);

      ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();

      String enunciado = "";
      String respuesta = "";
      String linea = "";
      int noPregunta = 1;

      ArrayList<String> opciones = new ArrayList<String>();

      while ((linea = br.readLine()) != null) {

        String[] palabras = linea.split(" ");

        if (palabras[0].equalsIgnoreCase("Pregunta" + noPregunta + ":")) {
          int contador = 0;
          for (int x = 1; x < palabras.length; x++) {

            if (contador == 15) {
              enunciado += palabras[x] + "\n";
              contador = 0;
            } else {

              enunciado += palabras[x] + " ";
              contador++;
            }

          }

        }

        if (palabras[0].equalsIgnoreCase("opciones:")) {

          String opcionModificada = "";
          for (int i = 1; i < palabras.length; i++) {

            String opcion = palabras[i];
            if (opcion.contains("_")) {

              String[] confiOpciones = opcion.split("_");
              for (int x = 0; x < confiOpciones.length; x++) {

                opcionModificada += confiOpciones[x] + " ";
              }

            } else {

              opcionModificada = opcion;
            }

            opciones.add(opcionModificada);
            opcionModificada = "";

          }

        }

        if (palabras[0].equalsIgnoreCase("respuesta:")) {

          respuesta = palabras[1];
          String respuestaModificada = "";

          if (respuesta.contains("_")) {

            String[] confiRespuesta = respuesta.split("_");
            for (int x = 0; x < confiRespuesta.length; x++) {

              respuestaModificada += confiRespuesta[x] + " ";
            }

          } else {

            respuestaModificada = respuesta;
          }

          Pregunta preg = new Pregunta(enunciado, opciones, respuestaModificada);
          preguntas.add(preg);
          opciones = new ArrayList<String>();

          enunciado = "";
          noPregunta++;

        }

      }

      Examen examen = new Examen(nombreExamen, preguntas, tiempoExamen);
      examenes.add(examen);

      br.close();
      fr.close();

    } catch (FileNotFoundException e) {
      System.out.println("No se encontro el archivo");

    } catch (IOException e) {
      System.out.println("Error de lectura");

    }

  }

  /**
   * @return the examenes
   */
  public ArrayList<Examen> getExamenes() {

    return examenes;
  }

  /**
   * @param examenes the examenes to set
   */
  public void setExamenes(ArrayList<Examen> examenes) {
    this.examenes = examenes;
  }

  /**
   * verifica si todas las preguntas del examen han sido respondidas
   * 
   * @param void
   * @return boolean
   */
  public boolean getTodasLasPreguntasRespondidas() {
    return todasLasPreguntasRespondidas;
  }

  /**
   * Metodo que busca un examen dentro del array de examenes
   * 
   * @param nombreExamen
   * @return void
   */
  public void iniciarExamen(String nombreExamen) {

    if (examenes != null) {

      for (int i = 0; i < examenes.size(); i++) {

        if (examenes.get(i).getNombre().equalsIgnoreCase(nombreExamen)) {

          examenSeleccionado = i;

          break;
        }
      }

    } else {
      System.out.println("no hay examenes");
    }

  }

  /**
   * Metodo que busca un examen una vez fue realizado
   * 
   * @param nombreExamen
   * @return void
   */
  public void buscarHistorial(String nombreExamen) {

    if (historialExamenes != null) {

      for (int i = 0; i < historialExamenes.size(); i++) {

        if (historialExamenes.get(i).getExamen().getNombre().equalsIgnoreCase(nombreExamen)) {

          existe = true;
          examenAEscoger = i;
          break;
        }
      }

    } else {
      System.out.println("no hay historiales");
    }

  }

  /**
   * Metodo que guarda la respuesta del alumno
   * 
   * @param idPregunta
   * @param respuesta
   * @param idAlumno
   * @param esCorrecta
   * @return void
   */
  public void guardarRespuesta(int idPregunta, String respuesta, String idAlumno, boolean esCorrecta) {

    Boolean encontro = false;
    RespuestaAlumno unaRespuesta = new RespuestaAlumno(idPregunta, respuesta, idAlumno, esCorrecta);

    if (respuestasAlumnos == null) {
      respuestasAlumnos = new ArrayList<RespuestaAlumno>();
    }

    if (respuestasAlumnos.size() != 0) {

      for (int x = 0; x < respuestasAlumnos.size(); x++) {
        if (respuestasAlumnos.get(x).getIdPregunta() == idPregunta) {
          encontro = true;
        }
      }

      if (!encontro) {

        respuestasAlumnos.add(unaRespuesta);
      }
    } else {

      respuestasAlumnos.add(unaRespuesta);
    }

  }

  boolean guardarUnaVez = false;
  boolean yaEnHistorial = false;

  /**
   * Metodo que guarda los resultados de un examen
   * 
   * @param examen
   * @return void
   */
  public synchronized void guardarResultado(Examen examen) {
    if (!guardarUnaVez) {

      if (historialExamenes == null) {

        historialExamenes = new ArrayList<HistorialExamen>();
      }

      for (int x = 0; x < historialExamenes.size(); x++) {

        if (historialExamenes.get(x).getExamen().getNombre().equalsIgnoreCase(examen.getNombre())) {

          yaEnHistorial = true;
          break;

        } else {

          yaEnHistorial = false;
        }

      }

      if (yaEnHistorial == false) {

        guardarTodasLasPreguntas(examen);

        HistorialExamen historial = new HistorialExamen(examen, respuestasAlumnos);

        historialExamenes.add(historial);

        respuestasAlumnos = new ArrayList<RespuestaAlumno>();
      }
      guardarUnaVez = true;
    }

  }

  /**
   * Metodo que guarda todas las preguntas de un examen que no fueron respondidas
   * por los alumnos en el historial.
   * 
   * @param unExamen
   * @return void
   */
  public void guardarTodasLasPreguntas(Examen unExamen) {
    for (int x = 0; x < unExamen.getPreguntas().size(); x++) {
      Pregunta unaPregunta = unExamen.getPreguntas().get(x);
      Boolean preguntaExiste = false;
      int numPregunta = x + 1;
      for (int y = 0; y < respuestasAlumnos.size(); y++) {
        if (respuestasAlumnos.get(y).getIdPregunta() == numPregunta) {
          preguntaExiste = true;
          break;
        }
      }
      if (!preguntaExiste) {
        RespuestaAlumno nuevaRespuesta = new RespuestaAlumno(numPregunta, unaPregunta.getPregunta(), "no respondida",
            false);
        respuestasAlumnos.add(nuevaRespuesta);
      }

    }

  }

  /**
   * Metodo que guarda el historial de examenes en un archivo de texto como un
   * objeto serializado
   * 
   * @param void
   * @return void
   * @throws IOException
   */
  boolean guardarHistorial = false;

  public void guardarHistorialExamenes() {
    if (!guardarHistorial) {
      try {
        FileOutputStream fos = new FileOutputStream(new File("historialExamenes.txt"));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(historialExamenes);
        oos.close();
        fos.close();
      } catch (FileNotFoundException e) {
        System.out.println("No se encontro el archivo");
      } catch (IOException e) {
        System.out.println("Error al guardar el archivo");
      }
      guardarHistorial = true;
    }

  }

  /**
   * Metodo que carga el historial de examenes
   * 
   * @param void
   * @return void
   */
  public void cargarHistorialExamenes() {

    try {

      FileInputStream fis = new FileInputStream("historialExamenes.txt");

      if (fis.available() != 0) {

        ObjectInputStream ois = new ObjectInputStream(fis);
        historialExamenes = (ArrayList<HistorialExamen>) ois.readObject();
        ois.close();
        fis.close();

      }

    } catch (FileNotFoundException e) {
      System.out.println("No se encontro el archivo");
    } catch (ClassNotFoundException e) {
      System.out.println("No se encontro la clase");
    } catch (IOException ioe) {
      System.out.println("error al leer el archivo");
    }
  }

  private boolean agregado = false;
  private boolean mostrado = false;
  private ArrayList<Boolean> resulPreg = new ArrayList<Boolean>();
  private float totalPreguntas = 0;
  private DefaultComboBoxModel modelo = new DefaultComboBoxModel();

  /**
   * Metodo que muestra el historial de examenes
   * 
   * @param gui
   * @return void
   */

  public synchronized void mostrarHistorial(GuiServidor gui) {
    modelo = (DefaultComboBoxModel) gui.getCBregistroExamenes().getModel();

    if (historialExamenes != null) {

      for (int i = 0; i < historialExamenes.size(); i++) {

        String nombreExamen = historialExamenes.get(i).getExamen().getNombre();
        for (int x = 0; x < modelo.getSize(); x++) {
          if (nombreExamen.equalsIgnoreCase(modelo.getElementAt(x).toString())) {
            agregado = true;
            break;
          } else {
            agregado = false;
          }

        }

        if (agregado == false) {
          modelo.addElement(nombreExamen);
        }

        if (existe) {

          gui.eliminarJTable();
          mostrado = false;
          if (!modelo.getSelectedItem().toString().equalsIgnoreCase("Selecciona un examen")) {

            if (!mostrado) {

              for (int x = 0; x < historialExamenes.get(examenAEscoger).getRespuestasAlumnos().size(); x++) {
                String noPregunta = String
                    .valueOf(historialExamenes.get(examenAEscoger).getRespuestasAlumnos().get(x).getIdPregunta());
                String respuesta = historialExamenes.get(examenAEscoger).getRespuestasAlumnos().get(x).getEnunciado();
                String idAlumno = historialExamenes.get(examenAEscoger).getRespuestasAlumnos().get(x).getIdAlumno();
                String conversionResultado = convertirBoolean(
                    historialExamenes.get(examenAEscoger).getRespuestasAlumnos().get(x).getEsCorrecta());
                String[] datos = { noPregunta, respuesta, idAlumno, conversionResultado };
                gui.editarJTable(datos);

                totalPreguntas = historialExamenes.get(examenAEscoger).getExamen().getPreguntas().size();
                Boolean resulPreguntas = historialExamenes.get(examenAEscoger).getRespuestasAlumnos().get(x)
                    .getEsCorrecta();
                resulPreg.add(resulPreguntas);

              }

              gui.editarTfCalificacion(nota(resulPreg, totalPreguntas));

              mostrado = true;

            }

          }

        }

        resulPreg = new ArrayList<Boolean>();
      }

    }

  }

  /**
   * Metodo que calcula la nota del alumno
   * 
   * @param ArrayList<Boolean> resulPreg, int totalPreguntas
   * @return void
   */
  public String nota(ArrayList<Boolean> resulPreg, float totalPreguntas) {

    float nota = 0;
    for (int i = 0; i < resulPreg.size(); i++) {

      if (resulPreg.get(i) == true) {

        nota++;
      }

    }

    float notaFinal = (nota * 5) / totalPreguntas;
    String conversionNota = String.format("%.2f", notaFinal);

    return conversionNota;

  }

  /**
   * Metodo que agrega el resultado de la pregunta al alumno posteriormente
   * enviandose al cliente
   * 
   * @param resultado
   * @return void
   */
  public void agregarResultadoParaCliente(String resultado) {
    boolean agregado = false;
    if (resultadosParaAlumnos != null) {
      for (int x = 0; x < resultadosParaAlumnos.size(); x++) {

        if (resultadosParaAlumnos.get(x).equalsIgnoreCase(resultado)) {
          agregado = true;
        }

      }
      if (!agregado) {
        resultadosParaAlumnos.add(resultado);
      }

    } else {
      resultadosParaAlumnos.add(resultado);

    }

  }

  /**
   * Metodo que convierte un boolean a String para mostrarlo al cliente
   * 
   * @param respuesta
   * @return String
   */
  public String convertirBoolean(Boolean respuesta) {
    String resultado = "";
    if (respuesta == true) {
      resultado = "Correcta";
    } else {
      resultado = "Incorrecta";
    }
    return resultado;
  }

  /**
   * Metodo que regresa el resultado para los clientes
   * 
   * @param void
   * @return String
   */
  public String getResultadoParaClientes() {

    String resultado = "";
    if (resultadosParaAlumnos.size() > 0) {
      todasLasPreguntasParaClientes(examenes.get(examenSeleccionado));
      for (int i = 0; i < resultadosParaAlumnos.size(); i++) {
        resultado += resultadosParaAlumnos.get(i) + " ";

      }

    } else {
      resultado = "No hay resultados";
    }
    return resultado;
  }

  /**
   * Metodo que regresa el examen seleccionado
   * 
   * @param void
   * @return int
   */

  public int getExamenSeleccionado() {
    return examenSeleccionado;
  }

  /**
   * arraylist que guarda el resultado de las preguntas
   */
  ArrayList<Boolean> resulPregEnvResult = new ArrayList<Boolean>();

  /**
   * Metodo que recibe un examen, trae el numero de preguntas que tiene el examen
   * y regresa la nota del alumno
   * 
   * @param cliente
   * @return void
   */
  public synchronized String notaParaClientes(Examen unExamen) {
    int totalPreg = 0;
    String nota = "";
    if (examenes != null) {

      for (int x = 0; x < examenes.size(); x++) {

        if (examenes.get(x).getNombre().equalsIgnoreCase(unExamen.getNombre())) {
          totalPreg = examenes.get(x).getPreguntas().size();
          for (int y = 0; y < respuestasAlumnos.size(); y++) {
            Boolean respuesta = respuestasAlumnos.get(y).getEsCorrecta();
            resulPregEnvResult.add(respuesta);

          }

        }

      }
      nota = nota(resulPregEnvResult, totalPreg);
    }
    return nota;

  }

  /**
   * Metodo que recibe un examen y regresa todas las preguntas que tiene el examen
   * verificando si el alumno ya respondio la pregunta o no
   * 
   * @param examen
   */
  public void todasLasPreguntasParaClientes(Examen examen) {

    for (int i = 0; i < examen.getPreguntas().size(); i++) {
      String enunciadoPregunta = examen.getPreguntas().get(i).getPregunta();
      String numpreg = (i + 1) + "";
      boolean preguntaRespondida = false;
      for (int j = 0; j < resultadosParaAlumnos.size(); j++) {
        String[] campos = resultadosParaAlumnos.get(j).split(" ");
        String preguntaRespuesta = campos[1];
        if (numpreg.equals(preguntaRespuesta)) {
          preguntaRespondida = true;
          break;
        }
      }
      if (!preguntaRespondida) {
        resultadosParaAlumnos
            .add("-Pregunta: " + (i + 1) + " \nEnunciado: " + enunciadoPregunta + "\nRespondida por estudiante: "
                + "No respondida" + "\nRespuesta: " + "No respondida" + "\nEs correcta?: " + "incorrecta" + "\n");

      }
    }

  }

  /**
   * Metodo que retorna la cantidad de preguntas respondidas
   * 
   * @param void
   * @return int
   */
  public int verCantidadPreguntasRespondidas() {

    return respuestasAlumnos.size();

  }

}
