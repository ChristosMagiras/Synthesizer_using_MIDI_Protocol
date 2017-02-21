
public class UtilityNotes {
	
	/*-------------------------Pitch--------------------------*/
	/*--------------------------------------------------------*/
	/*--------------------------------------------------------*/
	/*--------------------------------------------------------*/
	/*--------------------------------------------------------*/
	
	private int Pitch;	

	public int getPitch() {
		return Pitch;
	}

	public void setPitch(int pitch) {
		Pitch = pitch;
	}
	
	
	private byte PitchMS = (byte)64 - (byte)256;
	
	public byte getPitchMS() {
		return (byte) ((byte) (Pitch % 128) - (byte)256);
	}

	public void setPitchMS(byte PitchMS) {
		this.PitchMS = PitchMS;
	}
	
	private byte PitchLS = (byte)0 - (byte)256;
	
	public byte getPitchLS() {
		return (byte) ((byte)(Pitch / 128) - (byte)256);
	}

	public void setPitchLS(byte PitchLS) {
		this.PitchLS = PitchLS;
	}
	
	public byte getStatusPitchWheel1stByte()
	{
		return (byte)0xe0;
	}
	
	/*-----------------------------Main Volume-------------------------------*/
	/*-----------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------*/
	/*-----------------------------------------------------------------------*/
	
	private byte MainVolume = (byte) (127 - 256);
	
	public byte getMainVolume() {
		return (byte) (MainVolume - 256);
	}

	public void setMainVolume(byte mainVolume) {
		MainVolume = mainVolume;
	}
	
	public byte getStatusMainVolumeLSByte()
	{
		return (byte)0x07 - (byte)256;
	}
	
	
	
	/*-----------------------------Modulation-------------------------------*/
	/*----------------------------------------------------------------------*/
	/*----------------------------------------------------------------------*/
	/*----------------------------------------------------------------------*/
	/*----------------------------------------------------------------------*/
	
	private byte Modulation = (byte) (0 - 256);
	
	public byte getModulation() {
		return Modulation;
	}

	public void setModulation(byte modulationSlider) {
		Modulation = (byte) (modulationSlider - 256);
	}
	
	public byte getStatusModulationLS()
	{
		return (byte) (0x01 - 256);
	}
	/*-------------------------------Constructor-----------------------------------*/

	public UtilityNotes() {
		super();
	}
		
}
