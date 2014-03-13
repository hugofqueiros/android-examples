package pt.ipp.isep.cma.databaseproject;

public class Person {

	private int id;
	private String fName;
	private String lName;
	
	public Person() {}
	
	public Person(int id, String fName, String lName) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return fName;
	}

	public void setFirstName(String fName) {
		this.fName = fName;
	}

	public String getLastName() {
		return lName;
	}

	public void setLastName(String lName) {
		this.lName = lName;
	}
	
	// Este método é invocado pelo Adapter da spinner para apresentar os dados desta classe
	// deste modo o que irá ser apresentado é o primeiro nome seguido do último nome.
	@Override
	public String toString() {
		return fName + " " + lName;
	}
}
