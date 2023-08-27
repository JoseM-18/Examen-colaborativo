package servidor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa a un examen
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Muñoz
 * @version 1.0
 */
public class Examen implements Serializable {

  private String nombre;
  private ArrayList<Pregunta> preguntas;
  private int tiempoExamen;

  /**
   * Constructor sin parametros
   * 
   * @param void
   * @return void
   */
  public Examen() {
    this.nombre = " ";
    this.tiempoExamen = 0;
  }

  /**
   * Constructor con parametros
   * 
   * @param nombre
   * @param preguntas
   * @param tiempoExamen
   * @return void
   */
  public Examen(String nombre, ArrayList<Pregunta> preguntas, int tiempoExamen) {
    this.nombre = nombre;
    this.preguntas = preguntas;
    this.tiempoExamen = tiempoExamen;
  }

  /**
   * @return the nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * @param nombre the nombre to set
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * @return the preguntas
   */
  public ArrayList<Pregunta> getPreguntas() {
    return preguntas;
  }

  /**
   * @param preguntas the preguntas to set
   */
  public void setPreguntas(ArrayList<Pregunta> preguntas) {
    this.preguntas = preguntas;
  }

  /**
   * @return the tiempoExamen
   */
  public int getTiempoExamen() {
    return tiempoExamen;
  }

  /**
   * @param tiempoExamen the tiempoExamen to set
   */
  public void setTiempoExamen(int tiempoExamen) {
    this.tiempoExamen = tiempoExamen;
  }

}
