package servidor;

import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * Clase que contiene la interfaz grafica del servidor
 * 
 * @author Jose Manuel Bravo & Andres Mauricio Muñoz
 * @version 1.0
 */
public class GuiServidor extends JFrame {

  private JPanel pnlPrincipalConf, pnlSuperiorConf, pnlInferiorConf, pnlCentralConf, pnlDerechoConf;
  private JPanel pnlNombreExamen, pnlAsignarTiempo, pnlMensaje, pnlSeleccionExamen;

  private JLabel lblTitulo;
  private Boolean inicio = false;

  private JLabel lblDefinirNombreExamen;
  private JTextField tfDefinirNombreExamen;

  private JLabel lblDefinirTiempo;
  private JTextField tfDefinirTiempo;

  private JLabel lblMensajeCargar;

  private JLabel lblSeleccionarExamen;

  private JTabbedPane pestanias;

  private JTextArea taInfo;
  private JScrollPane spInfo;

  private String[] examenes = { "Seleccione un examen" };
  private JComboBox cbExamenes;

  private JButton btnIniciarExamen, btnSeleccionarArchivo;

  private JPanel pnlPrincipalResult, pnlSuperiorResult, pnlInferiorResult, pnlCentralResult, pnlDerechoResult;
  private JPanel pnlTabla;

  private JLabel lblTituloAnt;
  private List<String> listaExamenes = new ArrayList<>();

  private ComboBoxModel modelo = new DefaultComboBoxModel<>(listaExamenes.toArray(new String[0]));
  private JComboBox cbRegistroExamenes;

  private JTable tabla;
  private DefaultTableModel modeloTabla;
  private JScrollPane scrollTabla;

  private colorCelda colorearCelda = new colorCelda();

  private String[] cabezera = { "Pregunta #", "Enunciado pregunta", "Respondida por", "Correcta?" };
  private String[][] data = { { "", "", "", "" } };

  private JLabel lblCalificacion;
  private JTextField tfCalificacion;

  private JPanel panel1, panel2;

  private JTextField tfCorrecto, tfIncorrecto;
  private JLabel lblCorrecto, lblIncorrecto;

  private Gestion unExamen;
  private boolean botonIniciado = false;

  /**
   * Constructor de la clase GuiServidor
   */
  public GuiServidor() {

    super("Servidor");
    initComponents();
    setSize(800, 600);
    setLocation(280, 100);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    unExamen = new Gestion();
    setIconImage(new ImageIcon(getClass().getResource("imagenes/Univalle.jpg")).getImage());
  }

