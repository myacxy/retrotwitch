package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.models.BaseModel;
import net.myacxy.retrotwitch.models.UserFollow;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public abstract class BaseTest
{
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
}
