
public class SoundSelect {
	
	/*-------------------------Program-------------------------------*/
	/*---------------------------------------------------------------*/
	/*---------------------------------------------------------------*/
	/*---------------------------------------------------------------*/
	/*---------------------------------------------------------------*/
	
	private byte ProgramSelect = (byte) (0 - 256);

	
	public byte getProgramSelect() {
		return (byte) (ProgramSelect - 256);
	}

	public void setProgramSelect(int programSelect) {
		ProgramSelect = (byte)programSelect;
	}
	
	public byte getStatusProgramSelectLSByte()
	{
		return (byte) (0x00 - 256);
	}
	
	/*------------------------------Bank------------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	
	
	
	


	public byte getStatusBankSelectMSByte() {
		return (byte) (0x00 - 256);
	}
	
	
	public byte getStatusBankSelectLSByte() {
		return (byte) (0x20 - 256);
	}
	
	private byte bankSelectMSByte = (byte) (0x3f - 256);//the default values for Bank A
	private byte bankSelectLSByte = (byte) (0x00 - 256);//the default values for Bank A

	public byte getBankSelectMSByte() {
		return bankSelectMSByte;
	}

	public void setBankSelectMSByte(byte bankSelectMSByte) {
		this.bankSelectMSByte = (byte) (bankSelectMSByte - 256);
	}

	public byte getBankSelectLSByte() {
		return bankSelectLSByte;
	}

	public void setBankSelectLSByte(byte bankSelectLSByte) {
		this.bankSelectLSByte = (byte) (bankSelectLSByte - 256);
	}
	
	/*------------------------------Combi-----------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	
	private byte[] setCombiChangeMessageByte = {(byte) (0xf0 - 256), (byte) (0x42 - 256), (byte) (0x30 - 256), //this message is for selecting the combi button!
												(byte) (0x7a - 256), (byte) (0x4e - 256), (byte) (0x00 - 256), 
												(byte) (0xf7 - 256)};
	
	public byte[] getCombiChangeMessageByte()
	{
		return setCombiChangeMessageByte;
	}
	
	/*------------------------------Prog------------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	
	private byte[] setProgChangeMessageByte = {(byte) (0xf0 - 256), (byte) (0x42 - 256), (byte) (0x30 - 256), //this message is for selecting the prog button!
												(byte) (0x7a - 256), (byte) (0x4e - 256), (byte) (0x02 - 256),
												(byte) (0xf7 - 256)};
	public byte[] getProgChangeMessageByte()
	{
		return setProgChangeMessageByte;
	}
	
	/*------------------------------Global----------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
	/*----------------------------------------------------------------*/
		private byte[] setGlobalChangeMessageByte = {(byte) (0xf0 - 256), (byte) (0x42 - 256), (byte) (0x30 - 256),//this message is for selecting the global button! 
													(byte) (0x7a - 256), (byte) (0x4e - 256), (byte) (0x06 - 256), 
													(byte) (0xf7 - 256)};
		
		public byte[] getGlobalChangeMessageByte()
		{
			return setGlobalChangeMessageByte;
		}
		
		
		private int MemorySoundProg = 0;
		private int MemoryBankProg = 0;
		
		private int MemorySoundCombi = 0;
		private int MemoryBankCombi = 0;


		public int getMemorySoundProg() {
			return MemorySoundProg;
		}

		public void setMemorySoundProg(int memorySoundProg) {
			MemorySoundProg = memorySoundProg;
		}

		public int getMemoryBankProg() {
			return MemoryBankProg;
		}

		public void setMemoryBankProg(int memoryBankProg) {
			MemoryBankProg = memoryBankProg;
		}

		public int getMemorySoundCombi() {
			return MemorySoundCombi;
		}

		public void setMemorySoundCombi(int memorySoundCombi) {
			MemorySoundCombi = memorySoundCombi;
		}

		public int getMemoryBankCombi() {
			return MemoryBankCombi;
		}

		public void setMemoryBankCombi(int memoryBankCombi) {
			MemoryBankCombi = memoryBankCombi;
		}
		
		private boolean FlagEvent = true;


		public boolean isFlagEvent() {
			return FlagEvent;
		}

		public void setFlagEvent(boolean flagEvent) {
			FlagEvent = flagEvent;
		}
		
		private boolean FlagCombiIsSelected = true;
		
		private boolean FlagProgIsSelected = false;


		public boolean isFlagCombiIsSelected() {
			return FlagCombiIsSelected;
		}

		public void setFlagCombiIsSelected(boolean flagCombiIsSelected) {
			FlagCombiIsSelected = flagCombiIsSelected;
		}

		public boolean isFlagProgIsSelected() {
			return FlagProgIsSelected;
		}

		public void setFlagProgIsSelected(boolean flagProgIsSelected) {
			FlagProgIsSelected = flagProgIsSelected;
		}
		
	}

