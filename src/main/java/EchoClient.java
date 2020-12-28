/*
 * @Author : Dakshesh Patel
 * Intro Socket
 * Basic Connection between Client and Server
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.InputStreamReader;

public class EchoClient {

    public static void main(String[] args) throws IOException {
        Socket client = null;
        String ip, input_message;
        int port;

        //get IP from args[0]
        ip = args[0];

        //get port from args[1] and convert to a number
        port = Integer.parseInt(args[1]);


        //Interface
        System.out.println("----------------------------------------------------------");
        System.out.println("----------------------Echo Client-------------------------");
        System.out.println("----------------------------------------------------------");


        try {
            //fetch I/O streams from the socket
            client = new Socket(ip, port);
            System.out.println("Client is successfully connected to the server.");
            System.out.println("----------------------------------------------------------");

            //read  a line from the server
            InputStreamReader socket_reader = new InputStreamReader(client.getInputStream());
            BufferedReader in = new BufferedReader(socket_reader);
            String output_message = in.readLine();

            //write the line to System.out
            System.out.println("Message From the Server : " + output_message);


            System.out.println("Write a Message and Press Enter to Send it to Server : ");
            InputStreamReader input_reader = new InputStreamReader(System.in);
            BufferedReader userInput = new BufferedReader(input_reader);

            //read a line in from the user
            input_message = userInput.readLine();

            //send the line to the server
            PrintWriter out = new PrintWriter(client.getOutputStream());
            out.println(input_message);
            out.flush();

            //close everything
            in.close();
            out.close();
            client.close();

            System.out.println("----------------------------------------------------------");
            System.out.println("Connection is Successfully closed!");
            System.out.println("----------------------------------------------------------");

        } catch (Exception e) {

            e.printStackTrace();
        }











    }

}