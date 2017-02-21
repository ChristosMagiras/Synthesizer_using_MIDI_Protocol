
public class StatusByte {

	public byte getStatusNoteOff1stByte(byte ChannelSelect)
	{
		return (byte)(0x80 + ChannelSelect - 256);
	}
	
	public byte getStatusNoteOn1stByte(byte ChannelSelect)
	{
		return (byte)(0x90 + ChannelSelect - 256);
	}
	
	public byte getStatusPolyphonicAftertouch1stByte(byte ChannelSelect)
	{
		return (byte)(0xa0 + ChannelSelect - 256);
	}
	
	public byte getStatusControl1stByte(Byte ChannelSelect)
	{
		return (byte)(0xb0 + ChannelSelect - 256);
	}
	
	public byte getStatusProgramChange1stByte(Byte ChannelSelect)
	{
		return (byte)(0xc0 + ChannelSelect - 256);
	}
	
	public byte getStatusChannelAftertouch1stByte(byte ChannelSelect)
	{
		return (byte)(0xd + ChannelSelect - 256);
	}
}
