package com.org.dispenser;

import java.util.Scanner;
import java.util.InputMismatchException;

public class DispenserImpl {

	static Scanner sn = new Scanner(System.in);

	public static void main(String[] args) {

		// Call to menu method
		menu();

	}

	/**
	 * Show the menu, request the option and executes them
	 */
	public static void menu() {

		// Coins
		double coins[] = { 0.5, 0.10, 0.20, 0.50, 1, 2 };

		// Drinks names
		String[][] drinkName = { { "Coca", "Redbull", "Water", "Orange juice" } };

		// Drinks price
		double[][] price = { { 1.0, 1.25, 0.5, 1.95 } };

		int amount[][] = new int[4][4];

		// Refill the matrix with 5
		fillMatrix(amount, 5);

		// Indicates if we go out or not
		boolean exit = false;
		int option, row, column, newAmount;
		String pos, password;
		double totalSales = 0;

		// Loop to request the options until we choose exit
		while (!exit) {

			// options
			System.out.println("1. Order drink");
			System.out.println("2. Show drinks");
			System.out.println("3. Refill drinks");
			System.out.println("4. Turn off machine");
			System.out.println("5. Return money");

			try {

				// Ask for an option

				System.out.println("Enter a number: ");
				option = sn.nextInt();

				// Do one of the options

				switch (option) {
				case 1:

					// Insert coins
					System.out.println("Insert coins: ");

					double insertedCoins = sn.nextInt();

					// Ask for the position

					pos = askString("Enter the drink code");

					// Validate the position

					if (validatePos(drinkName, pos)) {

						// Extract row and column

						row = extractNumber(pos, 0);
						column = extractNumber(pos, 1);

						if (insertedCoins >= price[row][column]) {

							// Indicate if there are values ​​in the matrix
							if (thereisValuePosition(amount, row, column)) {
								// Show the drink
								System.out.println("Here's you have your drink: " + drinkName[row][column]
										+ ". Thanks for your purchase");

								// Reduce the amount in 1
								reducePosition(amount, row, column, 1);

								if (insertedCoins > 0) {
									double returnedMoney = insertedCoins - price[row][column];

									System.out.println("Your change:" + returnedMoney);

									insertedCoins = 0;
								}

								// increase the amount
								totalSales += price[row][column];

							} else {
								System.out.println(
										"There are no more drinks of this type, wait for the technician to refill it");
							}
						} else {
							System.out.println("You have not entered enough money");
						}

					} else {
						System.out.println("The position is invalid");
					}

					break;
				case 2:

					// Show the drinks
					showDrinks(drinkName, price, amount);

					break;
				case 3:

					// Ask the password
					password = askString("Introduce the password");

					// Check if the password is correct
					if (equalStrings(password, "Dispenser2022")) {

						// ask for position

						pos = askString("Enter the drink code");

						// Extract the numbers
						row = extractNumber(pos, 0);
						column = extractNumber(pos, 1);

						// Validate the given position
						if (validatePos(drinkName, pos)) {
							// Introduce amount
							newAmount = askInteger("Insert the amount");

							// Increase the amount by the given value
							incrementPosition(amount, row, column, newAmount);

							System.out.println("The quantity has increased");

						} else {
							System.out.println("The entered position is incorrect");
						}

					} else {
						System.out.println("Wrong password");
					}

					break;
				case 4:

					// Show the saless
					System.out.println("The sales are of: " + totalSales);

					exit = true;
					break;
				case 5:
					insertedCoins = 0;
					System.out.println("Money returned");

				default:
					System.out.println("The options are between 1 and 7");
				}

				// Handles the exception in case an incorrect value is entered
			} catch (InputMismatchException e) {
				System.out.println("Must print a number");
				sn.next();
			}

		}

		System.out.println("End of the menu");

	}

	/**
	 * Fill the matrix with a number
	 * 
	 * @param matrix
	 * @param num
	 */
	public static void fillMatrix(int[][] matrix, int num) {

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = num;
			}
		}

	}

	/**
	 * Ask for a string
	 * 
	 * @param message
	 * @return
	 */
	public static String askString(String message) {

		System.out.println(message);
		String string = sn.next();

		return string;

	}

	/**
	 * Ask for a integer
	 * 
	 * @param message
	 * @return
	 */
	public static int askInteger(String message) {

		System.out.println(message);
		int number = sn.nextInt();

		return number;

	}

	/**
	 * Check if the position is correct
	 * 
	 * @param matrix
	 * @param pos
	 * @return
	 */
	public static boolean validatePos(String[][] matrix, String pos) {

		if (pos.length() != 2) {
			return false;
		}

		if (!(isNumber(pos.substring(0, 1)) && isNumber(pos.substring(1, 2)))) {
			return false;
		}

		int row = extractNumber(pos, 0);
		int column = extractNumber(pos, 1);

		if (!((row >= 0 && row < matrix.length) && (column >= 0 && column < matrix[0].length))) {
			return false;
		}

		return true;

	}

	/**
	 * Tell if a string is a number
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNumber(String num) {

		try {
			Integer.parseInt(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	/**
	 * Extract the number, -1 if there was an error
	 * 
	 * @param number
	 * @param pos
	 * @return
	 */
	public static int extractNumber(String number, int pos) {

		int num = -1;
		if (isNumber(number)) {
			num = Integer.parseInt(number.substring(pos, pos + 1));
		}

		return num;

	}

	/**
	 * Reduce the amount of a specific position
	 * 
	 * @param matrix
	 * @param row
	 * @param column
	 * @param amount
	 */
	public static void reducePosition(int[][] matrix, int row, int column, int amount) {
		matrix[row][column] -= amount;
	}

	/**
	 * Indicate if in a position there is a value greater than 0
	 * 
	 * @param matrix
	 * @param row
	 * @param column
	 * @return
	 */
	public static boolean thereisValuePosition(int[][] matrix, int row, int column) {
		if (matrix[row][column] > 0) {
			return true;
		}

		return false;
	}

	/**
	 * Show the drinks
	 * 
	 * @param names
	 * @param prices
	 */
	public static void showDrinks(String[][] names, double[][] prices, int [][] amount) {

		for (int i = 0; i < names.length; i++) {
			for (int j = 0; j < names[i].length; j++) {

				System.out.println(i + "" + j + " " + names[i][j] + " " + prices[i][j] + " Stock: " + amount[i][j]);

			}
		}

	}

	/**
	 * Indicate if two strings are equal
	 * 
	 * @param string1
	 * @param string2
	 * @return
	 */
	public static boolean equalStrings(String string1, String string2) {

		if (string1.equals(string2)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Increase the amount of a position
	 * 
	 * @param matrix
	 * @param row
	 * @param column
	 * @param amount
	 */
	public static void incrementPosition(int[][] matrix, int row, int column, int amount) {
		matrix[row][column] += amount;
	}

}
