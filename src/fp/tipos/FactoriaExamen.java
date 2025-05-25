package fp.tipos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FactoriaExamen {
	private static Examen parsearExamen(String lineaCSV) {
		//asignatura0,curso1,fecha2,hora3,duracion4,tipo5,asistentes6,inscripcion7,aulas8
		String[] res = lineaCSV.split(",");
		if(res.length != 9)
			throw new IllegalArgumentException("Formato no valido");
		String asignatura = res[0].trim();
		Integer curso = Integer.valueOf(res[1].trim());
		LocalDate fecha = LocalDate.parse(res[2].trim(), DateTimeFormatter.ofPattern("d/M/y"));
		LocalTime hora = LocalTime.parse(res[3].trim(), DateTimeFormatter.ofPattern("H:m"));
		LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
		Duration duracion = Duration.ofMinutes(Long.valueOf(res[4].trim()));
		TipoExamen tipo = TipoExamen.valueOf(res[5].trim().toUpperCase());
		Integer asistentes = Integer.valueOf(res[6].trim());
		Boolean inscripcion;
		//SI, SE PUEDE HACER POR IF, ME QUERIA COMPLICAR LA VIDA
		switch(res[7].trim()) {
		case "Si":
			 inscripcion = true;
		break;
		case "No":
			inscripcion = false;
		break;
		default:
			throw new IllegalArgumentException("Formato no valido");
		}
		List<Aula> aulas = parseaAula(res[8].trim());
		Examen exa = new Examen(asignatura, curso, fechaHora, duracion, tipo, asistentes, inscripcion, aulas);
		return exa;
	}

	private static List<Aula> parseaAula(String s) {
		List<Aula> lista = new ArrayList<>();
		String[] res = s.split(";");
		for(String aulaS: res) {
			String[] aulaDatos = aulaS.split("-");
			Aula aula = new Aula(aulaDatos[0].trim(), Integer.valueOf(aulaDatos[1].trim()));
			lista.add(aula);
		}
		return lista;
	}
	public static Stream<Examen> leeCSV(String rutaFichero){
		try {
			return Files.lines(Paths.get(rutaFichero))
					.skip(1)
					.map(x-> parsearExamen(x));
		}catch(IOException e) {
			System.out.println("e");
			return null;
		}
	}
}
