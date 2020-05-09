package Common;

public class Logger {
    public static void Log(String message){
        System.out.print("Application ID: "+ProcessHandle.current().pid()+"\t");
        System.out.println("Message: " + message);
    }

    public static void Log(Request request){
        System.out.print("Request Type: "+request.getRequestType().toString()+"\t");
        if(request.getUsername()!=null)
            System.out.print("Request Username: "+request.getUsername()+"\t");
        System.out.println("Request Message: "+request.getRequestMessage());
    }
}
