import java.util.ArrayList;
import java.util.List;

public class ThreadsClass {
	
	private MidiCommunication MidiCommunication = new MidiCommunication();
	
	StatusByte StatusByte = new StatusByte();
	SoundSelect SoundSelect= new SoundSelect();
	
	//EditorCompile EditorCompile = new EditorCompile();
	
	private int tempo = 120;//120 bpm // 2bps //Ts = 0 Te = 1 needs quarter second 500 msec 
	
	private int time = (int)(1000/(tempo/60)); //Mazi Me Saki mesw tempo pws vriskoume ton xrono gia ena tetarto??
	
	private boolean Running;
	
	public boolean isRunning() {
		return Running;
	}

	public void setRunning(boolean running) {
		Running = running;
	}

	private List<ByteArrayAndTimeStruct> MidiCommands = new ArrayList<ByteArrayAndTimeStruct>();
	
	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public void SetMidiCommands(List<ByteArrayAndTimeStruct> MidiCommands)
	{
		this.MidiCommands = MidiCommands;
		//Collections.synchronizedList(MidiCommands);
	}
	
	/***************************************************************/	
	/***************************************************************/	
	/***************************************************************/
	/***************************************************************/	
	/***************************************************************/
	
	public class MakeThreadForEditor implements Runnable//create the thread class so we can run the list
	{
		private Thread ThreadGenerator;//
		
