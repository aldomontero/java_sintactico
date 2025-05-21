import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class principal extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private int frameNumber = -1;

    private Timer timer;
    private JLabel imagen;
    private Label estado;
    private boolean frozen = false;
	private JFrame frm = null;
	
    public principal() {
        super("Ronny 1.1");
		
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			System.out.println("Error al cambiar el estilo de Windows: " + e);
		}
		
        int delay = 1000;
		ImageIcon icono = compilador.crearIcono("logo.gif");

        imagen = new JLabel(icono, JLabel.CENTER);
        estado = new Label("Iniciando la aplicacion...", Label.CENTER);
		estado.setBackground(Color.BLACK);
		estado.setForeground(Color.WHITE);
		
        timer = new Timer(delay, this);
        timer.setInitialDelay(0);
        timer.setCoalesce(true);
        
        getContentPane().add(imagen, BorderLayout.CENTER);
        getContentPane().add(estado, BorderLayout.SOUTH);
         
		setUndecorated(true);
		pack();
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
		startAnimation();
        
    }

    public void startAnimation() {
        if (frozen) {
        } else {
            if (!timer.isRunning()) {
                timer.start();
            }
        }
    }


    public void stopAnimation() {
        if (timer.isRunning())
            timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        frameNumber++;
		
        if(frameNumber == 1){
        	estado.setText("Construyendo interfaz...");
			frm = new compilador();
        } else if(frameNumber == 2){
        	estado.setText("Terminado.");
        } else if(frameNumber == 3){
        	stopAnimation();
        	dispose();
        	frm.setVisible(true);
        }
    }
}
	