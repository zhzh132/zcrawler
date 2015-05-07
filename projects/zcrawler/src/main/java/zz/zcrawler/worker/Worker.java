package zz.zcrawler.worker;

public interface Worker {

	public static final byte WAITING = 0;
	public static final byte WORKING = 1;
	public static final byte DEAD = 2;
	
	String getName();
	byte getStatus();
	boolean isWaitingOrDead();
}
