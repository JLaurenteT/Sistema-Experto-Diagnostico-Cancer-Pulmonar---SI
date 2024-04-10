package sistemaexpertodiagnosticocancerpulmonar;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class InterfazBienvenida extends javax.swing.JFrame {

    public InterfazBienvenida() {
        setTitle("Bienvenido al Sistema Experto de Diagnóstico de Cáncer Pulmonar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 750);
        setLocationRelativeTo(null); 

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Bienvenido al Sistema Experto de Diagnóstico de Cáncer Pulmonar");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); 
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        ImageIcon backgroundImage = new ImageIcon("src/Imagenes/Fondo1.jpg"); 
        JLabel backgroundLabel = new JLabel(backgroundImage);
        mainPanel.add(backgroundLabel, BorderLayout.CENTER);

        ImageIcon lungImage = new ImageIcon("src/Imagenes/Pulmon.png"); 
        JLabel lungLabel = new JLabel(lungImage);
        lungLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(lungLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Comenzar");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                InterfazEncuesta encuesta = new InterfazEncuesta();
                encuesta.setVisible(true); 
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton exitButton = new JButton("Salir");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonPanel.add(exitButton);
        add(mainPanel);
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

    public static void main(String args[]) {
 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterfazBienvenida interfaz = new InterfazBienvenida();
                interfaz.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
