#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "MidiCommunication.h"
#include <linux/soundcard.h>
#include <unistd.h>
#include <fcntl.h>
#include <ctype.h>	
#include <jni.h>
#include <pthread.h>

char *device; //select the name of midi device
int fd; //the integer to open the device for read


JNIEXPORT void JNICALL Java_MidiCommunication_GetDeviceName(JNIEnv *env, jobject obj)
{
		FILE *getDirection;//the file to use the terminal command "find"
   getDirection = popen("/usr/bin/find /dev/ -name midi[0-9]*", "r");//the command to use a terminal command (we use find in our case)
   if (getDirection == NULL) {
    printf("Unable to find Midi device\n");
    exit(1);
  }
	device = malloc(12 * sizeof(char));//12 is the length of the output of find terminal command ("\n" character equals to 2 chars on terminal)
	
   while (fgets(device, 12, getDirection) != NULL);//put the output of the terminal command to pointer char device
   
   device[(int)strlen(device)-1] = '\0';//declare last array of device to final character (so we can take off the New Line character)
}


JNIEXPORT void JNICALL Java_MidiCommunication_OpenDeviceForWrite(JNIEnv *env, jobject obj)
{
   
   
   // step 1: open the OSS device for writing
	   fd = open(device, O_WRONLY, 0);//establish the connection between the file and a file descriptor (in our case the file desriptor is the char * device)
	   if (fd < 0) {//if the open function returns a negative number (can't find the file desriptor)
		  printf("Error: cannot open %s\n", device);
		  exit(1);
	   }
	   
	   printf("Device opened for write\n");
   
}




JNIEXPORT void JNICALL Java_MidiCommunication_CloseDeviceForWrite(JNIEnv *env, jobject obj)
{
	close(fd);//close the device for write
}

FILE *fp;//file pointer to open the device for read
pthread_t readdevicethread;
int rcreaddevice;
void *statusreaddevice;

void *Open_Device_For_Read()
{
	/* opening file for reading */
	printf("Opening device for read\n");
   fp = fopen(device , "r+");
   if(fp == NULL) 
   {
      perror("Error opening file");
      pthread_exit(NULL);
   }
   printf("Device opened for read\n");
   printf("Pointer of fp: %p \n", fp);
   pthread_exit(NULL);
   	
}

JNIEXPORT void JNICALL Java_MidiCommunication_OpenDeviceForRead(JNIEnv *env, jobject obj)
{
   
   rcreaddevice = pthread_create(&readdevicethread, NULL, Open_Device_For_Read, NULL);
}

JNIEXPORT void JNICALL Java_MidiCommunication_CloseDeviceForRead(JNIEnv *env, jobject obj)
{
	printf("Device trying to close for read \n");
	fclose(fp);
	printf("Device closed for read\n");
	/*if(fp != NULL)
	{	
		fclose(fp);
		printf("Device closed for read\n");
	}
	else
	{
		printf("Error closing device for read\n");
	}*/
}


JNIEXPORT void JNICALL Java_MidiCommunication_SendMidiMessage(JNIEnv *env, jobject obj, jbyteArray byteArray)
{
   
   
	
	
	unsigned char *data;
	data = malloc((*env)->GetArrayLength(env, byteArray) * sizeof(char));
	data = (*env)->GetByteArrayElements(env, byteArray, NULL);
	if (data == NULL) return;
	
	 
	   //write the MIDI information to the OSS device
	   write(fd, data, (*env)->GetArrayLength(env, byteArray));
	   //(*env)->ReleaseStringUTFChars(string, data, 0);
	   (*env)->ReleaseByteArrayElements(env, byteArray, data, JNI_ABORT);
	   
	   return;
	
}

pthread_t tempothread;
unsigned long globaltempo = 0;
int rcthread = -1;
void *statusthread;

void *Send_Midi_Tempo()
{
   
   unsigned char data[1] = {0xf8};
	   
	while(globaltempo != 0)
	{
		write(fd, data, 1);
		usleep(globaltempo);
	}
	pthread_exit(NULL);
}


