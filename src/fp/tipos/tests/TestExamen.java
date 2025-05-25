package fp.tipos.tests;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fp.tipos.CalendarioExamenes;
import fp.tipos.Examen;
import fp.tipos.FactoriaExamen;
import fp.tipos.TipoExamen;

public class TestExamen {
	
	private static CalendarioExamenes TestLeeCSV() {
		Stream<Examen> res = FactoriaExamen.leeCSV("./data/examenes.csv");
		CalendarioExamenes cal = new CalendarioExamenes(res);
		System.out.println("Examenes leidos: " + cal.getNumeroExamenes());
		//System.out.println("Lectura calendario: " + cal.toString());
		return cal;
	}
	private static void TestgetExamenesPorAula(CalendarioExamenes calendario) {
		System.out.println("Exámenes por aula (por motivos de espacio, solo se muestran las asignaturas): ");
		Map<String, Set<Examen>> res = calendario.getExamenesPorAula();
		for(String aula:res.keySet()) {
			List<String> lista = res.get(aula).stream().map(x-> x.getAsignatura()).collect(Collectors.toList());
			System.out.println("Aula " + aula + lista);
		}
	}
	private static void TestgetExamenMayorPorcentajeAsistentes(CalendarioExamenes calendario,LocalTime t, String nombreAula) {
		System.out.println("Examen con mayor porcentaje de asistentes realizado en el aula " + nombreAula +  "y con hora de comienzo posterior a las " + t + ":");
		Examen res = calendario.getExamenMayorPorcentajeAsistentes(t, nombreAula);
		System.out.println(res + "\n");
	}
	
	private static void TestgetAulasExamenesTipo(CalendarioExamenes calendario,TipoExamen tipo) {
		SortedSet<String> res = calendario.getAulasExamenesTipo(tipo);
		System.out.println("ulas utilizadas en exámenes de tipo " + tipo + ": " + res + "\n");
	}
	private static void TestgetAulaMasOcupada(CalendarioExamenes calendario,LocalDate fecha) {
		String res = calendario.getAulaMasOcupada(fecha);
		System.out.println("Aula con mayor ocupación en la fecha " +fecha + ":" + res + "\n" );
	}
	
	private static void TestgetFechasConMasAulasDe(CalendarioExamenes calendario,Integer umbral) {
		List<LocalDate> res = calendario.getFechasConMasAulasDe(umbral);
		System.out.println("Fechas con mas de " + umbral + " aulas: " + res);
	}
	
	public static void main(String args[]) {
		System.out.println("TestLeeCSV===============");
		CalendarioExamenes calendario = TestLeeCSV();
		System.out.println("EJERCICIO 4.1===================================================================");
		TestgetExamenesPorAula(calendario);
		System.out.println("EJERCICIO 4.2===================================================================");
		TestgetExamenMayorPorcentajeAsistentes(calendario,  LocalTime.of(8, 30), "F1.30");
		TestgetExamenMayorPorcentajeAsistentes(calendario,  LocalTime.of(15, 30), "I2.31");
		System.out.println("EJERCICIO 4.3===================================================================");
		TestgetAulasExamenesTipo(calendario, TipoExamen.PRACTICO);
		TestgetAulasExamenesTipo(calendario, TipoExamen.TEORICO);
		System.out.println("EJERCICIO 4.4===================================================================");
		TestgetAulaMasOcupada(calendario, LocalDate.of(2024, 05, 25));
		TestgetAulaMasOcupada(calendario, LocalDate.of(2024, 06, 07));
		System.out.println("EJERCICIO 4.5===================================================================");
		TestgetFechasConMasAulasDe(calendario, 5);
		TestgetFechasConMasAulasDe(calendario, 8);
	}
}
