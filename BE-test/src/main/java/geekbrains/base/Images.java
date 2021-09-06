package geekbrains.base;

public enum Images {

    IMAGE_URL_GIF("https://i.gifer.com/9tYn.gif"),
    IMAGE_URL_PNG("https://wonder-day.com/wp-content/uploads/2020/10/wonder-day-among-us-png-48.png"),
    IMAGE_URL_JPG("https://bipbap.ru/wp-content/uploads/2017/04/000000843.jpg");


    private String path;
    Images(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
