package fp.tipos;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import fp.utiles.Checkers;

public class Examen implements Comparable<Examen>{
	private String asignatura;
	private Integer curso;
	private LocalDateTime fechaHora;
	private Duration duracion;
	private TipoExamen tipo;
	private Integer asistentes;
	private Boolean inscripcion;
	private List<Aula> aulas;
	public Examen(String asignatura, Integer curso, LocalDateTime fechaHora, Duration duracion, TipoExamen tipo,
			Integer asistentes, Boolean inscripcion, List<Aula> aulas) {
		super();
		this.asignatura = asignatura;
		this.curso = curso;
		this.fechaHora = fechaHora;
		this.duracion = duracion;
		this.tipo = tipo;
		this.asistentes = asistentes;
		this.inscripcion = inscripcion;
		this.aulas = aulas;
		Checkers.check("El número de asistentes debe ser mayor que 0", asistentes > 0);
		Checkers.check("La duración debe ser como mínimo de una hora", duracion.toHours() >= 1);
	}
	
	
	public Duration getDuracion() {
		return duracion;
	}



	public void setDuracion(Duration duracion) {
		this.duracion = duracion;
	}



	public String getAsignatura() {
		return asignatura;
	}



	public Integer getCurso() {
		return curso;
	}



	public LocalDateTime getFechaHora() {
		return fechaHora;
	}



	public TipoExamen getTipo() {
		return tipo;
	}



	public Integer getAsistentes() {
		return asistentes;
	}



	public Boolean getInscripcion() {
		return inscripcion;
	}



	public List<Aula> getAulas() {
		return aulas;
	}

	public List<Integer> getPuestos(){
		return this.getAulas().stream()
				.map(x-> x.capacidad())
				.collect(Collectors.toList());
	}
	
	public Integer getCapacidadMaxima() {
		return this.getAulas().stream()
				.mapToInt(x-> x.capacidad())
				.sum();
	}
	
	public Double getPorcentajeAsistentes() {
		return Double.valueOf(this.getAsistentes())/Double.valueOf(this.getCapacidadMaxima());
	}
	
	public Boolean usaAula(String nombreAula) {
		return this.getAulas().stream()
				.anyMatch(x-> x.nombre().equals(nombreAula));
	}
	
	@Override
	public String toString() {
		return "Examen [asignatura=" + asignatura + ", curso=" + curso + ", fechaHora=" + fechaHora + ", duracion="
				+ duracion + ", tipo=" + tipo + ", asistentes=" + asistentes + ", inscripcion=" + inscripcion
				+ ", aulas=" + aulas + "]";
	}
	
@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(this == obj)
			return true;
		if(obj.getClass() != this.getClass())
			return false;
		Examen other = (Examen) obj;
		if(this.getFechaHora().equals(other.getFechaHora()) && this.getCurso() == other.getCurso() && this.getAsignatura().equals(other.getAsignatura()))
			return true;
		else
			return false;
	}


@Override
public int compareTo(Examen o) {
	int r = 0;
	if(!(this.getFechaHora().equals(o.getFechaHora())))
		r = this.getFechaHora().compareTo(o.getFechaHora());
	else if(!(this.getCurso() == (o.getCurso())))
		r = this.getCurso().compareTo(o.getCurso());
	else
		r = this.getAsignatura().compareTo(o.getAsignatura());
	return r;
}
	
	
	
}
