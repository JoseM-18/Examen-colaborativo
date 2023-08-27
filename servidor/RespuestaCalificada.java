package servidor;

import java.io.Serializable;

/**
 * Clase que representa la resppuesta enviada por un alumno una vez fue
 * calificada
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Muñoz
 * @version 1.0
 */
public class RespuestaCalificada implements Serializable {

    private RespuestaAlumno respuestaAlumno;
    private String esCorrecta;

    /**
     * Constructor con parametros
     * 
     * @param respuestaAlumno
     * @param esCorrecta
     */
    public RespuestaCalificada(RespuestaAlumno respuestaAlumno, String esCorrecta) {
        this.respuestaAlumno = respuestaAlumno;
        this.esCorrecta = esCorrecta;
    }

    /**
     * @return the respuestaAlumno
     */
    public RespuestaAlumno getRespuestaAlumno() {
        return respuestaAlumno;
    }

    /**
     * @param respuestaAlumno the respuestaAlumno to set
     */
    public void setRespuestaAlumno(RespuestaAlumno respuestaAlumno) {
        this.respuestaAlumno = respuestaAlumno;
    }

    /**
     * @return the esCorrecta
     */
    public String getEsCorrecta() {
        return esCorrecta;
    }

    /**
     * @param esCorrecta the esCorrecta to set
     */
    public void setEsCorrecta(String esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

}