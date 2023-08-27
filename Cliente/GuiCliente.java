package Cliente;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;

/**
 * Clase que contiene la interfaz grafica del cliente
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Muñoz
 * @version 1.0
 */
public class GuiCliente extends JFrame {

  Icon aceptado = new ImageIcon(getClass().getResource("imagenes/aceptado.png"));
  Icon error = new ImageIcon(getClass().getResource("imagenes/error.png"));

  JPanel pnlPrincipal;
  JPanel pnlPrincipalNorte, pnlPrincipalCentro, pnlPrincipalSur;
  JPanel pnlNorte, pnlCentro, pnlDerecho, pnlSur;
  JPanel pnlOpcion1, pnlOpcion2, pnlOpcion3, pnlOpcion4;
  JScrollPane scrollPreguntas, scrollOpciones;

  JTabbedPane pestanias;

  JLabel lbTitulo, lbEnunciado;
  JLabel lbMinutos, lbTiempo;
  JTextField tfTiempo;
  JTextArea taEnunciado;

  JRadioButton rbOpcion1, rbOpcion2, rbOpcion3, rbOpcion4;
  JButton btnEnviar, btnCancelar, btnObtener;

  String mensaje = "-1";
  boolean termina = false;

  JComboBox cbNoPreguntas;

  List<String> listaPreguntas = new ArrayList<>();

  ComboBoxModel modelo = new DefaultComboBoxModel<>(listaPreguntas.toArray(new String[0]));

  Cliente unCliente;

  ButtonGroup grupoOpciones;

  boolean ocupado = false;

  JPanel pnlPrincipalResult, pnlSuperiorResult, pnlInferiorResult, pnlCentralResult, pnlDerechoResult;
  JPanel pnlArea;

  JLabel lblTituloAnt;

  JTextArea taResultados;
  JScrollPane scrollResultados;

  JLabel lblCalificacion;
  JTextField tfCalificacion;

  JPanel panel1, panel2;

  JTextField tfCorrecto, tfIncorrecto;
  JLabel lblCorrecto, lblIncorrecto;

  /**
   * Constructor de la clase
   */
  public GuiCliente() {
    super("Cliente");
    setIconImage(new ImageIcon(getClass().getResource("imagenes/Univalle.jpg")).getImage());
    initComponents();
    setSize(1000, 700);
    setLocation(240, 20);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    unCliente = new Cliente();

  }

