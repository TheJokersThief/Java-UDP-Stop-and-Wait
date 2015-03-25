/**
 * A UDP Server
 * @author Evan Smith (ID 113300626)
 */

import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;

class UDPStopAndWaitServer{
	private static final int BUFFER_SIZE = 1024;
	private static final int PORT = 6789;

	public static void main(String[] args) throws IOException {
		// Create a server socket
		DatagramSocket serverSocket = new DatagramSocket( PORT );

		// Set up byte arrays for sending/receiving data
        byte[] receiveData = new byte[ BUFFER_SIZE ];
        byte[] dataForSend = new byte[ BUFFER_SIZE ];

        // Infinite loop to check for connections 
        while(true){

        	// Get the received packet
        	DatagramPacket received = new DatagramPacket( receiveData, receiveData.length );
          	serverSocket.receive( received );

          	// Get the message from the packet
          	int message = ByteBuffer.wrap(received.getData( )).getInt();

            Random random = new Random( );
            int chance = random.nextInt( 100 );

            // 1 in 2 chance of responding to the message
            if( ((chance % 2) == 0) ){
              System.out.println("FROM CLIENT: " + message);

              // Get packet's IP and port
              InetAddress IPAddress = received.getAddress();
              int port = received.getPort();

              // Convert message to uppercase 
              dataForSend = ByteBuffer.allocate(4).putInt( message ).array();

              // Send the packet data back to the client
              DatagramPacket packet = new DatagramPacket( dataForSend, dataForSend.length, IPAddress, port );
              serverSocket.send( packet ); 
            } else {
              System.out.println( "Oops, packet with sequence number "+ message + " was dropped");
            }
       	}
	}
}