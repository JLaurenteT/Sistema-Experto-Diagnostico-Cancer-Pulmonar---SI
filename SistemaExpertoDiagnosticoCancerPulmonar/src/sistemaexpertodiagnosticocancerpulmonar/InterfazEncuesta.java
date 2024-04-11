package sistemaexpertodiagnosticocancerpulmonar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import org.jpl7.*;

public class InterfazEncuesta extends javax.swing.JFrame {
    private JTextField nombreField;
    private JTextField apellidosField;
    private JTextField edadField;
    private JTextField nacionalidadField;
    private ButtonGroup generoGroup;
    private ButtonGroup sintomasGroup;
    private ButtonGroup causasGroup;
    private JButton btnGuardarDatos;

    private String nombre;
    private String apellidos;
    private String edad;
    private String nacionalidad;
    private String genero;
    private String sintomaSeleccionado;
    private String causaSeleccionada;
    private boolean datosPersonalesGuardados = false;
    
    private ArrayList<JCheckBox> checkBoxesSintomas = new ArrayList<>();
    private ArrayList<JCheckBox> checkBoxesCausas = new ArrayList<>();


    public InterfazEncuesta() {
        setTitle("Encuesta de Diagnóstico de Cáncer Pulmonar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelDatosPersonales = new JPanel(new GridLayout(0, 2, 10, 10));
        panelDatosPersonales.setBorder(BorderFactory.createTitledBorder("Datos Personales"));

        nombreField = new JTextField();
        apellidosField = new JTextField();
        edadField = new JTextField();
        nacionalidadField = new JTextField();

        generoGroup = new ButtonGroup();
        JRadioButton rbMasculino = new JRadioButton("Masculino");
        rbMasculino.setActionCommand("Masculino");
        JRadioButton rbFemenino = new JRadioButton("Femenino");
        rbFemenino.setActionCommand("Femenino");
        generoGroup.add(rbMasculino);
        generoGroup.add(rbFemenino);

        panelDatosPersonales.add(new JLabel("Nombre:"));
        panelDatosPersonales.add(nombreField);
        panelDatosPersonales.add(new JLabel("Apellidos:"));
        panelDatosPersonales.add(apellidosField);
        panelDatosPersonales.add(new JLabel("Edad:"));
        panelDatosPersonales.add(edadField);
        panelDatosPersonales.add(new JLabel("Nacionalidad:"));
        panelDatosPersonales.add(nacionalidadField);
        panelDatosPersonales.add(new JLabel("Género:"));

        JPanel generoYBotonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        generoYBotonPanel.add(rbMasculino);
        generoYBotonPanel.add(rbFemenino);
        btnGuardarDatos = new JButton("Guardar Datos Personales");
        btnGuardarDatos.addActionListener(this::guardarDatosPersonales);
        generoYBotonPanel.add(btnGuardarDatos);

        panelDatosPersonales.add(generoYBotonPanel);
        
        add(panelDatosPersonales, BorderLayout.NORTH);

        cargarBaseConocimientos();

        JPanel panelSintomas = crearPanelDeSeleccion("¿Qué síntomas tiene actualmente?", "sintoma", checkBoxesSintomas);
        JPanel panelCausas = crearPanelDeSeleccion("¿Considera alguna de estas causas para los síntomas?", "factor_riesgo", checkBoxesCausas);

        JPanel panelBotones = new JPanel();
        JButton btnCalcularDiagnostico = new JButton("Calcular Diagnóstico");
        btnCalcularDiagnostico.addActionListener(this::calcularDiagnostico);
        panelBotones.add(btnCalcularDiagnostico);

        JPanel panelCentral = new JPanel(new GridLayout(2, 1));
        panelCentral.add(panelSintomas);
        panelCentral.add(panelCausas);

        add(panelCentral, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        pack(); 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarBaseConocimientos() {
        Query query = new Query("consult", new Term[]{new Atom("src/experto.pl")});
        System.out.println("consult " + (query.hasSolution() ? "succeeded" : "failed"));
    }
    
    private void guardarDatosPersonales(ActionEvent e) {
        nombre = nombreField.getText().trim();
    apellidos = apellidosField.getText().trim();
    edad = edadField.getText().trim();
    nacionalidad = nacionalidadField.getText().trim();
        genero = generoGroup.getSelection().getActionCommand();

        datosPersonalesGuardados = true;
        
        JOptionPane.showMessageDialog(this, "Datos personales guardados.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private JPanel crearPanelDeSeleccion(String titulo, String consultaProlog, ArrayList<JCheckBox> checkBoxesLista) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(titulo));

        Query query = new Query(consultaProlog, new Variable("X"));
        while (query.hasMoreSolutions()) {
            java.util.Map<String, Term> solution = query.nextSolution();
            JCheckBox checkBox = new JCheckBox(solution.get("X").toString());
            checkBoxesLista.add(checkBox);
            panel.add(checkBox);
        }

        return panel;
    }
    
    private void calcularDiagnostico(ActionEvent e) {
    if (!datosPersonalesGuardados) {
        JOptionPane.showMessageDialog(this, "Por favor, guarde sus datos personales antes de realizar el diagnóstico.", "Datos Personales Incompletos", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    double porcentajeFinal = 0.0;

    // Calculando el porcentaje total basado en síntomas seleccionados
    for (JCheckBox checkBox : checkBoxesSintomas) {
        if (checkBox.isSelected()) {
            // Asignando el porcentaje específico a cada síntoma
            switch (checkBox.getText()) {
                case "tos_persistente":
                    porcentajeFinal += 3.70; // Ejemplo de porcentaje para la tos persistente
                    break;
                case "dificultad_respiratoria":
                    porcentajeFinal += 3.30; // Ejemplo de porcentaje para el dolor de pecho
                    break;
                case "perdida_peso":
                    porcentajeFinal += 3.70; // Ejemplo de porcentaje para el dolor de pecho
                    break;
                case "dolor_taracico":
                    porcentajeFinal += 5.00; // Ejemplo de porcentaje para la pérdida de peso
                    break;
                
            }
        }
    }

    // Calculando el porcentaje total basado en factores de riesgo seleccionados
    for (JCheckBox checkBox : checkBoxesCausas) {
        if (checkBox.isSelected()) {
            // Asignando el porcentaje específico a cada factor de riesgo
            switch (checkBox.getText()) {
                case "tabaquismo":
                    porcentajeFinal += 15.75; // Ejemplo de porcentaje para el tabaquismo
                    break;
                case "exposicion_radon":
                    porcentajeFinal += 17.60; // Ejemplo de porcentaje para la exposición al asbesto
                    break;
                case "asbesto":
                    porcentajeFinal += 31.50; // Ejemplo de porcentaje para antecedentes familiares
                    break;
                case "contaminacion_aire":
                    porcentajeFinal += 6.30; // Ejemplo de porcentaje para antecedentes familiares
                    break;
            }
        }
    }
    
    StringBuilder mensaje = new StringBuilder();
    mensaje.append("Datos Personales:\n");
    mensaje.append("Nombre: ").append(nombreField.getText().trim()).append("\n");
    mensaje.append("Apellidos: ").append(apellidosField.getText().trim()).append("\n");
    mensaje.append("Edad: ").append(edadField.getText().trim()).append("\n");
    mensaje.append("Nacionalidad: ").append(nacionalidadField.getText().trim()).append("\n");
    mensaje.append("Género: ").append(generoGroup.getSelection().getActionCommand()).append("\n\n");
    
    mensaje.append(String.format("Porcentaje final: %.2f%%\n", porcentajeFinal));
    
    if (porcentajeFinal > 50) {
        mensaje.append("Diagnósticos recomendados:\n");
        agregarRecomendaciones(mensaje, "requiere_diagnostico", "cancer_pulmonar");
        if (porcentajeFinal > 80) {
            mensaje.append("Tratamientos recomendados:\n");
            agregarRecomendaciones(mensaje, "recibe_tratamiento", "cancer_pulmonar");
        }
    }
    
    JOptionPane.showMessageDialog(this, mensaje.toString(), "Resultado del Diagnóstico", JOptionPane.INFORMATION_MESSAGE);
}
    
    private void agregarRecomendaciones(StringBuilder mensaje, String relacion, String enfermedad) {
        Query query = new Query(relacion, new Term[]{new Atom(enfermedad), new Variable("X")});
        while (query.hasMoreSolutions()) {
            java.util.Map<String, Term> solution = query.nextSolution();
            mensaje.append("- ").append(solution.get("X")).append("\n");
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazEncuesta().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
