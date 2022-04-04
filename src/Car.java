import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static boolean isHaveWinner;
    private static final Lock lock= new ReentrantLock();
    static {
        isHaveWinner = false;
        CARS_COUNT = 0;
    }
	private CyclicBarrier cycb;
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(CyclicBarrier cycb, Race race, int speed) {
	this.cycb =cycb;
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cycb.await();
            cycb.await();


            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }

            lock.lock();
            if(!isHaveWinner){
                System.out.println("Winner: "+name);
                isHaveWinner=true;
            }
            lock.unlock();
            cycb.await();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

