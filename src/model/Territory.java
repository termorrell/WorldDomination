package model;

import java.util.LinkedList;

public class Territory {
	int id;
	String name;
	Player owner;
	Continent continent;
	Territory[] neighbours;
	LinkedList<Army> occupyingArmies;	//TODO: would an integer suffice?
	
}
