package net.myacxy.retrotwitch.helpers;

import org.junit.Assert;

public class Lock<T>
{
    public T result;
    private boolean mHasFailed;

    public void await() throws Exception
    {
        synchronized (this) {
            if(mHasFailed) {
                Assert.fail();
            }
        }
    }

    public synchronized void succeed(T result)
    {
        this.result = result;
        notifyAll();
    }

    public synchronized void fail()
    {
        mHasFailed = true;
        notifyAll();
    }
}
