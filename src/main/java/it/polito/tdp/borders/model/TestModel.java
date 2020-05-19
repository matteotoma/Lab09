package it.polito.tdp.borders.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
		System.out.println("Creo il grafo relativo al 2000\n");
		model.creaGrafo(2000);
		System.out.println(model.nArchi());
		
	}

}