		public Thread getThreadGenerator() {
			return ThreadGenerator;
		}
		public void setThreadGenerator(Thread threadGenerator) {
			ThreadGenerator = threadGenerator;
		}
		
		
		public void run()
		{
			ByteArrayAndTimeStruct TimeNow = new ByteArrayAndTimeStruct();
			ByteArrayAndTimeStruct TimeBefore = new ByteArrayAndTimeStruct();
			int j = 1;
			int pause = 0;
			Running = true;
			System.out.println("runnign thread!");
			System.out.println("Size of MidiCommands : "+MidiCommands.size());
			System.out.println("Boolean Running is : "+Running);
			for(ByteArrayAndTimeStruct u : MidiCommands)
			{
				if(Running == true)
				{
					try {
						if(j == 0)
						
						
						{
							if(u.getStatusOfEditor() == 0)
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
								
							}
							else if(u.getStatusOfEditor() >= 40 && u.getStatusOfEditor() <= 300)
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								tempo = u.getStatusOfEditor();
								time = (int)(1000/(tempo/60));
								j++;
							}
							else if(u.getStatusOfEditor() == 2)//SoundSelect
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								SoundSelect.setProgramSelect((byte)u.getMidiCommandsOneLine()[15]);
								SoundSelect.setBankSelectMSByte(u.getMidiCommandsOneLine()[9]);
								SoundSelect.setBankSelectLSByte(u.getMidiCommandsOneLine()[12]);
								byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
										StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
										StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
								Thread.sleep(1000);
								MidiCommunication.SendMidiMessage(MidiMessage);
								j++;
							}
							else if(u.getStatusOfEditor() == 3)//Arpegiator
							{
								
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 4)//LPF Cuttoff
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 5)//Resonance HPF
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 6)//Eg Intensity
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 7)//FA Release
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 8)//Assignable 1
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 9)//Assignable 2
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 10)//Assignable 3
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 11)//Assignable 4
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								for(int i = 0; i<u.getMidiCommandsOneLine().length; i++)
									System.out.println("u.getMidiCommandsOneLine : " + u.getMidiCommandsOneLine()[i]);
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 12)//Arp Gate
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 13)//Arp Velocity
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 14)//Arp Length
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() >= 1040 && u.getStatusOfEditor() <= 1300)//Clock
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendTempo(0);
								MidiCommunication.SendTempo(u.getStatusOfEditor() - 1000);
								j++;
							}
							else if(u.getStatusOfEditor() == 15)//Volume
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 16)//Pitch
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
							else if(u.getStatusOfEditor() == 17)//Modulation
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								j++;
							}
						}
						
						
						
						
						
						
						if(j == MidiCommands.size())
						{
							if(u.getStatusOfEditor() == 0)
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
									
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								
							}
							else if(u.getStatusOfEditor() >= 40 && u.getStatusOfEditor() <= 300)
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								tempo = u.getStatusOfEditor();
								time = (int)(1000/(tempo/60));
								j++;
							}
							else if(u.getStatusOfEditor() == 2)//SoundSelect
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								SoundSelect.setProgramSelect((byte)u.getMidiCommandsOneLine()[15]);
								SoundSelect.setBankSelectMSByte(u.getMidiCommandsOneLine()[9]);
								SoundSelect.setBankSelectLSByte(u.getMidiCommandsOneLine()[12]);
								byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
										StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
										StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
								Thread.sleep(1000);
								MidiCommunication.SendMidiMessage(MidiMessage);
								j++;
							}
							else if(u.getStatusOfEditor() == 3)//Arpegiator
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 4)//LPF Cuttoff
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 5)//Resonance HPF
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 6)//Eg Intensity
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 7)//FA Release
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 8)//Assignable 1
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 9)//Assignable 2
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 10)//Assignable 3
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 11)//Assignable 4
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								for(int i = 0; i<u.getMidiCommandsOneLine().length; i++)
									System.out.println("u.getMidiCommandsOneLine : " + u.getMidiCommandsOneLine()[i]);
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 12)//Arp Gate
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 13)//Arp Velocity
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 14)//Arp Length
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() >= 1040 && u.getStatusOfEditor() <= 1300)//Clock
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendTempo(0);
								MidiCommunication.SendTempo(u.getStatusOfEditor() - 1000);
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 15)//Volume
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 16)//Pitch
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 17)//Modulation
							{
								TimeNow = MidiCommands.get(j - 1);
								TimeBefore = MidiCommands.get(j - 2);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
						}
						
						
						
						
						
						
						else
						{
							
							
							if(u.getStatusOfEditor() == 0)
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
								
							}
							else if(u.getStatusOfEditor() >= 40 && u.getStatusOfEditor() <= 300)
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								System.out.println("");
								tempo = u.getStatusOfEditor();
								time = (int)(1000/(tempo/60));
								j++;
							}
							else if(u.getStatusOfEditor() == 2)//Sound Select
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								SoundSelect.setProgramSelect((byte)u.getMidiCommandsOneLine()[15]);
								SoundSelect.setBankSelectMSByte(u.getMidiCommandsOneLine()[9]);
								SoundSelect.setBankSelectLSByte(u.getMidiCommandsOneLine()[12]);
								byte[] MidiMessage = {StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectMSByte(), SoundSelect.getBankSelectMSByte(), 
										StatusByte.getStatusControl1stByte((byte) 0), SoundSelect.getStatusBankSelectLSByte(), SoundSelect.getBankSelectLSByte(), 
										StatusByte.getStatusProgramChange1stByte((byte) 0), SoundSelect.getStatusProgramSelectLSByte(), SoundSelect.getProgramSelect()};
								Thread.sleep(1000);
								MidiCommunication.SendMidiMessage(MidiMessage);
								j++;
							}
							else if(u.getStatusOfEditor() == 3)//Arpegiator
							{
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 4)//LPF Cuttoff
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 5)//Resonance HPF
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 6)//Eg Intensity
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 7)//FA Release
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 8)//Assignable 1
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 9)//Assignable 2
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 10)//Assignable 3
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 11)//Assignable 4
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								for(int i = 0; i<u.getMidiCommandsOneLine().length; i++)
									System.out.println("u.getMidiCommandsOneLine : " + u.getMidiCommandsOneLine()[i]);
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 12)//Arp Gate
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 13)//Arp Velocity
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 14)//Arp Length
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() >= 1040 && u.getStatusOfEditor() <= 1300)//Arp Length
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendTempo(0);
								MidiCommunication.SendTempo(u.getStatusOfEditor() - 1000);
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 15)//Volume
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 16)//Pitch
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
							else if(u.getStatusOfEditor() == 17)//Modulation
							{
								TimeNow = MidiCommands.get(j);
								TimeBefore = MidiCommands.get(j - 1);
								pause = TimeNow.getTstartOrEnd() - TimeBefore.getTstartOrEnd();
								System.out.println("Status Of Editor :"+u.getStatusOfEditor());
								MidiCommunication.SendMidiMessage(u.getMidiCommandsOneLine());
								Thread.sleep((int)((pause * time)/1000));
								j++;
							}
						}
					}
				 catch(InterruptedException ex) {
					 MidiCommands.clear();
					 ThreadGenerator = null;
				    Thread.currentThread().interrupt();
						}
				}
				else
				{
					Running = true;
					break;
				}
			}
			
			System.out.println("End of list");
			MidiCommands.clear();
			
			ThreadGenerator = null;
				
		}
		public void start()
		{
			if(this.ThreadGenerator == null)
			{
				ThreadGenerator = new Thread(this);
				ThreadGenerator.start();
			}
		}
		
	}
	
	/***************************************************************/	
	/***************************************************************/	
	/***************************************************************/
	/***************************************************************/	
	/***************************************************************/
	
	private MakeThreadForEditor MakeThreadForEditor = new MakeThreadForEditor();

	public MakeThreadForEditor getMakeThreadForEditor() {
		return MakeThreadForEditor;
	}

	public void setMakeThreadForEditor(MakeThreadForEditor makeThreadForEditor) {
		MakeThreadForEditor = makeThreadForEditor;
	}
}
