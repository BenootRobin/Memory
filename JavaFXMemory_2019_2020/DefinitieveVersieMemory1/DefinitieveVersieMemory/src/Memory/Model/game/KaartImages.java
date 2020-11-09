package Memory.Model.game;

//Foto's voor kaarten

public enum KaartImages {
    IMAGE1("Kaart1.jpg"),
    IMAGE2("Kaart2.jpg"),
    IMAGE3("Kaart3.jpg"),
    IMAGE4("Kaart4.jpg"),
    IMAGE5("Kaart5.jpg"),
    IMAGE6("Kaart6.jpg"),
    IMAGE7("Kaart7.jpg"),
    IMAGE8("Kaart8.jpg"),
    IMAGE9("Kaart9.jpg"),
    IMAGE10("Kaart10.jpg"),
    IMAGE11("Kaart11.jpg"),
    IMAGE12("Kaart12.jpg"),
    IMAGE13("Kaart13.jpg"),
    IMAGE14("Kaart14.jpg"),
    IMAGE15("Kaart15.jpg"),
    IMAGE16("Kaart16.jpg"),
    IMAGE17("Kaart17.jpg"),
    IMAGE18("Kaart18.jpg"),
    DEFAULT("defaultback.png"),
    ;

    private final String path;

    KaartImages(String path) {
        this.path = path;
    }

    public String getPath() {
        return "images/" + path;
    }
}
