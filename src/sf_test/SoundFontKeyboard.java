package sf_test;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.swing.JFrame;

public class SoundFontKeyboard {

    private static Synthesizer synth;
    private static MidiChannel channel;

    public static void main(String[] args) {
        try {
            // 1️⃣ Load Synthesizer
            synth = MidiSystem.getSynthesizer();
            synth.open();

            // 2️⃣ Load SoundFont file
            File sf2File = new File("sf/akai_steinway.sf2");
            Soundbank sb = MidiSystem.getSoundbank(sf2File);

            if (synth.isSoundbankSupported(sb)) {
                synth.loadAllInstruments(sb);
                System.out.println("✅ SoundFont loaded: " + sf2File.getName());
            } else {
                System.out.println("❌ SoundFont not supported!");
            }

            // 3️⃣ Get first available MIDI channel
            channel = synth.getChannels()[0];
            channel.programChange(0); // piano by default

            // 4️⃣ Create a small key listener window
            JFrame frame = new JFrame("Java SoundFont Keyboard 🎹");
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    playKey(e.getKeyChar());
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    stopKey(e.getKeyChar());
                }
            });

            frame.setVisible(true);
            frame.requestFocus();

            System.out.println("Press A S D F G H J to play notes.");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void playKey(char key) {
        int note = -1;
        switch (Character.toLowerCase(key)) {
            case 'a': note = 60; break; // C4
            case 's': note = 62; break; // D4
            case 'd': note = 64; break; // E4
            case 'f': note = 65; break; // F4
            case 'g': note = 67; break; // G4
            case 'h': note = 69; break; // A4
            case 'j': note = 71; break; // B4
        }

        if (note >= 0) {
            channel.noteOn(note, 100);
            System.out.println("Note On: " + note);
        }
    }

    private static void stopKey(char key) {
        int note = -1;
        switch (Character.toLowerCase(key)) {
            case 'a': note = 60; break;
            case 's': note = 62; break;
            case 'd': note = 64; break;
            case 'f': note = 65; break;
            case 'g': note = 67; break;
            case 'h': note = 69; break;
            case 'j': note = 71; break;
        }

        if (note >= 0) {
            channel.noteOff(note);
            System.out.println("Note Off: " + note);
        }
    }
}
