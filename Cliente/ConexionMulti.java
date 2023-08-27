package Cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 * Clase que envia y recibe informacion por medio de un socket MultiCast
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Muñoz
 * @version 1.0
 */
public class ConexionMulti extends Thread {

    private ArrayList<String> preguntasOcupadas = new ArrayList<String>();
    private GuiCliente guiCliente;
    private MulticastSocket socketMulticast;
    private InetAddress grupo;
    private DefaultComboBoxModel modelo;

    /**
     * Metodo que crea el socket multicast y une al grupo
     * 
     * @param guiCliente
     * @return void
     * @throws IOException
     */
    public ConexionMulti(GuiCliente guiCliente) {

        try {

            this.guiCliente = guiCliente;
            socketMulticast = new MulticastSocket(4000);
            socketMulticast.joinGroup(InetAddress.getByName("232.0.0.0"));
        } catch (IOException ieo) {
            System.out.println("Error al crear el socket multicast");
        }
    }

    private String numPreg = "";
    private int mens = -1;
    private String estado = "";

    /**
     * Metodo que recibe los mensajes del servidor y los muestra en la interfaz
     * 
     * @return void
     * @throws IOException
     */
    @Override
    public void run() {

        try {

            while (true) {

                byte[] datos = new byte[5000];
                DatagramPacket datagrama = new DatagramPacket(datos, datos.length);
                socketMulticast.receive(datagrama);
                String mensaje = new String(datagrama.getData());
                if (mensaje.contains("tiempo")) {
                    String[] time = mensaje.split(" ");
                    if (mensaje.contains(" 0:0") || mensaje.contains("respondidas")) {
                        guiCliente.enviarDatos("finalizar");
                    } else {
                        if (time.length > 1) {
                            guiCliente.editarTFtiempo(time[1]);
                        }
                    }

                }

                if (mensaje.contains("-Pregunta: ")) {

                    guiCliente.editarTAresultados(mensaje);
                    guiCliente.editarTAinfo("el tiempo se termino");

                }

                if (!mensaje.contains("tiempo") && !mensaje.contains("-Pregunta: ")) {

                    String[] dates = mensaje.split(" ");
                    numPreg = dates[0];
                    modelo = (DefaultComboBoxModel) guiCliente.cbNoPreguntas.getModel();
                    mens = Integer.parseInt(numPreg);
                    estado = dates[1];

                }

                if (mensaje.contains("ocupada") || mensaje.contains("libre") || mensaje.contains("respondida")) {
                    if (estado.equalsIgnoreCase("ocupada") && !extraerNoPregunta(numPreg)) {

                        preguntasOcupadas.add(numPreg);
                        modelo.removeElementAt(mens - 1);
                        modelo.insertElementAt(numPreg + " ocupada ", mens - 1);
                    }

                    if (estado.equalsIgnoreCase("libre") && extraerNoPregunta(numPreg)) {

                        preguntasOcupadas.remove(numPreg);
                        modelo.removeElementAt(mens - 1);
                        modelo.insertElementAt(numPreg, mens - 1);

                    }

                    if (estado.equalsIgnoreCase("respondida") && extraerNoPregunta(numPreg)) {

                        modelo.removeElementAt(mens - 1);
                        modelo.insertElementAt(numPreg + " respondida ", mens - 1);
                    }

                }

            }

        } catch (IOException ieo) {
            System.out.println("Error al recibir el mensaje");

        } catch (NumberFormatException nfe) {
            System.out.println("Error al convertir el numero de pregunta");
        }
    }

    /**
     * Funcion que extrae el numero de pregunta que esta ocupada
     *
     * @param NoPregunta
     * @return boolean
     */

    public boolean extraerNoPregunta(String NoPregunta) {
        Boolean esta = false;

        for (int x = 0; x < preguntasOcupadas.size(); x++) {
            String pregun = preguntasOcupadas.get(x);

            if (pregun.equalsIgnoreCase(NoPregunta)) {
                esta = true;
                break;
            }
        }

        return esta;
    }

}