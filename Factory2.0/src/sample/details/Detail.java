package sample.details;

public abstract class Detail {
    public String getId() {
        return String.valueOf(hashCode());
    }
}
