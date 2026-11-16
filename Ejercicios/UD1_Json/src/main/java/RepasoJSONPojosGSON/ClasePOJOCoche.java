package RepasoJSONPojosGSON;

import java.util.List;

public class ClasePOJOCoche {

	private String Model;
	private Integer Year;
	private Integer id_car;
	private Integer Value;
	private String Matrícula;
	private List<ClasePOJORevision> revisiones;
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public Integer getYear() {
		return Year;
	}
	public void setYear(Integer year) {
		Year = year;
	}
	public Integer getId_car() {
		return id_car;
	}
	public void setId_car(Integer id_car) {
		this.id_car = id_car;
	}
	public Integer getValue() {
		return Value;
	}
	public void setValue(Integer value) {
		Value = value;
	}
	public String getMatrícula() {
		return Matrícula;
	}
	public void setMatrícula(String matrícula) {
		Matrícula = matrícula;
	}
	public List<ClasePOJORevision> getRevisiones() {
		return revisiones;
	}
	public void setRevisiones(List<ClasePOJORevision> revisiones) {
		this.revisiones = revisiones;
	}
	@Override
	public String toString() {
		return "ClasePOJOCoche [Model=" + Model + ", Year=" + Year + ", id_car=" + id_car + ", Value=" + Value
				+ ", Matrícula=" + Matrícula + ", revisiones=" + revisiones + "]";
	}
	public ClasePOJOCoche(String model, Integer year, Integer id_car, Integer value, String matrícula,
			List<ClasePOJORevision> revisiones) {
		super();
		Model = model;
		Year = year;
		this.id_car = id_car;
		Value = value;
		Matrícula = matrícula;
		this.revisiones = revisiones;
	}
	public ClasePOJOCoche() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