  /**
   * Metodo que inicializa los componentes de la interfaz grafica
   * 
   * @param void
   * @return void
   */
  public void initComponents() {

    // Creamos pestañas y paneles.
    pestanias = new JTabbedPane();

    pnlPrincipalConf = new JPanel(new BorderLayout(0, 40));
    pnlSuperiorConf = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    pnlInferiorConf = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    pnlCentralConf = new JPanel(new GridLayout(4, 1));

    // Definimos el tipo de letra y el tamaño del titulo y lo agregamos al panel
    // superior.
    Font fontTitle = new Font("Times new roman", Font.BOLD, 25);
    lblTitulo = new JLabel("Examen colaborativo");
    lblTitulo.setFont(fontTitle);

    pnlSuperiorConf.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

    pnlSuperiorConf.add(lblTitulo);

    // Agregamos los componentes a cada panel para mejorar la distribucion.
    lblDefinirNombreExamen = new JLabel("Nombre del examen: ");
    tfDefinirNombreExamen = new JTextField(20);

    pnlNombreExamen = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

    pnlNombreExamen.add(lblDefinirNombreExamen);
    pnlNombreExamen.add(tfDefinirNombreExamen);
    // Fin nombre examen

    pnlAsignarTiempo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

    lblDefinirTiempo = new JLabel("Definir tiempo:            ");
    tfDefinirTiempo = new JTextField(20);

    pnlAsignarTiempo.add(lblDefinirTiempo);
    pnlAsignarTiempo.add(tfDefinirTiempo);
    // Fin definir tiempo

    pnlMensaje = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

    lblMensajeCargar = new JLabel("Para cargar las preguntas del examen presione el boton cargar examen.");

    pnlMensaje.add(lblMensajeCargar);
    // Fin mensaje

    pnlSeleccionExamen = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

    lblSeleccionarExamen = new JLabel("Seleccione examen a iniciar: ");
    cbExamenes = new JComboBox<>(examenes);

    pnlSeleccionExamen.add(lblSeleccionarExamen);
    pnlSeleccionExamen.add(cbExamenes);
    // Fin seleccion examen

    pnlCentralConf.setBorder(new MatteBorder(2, 0, 2, 0, Color.BLACK));

    // Añadimos todos los paneles al panel central
    pnlCentralConf.add(pnlNombreExamen);
    pnlCentralConf.add(pnlAsignarTiempo);
    pnlCentralConf.add(pnlMensaje);
    pnlCentralConf.add(pnlSeleccionExamen);

    // Agregamos los componentes al panel central derecho.
    pnlDerechoConf = new JPanel(new GridLayout(1, 1));

    taInfo = new JTextArea(10, 20);
    taInfo.setEditable(false);

    spInfo = new JScrollPane(taInfo);

    pnlDerechoConf.add(spInfo);

    // Agregamos los componentes al panel inferior.

    btnSeleccionarArchivo = new JButton("Cargar Examen");
    btnSeleccionarArchivo.setPreferredSize(new Dimension(170, 29));
    btnSeleccionarArchivo.setIcon(new ImageIcon(getClass().getResource("imagenes/cargarExamen.png")));
    btnIniciarExamen = new JButton("Iniciar examen");
    btnIniciarExamen.setPreferredSize(new Dimension(170, 29));
    btnIniciarExamen.setIcon(new ImageIcon(getClass().getResource("imagenes/iniciar.png")));

    pnlInferiorConf.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.BLACK, 2), "Acciones"));

    pnlInferiorConf.add(btnSeleccionarArchivo);
    pnlInferiorConf.add(btnIniciarExamen);

    // Añadimos los paneles al panel principal y posteriormente lo añadimos a las
    // pestañas
    pnlPrincipalConf.add(pnlSuperiorConf, BorderLayout.NORTH);
    pnlPrincipalConf.add(pnlCentralConf, BorderLayout.CENTER);
    pnlPrincipalConf.add(pnlDerechoConf, BorderLayout.EAST);
    pnlPrincipalConf.add(pnlInferiorConf, BorderLayout.SOUTH);

    pestanias.add("Configuración", pnlPrincipalConf);

    // Aqui abajo todo lo del panel resultados.

    Font fontTitle2 = new Font("Times new roman", Font.BOLD, 25);
    lblTituloAnt = new JLabel("Historial de examenes");
    lblTituloAnt.setFont(fontTitle2);

    cbRegistroExamenes = new JComboBox<>();
    cbRegistroExamenes.setModel(modelo);
    cbRegistroExamenes.addItem("Seleccione un examen");

    pnlPrincipalResult = new JPanel(new BorderLayout(0, 40));
    pnlSuperiorResult = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    pnlSuperiorResult.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

    pnlSuperiorResult.add(lblTituloAnt);
    pnlSuperiorResult.add(cbRegistroExamenes);

    pnlCentralResult = new JPanel(new BorderLayout(20, 20));

    pnlTabla = new JPanel(new GridLayout(1, 1));

    modeloTabla = new DefaultTableModel(data, cabezera) {

      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }

    };

    tabla = new JTable(modeloTabla);

    TableColumn columnaNoPregunta = tabla.getColumn("Pregunta #");
    TableColumn columnaEnunciado = tabla.getColumn("Enunciado pregunta");
    TableColumn columnaRespondidaPor = tabla.getColumn("Respondida por");
    TableColumn columnaCorrecta = tabla.getColumn("Correcta?");

    columnaNoPregunta.setMinWidth(100);
    columnaNoPregunta.setMaxWidth(100);

    columnaRespondidaPor.setMinWidth(100);
    columnaRespondidaPor.setMaxWidth(100);

    columnaCorrecta.setMinWidth(100);
    columnaCorrecta.setMaxWidth(100);

    tabla.setRowHeight(30);
    tabla.getColumnModel().getColumn(3).setCellRenderer(colorearCelda);

    scrollTabla = new JScrollPane(tabla);

    pnlTabla.add(scrollTabla);

    pnlCentralResult.add(pnlTabla, BorderLayout.CENTER);

    pnlDerechoResult = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

    tfCorrecto = new JTextField(2);
    tfCorrecto.setBackground(Color.GREEN);

    panel1 = new JPanel();
    panel1.add(tfCorrecto);

    lblCorrecto = new JLabel("Respuesta Correcta");

    tfIncorrecto = new JTextField(2);
    tfIncorrecto.setBackground(Color.RED);

    panel2 = new JPanel();
    panel2.add(tfIncorrecto);

    lblIncorrecto = new JLabel("Respuesta Incorrecta");

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

    pestanias.add("Anteriores", pnlPrincipalResult);

    add(pestanias);

    Manejo manejador = new Manejo();

    btnSeleccionarArchivo.addActionListener(manejador);
    btnIniciarExamen.addActionListener(manejador);
    cbRegistroExamenes.addActionListener(manejador);
    addWindowListener(manejador);

  }

  /**
   * Método que permite acceder a la variable inicio.
   * 
   * @param texto
   * @return void
   */
  public boolean getInicio() {
    return inicio;
  }

  /**
   * Método que permite editar el JTextArea de la pestaña de configuración.
   * 
   * @param texto
   * @return void
   */
  public void editarTAinfo(String texto) {
    taInfo.append(texto);
  }

  /**
   * Método que permite editar el JComboBox de la pestaña de resultados.
   * 
   * @param texto
   * @return void
   */
  public void editarCbRegistroExamenes(String texto) {
    cbRegistroExamenes.addItem(texto);
  }

  /**
   * Método que permite editar el JTextField de la pestaña de resultados.
   * 
   * @param texto
   * @return void
   */
  public void editarTfCalificacion(String texto) {
    tfCalificacion.setText(texto);
  }

  /**
   * Método que permite editar la tabla de la pestaña de resultados.
   * 
   * @param String[]
   * @return void
   */
  public void editarJTable(String[] datos) {
    modeloTabla.addRow(datos);
  }

  /**
   * Método que permite eliminar la tabla de la pestaña de resultados.
   * 
   * @return void
   */
  public void eliminarJTable() {
    modeloTabla.setRowCount(0);
  }

  /**
   * Método que retorna el JComboBox de la pestaña de resultados llamada registro
   * de examenes.
   * 
   * @param void
   * @return JComboBox
   * 
   */
  public JComboBox getCBregistroExamenes() {
    return cbRegistroExamenes;
  }

  /**
   * Metodo que retorna el objeto de la clase Gestion.
   * 
   * @return
   */
  public Gestion getGestion() {
    return unExamen;
  }

  /**
   * Método que permite borrar los datos de la pestaña de configuración.
   * 
   * @param void
   * @return void
   */
  public void borrarDatos() {
    tfDefinirNombreExamen.setText("");
    tfDefinirTiempo.setText("");
  }

  /**
   * Método que permite cambiar el color del borde del panel inferiorResult segun
   * la calificación del examen.
   * 
   * @param void
   * @return void
   */
  public void cambiarColorBorde() {

    String calificacion = tfCalificacion.getText();
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
   * Clase interna que permite manejar de manera individual las celdas de la
   * tabla.
   */
  class colorCelda extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
        int row, int column) {

      if (table.getValueAt(row, 3).equals("Correcta")) {

        setForeground(Color.green);
        setFont(new Font("Arial", Font.BOLD, 12));
      }

      if (table.getValueAt(row, 3).equals("Incorrecta")) {

        setForeground(Color.red);
        setFont(new Font("Arial", Font.BOLD, 12));
      }

      super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

      return this;
    }

  }

  /**
   * Objeto de la clase Date que permite obtener la hora actual.
   */
  Date hora = new Date();
  SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
  String horaActual = formato.format(hora);

  /**
   * Clase interna Manejo que implementa ActionListener y WindowListener.
   * 
   */
  class Manejo implements ActionListener, WindowListener {

    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == btnSeleccionarArchivo) {

        try {

          JFileChooser fc = new JFileChooser();
          fc.showOpenDialog(null);
          File archivo = fc.getSelectedFile();
          String ruta = archivo.getAbsolutePath();

          String nombreExamen = tfDefinirNombreExamen.getText();
          if (nombreExamen.isEmpty()) {

            taInfo.append("\nPor favor ingrese el nombre del examen " + " - " + horaActual);
            tfDefinirNombreExamen.requestFocus();

          } else {

            int tiempoExamen = Integer.parseInt(tfDefinirTiempo.getText());

            if (tiempoExamen <= 0) {
              JOptionPane.showMessageDialog(null, "El tiempo de examen debe ser mayor a 0");
              tfDefinirTiempo.requestFocus();

            } else {
              taInfo.append("\nTiempo de examen: " + tiempoExamen + " minutos" + " - " + horaActual);
              unExamen.leerArchivo(nombreExamen, ruta, tiempoExamen);
              cbExamenes.addItem(nombreExamen);
              borrarDatos();

            }

          }

        } catch (NumberFormatException ex) {

          taInfo.setText("\nError al cargar el examen, datos erroneos" + " - " + horaActual);
          tfDefinirTiempo.requestFocus();

        } catch (NullPointerException npe) {

          taInfo.setText("\nError al cargar el examen, no se ha seleccionado ningun archivo" + " - " + horaActual);

        }

      }

      if (e.getSource() == btnIniciarExamen) {

        if (cbExamenes.getSelectedItem() == null || cbExamenes.getSelectedItem().equals("Seleccione un examen")) {

          taInfo.setText("\nPor favor seleccione un examen para iniciar" + " - " + horaActual);

        } else {

          String nombreExamen = (String) cbExamenes.getSelectedItem();

          unExamen.iniciarExamen(nombreExamen);
          inicio = true;

          taInfo.setText("\nExamen iniciado correctamente" + " - " + horaActual);

        }

      }

      if (e.getSource() == cbRegistroExamenes) {

        String nombreExamen = (String) cbRegistroExamenes.getSelectedItem();
        if (nombreExamen.equals("Seleccione un examen")) {
          taInfo.setText("\nPor favor seleccione un examen para ver su historial" + " - " + horaActual);
        } else {
          unExamen.buscarHistorial(nombreExamen);
          unExamen.mostrarHistorial(GuiServidor.this);
          cambiarColorBorde();
        }

      }

    }

    @Override
    public void windowOpened(WindowEvent e) {
      taInfo.setText("\nBienvenido al sistema de examenes" + " - " + horaActual);
      unExamen.cargarHistorialExamenes();
      unExamen.mostrarHistorial(GuiServidor.this);

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

  }

}
