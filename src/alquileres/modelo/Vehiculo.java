package alquileres.modelo;

import java.util.Comparator;

/**
 * Representa a un vehículo en alquiler De esta clase no se crearán instancias
 * 
 * De un vehículo se conoce su matrícula, marca, modelo y el precio a pagar
 * por día de alquiler
 * 
 * Para todo vehículo se puede calcular su coste de alquiler que depende del
 * nº de días que se alquile (llamaremos a esta operación
 * calcularPrecioAlquiler() )
 * 
 * Dos vehículos pueden compararse por su matrícula (es su orden natural)
 * 
 * Dos vehículos son iguales si además de pertenecer a la misma clase tienen
 * la misma matrícula
 * 
 * @author Sergio Garru�s Aizcorbe
 */
public abstract class Vehiculo implements Comparator<Vehiculo> {
	private String matricula;
	private String marca;
	private String modelo;
	private double precioDia;

	/**
	 * Constructor
	 */
	public Vehiculo(String matricula, String marca, String modelo, double precioDia) {
		this.matricula = matricula.toUpperCase();
		this.marca = marca.toUpperCase();
		this.modelo = modelo.toUpperCase();
		this.precioDia = precioDia;

	}

	public String getMatricula() {
		return matricula;
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public double getPrecioDia() {
		return precioDia;
	}

	public double calcularPrecioAlquiler(int numeroDias) {

		return numeroDias * precioDia;
	}

	public int compare(Vehiculo vehiculo1, Vehiculo vehiculo2) {
		if (Integer.parseInt(vehiculo1.getMatricula().substring(0, 5)) < Integer
				.parseInt(vehiculo2.getMatricula().substring(0, 5))) {
			return -1;
		}
		if (Integer.parseInt(vehiculo1.getMatricula().substring(0, 5)) == Integer
				.parseInt(vehiculo2.getMatricula().substring(0, 5))) {
			return 0;
		}
		return 1;

	}

	public boolean equals(Vehiculo vehiculo) {
		if (vehiculo == null) {
			return false;
		}
		if (vehiculo == this) {
			return true;
		}
		if (this.getClass() != vehiculo.getClass()) {
			return false;
		}
		
		return vehiculo.getMatricula() == getMatricula();
	}

	/**
	 * Redefinición de hashCode()
	 * 
	 */
	@Override
	public int hashCode() {
		return matricula.hashCode() * 13;
	}

	public String toString() {
		return "Matr�cula: " + matricula + " | Marca: " + marca + " | Modelo: " + modelo + "\nPrecio d�a alquiler: "
				+ precioDia + "� | ";
	}

}