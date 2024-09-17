package learn.domain;

import java.util.ArrayList;

public class Result<T> {
    private ArrayList<String> messages = new ArrayList<>();
    private T payload;

    public boolean isSuccess() {
        return messages.size() == 0;
    }

    public ArrayList<String> getErrorMessages() {
        return new ArrayList<>(messages);
    }

    public void addErrorMessage(String message) {
        messages.add(message);
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?> result = (Result<?>) o;
        return messages.equals(result.messages) && payload.equals(result.payload);
    }

    @Override
    public int hashCode() {
        return payload.hashCode();
    }

}
