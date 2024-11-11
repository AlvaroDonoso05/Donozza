package modelo;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
	private int numMesa;
	private List<Comanda> comandasMesa;
	
	public Mesa(int numMesa) {
		this.numMesa = numMesa;
		this.comandasMesa = new ArrayList<>();
	}

	public int getNumMesa() {
		return numMesa;
	}

	public void setNumMesa(int numMesa) {
		this.numMesa = numMesa;
	}

	public List<Comanda> getComandasMesa() {
		return comandasMesa;
	}

	public void setComandasMesa(List<Comanda> comandasMesa) {
		this.comandasMesa = comandasMesa;
	}
	
	public String[][] llenarMatrizComandas() throws Exception{

		String[][] comandas = new String[this.comandasMesa.size()][3];
		
		try {
			for(int i=0;i<this.comandasMesa.size();i++) {
				comandas[i][0]=this.comandasMesa.get(i).getNombre();
				comandas[i][1]=String.valueOf(this.comandasMesa.get(i).getCantidad());
				comandas[i][2]=String.valueOf(this.comandasMesa.get(i).getPrecio());
			}
		}catch(Exception e) {
			throw e;
		};
		return comandas;
		
	}
}
