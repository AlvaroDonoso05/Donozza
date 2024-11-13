package controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

<<<<<<< HEAD
=======
import controlador.Logger;
import modelo.Pizzas;
>>>>>>> branch 'master' of https://github.com/Donoso005/Donozza.git
import modelo.Comanda;
import vista.Vista;

public class Controlador implements ActionListener{
	
	Vista vista;
	Logger logger = new Logger();
	public ArrayList<Comanda>comandas = new ArrayList<>();

	public Controlador(Vista frame) {
		this.vista=frame;
		this.vista.btnMesa1.addActionListener(this);
		this.vista.btnMesa2.addActionListener(this);
		this.vista.btnMesa3.addActionListener(this);
		this.vista.btnMesa4.addActionListener(this);
		this.vista.btnMesa5.addActionListener(this);
		this.vista.btnMesa6.addActionListener(this);
		this.vista.btnMesa7.addActionListener(this);
		this.vista.btnMesa8.addActionListener(this);
		this.vista.btnPMVolver.addActionListener(this);
		
	}
	
	
	public void llenarTabla() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//BOTONES MESAS
		if(e.getSource()==vista.btnMesa1) {
			vista.lblMesa.setText("MESA 1");
		}
		if(e.getSource()==vista.btnMesa2) {
			vista.lblMesa.setText("MESA 2");
		}
		if(e.getSource()==vista.btnMesa3) {
			vista.lblMesa.setText("MESA 3");
		}
		if(e.getSource()==vista.btnMesa4) {
			vista.lblMesa.setText("MESA 4");
		}
		if(e.getSource()==vista.btnMesa5) {
			vista.lblMesa.setText("MESA 5");
		}
		if(e.getSource()==vista.btnMesa6) {
			vista.lblMesa.setText("MESA 6");
		}
		if(e.getSource()==vista.btnMesa7) {
			vista.lblMesa.setText("MESA 7");
		}
		if(e.getSource()==vista.btnMesa8) {
			vista.panel.setVisible(false);
			vista.panelMesa.setVisible(true);
			vista.lblMesa.setText("MESA 8");
		}
		
		//BOTONES SECCIÓN COMANDAS
		if(e.getSource()==vista.btnPMVolver) {
			vista.panel.setVisible(true);
			vista.panelMesa.setVisible(false);
		}
	}

	
}
