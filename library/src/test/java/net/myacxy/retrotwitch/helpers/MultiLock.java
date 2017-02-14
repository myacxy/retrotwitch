package net.myacxy.retrotwitch.helpers;

import net.myacxy.retrotwitch.v3.models.BaseModel;

import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MultiLock
{
    private final CountDownLatch latch;

    public HashMap<String, List<? extends BaseModel>> multiResults = new HashMap<>();
    public HashMap<String, ? super BaseModel> singleResults = new HashMap<>();
    private boolean mHasFailed;

    public MultiLock(int count)
    {
        latch = new CountDownLatch(count);
    }

    public void await() throws Exception
    {
        latch.await();
        if(mHasFailed) {
            Assert.fail();
        }
    }

    public synchronized  <M extends BaseModel> void succeed(String key, M result)
    {
        singleResults.put(key, result);
        latch.countDown();
    }

    public synchronized void succeed(String key, List<? extends BaseModel> result)
    {
        multiResults.put(key, result);
        latch.countDown();
    }

    public synchronized void fail()
    {
        mHasFailed = true;
        latch.notifyAll();
    }

    public <T extends BaseModel> T getSingleResult(String key, Class<T> clazz)
    {
        return clazz.cast(singleResults.get(key));
    }

    public <T extends List<? extends BaseModel>> T getMultiResult(String key, Class<T> clazz)
    {
        return clazz.cast(multiResults.get(key));
    }
}
