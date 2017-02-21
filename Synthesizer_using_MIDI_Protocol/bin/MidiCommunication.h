/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class MidiCommunication */

#ifndef _Included_MidiCommunication
#define _Included_MidiCommunication
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     MidiCommunication
 * Method:    GetDeviceName
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_MidiCommunication_GetDeviceName
  (JNIEnv *, jobject);

/*
 * Class:     MidiCommunication
 * Method:    SendMidiMessage
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_MidiCommunication_SendMidiMessage
  (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     MidiCommunication
 * Method:    SendTempo
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_MidiCommunication_SendTempo
  (JNIEnv *, jobject, jlong);

/*
 * Class:     MidiCommunication
 * Method:    GetSoundName
 * Signature: (B)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_MidiCommunication_GetSoundName
  (JNIEnv *, jobject, jbyte);

/*
 * Class:     MidiCommunication
 * Method:    OpenDeviceForWrite
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_MidiCommunication_OpenDeviceForWrite
  (JNIEnv *, jobject);

/*
 * Class:     MidiCommunication
 * Method:    OpenDeviceForRead
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_MidiCommunication_OpenDeviceForRead
  (JNIEnv *, jobject);

/*
 * Class:     MidiCommunication
 * Method:    CloseDeviceForWrite
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_MidiCommunication_CloseDeviceForWrite
  (JNIEnv *, jobject);

/*
 * Class:     MidiCommunication
 * Method:    CloseDeviceForRead
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_MidiCommunication_CloseDeviceForRead
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
