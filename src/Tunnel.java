import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private Semaphore streamPatency;
    public Tunnel(int patency) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        streamPatency =new Semaphore(patency);
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                streamPatency.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                streamPatency.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
