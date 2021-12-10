package com.example.backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

@RestController
@SpringBootApplication
public class BackendApplication extends JFrame {


	//@GetMapping("/save")
	public static void save() {

		JFrame parentFrame = new JFrame();

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");

		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"xml files (*.xml)", "xml");
		fileChooser.setFileFilter(filter);
		filter = new FileNameExtensionFilter(
				"json files (*.json)", "json");
		fileChooser.setFileFilter(filter);

		int userSelection = fileChooser.showSaveDialog(parentFrame);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			String file = ((File) fileToSave).getAbsolutePath();
			if (fileChooser.getFileFilter().equals(filter)){
				if (!file.endsWith(".json"))
					file = new String(file + ".json");
			}
			else {
				if (!file.endsWith(".xml"))
					file = new String(file + ".xml");
			}
			try {
				File myObj = new File(file);
				if (myObj.createNewFile()) {
					System.out.println("File created: " + myObj.getName());
				} else {
					System.out.println("File already exists.");
				}
			}catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
	}
	//@GetMapping("/load")
	public static void load(){
		JFrame parentFrame = new JFrame();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		fileChooser.setDialogTitle("Select xml file or Json file");
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML and JSON files", "xml", "json");
		fileChooser.addChoosableFileFilter(filter);
		int result = fileChooser.showOpenDialog(parentFrame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		}
	}

	public static void main(String[] args) {

		var ctx = new SpringApplicationBuilder(BackendApplication.class)
				.headless(false).run(args);


	}
}