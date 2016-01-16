package net.myacxy.retrotwitch;

public abstract class BaseTest
{

    public class Lock
    {
        public synchronized void await() throws Exception
        {
            wait();
        }

        public synchronized void release()
        {
            notifyAll();
        }
    }
}
