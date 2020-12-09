package pl.marcisz.patryk.iss;

public class Main {
    public static void main(String[] args) {
        ISSHttpClient client = new ISSHttpClient();
        double speedOfIss = client.getSpeedOfIss();
        System.out.println(speedOfIss);
    }
}
