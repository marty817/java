package poste;

public interface Persona {
	    private String nome;

	    public Persona(String nome) {
	        this.nome = nome;
	    }

	    public String getNome() {
	        return nome;
	    }
	}
}
