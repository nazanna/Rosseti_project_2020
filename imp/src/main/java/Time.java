public class Time {
    public int minute = 300;
    public int day=1;
    public long start = System.currentTimeMillis();
    public int currentTime;
    //класс для работы с time
    public int getCurrentTime() {
        currentTime = (int) ((System.currentTimeMillis() - start) / minute);
        if (currentTime >= 24*60) {
            day++;
            start = System.currentTimeMillis();
            currentTime = (int) ((System.currentTimeMillis() - start) / minute);
        }

        return currentTime;
    }
}
