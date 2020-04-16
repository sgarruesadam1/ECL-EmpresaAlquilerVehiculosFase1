package alquileres.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import alquileres.test.Utilidades;

/**
 * La clase guarda en una colección List (un ArrayList) la flota de vehículos
 * que una agencia de alquiler posee
 * 
 * Los vehículos se modelan como un interface List que se instanciará como una
 * colección concreta ArrayList
 * 
 * @author Sergio Garru�s Aizcorbe
 */
public class AgenciaAlquiler {
	private String nombre; // el nombre de la agencia
	private List<Vehiculo> flota; // la lista de vehículos

	/**
	 * Constructor
	 * 
	 * @param nombre el nombre de la agencia
	 */
	public AgenciaAlquiler(String nombre) {
		this.nombre = nombre;
		this.flota = new ArrayList<>();
	}

	/**
	 * añade un nuevo vehículo solo si no existe
	 * 
	 */
	public void addVehiculo(Vehiculo v) {
		if (!flota.contains(v)) {
			flota.add(v);
		}
	}

	/**
	 * Extrae los datos de una línea, crea y devuelve el vehículo correspondiente
	 * 
	 * Formato de la línea: C,matricula,marca,modelo,precio,plazas para coches
	 * F,matricula,marca,modelo,precio,volumen para furgonetas
	 * 
	 * 
	 * Asumimos todos los datos correctos. Puede haber espacios antes y después de
	 * cada dato
	 */
	private Vehiculo obtenerVehiculo(String str) {
		String[] split = str.split(",");
		if (split[0].trim().equals("C")) {
			Vehiculo vehiculo = new Coche(split[1].trim(), split[2].trim(), split[3].trim(),
					Double.parseDouble(split[4].trim()), Integer.parseInt(split[5].trim()));
			return vehiculo;
		}

		Vehiculo vehiculo = new Furgoneta(split[1].trim(), split[2].trim(), split[3].trim(),
				Double.parseDouble(split[4].trim()), Double.parseDouble(split[5].trim()));
		return vehiculo;
	}

	/**
	 * La clase Utilidades nos devuelve un array con las líneas de datos de la
	 * flota de vehículos
	 */
	public void cargarFlota() {
		String[] datos = Utilidades.obtenerLineasDatos();
		String error = null;
		try {
			for (String linea : datos) {
				error = linea;
				Vehiculo vehiculo = obtenerVehiculo(linea);
				this.addVehiculo(vehiculo);

			}
		} catch (Exception e) {
			System.out.println(error);
		}

	}

	/**
	 * Representación textual de la agencia
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Veh�culos en alquiler de la agencia " + nombre + "\nTotal veh�culos" + flota.size() + "\n");
		for (Vehiculo vehiculo : flota) {
			sb.append("\n" + vehiculo.toString() + "\n\n-----------------------------------------------------");
		}
		return sb.toString();

	}

	/**
	 * Busca todos los coches de la agencia Devuelve un String con esta información
	 * y lo que costaría alquilar cada coche el nº de días indicado *
	 * 
	 */
	public String buscarCoches(int dias) {

		StringBuilder sb = new StringBuilder();
		sb.append("Coches de alquiler de la agencia \n");
		for (Vehiculo vehiculo : flota) {
			if (vehiculo instanceof Coche)
				sb.append("\n" + vehiculo.toString() + "\nCoste alquiler" + dias + "d�as: "
						+ vehiculo.calcularPrecioAlquiler(dias)
						+ "\n-----------------------------------------------------");
		}
		return sb.toString();

	}

	/**
	 * Obtiene y devuelve una lista de coches con más de 4 plazas ordenada por
	 * matrícula - Hay que usar un iterador
	 * 
	 */
	public List<Coche> cochesOrdenadosMatricula() {
		List<Coche> lista = new ArrayList<>();
		Iterator<Vehiculo> it = flota.iterator();
		while (it.hasNext()) {
			Vehiculo coche = it.next();
			if (coche instanceof Coche && ((Coche) coche).getNumPlazas() > 4) {
				lista.add((Coche) coche);
			}
		}
		Collections.sort(lista, new Comparator<Vehiculo>() {
			public int compare(Vehiculo vehiculo1, Vehiculo vehiculo2) {
				if (Integer.parseInt(vehiculo1.getMatricula().substring(0, 4)) > Integer
						.parseInt(vehiculo2.getMatricula().substring(0, 4))) {
					return -1;
				}
				if (Integer.parseInt(vehiculo1.getMatricula().substring(0, 4)) == Integer
						.parseInt(vehiculo2.getMatricula().substring(0, 4))) {
					return 0;
				}
				return 1;

			}
		});
		return lista;
	}

	/**
	 * Devuelve la relación de todas las furgonetas ordenadas de mayor a menor
	 * volumen de carga
	 * 
	 */
	public List<Furgoneta> furgonetasOrdenadasPorVolumen() {

		List<Furgoneta> lista = new ArrayList<>();
		for (Vehiculo furgoneta : flota) {
			if (furgoneta instanceof Furgoneta) {
				lista.add((Furgoneta) furgoneta);
			}
		}
		Collections.sort(lista, (Collections.reverseOrder(new Comparator<Vehiculo>() {
			public int compare(Vehiculo vehiculo1, Vehiculo vehiculo2) {
				if (((Furgoneta) vehiculo1).getVolumen() < ((Furgoneta) vehiculo2).getVolumen()) {
					return -1;
				}
				if (((Furgoneta) vehiculo1).getVolumen() == ((Furgoneta) vehiculo2).getVolumen()) {
					return 0;
				}
				return 1;

			}
		})));
		return lista;

	}

	/**
	 * Genera y devuelve un map con las marcas (importa el orden) de todos los
	 * vehículos que hay en la agencia como claves y un conjunto (importa el orden)
	 * de los modelos en cada marca como valor asociado
	 */
	public Map<String, Set<String>> marcasConModelos() {
		Map<String, Set<String>> map = new TreeMap<>();
		TreeSet<String> claves = new TreeSet<>();
		for(Vehiculo vehiculo : flota)
		{	
			if(!claves.contains(vehiculo.getMarca()))
			{
			claves.add(vehiculo.getMarca());
			}
		}
		for(String marca : claves)
		{
			Set<String> set = new TreeSet<>();
			for(Vehiculo vehiculo : flota)
			{
				
				if(vehiculo.getMarca().equals(marca))
				{
					set.add(vehiculo.getModelo());
				}
			}
			map.put(marca, set);
		}
		return map;
	}

}