void Change_Midi_Tempo(unsigned long tempo)
{
	if(tempo == 0)
	{
		globaltempo = 0;
	}
	else
	{
		globaltempo = ((unsigned long)1000000 / ((tempo*24) / 60));
	//	printf("usleep is : %ld \n", globaltempo);DEBUG
	}
}

JNIEXPORT void JNICALL Java_MidiCommunication_SendTempo (JNIEnv *env, jobject obj, jlong Jtempo)
{
	long tempo = (long)Jtempo;
	Change_Midi_Tempo(0);

	if(rcthread == 0)
			rcthread = pthread_join(tempothread, &statusthread);
		
			Change_Midi_Tempo(tempo);
		rcthread = pthread_create(&tempothread, NULL, Send_Midi_Tempo, NULL);
}

	char SoundName[16];
	
	pthread_t soundnamethread;
	
	int rcsoundname;
	void *statusSoundName;
	
	char buffer[26];
	
	pthread_t soundnamethreadSend;
	int rcsoundnameSend;
	void *statusSoundNameSend;
	
void *Send_Sound_Name(void *JChooseBetweenCombiOrProg)
{
	char data[7] = {0xF0, 0x42, 0x30, 0x7A, (char)JChooseBetweenCombiOrProg, 0x00, 0xF7};
		 write(fd, data, 7);
		 	pthread_exit(NULL);
}
	
void *Get_Sound_Name()
{
	printf("EDW1\n");
	//for(int i = 0; i< 26; i++)
		//buffer[i] = fgetc(fp);
	fgets (buffer, 26, fp);	
	
	printf("EDW2\n");
	/*for(int i = 7; i < 26; i++)
		{
			if(buffer[i] <= 0x20 || buffer[i] >= 0x7F)
			{
				//printf("Error on Name\n");
				return NULL;
			}
		}
	*/
	int j = 0;
	int i = 0;
	

	printf("buffer[15]%c\n", buffer[15]);
	
	printf("buffer[7]%c\n", buffer[7]);
	
	printf("buffer[6] %hhX \n", buffer[6]);
	//fgets (str, 26, fp);
  printf("Edw3\n");
   

   while(fgetc(fp) != 0xf7);
   printf("EDW4\n");
 	for(i = 7; i < 26; i++)
   {
	  if(i == 14 || i == 22)
	  	printf("Found 14 or 22\n");
		
	else
		//printf("[%d]%c\n", i, buffer[i]);
		{
			SoundName[j] = buffer[i];
			j++;
		}
		//printf("i is : %d \n", i);
		//printf("buffer[%d] is : %hhX \n", i , buffer[i]);
		//printf("buffer[%d] is : %c \n", i , buffer[i]);
		
   }
   printf("\n");
   	printf("final i is : %d \n", i);
	pthread_exit(NULL);
}	

JNIEXPORT jstring JNICALL Java_MidiCommunication_GetSoundName (JNIEnv *env, jobject obj, jbyte JChooseBetweenCombiOrProg)
{

	char data[7] = {0xF0, 0x42, 0x30, 0x7A, (unsigned char)JChooseBetweenCombiOrProg, 0x00, 0xF7};
	
	 write(fd, data, 7);
	 printf("EDW11\n");
	
	// rcsoundnameSend = pthread_create(&soundnamethreadSend, NULL, Send_Sound_Name, JChooseBetweenCombiOrProg);
	//if(rcreaddevice == 0)
	//	rcreaddevice = pthread_join(readdevicethread, &statusreaddevice);
	if(fp != NULL)
	{
		rcsoundname = pthread_create(&soundnamethread, NULL, Get_Sound_Name, NULL);
	}
	else
	{
		printf("Cannot find fp\n");
	}
//	if(rcsoundname == 0)	
		rcsoundname = pthread_join(soundnamethread, &statusSoundName);
	
	//(*env)->NewStringUTF(env, SoundName)
	
   return (*env)->NewStringUTF(env, SoundName);
   
}
