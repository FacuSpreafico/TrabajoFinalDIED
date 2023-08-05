package entidades;

public class Stock {
	
	private Sucursal sucu;
	private Producto prod;
	private Integer stock;
	
	
	public Sucursal getSucu() {
		return sucu; 
	}
	public void setSucu(Sucursal sucu) {
		this.sucu = sucu;
	}
	public Producto getProd() {
		return prod;
	}
	public void setProd(Producto prod) {
		this.prod = prod;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Stock(Sucursal sucu, Producto prod, Integer stock) {
		super();
		this.sucu = sucu;
		this.prod = prod;
		this.stock = stock;
	}
	public Stock() {
		super();
	}
	


}
