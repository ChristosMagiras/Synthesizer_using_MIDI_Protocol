import java.nio.ByteBuffer;

public class MidiCommunication {

	
	public native void GetDeviceName(); //The C Function
	static
	{
		System.load("/home/tito/workspace/Synthesizer_using_MIDI_Protocol/bin/libMidiCommunication.so"); // The C Program
	}
	
	public native void SendMidiMessage(byte[] ByteArray); //The C Function
	static
	{
		System.load("/home/tito/workspace/Synthesizer_using_MIDI_Protocol/bin/libMidiCommunication.so"); // The C Program
	}
	
	public native void SendTempo(long tempo);
	static
	{
		System.load("/home/tito/workspace/Synthesizer_using_MIDI_Protocol/bin/libMidiCommunication.so"); // The C Program
	}
	
	public native String GetSoundName(byte ChooseCombiOrProg);
	static
	{
		System.load("/home/tito/workspace/Synthesizer_using_MIDI_Protocol/bin/libMidiCommunication.so"); // The C Program
	}
	
	public native void OpenDeviceForWrite();
	static
	{
		System.load("/home/tito/workspace/Synthesizer_using_MIDI_Protocol/bin/libMidiCommunication.so"); // The C Program
	}
	
	public native void OpenDeviceForRead();
	static
	{
		System.load("/home/tito/workspace/Synthesizer_using_MIDI_Protocol/bin/libMidiCommunication.so"); // The C Program
	}
	
	public native void CloseDeviceForWrite();
	static
	{
		System.load("/home/tito/workspace/Synthesizer_using_MIDI_Protocol/bin/libMidiCommunication.so"); // The C Program
	}
	
	public native void CloseDeviceForRead();
	static
	{
		System.load("/home/tito/workspace/Synthesizer_using_MIDI_Protocol/bin/libMidiCommunication.so"); // The C Program
	}
	
	public MidiCommunication() {
		super();
	}
}
