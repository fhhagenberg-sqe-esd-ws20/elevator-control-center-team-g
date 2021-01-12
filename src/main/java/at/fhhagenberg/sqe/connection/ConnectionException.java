package at.fhhagenberg.sqe.connection;

@SuppressWarnings("serial")
public class ConnectionException extends RuntimeException{
    public ConnectionException(String e) {
        super(e);
    }
}
