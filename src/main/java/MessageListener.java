import java.util.Scanner;

/*
* Message Listener
* While we handle both user input and messages in a client, we want to ensure that the messages coming from the server
* are handled in real time and are not dependent on the user having to enter a choice to move onto the ``z``z`          z           Z
*/
public class MessageListener extends Thread {

    public Scanner serverInput;
    public String serverResponse;

    public MessageListener(Scanner serverInput) {
        this.serverInput = serverInput;
        this.serverResponse = "";
    }

    public void run() {

        do {
            if(serverInput.hasNext()) {
                // Get the response
                this.serverResponse = serverInput.nextLine();
            }

            // Print it if it doesn't equal to the end of the block
            if(!this.serverResponse.equals("endblock")) {
                System.out.println(this.serverResponse);
            }
        } while(!this.serverResponse.equals("leave"));
    }
}
