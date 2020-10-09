package mundo;

public class Cube {
	
	//Atributos
	private char frontal, izquierdo, derecha, superior, inferior, posterior;
	
	public Cube() {
		frontal = 'Z';
		izquierdo = 'R';
		derecha = 'N';
		superior = 'B';
		posterior = 'V';
		inferior = 'A';
	}
//---------------------------------------Rotacion Cubos-------------------------------------------
	
	public void hor() {
		char aux = this.posterior;
		this.posterior = this.derecha;
		this.derecha = this.frontal;
		this.frontal = this.izquierdo;
		this.izquierdo = aux;
	}
	
	public void ver() {
		char aux = this.frontal;
		this.frontal = this.superior;
		this.superior = this.posterior;
		this.posterior = this.inferior;
		this.inferior = aux;
		
	}
	
	public void tra() {
		char aux = this.superior;
		this.superior = this.izquierdo;
		this.izquierdo = this.inferior;
		this.inferior = this.derecha;
		this.derecha = aux;
	}
//----------------------------------------Getter y Setters------------------------------------------------	
	
	public char getFrontal() {
		return frontal;
	}

	public char getIzquierdo() {
		return izquierdo;
	}

	public char getDerecha() {
		return derecha;
	}

	public char getSuperior() {
		return superior;
	}

	public char getInferior() {
		return inferior;
	}

	public char getPosterior() {
		return posterior;
	}

	public void setFrontal(char frontal) {
		this.frontal = frontal;
	}

	public void setIzquierdo(char izquierdo) {
		this.izquierdo = izquierdo;
	}

	public void setDerecha(char derecha) {
		this.derecha = derecha;
	}

	public void setSuperior(char superior) {
		this.superior = superior;
	}

	public void setInferior(char inferior) {
		this.inferior = inferior;
	}

	public void setPosterior(char posterior) {
		this.posterior = posterior;
	}

}
