package servidor;

import java.io.Serializable;
import java.util.*;

/**
 * Clase que representa los resultados de un examen
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Muñoz
 * @version 1.0
 */
public class HistorialExamen implements Serializable {

  private Examen examen;
  private ArrayList<RespuestaAlumno> respuestasAlumnos;

  /**
   * Constructor con parametros
   * 
   * @param examen
   * @param respuestasAlumnos
   */
  public HistorialExamen(Examen examen, ArrayList<RespuestaAlumno> respuestasAlumnos) {
    this.examen = examen;
    this.respuestasAlumnos = respuestasAlumnos;
  }

  /**
   * @return the examen
   */
  public Examen getExamen() {
    return examen;
  }

  /**
   * @param examen the examen to set
   */
  public void setExamen(Examen examen) {
    this.examen = examen;
  }

  /**
   * @return the respuestasAlumnos
   */
  public ArrayList<RespuestaAlumno> getRespuestasAlumnos() {
    return respuestasAlumnos;
  }

  /**
   * @param respuestasAlumnos the respuestasAlumnos to set
   */
  public void setRespuestasAlumnos(ArrayList<RespuestaAlumno> respuestasAlumnos) {
    this.respuestasAlumnos = respuestasAlumnos;
  }

}
