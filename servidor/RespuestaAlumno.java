package servidor;

import java.io.Serializable;

/**
 * Clase que representa la respuesta enviada por un elumno
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Muñoz
 * @version 1.0
 */
public class RespuestaAlumno implements Serializable {

    private int idPregunta;
    private String enunciado;
    private String idAlumno;
    private Boolean esCorrecta;

    /**
     * Constructor con parametros
     * 
     * @param idPregunta
     * @param repuesta
     * @param idAlumno
     * @param esCorrecta
     * @return void
     */
    public RespuestaAlumno(int idPregunta, String enunciado, String idAlumno, Boolean esCorrecta) {

        this.idPregunta = idPregunta;
        this.enunciado = enunciado;
        this.idAlumno = idAlumno;
        this.esCorrecta = esCorrecta;
    }

    /**
     * @return the idPregunta
     */
    public int getIdPregunta() {
        return idPregunta;
    }

    /**
     * @param idPregunta the idPregunta to set
     */
    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    /**
     * @return the respuesta
     */
    public String getEnunciado() {
        return enunciado;
    }

    /**
     * @param respuesta the respuesta to set
     */
    public void setRespuesta(String enunciado) {
        this.enunciado = enunciado;
    }

    /**
     * @return the idAlumno
     */
    public String getIdAlumno() {
        return idAlumno;
    }

    /**
     * @param idAlumno the idAlumno to set
     */
    public void setIdAlumno(String idAlumno) {
        this.idAlumno = idAlumno;
    }

    /**
     * @return the esCorrecta
     */
    public Boolean getEsCorrecta() {
        return esCorrecta;
    }

    /**
     * @param esCorrecta the esCorrecta to set
     */
    public void setEsCorrecta(Boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

}