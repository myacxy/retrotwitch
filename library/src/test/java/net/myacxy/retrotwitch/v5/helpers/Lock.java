package net.myacxy.retrotwitch.v5.helpers;

import org.junit.Assert;

public class Lock<T>
{
    public T result;
    private boolean mHasFailed;

    public synchronized void await() throws Exception
    {
        wait();
        if(mHasFailed) {
            Assert.fail();
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
