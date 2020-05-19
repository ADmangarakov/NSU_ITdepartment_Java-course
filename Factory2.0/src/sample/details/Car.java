package sample.details;

public class Car extends Detail {
    private final Engine engine;
    private final Body body;
    private final Accessory accessory;

    public Car(Engine engine, Body body, Accessory accessory) {
        this.engine = engine;
        this.body = body;
        this.accessory = accessory;
    }

    @Override
    public String getId() {
        return hashCode() + "(Body:" + body.getId() + ", Motor:" + engine.getId() + ", Accessory:" + accessory.getId() + ")\n";
    }
}
