
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EditorCompile extends StructuralController{
	
	private int SquareBracketsCount = 0;
	private String TextOfMidiEditor;

	MidiCommunication MidiCommunication = new MidiCommunication();
	
	SoundEffects SoundEffects = new SoundEffects();
	StatusByte StatusByte = new StatusByte();
	SoundSelect SoundSelect = new SoundSelect();
	UtilityNotes UtilityNotes = new UtilityNotes();
	
	Timer timer = new Timer();
	//booleans for checking what information we have
	
	//thread for running the generator()
	
	//StructuralController StructuralController = new StructuralController();
	
	ThreadsClass ThreadsClass = new ThreadsClass();
	ThreadsClass.MakeThreadForEditor MakeThreadForEditor = ThreadsClass.getMakeThreadForEditor();
	
	//MakeThreadForEditor = ThreadsClass.getMakeThreadForEditor();
	
	
	//String for getting the number
	String Number = "";
	
	private byte Velocity = 100;
	private byte NotePosition;
	private byte NoteOn = (byte)0x90;
	private byte NoteOff = (byte)0x80;
	
	private int tempo = 80;//120 bpm // 2bps //Ts = 0 Te = 1 needs quarter second 500 msec 
	
	private int time = (int)(1000/(tempo/60)); //Mazi Me Saki mesw tempo pws vriskoume ton xrono gia ena tetarto??
	
	private boolean Success = true;

	float Ts;//Time Start
	float Te;//Time End
	
	
	List<ByteArrayAndTimeStruct> MidiCommands = new ArrayList<ByteArrayAndTimeStruct>();
	
	
	
	public List<ByteArrayAndTimeStruct> getMidiCommands() {
		return MidiCommands;
	}



	public void setMidiCommands(List<ByteArrayAndTimeStruct> midiCommands) {
		MidiCommands = midiCommands;
	}

	//with patterns
	public void GenerateMidiCommandsPattern()
	{
		String getCommand = "";
		System.out.println("Starting debugging: ");
		int i = 0;// /**/
		while(i < TextOfMidiEditor.length())
		{
				while(TextOfMidiEditor.charAt(i) != ';')// || TextOfMidiEditor.charAt(i) != '/')
				{
					getCommand = getCommand+TextOfMidiEditor.charAt(i);
					if(i + 1 == TextOfMidiEditor.length())
						break;
					i++;
				}
				getCommand = getCommand + TextOfMidiEditor.charAt(i);

				i++;
				if(GetStructuralController(getCommand) == "COMMENTS")
				{
					System.out.println("Found Comment");
				}
				else if(GetStructuralController(getCommand) == "NOTE")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStructNoteOn = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStructNoteOn.setTstartOrEnd(GetNumber(getCommand, 1));
					byte[] byteArrayNoteOn = { NoteOn, GetNotePosition(getCommand), (byte)GetNumber(getCommand, 3)};
					ByteArrayAndTimeStructNoteOn.setMidiCommandsOneLine(byteArrayNoteOn);
					ByteArrayAndTimeStructNoteOn.setStatusOfEditor(0);
					MidiCommands.add(ByteArrayAndTimeStructNoteOn);
					
					ByteArrayAndTimeStruct ByteArrayAndTimeStructNoteOff = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStructNoteOff.setTstartOrEnd(GetNumber(getCommand, 2));
					byte[] byteArrayNoteOff = { NoteOff, GetNotePosition(getCommand), (byte)GetNumber(getCommand, 3)};
					ByteArrayAndTimeStructNoteOff.setMidiCommandsOneLine(byteArrayNoteOff);
					ByteArrayAndTimeStructNoteOff.setStatusOfEditor(0);
					MidiCommands.add(ByteArrayAndTimeStructNoteOff);
					
					System.out.println("Found Note");
				}
				else if(GetStructuralController(getCommand) == "TEMPO")
				{
					//System.out.println("Ts is : " + GetNumber(getCommand, 0));
					//System.out.println("Tempo is : " + GetNumber(getCommand, 1));
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					ByteArrayAndTimeStruct.setStatusOfEditor((int)GetNumber(getCommand, 1));
					MidiCommands.add(ByteArrayAndTimeStruct);
					//for (ByteArrayAndTimeStruct k: MidiCommands)
						//{System.out.println(k.getStatusOfEditor());System.out.println(k.getTstartOrEnd());}
					System.out.println("Found Tempo");
				}
				else if(GetStructuralController(getCommand) == "ARPEGIATOR")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					System.out.println("Ts is : "+GetNumber(getCommand, 0));
					System.out.println("0 or 1 is : "+GetNumber(getCommand, 1));
					
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					if((int)GetNumber(getCommand, 1) == 0)
					{
						SoundEffects.setArpegiatorModeOn_Off((byte)0x00);
					}
					else
					{
						SoundEffects.setArpegiatorModeOn_Off((byte)0x7f);
					}
					
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getStatusArpegiator1stMsgMS(), SoundEffects.getStatusArpegiator1stMsgLS(), 
							StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getStatusArpegiator2ndMsgMS(), SoundEffects.getStatusArpegiatorOn_Off2ndMsgLS(), 
							StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getStatusArpegiator3rdMsgMS(), SoundEffects.getArpegiatorModeOn_Off()};
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					ByteArrayAndTimeStruct.setStatusOfEditor(3);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found Arpegiator");
				}
				else if(GetStructuralController(getCommand) == "LPFCUTTOFF")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(4);
					SoundEffects.setLPFCutOffSelect((int)GetNumber(getCommand, 1));
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getStatusLPFCutOffLSByte(), SoundEffects.getLPFCutOffSelect()};
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found LPF Cuttoff");
				}
				else if(GetStructuralController(getCommand) == "RESONANCEHPF")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(5);
					SoundEffects.setResonance_HPFSelect((int)GetNumber(getCommand, 1));
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusResonance_HPFSelectLSByte(), (byte)SoundEffects.getResonance_HPFSelect()};
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found Resonance HPF");
				}
				else if(GetStructuralController(getCommand) == "EGINTENSITY")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(6);
					SoundEffects.setEg_IntensitySelect((int)GetNumber(getCommand, 1));
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusEg_IntensityLSByte(), (byte)SoundEffects.getEg_IntensitySelect()};
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found Eg Intensity");
				}
				else if(GetStructuralController(getCommand) == "FARELEASE")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(7);
					SoundEffects.setFa_ReleaseSelect((int)GetNumber(getCommand, 1));
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusFa_ReleaseLSByte(), (byte)SoundEffects.getFa_ReleaseSelect()};
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found Fa Release");
				}
				else if(GetStructuralController(getCommand) == "ASSIGNABLE1")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(8);
					SoundEffects.setAssignableKnob00Select((int)GetNumber(getCommand, 2));
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getAssignableKnob00StatusByte(), (byte) SoundEffects.getAssignableKnob00Select()};
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 1));
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found ASSIGNABLE1");
				}
				else if(GetStructuralController(getCommand) == "ASSIGNABLE2")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(9);
					SoundEffects.setAssignableKnob01Select((int)GetNumber(getCommand, 2));
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getAssignableKnob01StatusByte(), (byte) SoundEffects.getAssignableKnob01Select()};
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 1));
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found ASSIGNABLE2");
				}
				else if(GetStructuralController(getCommand) == "ASSIGNABLE3")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(10);
					SoundEffects.setAssignableKnob02Select((int)GetNumber(getCommand, 2));
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getAssignableKnob02StatusByte(), (byte) SoundEffects.getAssignableKnob02Select()};
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 1));
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found ASSIGNABLE3");
				}
				else if(GetStructuralController(getCommand) == "ASSIGNABLE4")//????
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(11);
					SoundEffects.setAssignableKnob03Select((int)GetNumber(getCommand, 2));
					System.out.println("getAssignableKnob03Select is : "+SoundEffects.getAssignableKnob03Select());
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte)0), SoundEffects.getAssignableKnob03StatusByte(), (byte) SoundEffects.getAssignableKnob03Select()};
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 1));
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found ASSIGNABLE4");
				}
				else if(GetStructuralController(getCommand) == "ARPGATE")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(12);
					SoundEffects.setArp_gateSelect((int)GetNumber(getCommand, 1));
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator1stMsgMS(), SoundEffects.getStatusArpegiator1stMsgLS(), 
							StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator2ndMsgMS(), SoundEffects.getStatusArp_Gate2ndMsgLS(), 
							StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator3rdMsgMS(), (byte)SoundEffects.getArp_gateSelect()};
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found Arp Gate");
				}
				else if(GetStructuralController(getCommand) == "ARPVELOCITY")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(13);
					SoundEffects.setArp_VelocitySelect((int)GetNumber(getCommand, 1));
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator1stMsgMS(), SoundEffects.getStatusArpegiator1stMsgLS(),
							StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator2ndMsgMS(), SoundEffects.getStatusArp_Velocity2ndMsgLS(), 
							StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator3rdMsgMS(), (byte)SoundEffects.getArp_VelocitySelect()};
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found Arp Velocity");
				}
				else if(GetStructuralController(getCommand) == "ARPLENGTH")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(14);
					SoundEffects.setArp_LengthSelect((int)GetNumber(getCommand, 1));
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator1stMsgMS(), SoundEffects.getStatusArpegiator1stMsgLS(), 
							StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator2ndMsgMS(), SoundEffects.getStatusArp_Length2ndMsgLS(), 
							StatusByte.getStatusControl1stByte((byte) 0), SoundEffects.getStatusArpegiator3rdMsgMS(), (byte)SoundEffects.getArp_LengthSelect()};
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found Arp Length");
				}
				else if(GetStructuralController(getCommand) == "CLOCK")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor((int)GetNumber(getCommand, 1)+1000);
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found Clock");
				}
				else if(GetStructuralController(getCommand) == "VOLUME")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(15);
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					UtilityNotes.setMainVolume((byte) GetNumber(getCommand, 1));
					System.out.println("Ts is : "+GetNumber(getCommand, 0));
					System.out.println("S is : "+(byte)GetNumber(getCommand, 1));
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte)0), UtilityNotes.getStatusMainVolumeLSByte(), UtilityNotes.getMainVolume()};
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found Volume");
				}
				else if(GetStructuralController(getCommand) == "PITCH")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(16);
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					
					//checking the S if its between 0 and 16383
					if((int)GetNumber(getCommand, 1) >= 0 && (int)GetNumber(getCommand, 1) <= 16383)
					{
						UtilityNotes.setPitch((int)GetNumber(getCommand, 1));
						byte[] MidiMessage = {UtilityNotes.getStatusPitchWheel1stByte(), UtilityNotes.getPitchMS(), UtilityNotes.getPitchLS()};
						ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					}
					else
					{
						Success = false;
						System.out.println("Found error");
						break;
					}
					
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found Pitch");
				}
				else if(GetStructuralController(getCommand) == "MODULATION")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(17);
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					UtilityNotes.setModulation((byte) GetNumber(getCommand, 1));
					byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), UtilityNotes.getStatusModulationLS(), UtilityNotes.getModulation()};
					ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
					MidiCommands.add(ByteArrayAndTimeStruct);
					System.out.println("Found Modulation");
				}
				else if(GetStructuralController(getCommand) == "SOUNDSELECT")
				{
					ByteArrayAndTimeStruct ByteArrayAndTimeStruct = new ByteArrayAndTimeStruct();
					ByteArrayAndTimeStruct.setStatusOfEditor(2);
					ByteArrayAndTimeStruct.setTstartOrEnd(GetNumber(getCommand, 0));
					System.out.println("Compare to Combi is : "+GetString(getCommand, 3).compareTo("Combi"));
					System.out.println("Compare to A is : "+GetString(getCommand, 5).compareTo("A"));
					//syntax the message
					if(GetString(getCommand, 3).compareTo("Combi") == 0)
					{
						System.out.println("Found Combi");
						
						if(GetString(getCommand, 5).compareTo("A") == 0)
						{
							System.out.println("Found Combi A");
							SoundSelect.setProgramSelect((int)GetNumber(getCommand, 1));
							SoundSelect.setBankSelectMSByte((byte) 0x3f);
							SoundSelect.setBankSelectLSByte((byte) 0x00);
							
							byte[] MidiMessage = {(byte) (0xf0 - 256), (byte) (0x42 - 256), (byte) (0x30 - 256), //this message is for selecting the combi button!
									(byte) (0x7a - 256), (byte) (0x4e - 256), (byte) (0x00 - 256), 
									(byte) (0xf7 - 256), StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
									StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
									StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
							
							ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
							MidiCommands.add(ByteArrayAndTimeStruct);
						}
						else if(GetString(getCommand, 5).compareTo("B") == 0)
						{
							System.out.println("Found Combi B");
							SoundSelect.setProgramSelect((int)GetNumber(getCommand, 1));
							SoundSelect.setBankSelectMSByte((byte) 0x3f);
							SoundSelect.setBankSelectLSByte((byte) 0x01);
							
							byte[] MidiMessage = {(byte) (0xf0 - 256), (byte) (0x42 - 256), (byte) (0x30 - 256), //this message is for selecting the combi button!
									(byte) (0x7a - 256), (byte) (0x4e - 256), (byte) (0x00 - 256), 
									(byte) (0xf7 - 256), StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
									StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
									StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
							
							ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
							MidiCommands.add(ByteArrayAndTimeStruct);
						}
						else if(GetString(getCommand, 5).compareTo("C") == 0)
						{
							System.out.println("Found Combi C");
							SoundSelect.setProgramSelect((int)GetNumber(getCommand, 1));
							SoundSelect.setBankSelectMSByte((byte) 0x3f);
							SoundSelect.setBankSelectLSByte((byte) 0x02);
							
							byte[] MidiMessage = {(byte) (0xf0 - 256), (byte) (0x42 - 256), (byte) (0x30 - 256), //this message is for selecting the combi button!
									(byte) (0x7a - 256), (byte) (0x4e - 256), (byte) (0x00 - 256), 
									(byte) (0xf7 - 256), StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
									StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
									StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
							
							ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
							MidiCommands.add(ByteArrayAndTimeStruct);
						}
					}
					else if(GetString(getCommand, 3).compareTo("Prog") == 0)
					{
						System.out.println("Found Prog");
						
						if(GetString(getCommand, 5).compareTo("A") == 0)
						{
							System.out.println("Found Prog A");
							SoundSelect.setProgramSelect((int)GetNumber(getCommand, 1));
							SoundSelect.setBankSelectMSByte((byte) 0x3f);
							SoundSelect.setBankSelectLSByte((byte) 0x00);
							
							byte[] MidiMessage = {(byte) (0xf0 - 256), (byte) (0x42 - 256), (byte) (0x30 - 256), //this message is for selecting the combi button!
									(byte) (0x7a - 256), (byte) (0x4e - 256), (byte) (0x02 - 256), 
									(byte) (0xf7 - 256), StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
									StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
									StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
							
							ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
							MidiCommands.add(ByteArrayAndTimeStruct);
							
						}
						else if(GetString(getCommand, 5).compareTo("B") == 0)
						{
							System.out.println("Found Prog B");
							SoundSelect.setProgramSelect((int)GetNumber(getCommand, 1));
							SoundSelect.setBankSelectMSByte((byte) 0x3f);
							SoundSelect.setBankSelectLSByte((byte) 0x01);
							
							byte[] MidiMessage = {(byte) (0xf0 - 256), (byte) (0x42 - 256), (byte) (0x30 - 256), //this message is for selecting the combi button!
									(byte) (0x7a - 256), (byte) (0x4e - 256), (byte) (0x02 - 256), 
									(byte) (0xf7 - 256), 
									StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
									StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
									StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
							
							ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
							MidiCommands.add(ByteArrayAndTimeStruct);
						}
						else if(GetString(getCommand, 5).compareTo("C") == 0)
						{
							System.out.println("Found Prog C");
							SoundSelect.setProgramSelect((int)GetNumber(getCommand, 1));
							SoundSelect.setBankSelectMSByte((byte) 0x3f);
							SoundSelect.setBankSelectLSByte((byte) 0x02);
							
							byte[] MidiMessage = {(byte) (0xf0 - 256), (byte) (0x42 - 256), (byte) (0x30 - 256), //this message is for selecting the combi button!
									(byte) (0x7a - 256), (byte) (0x4e - 256), (byte) (0x02 - 256), 
									(byte) (0xf7 - 256), StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
									StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
									StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
							
							ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
							MidiCommands.add(ByteArrayAndTimeStruct);
						}
						else if(GetString(getCommand, 5).compareTo("D") == 0)
						{
							System.out.println("Found Prog D");
							SoundSelect.setProgramSelect((int)GetNumber(getCommand, 1));
							SoundSelect.setBankSelectMSByte((byte) 0x3f);
							SoundSelect.setBankSelectLSByte((byte) 0x03);
							
							byte[] MidiMessage = {(byte) (0xf0 - 256), (byte) (0x42 - 256), (byte) (0x30 - 256), //this message is for selecting the combi button!
									(byte) (0x7a - 256), (byte) (0x4e - 256), (byte) (0x02 - 256), 
									(byte) (0xf7 - 256), StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
									StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
									StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
							
							ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
							MidiCommands.add(ByteArrayAndTimeStruct);
						}
						else if(GetString(getCommand, 5).compareTo("G") == 0)
						{
							System.out.println("Found Prog G");
							SoundSelect.setProgramSelect((int)GetNumber(getCommand, 1));
							SoundSelect.setBankSelectMSByte((byte) 0x79);
							SoundSelect.setBankSelectLSByte((byte) 0x00);
							
							byte[] MidiMessage = {(byte) (0xf0 - 256), (byte) (0x42 - 256), (byte) (0x30 - 256), //this message is for selecting the combi button!
									(byte) (0x7a - 256), (byte) (0x4e - 256), (byte) (0x02 - 256), 
									(byte) (0xf7 - 256), StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
									StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
									StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
							
							ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
						}
						else if(GetString(getCommand, 5).compareTo("Gd") == 0)
						{
							System.out.println("Found Prog Gd");
							SoundSelect.setProgramSelect((int)GetNumber(getCommand, 1));
							SoundSelect.setBankSelectMSByte((byte) 0x78);
							SoundSelect.setBankSelectLSByte((byte) 0x00);
							
							byte[] MidiMessage = {(byte) (0xf0 - 256), (byte) (0x42 - 256), (byte) (0x30 - 256), //this message is for selecting the combi button!
									(byte) (0x7a - 256), (byte) (0x4e - 256), (byte) (0x02 - 256), 
									(byte) (0xf7 - 256), StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
									StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
									StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
							
							ByteArrayAndTimeStruct.setMidiCommandsOneLine(MidiMessage);
							MidiCommands.add(ByteArrayAndTimeStruct);
						}
					}
					
					
					System.out.println("Combi Or Prog:  "+GetString(getCommand, 3));
					//System.out.println("SoundSelect "+GetString(getCommand, 4));
					System.out.println("Bank Select"+GetString(getCommand, 5));
					System.out.println("Ts  : "+GetNumber(getCommand, 0));
					System.out.println("Program Select : "+GetNumber(getCommand, 1));
					System.out.println("Found SoundSelect");
				}
				else if(GetStructuralController(getCommand) == "SPACE")
				{
					System.out.println("Found space");
				}
				else if(GetStructuralController(getCommand) == "ERROR")
				{
					Success = false;
					System.out.println("Found error");
					break;
				}
				else
				{
					Success = false;
					System.out.println("FATAL ERROR");
					break;
				}
				if(i + 1== TextOfMidiEditor.length())
					break;
				
				System.out.println(getCommand);
				getCommand = "";
			
			
		}
		
		if(Success == true)
		{
			
			Collections.sort(MidiCommands, new Comparator<ByteArrayAndTimeStruct>() {//Sort the list ascending
		        public int compare(ByteArrayAndTimeStruct p1, ByteArrayAndTimeStruct p2) {
		        	 return p1.getTstartOrEnd() - p2.getTstartOrEnd(); // Ascending
		        }
	
		    });
			
			ThreadsClass.SetMidiCommands(MidiCommands);
			ThreadsClass.setRunning(true);
			ThreadsClass.setTempo(tempo);
			ThreadsClass.setTime(time);
			
			MakeThreadForEditor = ThreadsClass.getMakeThreadForEditor();
			
		//	System.out.println("Size of Midi Commands outside thread : " + MidiCommands.size());
	
			MakeThreadForEditor.start();
			
		//	MidiCommands.clear();
		}
		else
		{
			System.out.println("Terminating MIDI Editor Compile");
			MidiCommands.clear();
		}
	}
	
	public void DestroyMidiCommands()
	{
		ThreadsClass.setRunning(false);
		//MidiCommands.clear();
		int i = 0;
		byte[] ByteArray = {NoteOff, (byte)i, 100};
		for(int j = 0; j < 20; j++)
		{
			for(i = 0; i < 128; i++)
			{
				ByteArray[1] = (byte)i;
				MidiCommunication.SendMidiMessage(ByteArray);
			}
		}
		MakeThreadForEditor.setThreadGenerator(null);
	}
	
	public String getTextOfMidiEditor() {
		return TextOfMidiEditor;
	}

	public void setTextOfMidiEditor(String textOfMidiEditor) {
		TextOfMidiEditor = textOfMidiEditor;
	}
	

	
}