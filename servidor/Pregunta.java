package servidor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa a una pregunta de un examen
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Muñoz
 * @version 1.0
 */
public class Pregunta implements Serializable {

  private String pregunta;
  private ArrayList<String> opciones = new ArrayList<String>();
  private String respuesta;

  /**
   * Constructor sin parametros
   * 
   * @param void
   * @return void
   */
  public Pregunta() {

    this.pregunta = " ";
    this.respuesta = " ";
  }

  /**
   * Constructor con parametros
   * 
   * @param pregunta
   * @param opciones
   * @param respuesta
   * @return void
   */
  public Pregunta(String pregunta, ArrayList<String> opciones, String respuesta) {
    this.pregunta = pregunta;
    this.opciones = opciones;
    this.respuesta = respuesta;
  }

  /**
   * @return the pregunta
   */
  public String getPregunta() {
    return pregunta;
  }

  /**
   * @param pregunta the pregunta to set
   */
  public void setPregunta(String pregunta) {
    this.pregunta = pregunta;
  }

  /**
   * @return the opciones
   */
  public ArrayList<String> getOpciones() {
    return opciones;
  }

  /**
   * @param opciones the opciones to set
   */
  public void setOpciones(ArrayList<String> opciones) {
    this.opciones = opciones;
  }

  /**
   * @return the respuesta
   */
  public String getRespuesta() {
    return respuesta;
  }

  /**
   * @param respuesta the respuesta to set
   */
  public void setRespuesta(String respuesta) {
    this.respuesta = respuesta;
  }

}