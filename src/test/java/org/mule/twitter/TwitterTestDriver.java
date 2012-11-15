/**
 * Copyright (c) MuleSoft, Inc. All rights reserved. http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.md file.
 */

/**
 * This file was automatically generated by the Mule Cloud Connector Development Kit
 */

package org.mule.twitter;

import org.mule.api.callback.SourceCallback;
import org.mule.tck.AbstractMuleTestCase;
import twitter4j.Status;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class TwitterTestDriver extends AbstractMuleTestCase
{

    private TwitterConnector connector;

    public TwitterTestDriver()
    {
        super();
        setStartContext(true);
    }

    @Override
    protected void doSetUp() throws Exception
    {
        connector = new TwitterConnector();
        connector.setMuleContext(muleContext);
        connector.setConsumerKey(System.getenv("consumerKey"));
        connector.setConsumerSecret(System.getenv("consumerSecret"));
        connector.setAccessKey(System.getenv("accessKey"));
        connector.setAccessSecret(System.getenv("accessSecret"));
        connector.setUseSSL(true);
        connector.init();
    }

    public void testSearch() throws Exception
    {
        assertNotNull(connector.search("world", null, null, 0L, 0, 0, "2012-04-23", 0L,
            "37.781157,-122.398720", "25", "mi", null, "mixed"));
    }

    public void testGetTrends() throws Exception
    {
        assertNotNull(connector.getLocationTrends(1));
    }

    public void testSearchPlaces() throws Exception
    {
        assertNotNull(connector.searchPlaces(50.0, 50.0, null));
    }

    public void testGetUserInfo() throws Exception
    {
        System.out.println(connector.showUser());
    }

    public void testUpdateStatus() throws Exception
    {
        long id = connector.updateStatus("Foo bar baz " + new Date(), -1, null, null).getId();
        assertTrue(connector.showStatus(id).getText().contains("Foo bar baz"));
    }
    
    /** Run only one of those tests per connector instance */
    public void testSampleStream() throws Exception
    {
        connector.sampleStream(new SourceCallback()
        {
            @Override
            public Object process() throws Exception
            {
                return null;
            }

            @Override
            public Void process(Object payload)
            {
                assertNotNull(payload);
                assertTrue(payload instanceof Status);
                System.out.println("Sample: " + payload);
                return null;
            }

            public Object process(Object payload, Map<String, Object> properties) throws Exception
            {
                System.out.println(payload);
                return null;
            }
        });
        Thread.sleep(10000);
    }

    /** Run only one of those tests per connector instance */
    public void ignoreTestFilteredStream() throws Exception
    {
        connector.filteredStream(0, null, Arrays.asList("mulesoft"), new SourceCallback()
        {
            @Override
            public Object process() throws Exception
            {
                return null;
            }

            @Override
            public Void process(Object payload)
            {
                assertNotNull(payload);
                assertTrue(payload instanceof Status);
                System.out.println("Filtered: " + payload);
                return null;
            }

            public Object process(Object payload, Map<String, Object> properties) throws Exception
            {
                return null;
            }
        });
        Thread.sleep(20000);
    }

    /** Run only one of those tests per connector instance */
    public void ignoreTestUserStream() throws Exception
    {
        connector.userStream(null, new SourceCallback()
        {
            @Override
            public Object process() throws Exception
            {
                return null;
            }

            @Override
            public Void process(Object payload)
            {
                assertNotNull(payload);
                assertTrue(payload instanceof UserEvent);
                System.out.println("User: " + payload);
                return null;
            }

            public Object process(Object payload, Map<String, Object> properties) throws Exception
            {
                return null;
            }
        });

        connector.updateStatus("Foobar " + new Date(), -1, null, null);
        Thread.sleep(1000);

        connector.updateStatus("Foobar " + new Date(), -1, null, null);
        Thread.sleep(1000);

        connector.updateStatus("Foobar " + new Date(), -1, null, null);
        Thread.sleep(1000);

        connector.updateStatus("Foobar " + new Date(), -1, null, null);
        Thread.sleep(10000);
    }
}
