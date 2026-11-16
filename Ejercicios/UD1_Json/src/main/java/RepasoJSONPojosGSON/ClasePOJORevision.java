package RepasoJSONPojosGSON;

public class ClasePOJORevision {

	private String fecha;
	private String tipo;
	public ClasePOJORevision(String fecha, String tipo) {
		super();
		this.fecha = fecha;
		this.tipo = tipo;
	}
	public ClasePOJORevision() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "ClasePOJORevision [fecha=" + fecha + ", tipo=" + tipo + "]";
	}
	
	
	
}
