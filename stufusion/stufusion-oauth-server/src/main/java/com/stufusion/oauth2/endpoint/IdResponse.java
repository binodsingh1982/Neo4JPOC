package com.stufusion.oauth2.endpoint;

public class IdResponse {

    private Object Id;

    public IdResponse() {

    }

    public static IdResponse get(Object id) {
        IdResponse generatedId = new IdResponse();
        generatedId.setId(id);
        return generatedId;
    }

    public Object getId() {
        return Id;
    }

    public void setId(Object id) {
        Id = id;
    }

}
