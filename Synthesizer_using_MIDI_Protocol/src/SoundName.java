
public class SoundName {

	private MidiCommunication MidiCommunication;
	private SoundSelect SoundSelect;
	private String ProgNamesTable[][] = new String[128][6];
	private String CombiNamesTable[][] = new String[128][3];
	private byte [] ChangeBank = new byte[9];
	private boolean flag;
	
	private int SoundNumber = 0;
	private int BankNumber = 0;
	private boolean ChooseBetweenCombiOrProg = false; //false is Combi, true is Prog
	
	public void setProgNamesTable()
	{
		
		flag = false;
		
		for(int j = 0; j < 6; j++)
		{
			
			switch (j)
			{
				case 0:
					ChangeBank[0] = (byte)176; ChangeBank[1] = (byte)0; ChangeBank[2] = (byte)63;
					ChangeBank[3] = (byte)176; ChangeBank[4] = (byte)32; ChangeBank[5] = (byte)0;
					ChangeBank[6] = (byte)192; ChangeBank[7] = (byte)0; ChangeBank[8] = (byte)0;
					break;
				case 1:
					ChangeBank[0] = (byte)176; ChangeBank[1] = (byte)0; ChangeBank[2] = (byte)63;
					ChangeBank[3] = (byte)176; ChangeBank[4] = (byte)32; ChangeBank[5] = (byte)1;
					ChangeBank[6] = (byte)192; ChangeBank[7] = (byte)0; ChangeBank[8] = (byte)0;
					break;
				case 2:
					ChangeBank[0] = (byte)176; ChangeBank[1] = (byte)0; ChangeBank[2] = (byte)63;
					ChangeBank[3] = (byte)176; ChangeBank[4] = (byte)32; ChangeBank[5] = (byte)2;
					ChangeBank[6] = (byte)192; ChangeBank[7] = (byte)0; ChangeBank[8] = (byte)0;
					break;
				case 3:
					ChangeBank[0] = (byte)176; ChangeBank[1] = (byte)0; ChangeBank[2] = (byte)63;
					ChangeBank[3] = (byte)176; ChangeBank[4] = (byte)32; ChangeBank[5] = (byte)3;
					ChangeBank[6] = (byte)192; ChangeBank[7] = (byte)0; ChangeBank[8] = (byte)0;
					break;
				case 4:
					ChangeBank[0] = (byte)176; ChangeBank[1] = (byte)0; ChangeBank[2] = (byte)121;
					ChangeBank[3] = (byte)176; ChangeBank[4] = (byte)32; ChangeBank[5] = (byte)0;
					ChangeBank[6] = (byte)192; ChangeBank[7] = (byte)0; ChangeBank[8] = (byte)0;
					break;
				case 5:
					ChangeBank[0] = (byte)176; ChangeBank[1] = (byte)0; ChangeBank[2] = (byte)120;
					ChangeBank[3] = (byte)176; ChangeBank[4] = (byte)32; ChangeBank[5] = (byte)0;
					ChangeBank[6] = (byte)192; ChangeBank[7] = (byte)0; ChangeBank[8] = (byte)0;
					break;
			}
			
			int i = 0;
			MidiCommunication.SendMidiMessage(ChangeBank);
			
			if(flag == false)
			{
				for(i = 120; i < 128; i++)
				{
					
					byte [] ByteArray = {(byte)192, (byte)0 , (byte)i};
					MidiCommunication.SendMidiMessage(ByteArray);
					String dummy3 = MidiCommunication.GetSoundName((byte)0x10);
					ProgNamesTable[i][j] = MidiCommunication.GetSoundName((byte)0x10);
						
				}
				
				flag = true;
				j = -1;
				
			}
			else
			{	
				for(i = 0; i < 128; i++)
				{
					
					byte [] ByteArray = {(byte)192, (byte)0 , (byte)i};
					MidiCommunication.SendMidiMessage(ByteArray);
					String dummy3 = MidiCommunication.GetSoundName((byte)0x10);
					ProgNamesTable[i][j] = MidiCommunication.GetSoundName((byte)0x10);
						
		
				}
			}
		}
		
		
	}
	
	
	
	
	public void setCombiNamesTable()
	{
		flag = false;
		for(int j = 0; j < 3; j++)
		{
			
			
			switch (j)
			{
				case 0:
					ChangeBank[0] = (byte)176; ChangeBank[1] = (byte)0; ChangeBank[2] = (byte)63;
					ChangeBank[3] = (byte)176; ChangeBank[4] = (byte)32; ChangeBank[5] = (byte)0;
					ChangeBank[6] = (byte)192; ChangeBank[7] = (byte)0; ChangeBank[8] = (byte)0;
					break;
				case 1:
					ChangeBank[0] = (byte)176; ChangeBank[1] = (byte)0; ChangeBank[2] = (byte)63;
					ChangeBank[3] = (byte)176; ChangeBank[4] = (byte)32; ChangeBank[5] = (byte)1;
					ChangeBank[6] = (byte)192; ChangeBank[7] = (byte)0; ChangeBank[8] = (byte)0;
					break;
				case 2:
					ChangeBank[0] = (byte)176; ChangeBank[1] = (byte)0; ChangeBank[2] = (byte)63;
					ChangeBank[3] = (byte)176; ChangeBank[4] = (byte)32; ChangeBank[5] = (byte)2;
					ChangeBank[6] = (byte)192; ChangeBank[7] = (byte)0; ChangeBank[8] = (byte)0;
					break;
			}
			
			int i = 0;
			MidiCommunication.SendMidiMessage(ChangeBank);
			if(flag == false)
			{
				for(i = 120; i < 128; i++)
				{
					
					byte [] ByteArray = {(byte)192, (byte)0 , (byte)i};
					MidiCommunication.SendMidiMessage(ByteArray);
					String dummy3 = MidiCommunication.GetSoundName((byte)0x19);
					CombiNamesTable[i][j] = MidiCommunication.GetSoundName((byte)0x19);
						
				}
				
				flag = true;
				j = -1;
				
			}
			else
			{
				for(i = 0; i < 128; i++)
				{
					byte [] ByteArray = {(byte)192, (byte)0 , (byte)i};
					MidiCommunication.SendMidiMessage(ByteArray);
					String dummy3 = MidiCommunication.GetSoundName((byte)0x19);
					CombiNamesTable[i][j] = MidiCommunication.GetSoundName((byte)0x19);
				}
			}
		}
	}

