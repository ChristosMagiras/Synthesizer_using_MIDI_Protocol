
public class ByteArrayAndTimeStruct {
	
		private byte[] MidiCommandsOneLine;
		private int TstartOrEnd;
		private int StatusOfEditor;
		
		public byte[] getMidiCommandsOneLine() {
			return MidiCommandsOneLine;
		}
		public void setMidiCommandsOneLine(byte[] midiCommandsOneLine) {
			MidiCommandsOneLine = midiCommandsOneLine;
		}
		public int getTstartOrEnd() {
			return TstartOrEnd;
		}
		public void setTstartOrEnd(float tstartOrEnd) {
			tstartOrEnd = (float)(tstartOrEnd * 1000);
			TstartOrEnd = (int)tstartOrEnd;
		}
		public int getStatusOfEditor() {
			return StatusOfEditor;
		}
		public void setStatusOfEditor(int statusOfEditor) {
			StatusOfEditor = statusOfEditor;
		}
		public ByteArrayAndTimeStruct() {
			super();
		}
		
		

}
