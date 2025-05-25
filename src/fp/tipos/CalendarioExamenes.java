package fp.tipos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalendarioExamenes {
	private List<Examen> examenes;
	
	public CalendarioExamenes(Stream<Examen> stream) {
		List<Examen> examenes = stream.collect(Collectors.toList());
		this.examenes = examenes;
	}
	
	public List<Examen> getExamenes(){
		return examenes;
	}
	
	public Integer getNumeroExamenes() {
		return examenes.size();
	}
	
@Override
	public String toString() {
		String res = "";
		for(int i = 0; i< examenes.size(); i++) {
			res += examenes.get(i);
			if(i != examenes.size() - 1)
				res += "\n";
		}
		return res;
	}

	public Map<String, Set<Examen>> getExamenesPorAula(){
		Map<String, Set<Examen>> res = new HashMap<>();
		for(Examen e: this.getExamenes()) {
			for(Aula a:e.getAulas()) {
				if(res.keySet().contains(a.nombre())) {
					Set<Examen> conj = res.get(a.nombre());
					conj.add(e);
					res.put(a.nombre(), conj);
				}else {
					Set<Examen> conj = new HashSet<>();
					conj.add(e);
					res.put(a.nombre(), conj);
				}
			}
		}
		return res;
	}
	
	public Examen getExamenMayorPorcentajeAsistentes(LocalTime t, String nombreAula) {
		return this.getExamenes().stream()
				.filter(x-> x.getFechaHora().toLocalTime().isAfter(t))
				.filter(x-> this.getExamenesPorAula().get(nombreAula).contains(x))
				.max(Comparator.comparing(Examen::getPorcentajeAsistentes))
				.orElse(null);
	}
	
	public SortedSet<String> getAulasExamenesTipo(TipoExamen tipo){
		return this.getExamenes().stream()
				.filter(x-> x.getTipo().equals(tipo))
				.flatMap(x-> x.getAulas().stream())
				.map(x-> x.nombre())
				.collect(Collectors.toCollection(TreeSet::new));
	}
	public String getAulaMasOcupada(LocalDate fecha) {
		Map<String, Set<Examen>> res =  getExamenesPorAula();
		Function<Entry<String, Set<Examen>>, Long> funcion = x -> x.getValue().stream()
				.filter(l-> l.getFechaHora().toLocalDate().equals(fecha))
				.mapToLong(l-> l.getDuracion().toMinutes())
				.sum();
				
		return res.entrySet().stream()
				.max(Comparator.comparing(funcion))
				.orElseThrow(NoSuchElementException::new)
				.getKey();
	}
	public List<LocalDate> getFechasConMasAulasDe(Integer umbral){
		/*
		 * Creamos un map por fechas de los examenes
		 * Para cada lista de examnes, lo convertimos en el numero de aulas totales
		 */
		Function<List<Examen>, Integer> contarAulas = x -> x.stream()
				.mapToInt(l-> l.getAulas().size())
				.sum();
		return this.getExamenes().stream()
				.collect(Collectors.groupingBy(x-> x.getFechaHora().toLocalDate(),
						HashMap::new, Collectors.collectingAndThen(Collectors.toList(), contarAulas))).entrySet().stream()
				.filter(x-> x.getValue()> umbral)
				.map(x-> x.getKey())
				.collect(Collectors.toList());
	}
}
