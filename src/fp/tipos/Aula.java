package fp.tipos;

import fp.utiles.Checkers;

public record Aula(String nombre, Integer capacidad) {
	public Aula{
		Checkers.check("El nombre debe comenzar por una letra", Character.isLetter(nombre.charAt(0)));
		Checkers.check("La capacidad debe ser mayor que cero", capacidad > 0);
	}	
@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(this == obj)
			return true;
		if(obj.getClass() != this.getClass())
			return false;
		Aula other = (Aula) obj;
		if(this.nombre.equals(other.nombre) && this.capacidad == other.capacidad)
			return true;
		else
			return false;
	}
	
}
