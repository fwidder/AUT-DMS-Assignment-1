package florianWidder.StudentID18999061.assesment1.shared.model;

public class ResponseMessage extends RequestMessage {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Object Response;

    public Object getResponse() {
	return Response;
    }

    public void setResponse(final Object response) {
	Response = response;
    }

}
