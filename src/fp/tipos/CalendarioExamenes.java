package fp.tipos;

import java.util.List;
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
}
