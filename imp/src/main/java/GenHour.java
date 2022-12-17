import java.util.Random;

public class GenHour {
    Random random = new Random();
    //класс для задания мощности за час для различных генераций
    public double PowHour(String agent, int time) {
        double pow = 0;
        double power;
        switch (agent) {
            case "Wind": {
                power = 13.2;
                pow = Math.abs(random.nextGaussian() * power);
                break;
            }
            case "Sun": {
                power = 13;
                pow = Math.abs(((Math.random() / 10 + 0.95) * powSun(time)) * power);
                break;
            }
            case "System": {
                power = 2000100;
                pow = power;
                break;
            }
            case "Heat": {
                power = 10;
                pow = power;
                break;
            }

        }
        return pow;
    }

    public double powSun(int time) {
        double powSun = 0;
        switch (time) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 21:
            case 22:
            case 23: {
                powSun = 0;
                break;
            }
            case 6: {
                powSun = 0.011535;
                break;
            }
            case 7: {
                powSun = 0.150981;
                break;
            }
            case 8: {
                powSun = 0.315325;
                break;
            }
            case 9: {
                powSun = 0.582477;
                break;
            }
            case 10: {
                powSun = 0.841229;
                break;
            }
            case 11: {
                powSun = 0.901314;
                break;
            }
            case 12: {
                powSun = 0.959698;
                break;
            }
            case 13: {
                powSun = 1;
                break;
            }
            case 14: {
                powSun = 0.927984;
                break;
            }
            case 15: {
                powSun = 0.830592;
                break;
            }
            case 16: {
                powSun = 0.701098;
                break;
            }
            case 17: {
                powSun = 0.510814;
                break;
            }
            case 18: {
                powSun = 0.265485;
                break;
            }
            case 19: {
                powSun = 0.073026;
                break;
            }
            case 20: {
                powSun = 0.008437;
                break;
            }
        }
        return powSun;
    }
}
