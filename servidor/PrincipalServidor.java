package servidor;

/**
 * Clase principal del servidor
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Muñoz
 * @version 1.0
 */
public class PrincipalServidor {

  public static void main(String[] args) {
    GuiServidor app = new GuiServidor();
    ManejadorConexion manejador = new ManejadorConexion();
    manejador.iniciarServidor(app);
  }
}
