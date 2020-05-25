package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Employee;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Employee> list = new ArrayList<>();

		try {

			System.out.print("Enter the full path: ");
			String path = sc.next();
			System.out.print("Enter salary: ");
			double baseSalary = sc.nextDouble();

			try (BufferedReader bf = new BufferedReader(new FileReader(path))) {

				String line = bf.readLine();

				while (line != null) {
					String[] fields = line.split(",");
					String name = fields[0];
					String email = fields[1];
					double salary = Double.parseDouble(fields[2]);

					list.add(new Employee(name, email, salary));
					line = bf.readLine();
				}

				Comparator<String> comp = (e1, e2) -> e1.toUpperCase().compareTo(e2.toUpperCase());

				List<String> emails = list.stream().filter(x -> x.getSalary() > baseSalary).map(x -> x.getEmail())
						.sorted(comp).collect(Collectors.toList());
				System.out.println(
						"Email of people whose salary is more than " + String.format("%.2f", baseSalary) + ":");
				emails.forEach(System.out::println);

				double sum = list.stream().filter(e -> e.getName().charAt(0) == 'M').map(e -> e.getSalary()).reduce(0.0,
						(x1, x2) -> x1 + x2);
				System.out.print("Sum of salary of people whose name starts with 'M': " + String.format("%.2f", sum));

				// list.forEach(System.out::println);

			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}

		} catch (NoSuchElementException e) {
			System.out.println("Error: " + e.getMessage());
		} 
		sc.close();

	}

}
