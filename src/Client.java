//NETWORK DISCOVERY LOCALLY!!!!
//https://www.developer.com/web-services/dynamic-service-discovery-with-java/


import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Client {
    private static Socket clientSocket;
    public static Scanner scanner = new Scanner(System.in);

    public void join(String name) throws UnknownHostException, IOException {
        System.out.println("establishing connection with host..");
        clientSocket = new Socket("192.168.2.201", 8808);   
        
        DataInputStream in = new DataInputStream(clientSocket.getInputStream());

        play(in);
    }

    public static SourceDataLine getSource() throws LineUnavailableException {
        SourceDataLine.Info info = new SourceDataLine.Info(SourceDataLine.class, Constants.format);
        if (!AudioSystem.isLineSupported(info)) {
            return null;
        }
        return (SourceDataLine) AudioSystem.getLine(info);
    }

    public static void play(DataInputStream inputStream) throws IOException {
        SourceDataLine out;

        try {
            out = getSource();
            out.open();
        } catch (LineUnavailableException e) {
            System.out.println("no available speaker found..");
            out = null;
        }

        // start speaker
        out.start();

        // stream audio from 
        byte[] bufferBytes = new byte[Constants.BUFFER_SIZE];
        while (true) {
           out.write(bufferBytes, 0, inputStream.read(bufferBytes));
        }

        // out.drain();
        // out.close();
    }
}
