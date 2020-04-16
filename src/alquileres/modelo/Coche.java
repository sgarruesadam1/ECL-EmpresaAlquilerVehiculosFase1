package alquileres.modelo;

/**
 * Un coche es un vehículo que añade un nº de plazas
 * 
 * El coste final de alquiler depende no solo del nº de días de alquiler 
 * sino también del nº de plazas. Si el nº de plazas es > 4 se añaden 5€ más por día 
 * @author Sergio Garru�s Aizcorbe
 */
public class Coche extends Vehiculo{
	private int numPlazas;

	public Coche(String matricula, String marca, String modelo, double precioDia, int numPlazas) {
		super(matricula, marca, modelo, precioDia);
		this.numPlazas = numPlazas;
	}

	public int getNumPlazas() {
		return numPlazas;
	}
	public double calcularPrecioAlquiler(int numeroDias) {
		
		if(numPlazas > 4)
		{
		return numeroDias * (super.getPrecioDia() + 5);
		}
		return numeroDias * super.getPrecioDia();
	}
	public String toString() {
		return "COCHE\n" + super.toString() + "Plazas: " + numPlazas;
	}
}
