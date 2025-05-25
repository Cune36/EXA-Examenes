package fp.tipos.tests;

import java.util.stream.Stream;

import fp.tipos.CalendarioExamenes;
import fp.tipos.Examen;
import fp.tipos.FactoriaExamen;

public class TestExamen {
	
	private static CalendarioExamenes TestLeeCSV() {
		Stream<Examen> res = FactoriaExamen.leeCSV("./data/examenes.csv");
		CalendarioExamenes cal = new CalendarioExamenes(res);
		System.out.println("Examenes leidos: " + cal.getNumeroExamenes());
		System.out.println("Lectura calendario: " + cal.toString());
		return cal;
	}
	
	public static void main(String args[]) {
		System.out.println("TestLeeCSV");
		CalendarioExamenes calendario = TestLeeCSV();
	}
}
