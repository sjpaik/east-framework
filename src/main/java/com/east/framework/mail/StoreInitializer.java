package com.east.framework.mail;

import java.util.Properties;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 * javax.mail.Store Initializer.
 *
 * Updated on : 2016-10-06 Updated by : love.
 */
public class StoreInitializer {

    private Store store;

    private Session session;

    /**
     * 생성자.
     * 
     * @param props
     *            Properties
     */
    public StoreInitializer(Properties props) {
        try {
            session = Session.getDefaultInstance(props, null);
            store = session.getStore(props.getProperty("mail.store.protocol"));
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

    }

    /**
     * @return the store
     */
    public Session getSession() {
        return session;
    }

    /**
     * @return the store
     */
    @Deprecated
    public Store getStore() {
        return store;
    }

}