  /**
   * Metodo que inicializa los componentes de la interfaz grafica
   * 
   * @param void
   * @return void
   */
  public void initComponents() {

    pestanias = new JTabbedPane();

    // Panel principal del JFrame
    pnlPrincipal = new JPanel(new BorderLayout(1, 1));

    // Panel titulo
    pnlPrincipalNorte = new JPanel();

    Font fontTitle = new Font("Times new roman", Font.BOLD, 23);
    lbTitulo = new JLabel("Examen colaborativo");
    lbTitulo.setFont(fontTitle);

    pnlPrincipalNorte.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLUE));

    pnlPrincipalNorte.add(lbTitulo);

    // panel centro
    pnlPrincipalCentro = new JPanel(new BorderLayout(1, 30));

    // Panel norte
    pnlNorte = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

    lbEnunciado = new JLabel("Pregunta No. ");
    cbNoPreguntas = new JComboBox<>();
    cbNoPreguntas.setModel(modelo);

    btnObtener = new JButton("Obtener Pregunta");
    btnObtener.setPreferredSize(new Dimension(170, 29));
    btnObtener.setIcon(new ImageIcon(getClass().getResource("imagenes/pregunta.png")));

    pnlNorte.add(lbEnunciado);
    pnlNorte.add(cbNoPreguntas);
    pnlNorte.add(btnObtener);

    pnlNorte.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLUE));

    pnlPrincipalCentro.add(pnlNorte, BorderLayout.NORTH);

    // Panel centro
    pnlCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 2));

    taEnunciado = new JTextArea();
    scrollPreguntas = new JScrollPane(taEnunciado);
    scrollPreguntas.setPreferredSize(new Dimension(650, 363));

    pnlCentro.add(scrollPreguntas);

    pnlCentro.setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));

    pnlPrincipalCentro.add(pnlCentro, BorderLayout.CENTER);

    // Panel derecho
    pnlDerecho = new JPanel(new GridLayout(4, 1, 0, 0));

    rbOpcion1 = new JRadioButton("Opcion 1.");
    rbOpcion2 = new JRadioButton("Opcion 2.");
    rbOpcion3 = new JRadioButton("Opcion 3.");
    rbOpcion4 = new JRadioButton("Opcion 4.");

    grupoOpciones = new ButtonGroup();
    grupoOpciones.add(rbOpcion1);
    grupoOpciones.add(rbOpcion2);
    grupoOpciones.add(rbOpcion3);
    grupoOpciones.add(rbOpcion4);

    pnlOpcion1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    pnlOpcion2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    pnlOpcion3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
    pnlOpcion4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

    pnlOpcion1.add(rbOpcion1);
    pnlOpcion2.add(rbOpcion2);
    pnlOpcion3.add(rbOpcion3);
    pnlOpcion4.add(rbOpcion4);

    pnlDerecho.add(pnlOpcion1);
    pnlDerecho.add(pnlOpcion2);
    pnlDerecho.add(pnlOpcion3);
    pnlDerecho.add(pnlOpcion4);

    scrollOpciones = new JScrollPane(pnlDerecho);
    scrollOpciones.setPreferredSize(new Dimension(230, 100));

    pnlPrincipalCentro.add(scrollOpciones, BorderLayout.EAST);
    pnlDerecho.setVisible(false);

    // Panel Sur
    pnlSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 30));

    btnEnviar = new JButton("Enviar");
    btnEnviar.setPreferredSize(new Dimension(170, 29));
    btnEnviar.setIcon(new ImageIcon(getClass().getResource("imagenes/click.png")));
    btnCancelar = new JButton("Cancelar");
    btnCancelar.setPreferredSize(new Dimension(170, 29));
    btnCancelar.setIcon(new ImageIcon(getClass().getResource("imagenes/cancelar.png")));

    lbTiempo = new JLabel("Tiempo restante: ");
    tfTiempo = new JTextField(5);
    lbMinutos = new JLabel("minutos.");

    tfTiempo.setEditable(false);

    pnlSur.add(btnEnviar);
    pnlSur.add(btnCancelar);
    pnlSur.add(lbTiempo);
    pnlSur.add(tfTiempo);
    pnlSur.add(lbMinutos);

    pnlSur.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.BLUE, 2), "Opciones"));

    pnlPrincipalCentro.add(pnlSur, BorderLayout.SOUTH);

    // Añadimos paneles principales al panel principal
    pnlPrincipal.add(pnlPrincipalNorte, BorderLayout.NORTH);
    pnlPrincipal.add(pnlPrincipalCentro, BorderLayout.CENTER);

    pestanias.add("Examen Colaborativo", pnlPrincipal);

    // Aqui abajo todo lo del panel resultados.

    Font fontTitle2 = new Font("Times new roman", Font.BOLD, 25);
    lblTituloAnt = new JLabel("RESULTADOS");
    lblTituloAnt.setFont(fontTitle2);

    pnlPrincipalResult = new JPanel(new BorderLayout(0, 40));
    pnlSuperiorResult = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    pnlSuperiorResult.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

    pnlSuperiorResult.add(lblTituloAnt);

    pnlCentralResult = new JPanel(new BorderLayout(20, 20));

    pnlArea = new JPanel(new GridLayout(1, 1));

    taResultados = new JTextArea();
    taResultados.setEditable(false);

    scrollResultados = new JScrollPane(taResultados);

    pnlArea.add(scrollResultados);

    pnlCentralResult.add(pnlArea, BorderLayout.CENTER);

    pnlDerechoResult = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    tfCorrecto = new JTextField(2);
    tfCorrecto.setBackground(Color.GREEN);

    panel1 = new JPanel();
    panel1.add(tfCorrecto);

    lblCorrecto = new JLabel("Aprobó el examen");

    tfIncorrecto = new JTextField(2);
    tfIncorrecto.setBackground(Color.RED);

    panel2 = new JPanel();
    panel2.add(tfIncorrecto);

    lblIncorrecto = new JLabel("Reprobó el examen");

    pnlDerechoResult.add(lblCorrecto);
    pnlDerechoResult.add(panel1);
    pnlDerechoResult.add(lblIncorrecto);
    pnlDerechoResult.add(panel2);

    pnlCentralResult.add(pnlDerechoResult, BorderLayout.SOUTH);

    pnlCentralResult.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

    pnlInferiorResult = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

    lblCalificacion = new JLabel("LOS ESTUDIANTES OBTUVIERON UNA CALIFICACIÓN DE: ");
    tfCalificacion = new JTextField(10);
    tfCalificacion.setEditable(false);

    pnlInferiorResult.add(lblCalificacion);
    pnlInferiorResult.add(tfCalificacion);

    pnlPrincipalResult.add(pnlSuperiorResult, BorderLayout.NORTH);
    pnlPrincipalResult.add(pnlCentralResult, BorderLayout.CENTER);
    pnlPrincipalResult.add(pnlInferiorResult, BorderLayout.SOUTH);

    add(pestanias);

    ManejoEventos manejador = new ManejoEventos();

    btnObtener.addActionListener(manejador);
    btnEnviar.addActionListener(manejador);
    btnCancelar.addActionListener(manejador);

  }

  /**
   * Método que permite editar el enunciado del examen.
   * 
   * @param info
   * @return void
   */
  public void editarTAinfo(String info) {
    taEnunciado.setText(info);
  }

  /**
   * Método que permite editar el área de resultados.
   * 
   * @param info
   * @return void
   */
  public void editarTAresultados(String info) {
    taResultados.setText(info);
  }

  /**
   * Metodo que permite editar el radio button de la opción 1.
   * 
   * @param opcion1
   * @return void
   */
  public void editarOpcion1(String opcion1) {
    rbOpcion1.setText(opcion1);
  }

  /**
   * Metodo que permite editar el radio button de la opción 2.
   * 
   * @param opcion2
   * @return void
   */
  public void editarOpcion2(String opcion2) {
    rbOpcion2.setText(opcion2);
  }

  /**
   * Metodo que permite editar el radio button de la opción 3.
   * 
   * @param opcion3
   * @return void
   */
  public void editarOpcion3(String opcion3) {
    rbOpcion3.setText(opcion3);
  }

  /**
   * Metodo que permite editar el radio button de la opción 4.
   * 
   * @param opcion4
   * @return void
   */
  public void editarOpcion4(String opcion4) {
    rbOpcion4.setText(opcion4);
  }

  /**
   * Método que permite editar el textField donde va tiempo del examen.
   * 
   * @param tiempo
   * @return void
   */
  public void editarTFtiempo(String tiempo) {
    tfTiempo.setText(tiempo);
  }

  /**
   * Metodo que permite mostrar el panel derecho.
   * 
   * @param void
   * @return void
   */
  public void mostrarPnlDerecho() {
    pnlDerecho.setVisible(true);
  }

  /**
   * Metodo que permite ocultar el panel derecho.
   * 
   * @param void
   * @return void
   */
  public void ocultarPnlDerecho() {
    pnlDerecho.setVisible(false);
  }

  /**
   * Metodo que devuelve el mensaje que se va a enviar al servidor.
   * 
   * @param void
   * @return String
   */
  public String infoMensaje() {
    return mensaje;
  }

  /**
   * Metodo que permite agregar la pestaña de resultados.
   * 
   * @param void
   * @return void
   */
  public void verResultados() {
    pestanias.add("Resultados", pnlPrincipalResult);
  }

  /**
   * Metodo que permite editar el textField de la calificación.
   * 
   * @param String
   * @return void
   */
  public void editarTfCalificacion(String calificacion) {

    tfCalificacion.setText(calificacion);
    calificacion = calificacion.replace(',', '.');
    float aprobo = Float.parseFloat(calificacion);

    if (aprobo < 3) {

      pnlInferiorResult.setBorder(BorderFactory.createTitledBorder(
          BorderFactory.createLineBorder(Color.RED, 2), "CALIFICACIÓN"));

    } else {

      pnlInferiorResult.setBorder(BorderFactory.createTitledBorder(
          BorderFactory.createLineBorder(Color.GREEN, 2), "CALIFICACIÓN"));

    }
  }

  /**
   * Metodo que envia funciona como puente entre la gui y el cliente para enviar
   * los datos al servidor.
   * 
   * @param mensaje
   */
  public void enviarDatos(String mensaje) {
    unCliente.enviarDatos(mensaje);
  }

  /**
   * Metodo que permite borrar la opción seleccionada.
   * 
   * @param void
   * @return void
   */
  public void borrarOpcionSeleccionada() {
    grupoOpciones.clearSelection();
  }

  /**
   * Metodo que permite mostrar el scroll de las opciones.
   * 
   * @param void
   * @return void
   */
  public void mostrarScrollOpciones() {
    scrollOpciones.setVisible(true);
  }

  /**
   * Metodo que permite ocultar el scroll de las opciones.
   * 
   * @param void
   * @return void
   */
  public void ocultarScrollOpciones() {
    scrollOpciones.setVisible(false);
  }

  /**
   * Clase que maneja los eventos de la Gui del cliente
   * 
   * @author Jose Manuel Bravo & Andres Mauricio Muñoz
   * @version 1.0
   */
  class ManejoEventos implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

      if (e.getSource() == btnObtener) {

        mensaje = "seleccionar " + (String) cbNoPreguntas.getSelectedItem();

        unCliente.enviarDatos(mensaje);

        cbNoPreguntas.setEnabled(false);

      }

      if (e.getSource() == btnEnviar) {

        if (rbOpcion1.isSelected()) {

          mensaje = "respuesta 1";
          unCliente.enviarDatos(mensaje);

        }

        if (rbOpcion2.isSelected()) {

          mensaje = "respuesta 2";
          unCliente.enviarDatos(mensaje);

        }

        if (rbOpcion3.isSelected()) {

          mensaje = "respuesta 3";
          unCliente.enviarDatos(mensaje);

        }

        if (rbOpcion4.isSelected()) {

          mensaje = "respuesta 4";
          unCliente.enviarDatos(mensaje);

        }

        if (grupoOpciones.getSelection() == null) {
          JOptionPane.showMessageDialog(null, "Debe seleccionar una opción");
        }

        borrarOpcionSeleccionada();
        cbNoPreguntas.setEnabled(true);

      }

      if (e.getSource() == btnCancelar) {

        mensaje = "cancelar";
        unCliente.enviarDatos(mensaje);
        cbNoPreguntas.setEnabled(true);

      }

    }

  }

  /**
   * Clase principal del cliente
   * 
   * @author Jose Manuel Bravo & Andres Mauricio Muñoz
   * @version 1.0
   */
  public class PrincipalCliente {
    public static void main(String[] args) {
      GuiCliente app = new GuiCliente();

      ConexionMulti conexionMulti = new ConexionMulti(app);
      conexionMulti.start();
      app.unCliente.iniciarCliente(app);
    }
  }

}
