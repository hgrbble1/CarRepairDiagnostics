package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarDiagnosticEngine {

	public void executeDiagnostics(Car car) {
		if (car == null) throw new IllegalArgumentException("Car must not be null");

		/**Step 1
		 * Checking For Missing Data Fields
		 */
		checkForMissingFields(car);
		/**Step 2
		 * Checking For Missing Parts
		 */
		checkForMissingParts(car);
		/**Step 3
		 * Checking For Damaged Parts
		 */
		checkForDamagedParts(car);
		/**Step 4
		 * Validation Succeeded Tell the User the Car is ready To go
		 */
		System.out.println("Thank you for using UbiquiSoft Car Diagnostic. \nYour car has successfully went through all of our steps and is cleared for Travel");


		/*
		 * Implement basic diagnostics and print results to console.
		 *
		 * The purpose of this method is to find any problems with a car's data or parts.
		 *
		 * Diagnostic Steps:
		 *      First   - Validate the 3 data fields are present, if one or more are
		 *                then print the missing fields to the console
		 *                in a similar manner to how the provided methods do.
		 *
		 *      Second  - Validate that no parts are missing using the 'getMissingPartsMap' method in the Car class,
		 *                if one or more are then run each missing part and its count through the provided missing part method.
		 *
		 *      Third   - Validate that all parts are in working condition, if any are not
		 *                then run each non-working part through the provided damaged part method.
		 *
		 *      Fourth  - If validation succeeds for the previous steps then print something to the console informing the user as such.
		 * A damaged part is one that has any condition other than NEW, GOOD, or WORN.
		 *
		 * Important:
		 *      If any validation fails, complete whatever step you are actively one and end diagnostics early.
		 *
		 * Treat the console as information being read by a user of this application. Attempts should be made to ensure
		 * console output is as least as informative as the provided methods.
		 */


	}

	/**
	 * Checking For missing data fields
	 * If there are missing data fields then we Print out the missing data fields and exit the program.
	 * @param car
	 */
	private void checkForMissingFields(Car car) {
		List<String> missingFields = car.getMissingDataFields();
		if(missingFields.size() > 0) {
			System.out.println("Your car has failed our Diagnostic Test, because your car is missing field(s). The missing Field(s) are listed below:");
			missingFields.forEach((str) -> printMissingField(str));
			System.exit(0);
		}
	}
	/**
	 * Checking For missing Parts
	 * If there are missing parts then we Print out the missing parts and exit the program.
	 * @param car
	 */
	private void checkForMissingParts(Car car){
		Map<PartType, Integer> missingParts = car.getMissingPartsMap();
		if (missingParts.size() > 0) {
			System.out.println("Your car has failed our Diagnostic Test, because your car is missing part(s). The missing Part(s) are listed below:");
			missingParts.forEach((PartType, Integer) -> printMissingPart(PartType, Integer));
			System.exit(0);
		}
	}
	/**
	 * Checking For damaged Parts
	 * If there are missing parts then we Print out the damaged parts and exit the program.
	 * @param car
	 */
	private void checkForDamagedParts(Car car) {
		Map<PartType, ConditionType> damagedParts = car.getDamagedPartsMap();
		if (damagedParts.size() > 0) {
			System.out.println("Your car has failed our Diagnostic Test, because your car has damaged part(s). The Damaged Part(s) are listed below:");
			damagedParts.forEach((PartType, ConditionType)-> printDamagedPart(PartType, ConditionType));
			System.exit(0);
		}
	}


	private void printMissingField (String field){
		if (field == null) throw new IllegalArgumentException("Field must not be null");
		System.out.println(String.format("Missing field Detected: The Car's - %s - must be filled in order to complete diagnostic check", field));
	}

	private void printMissingPart (PartType partType, Integer count){
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart (PartType partType, ConditionType condition){
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main (String[]args) throws JAXBException {
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar3.xml");

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");

			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);

	}
}