	public String[][] getProgNamesTable() {
		return ProgNamesTable;
	}

	public String[][] getCombiNamesTable() {
		return CombiNamesTable;
	}

	public void InitializeCombiNames()
	{
		MidiCommunication.SendMidiMessage(SoundSelect.getCombiChangeMessageByte());
	}
	
	public void InitializeProgNamesGAndGdBank()
	{
		MidiCommunication.SendMidiMessage(SoundSelect.getProgChangeMessageByte());
	}
	
	
	public void setSoundNumber(int soundNumber) {
		SoundNumber = soundNumber;
	}

	public int getSoundNumber() {
		return SoundNumber;
	}

	public void setBankNumber(int bankNumber) {
		BankNumber = bankNumber;
	}
	
	public int getBankNumber() {
		return BankNumber;
	}
	
	public void setChooseBetweenCombiOrProg(boolean chooseBetweenCombiOrProg) {
		ChooseBetweenCombiOrProg = chooseBetweenCombiOrProg;
	}

	public String GetSoundName()
	{
		
		if(ChooseBetweenCombiOrProg == false)
		{
			return CombiNamesTable[SoundNumber][BankNumber];
		}
		else
		{
			return ProgNamesTable[SoundNumber][BankNumber];
		}
		
	}
	
	public SoundName(MidiCommunication midiCommunication, SoundSelect soundSelect) {
		super();
		MidiCommunication = midiCommunication;
		SoundSelect = soundSelect;
	}
	
	
	
}
