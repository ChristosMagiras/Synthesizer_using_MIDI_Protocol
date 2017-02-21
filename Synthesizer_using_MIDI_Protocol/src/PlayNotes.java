
public class PlayNotes {

	private byte noteOn = (byte)0x90 - (byte)256;// we need to substract 256 so we can convert it to unsigned
	private byte noteOff = (byte)0x80 - (byte)256;
	private byte noteSelected;
	
	private byte Velocity = (byte)63 - (byte)256;
	
	
	private int octave = (byte)4 - (byte)256;
	
	public byte getNoteOn() {
		return noteOn;
	}

	public void setNoteOn(byte noteOn) {
		this.noteOn = (byte)(noteOn - 256);
	}

	public byte getNoteOff() {
		return noteOff;
	}

	public void setNoteOff(byte noteOff) {
		this.noteOff = (byte)(noteOff - 256);
	}

	public byte getNoteSelected() {
		return noteSelected;
	}

	public void setNoteSelected(byte noteSelected) {
		this.noteSelected = (byte)(noteSelected - 256);
	}

	public byte getVelocity() {
		return Velocity;
	}

	public void setVelocity(byte velocity) {
		Velocity = (byte)(velocity - 256);
	}

	public int getOctave() {
		return octave;
	}

	public void setOctave(int octave) {
		this.octave = (byte)(octave - 256);
	}
	
	public PlayNotes() {
		super();
	}

	
}
