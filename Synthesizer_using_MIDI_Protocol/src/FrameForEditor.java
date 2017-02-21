import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

public class FrameForEditor {
	
	JFrame EditorFrame;
	String textOfEditor;
	
	EditorCompile EditorCompile = new EditorCompile();
	
	
	
	
	public FrameForEditor()
	{
		initializeEditorFrame();
	}
	
	private void initializeEditorFrame()
	{
		//Initialize the EditorFrame
		
		EditorFrame = new JFrame("Editor POWAH!");
		EditorFrame.setBounds(1000, 100, 670, 800);
		EditorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		EditorFrame.getContentPane().setLayout(null);
		
		//Set the Editor Text
		
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 37, 650, 755);
				EditorFrame.getContentPane().add(scrollPane);
				

				final JEditorPane editorPane = new JEditorPane();
				editorPane.setBounds(10, 37, 650, 755);
				scrollPane.setViewportView(editorPane);
				
		
		
		
		//Set the buttons of EditorFrame
		
		
		//OpenButton
		
		JButton OpenButton = new JButton("Open");
		
		//Events of OpenButton
		OpenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Editor POWAH!");
				JFileChooser fc = new JFileChooser();
				int rVal = fc.showOpenDialog(EditorFrame);
				File file;
				//File directory = new File("/home/tito/");
				FileReader FileReader;
				String text = "";
				//BufferedReader BufferedReader;
				if (rVal == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
					//fc.setCurrentDirectory(directory);
					EditorFrame.setTitle(file.getPath());
					
					try
					{
						
					String str;
					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
					
					while((str = br.readLine()) != null)
						text = text + str +"\n";
					
					editorPane.setText(text);
					}
					catch(IOException e)
					{
						System.out.println("Error Opening file");
					}
					}
				
			      if (rVal == JFileChooser.CANCEL_OPTION) {
			    	  file = null;
			      }
					}
				});
		
		OpenButton.setBounds(10, 10, 100, 17);
		OpenButton.setFont(new Font("Dialog", Font.ITALIC, 12));
		EditorFrame.getContentPane().add(OpenButton);
		
		
		//SaveButton
		
		
		JButton SaveButton = new JButton("Save");
		
		//Events of SaveButton
		SaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Editor POWAH!");
				if(EditorFrame.getTitle().compareTo("Editor POWAH!") != 0)
				{
					try
					{
						File file = new File(EditorFrame.getTitle());
						FileWriter fw = new FileWriter(file);
						PrintWriter pw = new PrintWriter(fw);
						pw.print(editorPane.getText());
						pw.close();
					}
					catch(IOException e)
					{
						System.out.println("Error saving file");
					}
				}
					}
				});
		
		SaveButton.setBounds(120, 10, 100, 17);
		SaveButton.setFont(new Font("Dialog", Font.ITALIC, 12));
		EditorFrame.getContentPane().add(SaveButton);
		
		
		//SaveAsButton
		
		
		JButton SaveAsButton = new JButton("Save As");
		
		//Events of SaveAsButton
		SaveAsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Editor POWAH!");
				JFileChooser fc = new JFileChooser();
				int rVal = fc.showSaveDialog(EditorFrame);
				File file;
				//File directory = new File("/home/tito/");
				
				
				if (rVal == JFileChooser.APPROVE_OPTION) {
					try
					{
						
						file = fc.getSelectedFile();
						FileWriter fw = new FileWriter(file);
						PrintWriter pw = new PrintWriter(fw);
						pw.print(editorPane.getText());
						pw.close();
					}
					catch(IOException e)
					{
						System.out.println("Error saving file");
					}
				}
				if (rVal == JFileChooser.CANCEL_OPTION) {
			    	  file = null;
				}
			}
			});
		
		SaveAsButton.setBounds(230, 10, 100, 17);
		SaveAsButton.setFont(new Font("Dialog", Font.ITALIC, 12));
		EditorFrame.getContentPane().add(SaveAsButton);
		
		
		//PlayButton
		
		
		JButton PlayButton = new JButton("Play");
		
		//Events of PlayButton
		PlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				EditorCompile.setTextOfMidiEditor(editorPane.getText());
				EditorCompile.GenerateMidiCommandsPattern();
				
					}
				});
		
		PlayButton.setBounds(340, 10, 100, 17);
		PlayButton.setFont(new Font("Dialog", Font.ITALIC, 12));
		EditorFrame.getContentPane().add(PlayButton);
		
		JButton StopButton = new JButton("Stop");
		StopButton.setBounds(450, 10, 100, 17);
		
		//Events of PlayButton
				StopButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
							EditorCompile.DestroyMidiCommands();
							
							}
						});
		
		StopButton.setFont(new Font("Dialog", Font.ITALIC, 12));
		EditorFrame.getContentPane().add(StopButton);
		
		//Set the Label so we get the name of Midi File we opened
		
		JLabel lblMidiFile = new JLabel("Editor");
		lblMidiFile.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblMidiFile.setBounds(560, 10, 100, 17);
		EditorFrame.getContentPane().add(lblMidiFile);
		
		
		
	}
}
