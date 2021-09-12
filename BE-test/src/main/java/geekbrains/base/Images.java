package geekbrains.base;

public enum Images {

    IMAGE_URL_GIF("https://i.gifer.com/9tYn.gif"),
    IMAGE_URL_PNG("https://wonder-day.com/wp-content/uploads/2020/10/wonder-day-among-us-png-48.png"),
    IMAGE_URL_JPG("https://bipbap.ru/wp-content/uploads/2017/04/000000843.jpg"),
    VIDEO("src/test/resources/video.mp4"),

//    TEST_JPG("src/test/resources/image.jpg", "image/jpeg"),
//    TEST_BMP("src/test/resources/imagefor.bmp", "image/bmp");

    MORE_10Mb("src/test/resources/More10Mb.jpg"),
    TEST_JPG("src/test/resources/image.jpg"),
    TEST_BMP("src/test/resources/imagefor.bmp"),
    TEST_GIF("src/test/resources/forgif.gif"),
    BEFORE_10Mb("src/test/resources/before10Mb.jpg"),
    TEXT("src/test/resources/text.txt"),
    ONE_TO_ONE("src/test/resources/OnetoOne.jpg");




    private String path;
//    private String format;
    Images(String path) {
        this.path = path;
//        this.format = format;
    }

    public String getPath() {
        return path;
    }
//    public String getFormat() {
//        return format;
//    }
}
