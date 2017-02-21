import java.util.regex.*;


public class StructuralController{
	
	String NotePosition = "[A-G][#|b]?\\d";//"[A-G][#|b]?\\d";
	String FloatNumber = "([0]|([1-9][0-9]*))([.][0]*[1-9][0-9]*)?";//"[0-9]+([.]?[0-9]*)?";//"([0]?|[1-9][0-9]*)([.][0]*[1-9][0-9]*)?";
	String ByteNumber = "([1][0-1][0-9]|[1][2][0-7]|[1-9]?[0-9])";//"[0-9]+";
	String TempoNumber = "([4-9][0-9]|[1-2][0-9][0-9]|(300))";
	String ChooseCombi = "((Combi)\\s*[,]\\s*[B]\\s*[=]\\s*[A-C])";
	String ChooseProg = "((Prog)\\s*[,]\\s*[B]\\s*[=]\\s*([A-G]|(Gd)))";
	String TwoBytesNumber = "(([1][0-6][0-3][0-8][0-3])|([1-9][0-9][0-9][0-9])|([1-9][0-9][0-9])|([1-9][0-9])|([0-9]))";//"[0-9]+";// between 0 and 16383 default 8191
	
	public String GetStructuralController(String input)
	{	
		
		
		//Note pattern 
		 Pattern Note = Pattern.compile("\\s*"+NotePosition+"[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"?\\s*[,]\\s*(Te)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[V]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher NoteMatcher = Note.matcher(input);
		 boolean NoteBoolean = NoteMatcher.matches();
		
		 //Tempo Pattern
		 //Tempo[Ts = 0, S = 50];
		 Pattern Tempo = Pattern.compile("\\s*(Tempo)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+TempoNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher TempoMatcher = Tempo.matcher(input);
		 boolean TempoBoolean = TempoMatcher.matches();
		 
		 Pattern Comments = Pattern.compile("\\s*(//)(.*\\s*)*[;]\\s*");// (\\s*.*\\s*)*(.*\\s*.*) saddas asddsa adsads
		 Matcher CommentsMatcher = Comments.matcher(input);
		 boolean CommentsBoolean = CommentsMatcher.matches();
		 
		 Pattern Space = Pattern.compile("\\s*");
		 Matcher SpaceMatcher = Space.matcher(input);
		 boolean SpaceBoolean = SpaceMatcher.matches();
		 
		 Pattern SoundSelect = Pattern.compile("\\s*(Sound)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*("+ChooseCombi+"|"+ChooseProg+")\\s*[,]\\s*[P]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher SoundSelectMatcher = SoundSelect.matcher(input);
		 boolean SoundSelectBoolean = SoundSelectMatcher.matches();
		 
		 
		 
		 Pattern Arpegiator = Pattern.compile("\\s*(Arpegiator)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*[0-1]\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher ArpegiatorMatcher = Arpegiator.matcher(input);
		 boolean ArpegiatorBoolean = ArpegiatorMatcher.matches();
		 
		 
		 
		 Pattern LPFCuttoff = Pattern.compile("\\s*(Cuttoff)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher LPFCuttoffMatcher = LPFCuttoff.matcher(input);
		 boolean LPFCuttoffBoolean = LPFCuttoffMatcher.matches();
		  
		 Pattern ResonanceHPF = Pattern.compile("\\s*(ResonanceHPF)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher ResonanceHPFMatcher = ResonanceHPF.matcher(input);
		 boolean ResonanceHPFBoolean = ResonanceHPFMatcher.matches();
		 
		 Pattern EgIntensity = Pattern.compile("\\s*(EgIntensity)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher EgIntensityMatcher = EgIntensity.matcher(input);
		 boolean EgIntensityBoolean = EgIntensityMatcher.matches();
		 
		 Pattern FaRelease = Pattern.compile("\\s*(FaRelease)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher FaReleaseMatcher = FaRelease.matcher(input);
		 boolean FaReleaseBoolean = FaReleaseMatcher.matches();
		 
		 
		 Pattern Assignable1 = Pattern.compile("\\s*(Assignable1)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher Assignable1Matcher = Assignable1.matcher(input);
		 boolean Assignable1Boolean = Assignable1Matcher.matches();
		 
		 Pattern Assignable2 = Pattern.compile("\\s*(Assignable2)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher Assignable2Matcher = Assignable2.matcher(input);
		 boolean Assignable2Boolean = Assignable2Matcher.matches();
		 
		 Pattern Assignable3 = Pattern.compile("\\s*(Assignable3)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher Assignable3Matcher = Assignable3.matcher(input);
		 boolean Assignable3Boolean = Assignable3Matcher.matches();
		 
		 Pattern Assignable4 = Pattern.compile("\\s*(Assignable4)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher Assignable4Matcher = Assignable4.matcher(input);
		 boolean Assignable4Boolean = Assignable4Matcher.matches();
		 
		 
		 Pattern ArpGate = Pattern.compile("\\s*(ArpGate)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher ArpGateMatcher = ArpGate.matcher(input);
		 boolean ArpGateBoolean = ArpGateMatcher.matches();
		 
		 Pattern ArpVelocity = Pattern.compile("\\s*(ArpVelocity)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher ArpVelocityMatcher = ArpVelocity.matcher(input);
		 boolean ArpVelocityBoolean = ArpVelocityMatcher.matches();
		 
		 Pattern ArpLength = Pattern.compile("\\s*(ArpLength)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher ArpLengthMatcher = ArpLength.matcher(input);
		 boolean ArpLengthBoolean = ArpLengthMatcher.matches();
		 
		 
		 Pattern Clock = Pattern.compile("\\s*(Clock)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+TempoNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher ClockMatcher = Clock.matcher(input);
		 boolean ClockBoolean = ClockMatcher.matches();
		 
		 Pattern Volume = Pattern.compile("\\s*(Volume)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher VolumeMatcher = Volume.matcher(input);
		 boolean VolumeBoolean = VolumeMatcher.matches();
		 
		 Pattern Pitch = Pattern.compile("\\s*(Pitch)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+TwoBytesNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher PitchMatcher = Pitch.matcher(input);
		 boolean PitchBoolean = PitchMatcher.matches();
		 
		 Pattern Modulation = Pattern.compile("\\s*(Modulation)\\s*[\\[]\\s*(Ts)\\s*[=]\\s*"+FloatNumber+"\\s*[,]\\s*[S]\\s*[=]\\s*"+ByteNumber+"\\s*[,]?\\s*[\\]]\\s*[;]\\s*");
		 Matcher ModulationMatcher = Modulation.matcher(input);
		 boolean ModulationBoolean = ModulationMatcher.matches();
		 
		if(NoteBoolean == true)
			return "NOTE";
		else if(TempoBoolean == true)
			return "TEMPO";
		else if(SoundSelectBoolean == true)
			return "SOUNDSELECT";
		else if(ArpegiatorBoolean == true)
			return "ARPEGIATOR";
		else if(LPFCuttoffBoolean == true)
			return "LPFCUTTOFF";
		else if(ResonanceHPFBoolean == true)
			return "RESONANCEHPF";
		else if(EgIntensityBoolean == true)
			return "EGINTENSITY";
		else if(FaReleaseBoolean == true)
			return "FARELEASE";
		else if(Assignable1Boolean == true)
			return "ASSIGNABLE1";
		else if(Assignable2Boolean == true)
			return "ASSIGNABLE2";
		else if(Assignable3Boolean == true)
			return "ASSIGNABLE3";
		else if(Assignable4Boolean == true)
			return "ASSIGNABLE4";
		else if(ArpGateBoolean == true)
			return "ARPGATE";
		else if(ArpVelocityBoolean == true)
			return "ARPVELOCITY";
		else if(ArpLengthBoolean == true)
			return "ARPLENGTH";
		else if(ClockBoolean == true)
			return "CLOCK";
		else if(VolumeBoolean == true)
			return "VOLUME";
		else if(PitchBoolean == true)
			return "PITCH";
		else if(ModulationBoolean == true)
			return "MODULATION";
		else if(CommentsBoolean == true)
			return "COMMENTS";
		else if(SpaceBoolean == true)
			return "SPACE";
		else
			return "ERROR";

		
		
		
	}
	
	public byte GetNotePosition(String input)//Cb5
	{
		byte NotePosition = (byte)0;
		Pattern GetNotePosition = Pattern.compile(this.NotePosition);
		Matcher GetNotePositionMatcher = GetNotePosition.matcher(input);
		//System.out.println((int)GetNotePositionMatcher.group(1).charAt(0));
		if(GetNotePositionMatcher.find())
		{
			
			if((int)GetNotePositionMatcher.group(0).charAt(0) == 67 || (int)GetNotePositionMatcher.group(0).charAt(0) == 68 || (int)GetNotePositionMatcher.group(0).charAt(0) == 69)
				NotePosition = (byte) (((int)GetNotePositionMatcher.group(0).charAt(0) - 67)* 2);
			else if((int)GetNotePositionMatcher.group(0).charAt(0) == 70 || (int)GetNotePositionMatcher.group(0).charAt(0) == 71)
				NotePosition = (byte) ((((int)GetNotePositionMatcher.group(0).charAt(0) - 68)* 2) + 1);
			else if((int)GetNotePositionMatcher.group(0).charAt(0) == 65 || (int)GetNotePositionMatcher.group(0).charAt(0) == 66)
				NotePosition = (byte) ((((int)GetNotePositionMatcher.group(0).charAt(0) - 61)* 2) + 1);
			
			
			if(GetNotePositionMatcher.group(0).charAt(1) == '#')
				NotePosition++;
			else if(GetNotePositionMatcher.group(0).charAt(1) == 'b')
				NotePosition--;	
			
			if((int)GetNotePositionMatcher.group(0).charAt(1) >= 48 && (int)GetNotePositionMatcher.group(0).charAt(1) <= 57)
			{
				NotePosition = (byte) (((12 * ((int)GetNotePositionMatcher.group(0).charAt(1) - 48)) + NotePosition));
				return NotePosition;
			}
			else if((int)GetNotePositionMatcher.group(0).charAt(2) >= 48 && (int)GetNotePositionMatcher.group(0).charAt(2) <= 57)
			
				NotePosition = (byte) (((12 * ((int)GetNotePositionMatcher.group(0).charAt(2) - 48)) + NotePosition));
			return NotePosition;
		}
		else
		return (byte)-10;
		
	}
	
	
	public float GetNumber(String input, int Repeat)
	{
		Pattern GetFloatNumber = Pattern.compile(FloatNumber);
		Matcher GetFloatNumberMatcher = GetFloatNumber.matcher(input);
		for (int i = 0; i <= Repeat; i++)
		{
			GetFloatNumberMatcher.find();
		}
		return Float.parseFloat(GetFloatNumberMatcher.group(0));
	}
	
	public String GetString(String input, int Repeat)
	{
		Pattern getString = Pattern.compile("[A-Za-z]+");
		Matcher GetStringMatcher = getString.matcher(input);
		for (int i = 0; i <= Repeat; i++)
		{
			GetStringMatcher.find();
		}
		return GetStringMatcher.group(0);
	}
	
	
}
