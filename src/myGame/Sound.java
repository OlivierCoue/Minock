package myGame;

import java.applet.Applet;
import java.applet.AudioClip;


public class Sound
{
	public static final AudioClip JUMP = Applet.newAudioClip(Sound.class.getResource("jump.wav"));
	public static final AudioClip AMBIENCE = Applet.newAudioClip(Sound.class.getResource("ambience.wav"));
	public static final AudioClip DEAD = Applet.newAudioClip(Sound.class.getResource("dead.wav"));
	public static final AudioClip MENU = Applet.newAudioClip(Sound.class.getResource("menu.wav"));
}
