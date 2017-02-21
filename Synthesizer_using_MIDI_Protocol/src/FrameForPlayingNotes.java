import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.List;

import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import javax.swing.JTree;
import javax.swing.JToggleButton;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JPanel;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;


public class FrameForPlayingNotes {

	private JFrame MainFrame;
	private final ButtonGroup BankGroup = new ButtonGroup();
	private final ButtonGroup SelectSoundEffectsGroup = new ButtonGroup();
	private final ButtonGroup CombiProgGlobalGroup = new ButtonGroup();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					FrameForPlayingNotes window = new FrameForPlayingNotes();//Basic generated java code when you create Swing Apps
					window.MainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrameForPlayingNotes() {
		initializeMainFrame();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeMainFrame() {
		MainFrame = new JFrame("Synthesizer using MIDI Protocol");
		MainFrame.setBounds(100, 100, 900, 450);
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//just java auto generated commands for frame
		MainFrame.getContentPane().setLayout(null);
		
		//
		/*------------------------------Create the objects of the classes-------------------------------*/
		/*----------------------------------------------------------------------------------------------*/
		/*----------------------------------------------------------------------------------------------*/
		/*----------------------------------------------------------------------------------------------*/
		/*----------------------------------------------------------------------------------------------*/
		final StatusByte StatusByte = new StatusByte();
		final UtilityNotes UtilityNotes = new UtilityNotes();
		final SoundSelect SoundSelect = new SoundSelect();
		final SoundEffects SoundEffects = new SoundEffects();
		final PlayNotes PlayNotes = new PlayNotes();
		final MidiCommunication MidiCommunication = new MidiCommunication();
		final SoundName SoundName = new SoundName(MidiCommunication, SoundSelect);
		
		/*----------------------------------------Initialize----------------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		
		
		JOptionPane.showMessageDialog(null, "Welcome to Synthesizer using MIDI Protocol project by Christos Mageiras and Sakis Tzoganakos.\nPress ok to start synchronizing PC with synthesizer to get names of sounds :)\n(Takes 5 minutes)");
		
			MidiCommunication.GetDeviceName();

			MidiCommunication.OpenDeviceForWrite();//Open the device for write
			

			
			//MidiCommunication.CloseDeviceForWrite();
			//get the prog names from synth
			SoundName.InitializeProgNamesGAndGdBank();
			try {
				MidiCommunication.OpenDeviceForRead();
				Thread.sleep(1000);

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}//Open the device for read
			SoundName.setProgNamesTable();
			
			
			MidiCommunication.CloseDeviceForRead();//Close the device for read
			
			//get the combi names from synth
			SoundName.InitializeCombiNames();
			try {
				MidiCommunication.OpenDeviceForRead();
				Thread.sleep(1000);

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			SoundName.setCombiNamesTable();
			
			
			MidiCommunication.CloseDeviceForRead();//Close the device for read
			
			//Set Combi and Prog to Bank Sound 0
			
			
			
			try {
				Thread.sleep(1000);
				byte[] InitializeBankA = {(byte)176, (byte)0, (byte)63, //set Bank A Sound 0
						(byte)176, (byte)32, (byte)0, 
						(byte)192, (byte)0, (byte)0};
				//byte[] InitializeSetSound0 = {(byte)192, (byte)0, (byte)0};
			    MidiCommunication.SendMidiMessage(InitializeBankA);
			    Thread.sleep(1000);
				MidiCommunication.SendMidiMessage(SoundSelect.getProgChangeMessageByte());
				Thread.sleep(1000);
				MidiCommunication.SendMidiMessage(InitializeBankA);
				Thread.sleep(1000);
				MidiCommunication.SendMidiMessage(SoundSelect.getCombiChangeMessageByte());

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		
		
			
			//MidiCommunication.OpenDeviceForRead();
			//String a = MidiCommunication.GetSoundName((byte)19);
			
			
			
			//System.out.println(MidiCommunication.GetSoundName((byte)0x19));;
			//MidiCommunication.CloseDeviceForRead();
			//MidiCommunication.CloseDeviceForRead();
		/*---------------------------------------On screen keyboard---------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		
		
		/*--------------------------------Create the Octave Spinner---------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		
		final JSpinner OctaveSelectSpinner = new JSpinner(new SpinnerNumberModel(4, 0, 9, 1));
		OctaveSelectSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				PlayNotes.setOctave((Integer)OctaveSelectSpinner.getValue());
			}
		});
		OctaveSelectSpinner.setBounds(640, 300, 28, 20);
		MainFrame.getContentPane().add(OctaveSelectSpinner);
		
		/*--------------------------------Create the Notes Button-----------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		/*------------------------------------------------------------------------------------------------*/
		
		
		JButton CNote = new JButton(""); // Play NTO button 
		CNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 0));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), (byte)PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					//System.out.println(MidiCommunication.GetSoundName((byte)0x10));
					MidiCommunication.SendMidiMessage(MidiMessage);

				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 0));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);

				}
				
			}
		});
		CNote.setBackground(Color.WHITE);//just java auto generated code on Buttons graphics
		CNote.setBounds(10, 300, 25, 75);
		MainFrame.getContentPane().add(CNote);
		
		JButton DbNote = new JButton("");//Play Reb button
		
		DbNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 1));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 1));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		DbNote.setBackground(Color.BLACK);
		DbNote.setBounds(35, 260, 20, 83);//just java auto generated code on Buttons graphics
		MainFrame.getContentPane().add(DbNote);
		
		JButton DNote = new JButton(""); // Play RE button
		
		DNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 2));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 2));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		
		
		DNote.setBackground(Color.WHITE);
		DNote.setBounds(55, 300, 25, 75);//just java auto generated code on Buttons graphics
		MainFrame.getContentPane().add(DNote);
		
		JButton EbNote = new JButton("");//Play Mib button
		
		EbNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 3));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 3));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		EbNote.setBackground(Color.BLACK);
		EbNote.setBounds(80, 260, 20, 83);//just java auto generated code on Buttons graphics
		MainFrame.getContentPane().add(EbNote);
		
		JButton ENote = new JButton("");//Play MI button
		
		ENote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 4));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 4));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		
		
		ENote.setBackground(Color.WHITE);
		ENote.setBounds(100, 300, 25, 75);//just java auto generated code on Buttons graphics
		MainFrame.getContentPane().add(ENote);
		
		JButton FNote = new JButton("");//Play FA button
		
		FNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 5));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 5));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		FNote.setBackground(Color.WHITE);
		FNote.setBounds(125, 300, 25, 75);//just java auto generated code on Buttons graphics
		MainFrame.getContentPane().add(FNote);
		
		JButton GbNote = new JButton("");//Play Solb button

		
		GbNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 6));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 6));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		GbNote.setBackground(Color.BLACK);
		GbNote.setBounds(150, 260, 20, 83);//just java auto generated code on Buttons graphics
		MainFrame.getContentPane().add(GbNote);
		
		JButton GNote = new JButton("");//Play SOL button
		
		GNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 7));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 7));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		
		
		
		
		GNote.setBackground(Color.WHITE);
		GNote.setBounds(170, 300, 25, 75);//just java auto generated code on Buttons graphics
		MainFrame.getContentPane().add(GNote);
		
		JButton AbNote = new JButton("");
		
		AbNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 8));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 8));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		AbNote.setBackground(Color.BLACK);
		AbNote.setBounds(195, 260, 20, 83);
		MainFrame.getContentPane().add(AbNote);
		
		JButton ANote = new JButton("");
		
		ANote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 9));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 9));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		ANote.setBackground(Color.WHITE);
		ANote.setBounds(215, 300, 25, 75);
		MainFrame.getContentPane().add(ANote);
		
		JButton BbNote = new JButton("");
		
		BbNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 10));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 10));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		BbNote.setBackground(Color.BLACK);
		BbNote.setBounds(240, 260, 20, 83);
		MainFrame.getContentPane().add(BbNote);
		
		JButton BNote = new JButton("");
		
		BNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 11));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 11));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		BNote.setBackground(Color.WHITE);
		BNote.setBounds(260, 300, 25, 75);
		MainFrame.getContentPane().add(BNote);
		
		JButton C2ndNote = new JButton("");
		
		C2ndNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 12));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
					
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 12));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		C2ndNote.setBackground(Color.WHITE);
		C2ndNote.setBounds(285, 300, 25, 75);
		MainFrame.getContentPane().add(C2ndNote);
		
		JButton Db2ndNote = new JButton("");
		
		Db2ndNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 13));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 13));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		Db2ndNote.setBackground(Color.BLACK);
		Db2ndNote.setBounds(310, 260, 20, 83);
		MainFrame.getContentPane().add(Db2ndNote);
		
		JButton D2ndNote = new JButton("");
		
		D2ndNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 14));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 14));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		D2ndNote.setBackground(Color.WHITE);
		D2ndNote.setBounds(330, 300, 25, 75);
		MainFrame.getContentPane().add(D2ndNote);
		
		JButton Eb2ndNote = new JButton("");
		
		Eb2ndNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 15));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 15));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		Eb2ndNote.setBackground(Color.BLACK);
		Eb2ndNote.setBounds(355, 260, 20, 83);
		MainFrame.getContentPane().add(Eb2ndNote);
		
		JButton E2ndNote = new JButton("");
		
		E2ndNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 16));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 16));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		E2ndNote.setBackground(Color.WHITE);
		E2ndNote.setBounds(375, 300, 25, 75);
		MainFrame.getContentPane().add(E2ndNote);
		
		JButton F2ndNote = new JButton("");
		
		F2ndNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 17));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 17));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		F2ndNote.setBackground(Color.WHITE);
		F2ndNote.setBounds(400, 300, 25, 75);
		MainFrame.getContentPane().add(F2ndNote);
		
		JButton Gb2ndNote = new JButton("");
		
		Gb2ndNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 18));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 18));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		Gb2ndNote.setBackground(Color.BLACK);
		Gb2ndNote.setBounds(425, 260, 20, 83);
		MainFrame.getContentPane().add(Gb2ndNote);
		
		JButton G2ndNote = new JButton("");
		
		G2ndNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 19));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 19));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		G2ndNote.setBackground(Color.WHITE);
		G2ndNote.setBounds(445, 300, 25, 75);
		MainFrame.getContentPane().add(G2ndNote);
		
		JButton Ab2ndNote = new JButton("");
		
		Ab2ndNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 20));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 20));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		Ab2ndNote.setBackground(Color.BLACK);
		Ab2ndNote.setBounds(470, 260, 20, 83);
		MainFrame.getContentPane().add(Ab2ndNote);
		
		JButton A2ndNote = new JButton("");
		
		A2ndNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 21));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 21));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		A2ndNote.setBackground(Color.WHITE);
		A2ndNote.setBounds(490, 300, 25, 75);
		MainFrame.getContentPane().add(A2ndNote);
		
		JButton Bb2ndNote = new JButton("");
		
		Bb2ndNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 22));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 22));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		Bb2ndNote.setBackground(Color.BLACK);
		Bb2ndNote.setBounds(515, 260, 20, 83);
		MainFrame.getContentPane().add(Bb2ndNote);
		
		JButton B2ndNote = new JButton("");
		
		B2ndNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 23));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 23));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		B2ndNote.setBackground(Color.WHITE);
		B2ndNote.setBounds(535, 300, 25, 75);
		MainFrame.getContentPane().add(B2ndNote);
		
		JButton C3rdNote = new JButton("");
		
		C3rdNote.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)//When BUTTON1 is clicked (left click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 24));
					byte[] MidiMessage = {StatusByte.getStatusNoteOn1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				if (e.getButton() == MouseEvent.BUTTON3)//when BUTTON3 is clicked (right click)
				{
					PlayNotes.setNoteSelected((byte)((PlayNotes.getOctave() * 12) + 24));
					byte[] MidiMessage = {StatusByte.getStatusNoteOff1stByte((byte) 0), PlayNotes.getNoteSelected(), PlayNotes.getVelocity()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				
			}
		});
		
		C3rdNote.setBackground(Color.WHITE);
		C3rdNote.setBounds(560, 300, 25, 75);
		MainFrame.getContentPane().add(C3rdNote);
		
		/*---------------------------------------Sliders---------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		
		final JSlider VelocitySlider = new JSlider(0, 127);//Create the Velocity Slider
		VelocitySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {//when the slider changes
				PlayNotes.setVelocity((byte)VelocitySlider.getValue());//set the new value to Velocity of the object
			}
		});
		
		
		VelocitySlider.setOrientation(SwingConstants.VERTICAL);//just java Slider graphics commands
		VelocitySlider.setBounds(872, 222, 16, 200);
		MainFrame.getContentPane().add(VelocitySlider);
		
		final JSlider PitchSlider = new JSlider(0, 16383);//Create the Pitch Slider
		PitchSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {//when the slider changes
				UtilityNotes.setPitch(PitchSlider.getValue());
				byte[] MidiMessage = {UtilityNotes.getStatusPitchWheel1stByte(), UtilityNotes.getPitchMS(), UtilityNotes.getPitchLS()};
				MidiCommunication.SendMidiMessage(MidiMessage);
				
			}
		});
		PitchSlider.setOrientation(SwingConstants.VERTICAL);//just java Slider graphics commands
		PitchSlider.setBounds(10, 0, 16, 200);
		MainFrame.getContentPane().add(PitchSlider);
		
		JButton ResetPitch = new JButton("");//Create the Reset Pitch button (so it can be to the default value (8192))
		ResetPitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//when clicking the button
				UtilityNotes.setPitch(8192);//set the value of pitch to 8192(default)
				PitchSlider.setValue(8192);//set the value of the slider to 8192 (default)
			}
		});
		ResetPitch.setBounds(10, 200, 16, 16);//just java Button graphics commands
		MainFrame.getContentPane().add(ResetPitch);
		
		final JSlider MainVolumeSlider = new JSlider(0, 127);//Create the Main Volume Slider
		MainVolumeSlider.setValue(127);//set the default volume value to 127 (maximum)
		UtilityNotes.setMainVolume((byte) MainVolumeSlider.getValue());
		MainVolumeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				UtilityNotes.setMainVolume((byte) MainVolumeSlider.getValue());
				byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte)0), UtilityNotes.getStatusMainVolumeLSByte(), UtilityNotes.getMainVolume()};
				MidiCommunication.SendMidiMessage(MidiMessage);
			}
		});
		MainVolumeSlider.setOrientation(SwingConstants.VERTICAL);
		MainVolumeSlider.setBounds(169, 0, 16, 200);
		MainFrame.getContentPane().add(MainVolumeSlider);

		final JSlider ModulationSlider = new JSlider(0, 127);//Create the Modulation Slider
		ModulationSlider.setValue(0);//set the default to 0 (minimum)
		UtilityNotes.setModulation((byte) ModulationSlider.getValue());
		ModulationSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				UtilityNotes.setModulation((byte) ModulationSlider.getValue());
				byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), UtilityNotes.getStatusModulationLS(), UtilityNotes.getModulation()};
				MidiCommunication.SendMidiMessage(MidiMessage);
				
			}
		});
		ModulationSlider.setOrientation(SwingConstants.VERTICAL);
		ModulationSlider.setBounds(70, 0, 16, 200);
		MainFrame.getContentPane().add(ModulationSlider);
		
		/*---------------------------------------Bank-Programs----------------------------------------*/
		/*--------------------------------------------------------------------------------------------*/
		/*--------------------------------------------------------------------------------------------*/
		/*--------------------------------------------------------------------------------------------*/
		/*--------------------------------------------------------------------------------------------*/
		
		final JLabel LabelSoundName = new JLabel(SoundName.GetSoundName());
		LabelSoundName.setFont(new Font("Dialog", Font.ITALIC, 12));
		LabelSoundName.setBounds(660, 49, 120, 15);
		MainFrame.getContentPane().add(LabelSoundName);
		
		final JRadioButton BankASelect = new JRadioButton("A Bank");//Create the A Bank radio button
		BankASelect.setSelected(true);
		BankASelect.setFont(new Font("Dialog", Font.ITALIC, 12));
		BankGroup.add(BankASelect);
		BankASelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SoundSelect.setBankSelectMSByte((byte) 0x3f);
				SoundSelect.setBankSelectLSByte((byte) 0x00);
				byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
										StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
										StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
				if(SoundSelect.isFlagEvent() == true)
					MidiCommunication.SendMidiMessage(MidiMessage);
				
				//Set the name of Sound
				SoundName.setBankNumber(0);
				SoundName.setSoundNumber(SoundSelect.getProgramSelect());
				LabelSoundName.setText(SoundName.GetSoundName());
				System.out.println("BankASelect Event!");
			}
		});
		BankASelect.setBounds(500, 25, 87, 12);
		MainFrame.getContentPane().add(BankASelect);
		
		final JRadioButton BankBSelect = new JRadioButton("B Bank");//Create the B Bank radio button
		BankBSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SoundSelect.setBankSelectMSByte((byte) 0x3f);
				SoundSelect.setBankSelectLSByte((byte) 0x01);
				byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
						StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
						StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
				if(SoundSelect.isFlagEvent() == true)
					MidiCommunication.SendMidiMessage(MidiMessage);
				
				//Set the name of Sound
				SoundName.setBankNumber(1);
				SoundName.setSoundNumber(SoundSelect.getProgramSelect());
				LabelSoundName.setText(SoundName.GetSoundName());
				System.out.println("BankBSelect Event!");
			}
		});
		BankBSelect.setFont(new Font("Dialog", Font.ITALIC, 12));
		BankGroup.add(BankBSelect);
		BankBSelect.setBounds(500, 37, 87, 12);
		MainFrame.getContentPane().add(BankBSelect);
		
		final JRadioButton BankCSelect = new JRadioButton("C Bank");//Create the C Bank radio button
		BankCSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundSelect.setBankSelectMSByte((byte) 0x3f);
				SoundSelect.setBankSelectLSByte((byte) 0x02);
				byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
						StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
						StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
				if(SoundSelect.isFlagEvent() == true)
					MidiCommunication.SendMidiMessage(MidiMessage);
				
				//Set the name of Sound
				SoundName.setBankNumber(2);
				SoundName.setSoundNumber(SoundSelect.getProgramSelect());
				LabelSoundName.setText(SoundName.GetSoundName());
				System.out.println("BankCSelect Event!");
			}
		});
		BankCSelect.setFont(new Font("Dialog", Font.ITALIC, 12));
		BankGroup.add(BankCSelect);
		BankCSelect.setBounds(587, 25, 87, 12);
		MainFrame.getContentPane().add(BankCSelect);
		
		final JRadioButton BankDSelect = new JRadioButton("D Bank");//Create the D Bank radio button
		BankDSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundSelect.setBankSelectMSByte((byte) 0x3f);
				SoundSelect.setBankSelectLSByte((byte) 0x03);
				byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
						StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
						StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
				if(SoundSelect.isFlagEvent() == true)
					MidiCommunication.SendMidiMessage(MidiMessage);
				
				//Set the name of Sound
				SoundName.setBankNumber(3);
				SoundName.setSoundNumber(SoundSelect.getProgramSelect());
				LabelSoundName.setText(SoundName.GetSoundName());
				System.out.println("BankDSelect Event!");
			}
		});
		BankDSelect.setFont(new Font("Dialog", Font.ITALIC, 12));
		BankGroup.add(BankDSelect);
		BankDSelect.setBounds(587, 37, 87, 12);
		MainFrame.getContentPane().add(BankDSelect);
		
		final JRadioButton BankGSelect = new JRadioButton("G Bank");//Create the G Bank radio button
		BankGSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundSelect.setBankSelectMSByte((byte) 0x79);
				SoundSelect.setBankSelectLSByte((byte) 0x00);
				byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
						StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
						StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
				if(SoundSelect.isFlagEvent() == true)
					MidiCommunication.SendMidiMessage(MidiMessage);
				
				System.out.println("BankGSelect Event!");
				
				//Set the name of Sound
				SoundName.setBankNumber(4);
				SoundName.setSoundNumber(SoundSelect.getProgramSelect());
				LabelSoundName.setText(SoundName.GetSoundName());
			}
		});
		BankGSelect.setFont(new Font("Dialog", Font.ITALIC, 12));
		BankGroup.add(BankGSelect);
		BankGSelect.setBounds(674, 25, 87, 12);
		MainFrame.getContentPane().add(BankGSelect);
		
		final JRadioButton BankGdSelect = new JRadioButton("G(d) Bank");//Create the G(d) Bank radio button
		BankGdSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundSelect.setBankSelectMSByte((byte) 0x78);
				SoundSelect.setBankSelectLSByte((byte) 0x00);
				byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
						StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
						StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
				if(SoundSelect.isFlagEvent() == true)
					MidiCommunication.SendMidiMessage(MidiMessage);
				
				System.out.println("BankGdSelect Event!");
				
				//Set the name of Sound 
				SoundName.setBankNumber(5);
				SoundName.setSoundNumber(SoundSelect.getProgramSelect());
				LabelSoundName.setText(SoundName.GetSoundName());
			}
		});
		BankGdSelect.setFont(new Font("Dialog", Font.ITALIC, 12));
		BankGroup.add(BankGdSelect);
		BankGdSelect.setBounds(674, 37, 87, 12);
		MainFrame.getContentPane().add(BankGdSelect);
		
		SpinnerNumberModel ProgramSelectValues = new SpinnerNumberModel(0, 0, 127, 1);//Create spinner to select which program (sound of synthesizer) is desirable
		final JSpinner ProgramSelect = new JSpinner(ProgramSelectValues);
		ProgramSelect.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				SoundSelect.setProgramSelect((Integer) ProgramSelect.getValue()); //to get value of Jspinner use (Byte)JSpinner.getValue()
				byte[] MidiMessage = {StatusByte.getStatusProgramChange1stByte((byte)0), SoundSelect.getStatusProgramSelectLSByte(), (Byte)SoundSelect.getProgramSelect()};
				if(SoundSelect.isFlagEvent() == true)
					MidiCommunication.SendMidiMessage(MidiMessage);
				
				//Set the name of Sound
				SoundName.setSoundNumber(SoundSelect.getProgramSelect());
				LabelSoundName.setText(SoundName.GetSoundName());
				System.out.println("ProgramSelect Event!");
				
			}
		});
		
		/*---------------------------------------Sound Effects----------------------------------------*/
		/*--------------------------------------------------------------------------------------------*/
		/*--------------------------------------------------------------------------------------------*/
		/*--------------------------------------------------------------------------------------------*/
		/*--------------------------------------------------------------------------------------------*/
		
		
		/*---------------------------------------Arpegiator----------------------------------------*/
		/*-----------------------------------------------------------------------------------------*/
		/*-----------------------------------------------------------------------------------------*/
		/*-----------------------------------------------------------------------------------------*/
		/*-----------------------------------------------------------------------------------------*/
		
		JToggleButton Arpegiator = new JToggleButton("Arpegiator");//Create toggle button to enable/disable Arpegiator
		Arpegiator.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.SELECTED)
				{
					SoundEffects.setArpegiatorModeOn_Off((byte)0x7f);
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getStatusArpegiator1stMsgMS(), SoundEffects.getStatusArpegiator1stMsgLS(), 
											StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getStatusArpegiator2ndMsgMS(), SoundEffects.getStatusArpegiatorOn_Off2ndMsgLS(), 
											StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getStatusArpegiator3rdMsgMS(), SoundEffects.getArpegiatorModeOn_Off()};
					MidiCommunication.SendMidiMessage(MidiMessage);
					
					
				}
				else if(arg0.getStateChange() == ItemEvent.DESELECTED)
				{	
					SoundEffects.setArpegiatorModeOn_Off((byte)0x00);
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getStatusArpegiator1stMsgMS(), SoundEffects.getStatusArpegiator1stMsgLS(), 
							StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getStatusArpegiator2ndMsgMS(), SoundEffects.getStatusArpegiatorOn_Off2ndMsgLS(), 
							StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getStatusArpegiator3rdMsgMS(), SoundEffects.getArpegiatorModeOn_Off()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
			}
		}); 
		Arpegiator.setFont(new Font("Dialog", Font.ITALIC, 12));
		Arpegiator.setBounds(10, 380, 100, 17);
		MainFrame.getContentPane().add(Arpegiator);
		
		/*---------------------------------------Knob Effects----------------------------------------*/
		/*-------------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------------*/
		//SpinnerKnob03 and SpinnerTempo
		//needs to be here assigned so we can change which one is going to be visible and which one invisible
		final JSpinner SpinnerKnob03 = new JSpinner(new SpinnerNumberModel(1, 1, 127, 1));
		final JSpinner SpinnerTempo = new JSpinner(new SpinnerNumberModel(120, 40, 300, 1));//the Spinner for the tempo
		SpinnerTempo.setVisible(false);														//we can't use the same spinner
																							//it has different values
																							//than SpinnerKnob03
																							//SpinnerKnob03 has minimum 1 and maximum 127 value
																							//SpinnerTempo has minimum 40 and maximum 300 value
		
		
		final JLabel LabelKnob00 = new JLabel("LPF CUTOFF");//Create the Labelknob00 (so we can see what value we change of the SpinnerKnobs)
		LabelKnob00.setFont(new Font("Dialog", Font.ITALIC, 12));
		LabelKnob00.setBounds(500, 80, 101, 15);
		MainFrame.getContentPane().add(LabelKnob00);
		
		final JLabel LabelKnob01 = new JLabel("RESONANCE/HPF");//Create the Labelknob01 (so we can see what value we change of the SpinnerKnobs)
		LabelKnob01.setFont(new Font("Dialog", Font.ITALIC, 12));
		LabelKnob01.setBounds(660, 80, 101, 15);
		MainFrame.getContentPane().add(LabelKnob01);
		
		final JLabel LabelKnob02 = new JLabel("EG-INTENSITY");//Create the Labelknob02 (so we can see what value we change of the SpinnerKnobs)
		LabelKnob02.setFont(new Font("Dialog", Font.ITALIC, 12));
		LabelKnob02.setBounds(500, 100, 101, 15);
		MainFrame.getContentPane().add(LabelKnob02);
		
		final JLabel LabelKnob03 = new JLabel("FA-RELEASE");//Create the Labelknob03 (so we can see what value we change of the SpinnerKnobs)
		LabelKnob03.setFont(new Font("Dialog", Font.ITALIC, 12));
		LabelKnob03.setBounds(660, 100, 101, 15);
		MainFrame.getContentPane().add(LabelKnob03);
		
		final JRadioButton SoundEffectsSelectA = new JRadioButton("Select A");//Create the Radio Button Select A
		SoundEffectsSelectA.setSelected(true);
		SoundEffectsSelectA.addActionListener(new ActionListener() {//when you click the radio button Select A
			public void actionPerformed(ActionEvent arg0) {//set the labelknobs the following strings
				LabelKnob00.setText("LPF CUTOFF");
				LabelKnob01.setText("RESONANCE/HPF");
				LabelKnob02.setText("EG-INTENSITY");
				LabelKnob03.setText("FA-RELEASE");
				SpinnerKnob03.setVisible(true);//set the SpinnerKnob03 visible
				SpinnerTempo.setVisible(false);//set the SpinnerTempo invisible
			}
		});
		SoundEffectsSelectA.setFont(new Font("Dialog", Font.ITALIC, 12));
		SelectSoundEffectsGroup.add(SoundEffectsSelectA);
		SoundEffectsSelectA.setBounds(412, 80, 82, 15);
		MainFrame.getContentPane().add(SoundEffectsSelectA);
		
		final JRadioButton SoundEffectsSelectB = new JRadioButton("Select B");//Create the Radio Button Select B
		SoundEffectsSelectB.addActionListener(new ActionListener() {//when you click the radio button Select B
			public void actionPerformed(ActionEvent e) {//set the labelknobs the following strings
				LabelKnob00.setText("ASSIGNABLE 1");
				LabelKnob01.setText("ASSIGNABLE 2");
				LabelKnob02.setText("ASSIGNABLE 3");
				LabelKnob03.setText("ASSIGNABLE 4");
				SpinnerKnob03.setVisible(true);//set the SpinnerKnob03 visible
				SpinnerTempo.setVisible(false);//set the SpinnerTempo invisible
			}
		});
		SoundEffectsSelectB.setFont(new Font("Dialog", Font.ITALIC, 12));
		SelectSoundEffectsGroup.add(SoundEffectsSelectB);
		SoundEffectsSelectB.setBounds(412, 95, 76, 15);
		MainFrame.getContentPane().add(SoundEffectsSelectB);
		
		final JRadioButton SoundEffectsSelectC = new JRadioButton("Select C");//Create the Radio Button Select C
		SoundEffectsSelectC.addActionListener(new ActionListener() {//when you click the radio button Select C
			public void actionPerformed(ActionEvent e) {//set the labelknobs the following strings
				LabelKnob00.setText("ARP-GATE");
				LabelKnob01.setText("ARP-VELOCITY");
				LabelKnob02.setText("ARP-LENGTH");
				LabelKnob03.setText("TEMPO");//because we have tempo now (we can change the tempo when Radio Button Select C is selected
				SpinnerTempo.setVisible(true);//set the SpinnerTempo to visible
				SpinnerKnob03.setVisible(false);//set the SpinnerKnob03 to invisible
			}
		});
		SoundEffectsSelectC.setFont(new Font("Dialog", Font.ITALIC, 12));
		SelectSoundEffectsGroup.add(SoundEffectsSelectC);
		SoundEffectsSelectC.setBounds(412, 110, 76, 15);
		MainFrame.getContentPane().add(SoundEffectsSelectC);
		
		/*---------------------------------------Combi Prog Global-----------------------------------*/
		/*-------------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------------*/
		

		
		final JRadioButton CombiButton = new JRadioButton("Combi");//Create the Radio button so we can go to the Combi Programs (sounds of synthesizer)
		CombiButton.setSelected(true);
		final JRadioButton ProgButton = new JRadioButton("Prog");//Create the Prog button so we can go to the Prog Programs (sounds of synthesizer)
		
		
		CombiButton.addActionListener(new ActionListener() {//when you click the Combi Radio Button
			public void actionPerformed(ActionEvent e) {
				
				//When changing from Prog to Combi the synth STORES the Sound and Bank value from Prog
				//therefore we need to store that value as well
				
				if(SoundSelect.isFlagCombiIsSelected() == false)
				{
					SoundSelect.setMemorySoundProg(SoundName.getSoundNumber());
					SoundSelect.setMemoryBankProg(SoundName.getBankNumber());
					
					System.out.println("Combi Button Pressed!");
					
					SoundSelect.setFlagEvent(false);
					
					byte[] MidiMessage = SoundSelect.getCombiChangeMessageByte();
					MidiCommunication.SendMidiMessage(MidiMessage);
					
					SoundName.setChooseBetweenCombiOrProg(false);//false for combi true for prog
					
					
					
					SoundName.setSoundNumber(SoundSelect.getMemorySoundCombi());
					SoundName.setBankNumber(SoundSelect.getMemoryBankCombi());
					
					LabelSoundName.setText(SoundName.GetSoundName());
					
					//set invisible the D, G, and G(d) radio buttons (Combi category doesnt have those buttons)
					BankDSelect.setVisible(false);
					BankGSelect.setVisible(false);
					BankGdSelect.setVisible(false);
					
					
					
					switch (SoundName.getBankNumber())
					{
						case 0: 
								ProgramSelect.setValue((Integer)SoundName.getSoundNumber());
								BankASelect.setSelected(true);
								break;
								
						case 1: 
								ProgramSelect.setValue((Integer)SoundName.getSoundNumber());
								BankBSelect.setSelected(true);
								break;
						
						case 2: 
								ProgramSelect.setValue((Integer)SoundName.getSoundNumber());
								BankCSelect.setSelected(true);
								break;
						
					}
					SoundSelect.setFlagEvent(true);
					
					SoundSelect.setFlagCombiIsSelected(true);
					SoundSelect.setFlagProgIsSelected(false);
				}
			}
		});
		CombiProgGlobalGroup.add(CombiButton);
		CombiButton.setFont(new Font("Dialog", Font.ITALIC, 12));
		CombiButton.setBounds(412, 25, 65, 15);
		MainFrame.getContentPane().add(CombiButton);
		
		
		ProgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//When changing from Combi to Prog the synth STORES the Sound and Bank value from Combi
				//therefore we need to store that value as well
				
				if(SoundSelect.isFlagProgIsSelected() == false)
				{
					SoundSelect.setMemorySoundCombi(SoundName.getSoundNumber());
					SoundSelect.setMemoryBankCombi(SoundName.getBankNumber());
					
					System.out.println("Prog Button Pressed!");
					
					byte[] MidiMessage = SoundSelect.getProgChangeMessageByte();
					
					SoundSelect.setFlagEvent(false);
					
					MidiCommunication.SendMidiMessage(MidiMessage);
					SoundName.setChooseBetweenCombiOrProg(true);//false for combi true for prog
					
					
					SoundName.setSoundNumber(SoundSelect.getMemorySoundProg());
					SoundName.setBankNumber(SoundSelect.getMemoryBankProg());
					
					LabelSoundName.setText(SoundName.GetSoundName());
					
					//set visible the D, G, and G(d) radio buttons (Prog category has all the buttons)
					BankDSelect.setVisible(true);
					BankGSelect.setVisible(true);
					BankGdSelect.setVisible(true);
					
					switch (SoundName.getBankNumber())
					{
						case 0: 
								ProgramSelect.setValue((Integer)SoundName.getSoundNumber());
								BankASelect.setSelected(true);
								break;
								
						case 1: 
								ProgramSelect.setValue((Integer)SoundName.getSoundNumber());
								BankBSelect.setSelected(true);
								break;
						
						case 2: 
								ProgramSelect.setValue((Integer)SoundName.getSoundNumber());
								BankCSelect.setSelected(true);
								break;
						
						case 3: 
								ProgramSelect.setValue((Integer)SoundName.getSoundNumber());
								BankDSelect.setSelected(true);
								break;
						
						case 4: 
								ProgramSelect.setValue((Integer)SoundName.getSoundNumber());
								BankGSelect.setSelected(true);
								break;
						
						case 5: 
								ProgramSelect.setValue((Integer)SoundName.getSoundNumber());
								BankGdSelect.setSelected(true);
								break;
					}
					
					
					SoundSelect.setFlagEvent(true);
					SoundSelect.setFlagProgIsSelected(true);
					SoundSelect.setFlagCombiIsSelected(false);
				}
			}
		});
		CombiProgGlobalGroup.add(ProgButton);
		ProgButton.setFont(new Font("Dialog", Font.ITALIC, 12));
		ProgButton.setBounds(412, 40, 65, 15);
		MainFrame.getContentPane().add(ProgButton);
		
		JRadioButton GlobalButton = new JRadioButton("Global");//Create the Global button so we can go to the Global Programs (settings of synthesizer)
		GlobalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] MidiMessage = SoundSelect.getGlobalChangeMessageByte();
				MidiCommunication.SendMidiMessage(MidiMessage);
			}
		});
		CombiProgGlobalGroup.add(GlobalButton);
		GlobalButton.setFont(new Font("Dialog", Font.ITALIC, 12));
		GlobalButton.setBounds(412, 55, 65, 15);
		MainFrame.getContentPane().add(GlobalButton);
		ProgramSelect.setBounds(600, 49, 50, 20);
		MainFrame.getContentPane().add(ProgramSelect);
		

		final JSpinner SpinnerKnob00 = new JSpinner(new SpinnerNumberModel(1, 1, 127, 1));//Create the SpinnerKnob00 spinner
		SpinnerKnob00.addChangeListener(new ChangeListener() {//when you change the value of SpinnerKnob00 spinner
			public void stateChanged(ChangeEvent arg0) {
				if(SoundEffectsSelectA.isSelected())//if Select A radio button is selected
				{
					SoundEffects.setLPFCutOffSelect((Integer)SpinnerKnob00.getValue()); //to get value of Jspinner use (Integer)JSpinner.getValue()
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getStatusLPFCutOffLSByte(), SoundEffects.getLPFCutOffSelect()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				else if(SoundEffectsSelectB.isSelected())//if Select B radio button is selected
				{
					SoundEffects.setAssignableKnob00Select((Integer)SpinnerKnob00.getValue());
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getAssignableKnob00StatusByte(), (byte) SoundEffects.getAssignableKnob00Select()};
					MidiCommunication.SendMidiMessage(MidiMessage);
					
				}
				else if(SoundEffectsSelectC.isSelected())//if Select C radio button is selected
				{
					SoundEffects.setArp_gateSelect((Integer)SpinnerKnob00.getValue()); //to get value of Jspinner use (Integer)JSpinner.getValue()
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator1stMsgMS(), SoundEffects.getStatusArpegiator1stMsgLS(), 
											StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator2ndMsgMS(), SoundEffects.getStatusArp_Gate2ndMsgLS(), 
											StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator3rdMsgMS(), (byte)SoundEffects.getArp_gateSelect()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
			}
		});
		SpinnerKnob00.setBounds(601, 80, 50, 20);
		MainFrame.getContentPane().add(SpinnerKnob00);
		
		final JSpinner SpinnerKnob01 = new JSpinner(new SpinnerNumberModel(1, 1, 127, 1));//Create the SpinnerKnob01 spinner
		SpinnerKnob01.addChangeListener(new ChangeListener() {//when you change the value of SpinnerKnob01 spinner
			public void stateChanged(ChangeEvent e) {
				if(SoundEffectsSelectA.isSelected())//if Select A radio button is selected
				{
					SoundEffects.setResonance_HPFSelect((Integer)SpinnerKnob01.getValue()); //to get value of Jspinner use (Integer)JSpinner.getValue()
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusResonance_HPFSelectLSByte(), (byte)SoundEffects.getResonance_HPFSelect()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				else if(SoundEffectsSelectB.isSelected())//if Select B radio button is selected
				{
					SoundEffects.setAssignableKnob01Select((Integer)SpinnerKnob01.getValue());
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getAssignableKnob01StatusByte(), (byte)SoundEffects.getAssignableKnob01Select()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				else if(SoundEffectsSelectC.isSelected())//if Select C radio button is selected
				{
					SoundEffects.setArp_VelocitySelect((Integer)SpinnerKnob01.getValue()); //to get value of Jspinner use (Integer)JSpinner.getValue()
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator1stMsgMS(), SoundEffects.getStatusArpegiator1stMsgLS(),
											StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator2ndMsgMS(), SoundEffects.getStatusArp_Velocity2ndMsgLS(), 
											StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator3rdMsgMS(), (byte)SoundEffects.getArp_VelocitySelect()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
			}
		});
		SpinnerKnob01.setBounds(761, 80, 50, 20);
		MainFrame.getContentPane().add(SpinnerKnob01);
		
		final JSpinner SpinnerKnob02 = new JSpinner(new SpinnerNumberModel(1, 1, 127, 1));//Create the SpinnerKnob02 spinner
		SpinnerKnob02.addChangeListener(new ChangeListener() {//when you change the value of SpinnerKnob02 spinner
			public void stateChanged(ChangeEvent e) {
				if(SoundEffectsSelectA.isSelected())//if Select A radio button is selected
				{
					SoundEffects.setEg_IntensitySelect((Integer)SpinnerKnob02.getValue()); //to get value of Jspinner use (Integer)JSpinner.getValue()
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusEg_IntensityLSByte(), (byte)SoundEffects.getEg_IntensitySelect()};
					MidiCommunication.SendMidiMessage(MidiMessage);
					
				}
				else if(SoundEffectsSelectB.isSelected())//if Select B radio button is selected
				{
					SoundEffects.setAssignableKnob02Select((Integer)SpinnerKnob02.getValue());
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getAssignableKnob02StatusByte(), (byte)SoundEffects.getAssignableKnob02Select()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				else if(SoundEffectsSelectC.isSelected())//if Select C radio button is selected
				{
					SoundEffects.setArp_LengthSelect((Integer)SpinnerKnob02.getValue()); //to get value of Jspinner use (Integer)JSpinner.getValue()
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator1stMsgMS(), SoundEffects.getStatusArpegiator1stMsgLS(), 
											StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator2ndMsgMS(), SoundEffects.getStatusArp_Length2ndMsgLS(), 
											StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator3rdMsgMS(), (byte)SoundEffects.getArp_LengthSelect()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
			}
		});
		SpinnerKnob02.setBounds(601, 100, 50, 20);
		MainFrame.getContentPane().add(SpinnerKnob02);
		
		//final JSpinner SpinnerKnob03 = new JSpinner(new SpinnerNumberModel(1, 1, 127, 1));	They are up
		//final JSpinner SpinnerKnob04 = new JSpinner(new SpinnerNumberModel(120, 40, 300, 1)); because they need to 
		//																						be visible or invisible
		//																						depending on what SoundEffectSelect
		//																						you have select
		SpinnerKnob03.addChangeListener(new ChangeListener() {//when you change the value of SpinnerKnob03 spinner
			public void stateChanged(ChangeEvent e) {
				if(SoundEffectsSelectA.isSelected())//if Select A radio button is selected
				{
					SoundEffects.setFa_ReleaseSelect((Integer)SpinnerKnob03.getValue()); //to get value of Jspinner use (Integer)JSpinner.getValue()
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusFa_ReleaseLSByte(), (byte)SoundEffects.getFa_ReleaseSelect()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				else if(SoundEffectsSelectB.isSelected())//if Select B radio button is selected
				{
					SoundEffects.setAssignableKnob03Select((Integer)SpinnerKnob03.getValue());
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getAssignableKnob03StatusByte(), (byte) SoundEffects.getAssignableKnob03Select()};
					MidiCommunication.SendMidiMessage(MidiMessage);
				}
				//we don't need to check if Select C radio button is selected because we use the SpinnerTempo and the SpinnerKnob03 is invisible when Select C radio button is selected
			}
		});
		
		SpinnerTempo.addChangeListener(new ChangeListener() {//when you change the value of SpinnerTempo spinner
			public void stateChanged(ChangeEvent e) {
				if(SoundEffectsSelectC.isSelected())//if Select C radio button is selected
				{
					SoundEffects.setKnobTempoSelect((Integer)SpinnerTempo.getValue());
					MidiCommunication.SendTempo(SoundEffects.getKnobTempoSelect());
					
				}
			}
		});
		
		SpinnerKnob03.setBounds(761, 100, 50, 20);
		MainFrame.getContentPane().add(SpinnerKnob03);
		
		SpinnerTempo.setBounds(761, 100, 50, 20);
		MainFrame.getContentPane().add(SpinnerTempo);
		
		/*---------------------------------------Labels----------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		
		
		
		
		JLabel LabelProgramSelect = new JLabel("Program Select");
		LabelProgramSelect.setFont(new Font("Dialog", Font.ITALIC, 12));
		LabelProgramSelect.setBounds(500, 49, 100, 15);
		MainFrame.getContentPane().add(LabelProgramSelect);
		
		JLabel LabelPitchSlider = new JLabel("Pitch");
		LabelPitchSlider.setFont(new Font("Dialog", Font.ITALIC, 12));
		LabelPitchSlider.setBounds(26, 10, 30, 15);
		MainFrame.getContentPane().add(LabelPitchSlider);
		
		JLabel LabelNoteVelocitySlider = new JLabel("NoteVelocity");
		LabelNoteVelocitySlider.setFont(new Font("Dialog", Font.ITALIC, 12));
		LabelNoteVelocitySlider.setBounds(808, 200, 80, 15);
		MainFrame.getContentPane().add(LabelNoteVelocitySlider);
		
		JLabel LabelMainVolumeSlider = new JLabel("Volume");
		LabelMainVolumeSlider.setFont(new Font("Dialog", Font.ITALIC, 12));
		LabelMainVolumeSlider.setBounds(185, 10, 46, 15);
		MainFrame.getContentPane().add(LabelMainVolumeSlider);
		
		JLabel LabelModuleSlider = new JLabel("Modulation");
		LabelModuleSlider.setFont(new Font("Dialog", Font.ITALIC, 12));
		LabelModuleSlider.setBounds(86, 10, 69, 15);
		MainFrame.getContentPane().add(LabelModuleSlider);
		
		JLabel lblOctaveSelect = new JLabel("Octave");
		lblOctaveSelect.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblOctaveSelect.setBounds(590, 300, 50, 15);
		MainFrame.getContentPane().add(lblOctaveSelect);
		
		/*---------------------------------------Editor----------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		/*-------------------------------------------------------------------------------------*/
		
		
		JButton EditorButton = new JButton("Editor");
		EditorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Editor POWAH!");
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
				
				FrameForEditor window = new FrameForEditor();
				window.EditorFrame.setVisible(true);
				//EditorFrame.setVisible(true);
				
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				
			}
		});
		EditorButton.setBounds(258, 50, 100, 17);
		EditorButton.setFont(new Font("Dialog", Font.ITALIC, 12));
		MainFrame.getContentPane().add(EditorButton);
		
		JButton HelpButton = new JButton("Help");
		HelpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Welcome to Synthesizer using MIDI Protocol tutorial.\nTo play notes right click on piano\nto stop notes left click piano.\nThe editor syntax goes like this: \nCommand[Var1 = {Value}, Var2 = {Value}, .... VarN = {Value}];\nCheck the word file for more instructions :)\n");
			}
		});
		HelpButton.setFont(new Font("Dialog", Font.ITALIC, 12));
		HelpButton.setBounds(788, 31, 100, 17);
		MainFrame.getContentPane().add(HelpButton);
		
		
		
		
		

		
	}
}
