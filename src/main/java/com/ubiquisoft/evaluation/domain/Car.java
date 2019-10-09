package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private List<Part> parts;

	/**
	 * Returns a list of Missing PartType(s) and quantity of missing part.
	 * @return
	 */
	public Map<PartType, Integer> getMissingPartsMap() {

		Map<PartType, Integer> missingParts = createMissingPartsMap();

		for (Part part : parts) {
			switch (part.getType()) {
				case ENGINE:
					missingParts.remove(PartType.ENGINE);
					break;
				case ELECTRICAL:
					missingParts.remove(PartType.ELECTRICAL);
					break;
				case TIRE:
					if (missingParts.get(PartType.TIRE) == 1) missingParts.remove(PartType.TIRE);
					else missingParts.replace(PartType.TIRE, missingParts.get(PartType.TIRE) - 1);
					break;
				case OIL_FILTER:
					missingParts.remove(PartType.OIL_FILTER);
					break;
				case FUEL_FILTER:
					missingParts.remove(PartType.FUEL_FILTER);
					break;
				default:
					/**Throw an error if type is not valid**/
					throw new IllegalArgumentException("The Enum Type was Invalid. Can only be\nENGINE,ELECTRICAL,TIRE,OIL_FILTER,FUEL_FILTER");
			}
		}
		return missingParts;
		/*
		 * Return map of the part types missing.
		 *
		 * Each car requires one of each of the following types:
		 *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER
		 * and four of the type: TIRE
		 *
		 * Example: a car only missing three of the four tires should return a map like this:
		 *
		 *      {
		 *          "TIRE": 3
		 *      }
		 */
	}

	/**
	 * Returns a Map of Damaged PartType(s) and the condition of the part(s)
	 * @return
	 */

	public Map<PartType, ConditionType> getDamagedPartsMap() {
		Map<PartType, ConditionType> damagedParts = new HashMap<PartType, ConditionType>();
		for (Part part : parts) {
			switch(part.getCondition()) {
				case NEW:
				case GOOD:
				case WORN:
					/** The Part is good Nothing to do Here**/
					break;
				case NO_POWER:
				case USED:
				case FLAT:
				case SIEZED:
				case CLOGGED:
				case DAMAGED:
					/** Part is damaged must be replaced**/
					damagedParts.put(part.getType(), part.getCondition());
					break;
				default:
					/** Throw an error if type is invalid.**/
					throw new IllegalArgumentException("The Enum Type was Invalid. Can only be:\nNEW,GOOD,WORN,NO_POWER,USED,FLAT,SIEZED,CLOGGED,DAMAGED");
			}
		}
		return damagedParts;

		/*
		 * Return map of the part types damaged.
		 *
		 * Car's damaged types are
		 *      NO_POWER, USED, FLAT, SEIZED, CLOGGED, DAMAGED
		 * Example: a damaged Part would be
		 *
		 *      {
		 *          "TIRE": "FLAT",
		 * 			"ELECTRICAL: "NO_POWER"
		 *      }
		 */
	}

	/**
	 * Returns a list of missingDataFields if the Data Fields equal null or empty String.
	 * @return
	 */
	public List<String> getMissingDataFields() {
		List<String> missingDataFields = new ArrayList<String>();
		if (getYear().equals(null) || getYear().equals("")) missingDataFields.add("Year");
		if (getMake().equals(null) || getMake().equals("")) missingDataFields.add("Make");
		if (getModel().equals(null) || getModel().equals("")) missingDataFields.add("Model");
		return missingDataFields;
	}

	/**
	 * For future releases I was thinking this would be great for a Schema per each type of vehicle. Such as
	 * {
	 *     Type: Semi-Truck (MiniVan, HatchBack, Sedan....)
	 *     Wheels: 18,
	 *     ENGINE: 1,
	 *     OIL_FILTER: 1,
	 *     FUEL_FILTER: 1,
	 * }
	 * {
	 *     Type: Sedan (MiniVan, HatchBack, Sedan....)
	 *     Wheels: 4,
	 * 	   ENGINE: 1,
	 * 	   OIL_FILTER: 1,
	 * 	   FUEL_FILTER: 1,
	 * }
	 *
	 * I also just thought of what if you get the type of car, year.... and then use that information to ping a Car Dealers API to figure out all of the parts the car has.
	 * @return
	 */
	public Map<PartType, Integer> createMissingPartsMap() {
		Map<PartType, Integer> missingParts = new HashMap<PartType, Integer>();
		missingParts.put(PartType.ENGINE, 1);
		missingParts.put(PartType.ELECTRICAL, 1);
		missingParts.put(PartType.FUEL_FILTER, 1);
		missingParts.put(PartType.OIL_FILTER, 1);
		missingParts.put(PartType.TIRE, 4);
		return missingParts;
	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
